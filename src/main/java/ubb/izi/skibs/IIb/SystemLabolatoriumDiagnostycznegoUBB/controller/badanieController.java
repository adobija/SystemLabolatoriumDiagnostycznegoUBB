package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.BadanieRodzaj;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Pacjent;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Uzytkownik;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.WynikBadania;

import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.BadanieRodzajRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.PacjentRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.UzytkownikRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.WynikiRepo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class badanieController {

    private final PacjentRepo pacjentRepo;
    private final BadanieRodzajRepo badanieRodzajRepo;
    private final WynikiRepo wynikBadaniaRepo;
    private final UzytkownikRepo uzytkownikRepo;

    public badanieController(
            PacjentRepo pacjentRepo,
            BadanieRodzajRepo badanieRodzajRepo,
            WynikiRepo wynikBadaniaRepo,
            UzytkownikRepo uzytkownikRepo) {

        this.pacjentRepo = pacjentRepo;
        this.badanieRodzajRepo = badanieRodzajRepo;
        this.wynikBadaniaRepo = wynikBadaniaRepo;
        this.uzytkownikRepo = uzytkownikRepo;
    }

    @GetMapping("/dodaj-badanie")
    public String pokazFormularzBadania(Model model) {

        model.addAttribute("pacjenci", pacjentRepo.findAll());
        model.addAttribute("badania", badanieRodzajRepo.findAll());

        return "Template/dodaj-badanie";
    }

    @PostMapping("/dodaj-badanie")
    public String zapiszWyniki(
            @RequestParam Long pacjentId,
            @RequestParam List<Long> badanieIds,
            @RequestParam Map<String, String> wszystkiePola,
            @RequestParam(required = false) String komentarzDiagnosty,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        Pacjent pacjent = pacjentRepo.findById(pacjentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Nie znaleziono pacjenta o ID: " + pacjentId
                        )
                );

        Uzytkownik wykonujacy = uzytkownikRepo
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Nie znaleziono zalogowanego użytkownika."
                        )
                );

        for (Long badanieId : badanieIds) {

            BadanieRodzaj rodzajBadania =
                    badanieRodzajRepo.findById(badanieId)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Nie znaleziono badania o ID: "
                                                    + badanieId
                                    )
                            );

            String wartoscPola =
                    wszystkiePola.get("wynik_" + badanieId);

            String komentarzWyniku =
                    wszystkiePola.get("komentarz_" + badanieId);

            if (wartoscPola == null || wartoscPola.isBlank()) {
                continue;
            }

            BigDecimal wartoscLiczbowa;

            try {
                wartoscLiczbowa = new BigDecimal(
                        wartoscPola.replace(",", ".")
                );
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException(
                        "Nieprawidłowa wartość wyniku dla badania: "
                                + rodzajBadania.getNazwa()
                );
            }

            boolean nieprawidlowy =
                    czyWynikNieprawidlowy(
                            wartoscLiczbowa,
                            rodzajBadania
                    );

            WynikBadania wynik = new WynikBadania();

            wynik.setPacjent(pacjent);
            wynik.setBadanieRodzaj(rodzajBadania);
            wynik.setWartoscLiczbowa(wartoscLiczbowa);
            wynik.setJednostka(rodzajBadania.getJednostka());
            wynik.setWartoscOpisowa(komentarzWyniku);
            wynik.setKomentarzDiagnosty(komentarzDiagnosty);
            wynik.setFlagaNieprawidlowy(nieprawidlowy);
            wynik.setDataWykonania(LocalDateTime.now());
            wynik.setWykonanePrzez(wykonujacy);

            wynikBadaniaRepo.save(wynik);
        }

        redirectAttributes.addFlashAttribute(
                "komunikat",
                "Wyniki badań zostały zapisane."
        );

        return "redirect:/dodaj-badanie?sukces";
    }

    private boolean czyWynikNieprawidlowy(
            BigDecimal wynik,
            BadanieRodzaj rodzajBadania) {

        if (rodzajBadania.getWartoscMin() != null
                && wynik.compareTo(
                rodzajBadania.getWartoscMin()
        ) < 0) {
            return true;
        }

        if (rodzajBadania.getWartoscMax() != null
                && wynik.compareTo(
                rodzajBadania.getWartoscMax()
        ) > 0) {
            return true;
        }

        return false;
    }
}