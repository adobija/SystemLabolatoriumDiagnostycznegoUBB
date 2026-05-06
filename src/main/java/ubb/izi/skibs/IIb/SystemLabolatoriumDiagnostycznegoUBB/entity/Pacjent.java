package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Pacjent")
public class Pacjent {

    @Id
    @Column(name = "id_pacjenta")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_pacjenta")
    private Uzytkownik uzytkownik;

    @Column(nullable = false, length = 100)
    private String imie;

    @Column(nullable = false, length = 100)
    private String nazwisko;

    @Column(nullable = false, unique = true, length = 11)
    private String pesel;

    @Column(length = 20)
    private String telefon;

    @Column(name = "data_urodzenia", nullable = false)
    private LocalDate dataUrodzenia;

    @ManyToOne
    @JoinColumn(name = "id_plec", nullable = false)
    private Plec plec;

    @Column(length = 255)
    private String adres;

    @Column(name = "numer_karty_pacjenta", length = 50)
    private String numerKartyPacjenta;

    public Pacjent() {}

    // gettery/settery
}