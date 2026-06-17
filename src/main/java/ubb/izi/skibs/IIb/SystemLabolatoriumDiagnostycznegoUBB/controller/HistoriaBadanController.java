package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.WynikBadania;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.WynikiRepo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HistoriaBadanController {

    private final WynikiRepo wynikiRepo;

    public HistoriaBadanController(WynikiRepo wynikiRepo) {
        this.wynikiRepo = wynikiRepo;
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
}