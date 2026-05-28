package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {

        String username = authentication.getName();

        String rola = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replace("ROLE_", ""))
                .findFirst()
                .orElse("BRAK_ROLI");

        model.addAttribute("username", username);
        model.addAttribute("rola", rola);



        return "Template/dashboard";
    }
}