package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity.WynikBadania;

import java.util.List;
import java.util.Optional;

public interface WynikiRepo
        extends JpaRepository<WynikBadania, Long> {

    List<WynikBadania> findAllByOrderByDataWykonaniaDesc();

    List<WynikBadania>
    findByPacjentIdOrderByDataWykonaniaDesc(Long pacjentId);

    List<WynikBadania>
    findByPacjent_Uzytkownik_EmailOrderByDataWykonaniaDesc(
            String email
    );

    List<WynikBadania>
    findAllByIdInOrderByDataWykonaniaDesc(
            List<Long> ids
    );

    List<WynikBadania>
    findByIdInAndPacjent_Uzytkownik_EmailOrderByDataWykonaniaDesc(
            List<Long> ids,
            String email
    );

    Optional<WynikBadania>
    findByIdAndPacjent_Uzytkownik_Email(
            Long id,
            String email
    );
}