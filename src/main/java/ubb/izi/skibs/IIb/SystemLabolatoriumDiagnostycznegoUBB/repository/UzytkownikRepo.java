package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Uzytkownik;

import java.util.Optional;

public interface UzytkownikRepo extends JpaRepository <Uzytkownik, Long> {

    Optional<Uzytkownik> findByEmail(String email);
}
