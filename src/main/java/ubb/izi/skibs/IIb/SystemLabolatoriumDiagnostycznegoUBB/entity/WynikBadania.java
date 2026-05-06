package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WynikBadania")
public class WynikBadania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wyniku")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pozycji", nullable = false, unique = true)
    private ZleceniePozycja pozycja;

    @Column(name = "wartosc_liczbowa", precision = 10, scale = 2)
    private BigDecimal wartoscLiczbowa;

    @Column(name = "wartosc_opisowa", length = 2000)
    private String wartoscOpisowa;

    @Column(length = 50)
    private String jednostka;

    @Column(name = "flaga_nieprawidlowy")
    private Boolean flagaNieprawidlowy;

    @Column(name = "data_wykonania")
    private LocalDateTime dataWykonania;

    @ManyToOne
    @JoinColumn(name = "wykonane_przez")
    private Uzytkownik wykonanePrzez;

    public WynikBadania() {}

}
