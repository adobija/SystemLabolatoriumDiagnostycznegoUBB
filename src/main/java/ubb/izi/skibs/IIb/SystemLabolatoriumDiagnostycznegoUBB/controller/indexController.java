package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping("/")
    public String showMainPortal() {
        return "Template/SG";
    }
}
