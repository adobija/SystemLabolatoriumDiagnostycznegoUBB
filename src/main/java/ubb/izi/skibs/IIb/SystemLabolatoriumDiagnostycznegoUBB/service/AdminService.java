package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.service;

import org.springframework.stereotype.Service;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Rola;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Uzytkownik;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.RolaRepo;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.UzytkownikRepo;

import java.util.List;

@Service
public class AdminService {

    private final UzytkownikRepo uzytkownikRepo;
    private final RolaRepo rolaRepo;

    public AdminService(UzytkownikRepo uzytkownikRepo, RolaRepo rolaRepo) {
        this.uzytkownikRepo = uzytkownikRepo;
        this.rolaRepo = rolaRepo;
    }

    public List<Uzytkownik> wszyscyUzytkownicy() {
        return uzytkownikRepo.findAll();
    }

    public List<Rola> wszystkieRole() {
        return rolaRepo.findAll();
    }

    public void zmienRole(Long idUzytkownika, Long idRola) {
        Uzytkownik u = uzytkownikRepo.findById(idUzytkownika)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));

        Rola rola = rolaRepo.findById(idRola)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono roli"));

        u.setRola(rola);
        uzytkownikRepo.save(u);
    }
}