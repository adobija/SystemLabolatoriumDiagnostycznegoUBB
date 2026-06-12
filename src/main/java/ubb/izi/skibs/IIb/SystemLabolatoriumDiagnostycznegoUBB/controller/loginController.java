package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Pacjent;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Plec;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Rola;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Uzytkownik;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.PacjentRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.PlecRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.RolaRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.UzytkownikRepo;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class loginController
{


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UzytkownikRepo uzytkownikRepo;

    @Autowired
    private PacjentRepo pacjentRepo;

    @Autowired
    private RolaRepo rolaRepo;

    @Autowired
    private PlecRepo plecRepo;

    @GetMapping("/login")
    public String showPanelLogowania(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            return "redirect:/dashboard";
        }
        return "Template/Logowanie";
    }

    @GetMapping("/register")
    public String showPanelRejestracji(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            return "redirect:/dashboard";
        }
        return "Template/Rejestracja";
    }

    @PostMapping("/register")
    public String registerOfUser(Authentication authentication,
                                 @RequestParam("imie") String imie,
                                 @RequestParam("nazwisko") String nazwisko,
                                 @RequestParam("pesel") String pesel,
                                 @RequestParam("plec") String plec,
                                 @RequestParam("email") String email,
                                 @RequestParam("haslo") String haslo) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            return "redirect:/dashboard";
        }
        if (uzytkownikRepo.existsByEmail(email)) {
            return "redirect:/register?error=email_exists";
        }

        Uzytkownik uzytkownik = new Uzytkownik(email, passwordEncoder().encode(haslo));
        Rola rola = rolaRepo.findById(3L)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono roli"));
        uzytkownik.setRola(rola);
        uzytkownik.setDataUtworzenia(LocalDateTime.now());

        //zapisanie usera do bazy
        Uzytkownik nowoDodany = uzytkownikRepo.save(uzytkownik);

        Plec plecDlaPacjent = plecRepo.findByKod(plec);
        Pacjent pacjent = new Pacjent(imie, nazwisko, pesel, plecDlaPacjent);
        pacjent.setNumerKartyPacjenta("KARTA00"+ nowoDodany.getId());
        pacjent.setUzytkownik(nowoDodany);

        //zapisanie pacjenta do bazy
        pacjentRepo.save(pacjent);

        return "redirect:/Logowanie";
    }
}

