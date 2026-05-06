package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Lekarz")
public class Lekarz {

    @Id
    @Column(name = "id_lekarza")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_lekarza")
    private Uzytkownik uzytkownik;

    @Column(nullable = false, length = 100)
    private String imie;

    @Column(nullable = false, length = 100)
    private String nazwisko;

    @Column(length = 11)
    private String pesel;

    @Column(length = 20)
    private String telefon;

    @Column(name = "data_urodzenia")
    private LocalDate dataUrodzenia;

    @ManyToOne
    @JoinColumn(name = "id_plec")
    private Plec plec;

    @Column(length = 255)
    private String adres;

    @Column(length = 100)
    private String specjalizacja;

    @Column(name = "numer_prawa_wykonywania", length = 50)
    private String numerPrawaWykonywania;

    @Column(length = 100)
    private String oddzial;

    public Lekarz() {}

}