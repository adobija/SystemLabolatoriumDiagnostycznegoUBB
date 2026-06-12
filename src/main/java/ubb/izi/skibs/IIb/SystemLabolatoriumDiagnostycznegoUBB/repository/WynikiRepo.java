package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.WynikBadania;

import java.util.List;

public interface WynikiRepo
        extends JpaRepository<WynikBadania, Long> {

    List<WynikBadania>
    findByPacjentIdOrderByDataWykonaniaDesc(Long pacjentId);

    List<WynikBadania>
    findAllByOrderByDataWykonaniaDesc();
}