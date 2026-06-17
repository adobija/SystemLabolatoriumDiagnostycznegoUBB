package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.WynikBadania;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.WynikiRepo;
import org.springframework.web.bind.annotation.PathVariable;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.service.RaportPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HistoriaBadanController {

    private final WynikiRepo wynikiRepo;
    private final RaportPdfService raportPdfService;

    public HistoriaBadanController(
            WynikiRepo wynikiRepo,
            RaportPdfService raportPdfService) {

        this.wynikiRepo = wynikiRepo;
        this.raportPdfService = raportPdfService;
    }


    @GetMapping("/historia-badan")
    public String pokazHistorieBadan(Model model) {

        List<WynikBadania> wyniki =
                wynikiRepo.findAllByOrderByDataWykonaniaDesc();

        model.addAttribute("wyniki", wyniki);
        model.addAttribute("liczbaWynikow", wyniki.size());

        return "Template/historia-badan";
    }
    @GetMapping("/historia-badan/{id}")
    public String pokazSzczegolyBadania(
            @PathVariable Long id,
            Model model) {

        WynikBadania wynik = wynikiRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Nie znaleziono wyniku o ID: " + id
                        )
                );

        model.addAttribute("wynik", wynik);

        return "Template/szczegoly-badania";
    }

    @GetMapping("/historia-badan/{id}/pdf")
    public ResponseEntity<byte[]> pobierzPojedynczyPdf(
            @PathVariable Long id) {

        WynikBadania wynik = wynikiRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Nie znaleziono wyniku."
                        )
                );

        byte[] pdf = raportPdfService.generujRaport(
                List.of(wynik)
        );

        String nazwaPliku =
                "wynik-badania-" + wynik.getId() + ".pdf";

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + nazwaPliku + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(pdf);
    }
    @PostMapping("/historia-badan/pdf")
    public ResponseEntity<byte[]> pobierzWieleBadanPdf(
            @RequestParam("ids") List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nie wybrano żadnych wyników."
            );
        }

        List<WynikBadania> wyniki =
                wynikiRepo.findAllByIdInOrderByDataWykonaniaDesc(ids);

        if (wyniki.size() != ids.size()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nie znaleziono części wybranych wyników."
            );
        }

        Long pacjentId = wyniki.get(0)
                .getPacjent()
                .getId();

        boolean rozniPacjenci = wyniki.stream()
                .anyMatch(wynik ->
                        !wynik.getPacjent()
                                .getId()
                                .equals(pacjentId)
                );

        if (rozniPacjenci) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Do jednego raportu można wybrać wyniki tylko jednego pacjenta."
            );
        }

        byte[] pdf = raportPdfService.generujRaport(wyniki);

        String nazwaPliku =
                "raport-badan-pacjent-" + pacjentId + ".pdf";

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + nazwaPliku + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(pdf);
    }
    @PostMapping("/historia-badan/pdf-wiele")
    public ResponseEntity<byte[]> pobierzWieleBadanPdfNowy(
            @RequestParam("ids") List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nie wybrano żadnych wyników."
            );
        }

        List<WynikBadania> wyniki =
                wynikiRepo.findAllByIdInOrderByDataWykonaniaDesc(ids);

        if (wyniki.size() != ids.size()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nie znaleziono części wybranych wyników."
            );
        }

        Long pacjentId = wyniki.get(0)
                .getPacjent()
                .getId();

        boolean rozniPacjenci = wyniki.stream()
                .anyMatch(wynik ->
                        !wynik.getPacjent()
                                .getId()
                                .equals(pacjentId)
                );

        if (rozniPacjenci) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Do jednego raportu można wybrać wyniki tylko jednego pacjenta."
            );
        }

        byte[] pdf = raportPdfService.generujRaport(wyniki);

        String nazwaPliku =
                "raport-badan-pacjent-" + pacjentId + ".pdf";

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + nazwaPliku + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(pdf);
    }

}