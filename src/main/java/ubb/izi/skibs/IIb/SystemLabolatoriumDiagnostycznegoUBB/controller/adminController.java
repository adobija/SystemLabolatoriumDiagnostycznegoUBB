package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.service.AdminService;

@Controller
@RequestMapping("/admin")
public class adminController {

    private final AdminService adminService;

    public adminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Lista użytkowników
    @GetMapping("/users")
    public String listaUzytkownikow(Model model) {
        model.addAttribute("uzytkownicy", adminService.wszyscyUzytkownicy());
        model.addAttribute("role", adminService.wszystkieRole());
        return "Template/admin-uzytkownicy";
    }

    // Zmiana roli
    @PostMapping("/users/{id}/rola")
    public String zmienRole(@PathVariable Long id,
                            @RequestParam Long idRola) {
        adminService.zmienRole(id, idRola);
        return "redirect:/admin/users?sukces";
    }

}