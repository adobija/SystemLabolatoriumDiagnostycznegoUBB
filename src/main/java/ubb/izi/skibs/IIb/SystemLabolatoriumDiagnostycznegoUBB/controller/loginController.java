package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController
{
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
}

