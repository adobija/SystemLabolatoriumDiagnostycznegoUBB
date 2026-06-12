package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.Pacjent;

public interface PacjentRepo extends JpaRepository<Pacjent, Long> {
}
