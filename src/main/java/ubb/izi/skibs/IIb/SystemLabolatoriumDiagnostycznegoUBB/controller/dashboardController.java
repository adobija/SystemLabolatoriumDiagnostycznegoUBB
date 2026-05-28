package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        // TESTOWE DANE
        // później podmienisz na dane z logowania

        model.addAttribute("username", "Jan Kowalski");
        model.addAttribute("rola", "PACJENT");

        return "Template/dashboard";
    }
}