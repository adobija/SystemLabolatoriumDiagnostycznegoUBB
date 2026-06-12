package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Plec;

public interface PlecRepo extends JpaRepository<Plec, Long> {
    Plec findByKod(String kod);
}
