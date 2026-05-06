package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LogZdarzen")
public class LogZdarzen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_logu")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_uzytkownika")
    private Uzytkownik uzytkownik;

    @Column(name = "data_zdarzenia")
    private LocalDateTime dataZdarzenia;

    @ManyToOne
    @JoinColumn(name = "id_typ_zdarzenia")
    private TypZdarzenia typZdarzenia;

    @Column(length = 2000)
    private String opis;

    public LogZdarzen() {}

    // gettery/settery
}
