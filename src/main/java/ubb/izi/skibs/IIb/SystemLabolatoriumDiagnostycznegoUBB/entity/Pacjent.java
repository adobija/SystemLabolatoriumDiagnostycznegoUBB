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

    @Column(name = "data_urodzenia")
    private LocalDate dataUrodzenia;

    @ManyToOne
    @JoinColumn(name = "id_plec", nullable = false)
    private Plec plec;

    @Column(length = 255)
    private String adres;

    @Column(name = "numer_karty_pacjenta", length = 50)
    private String numerKartyPacjenta;

    public Pacjent() {
    }

    // gettery/settery
    public Pacjent(String imie, String nazwisko, String pesel, Plec plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.plec = plec;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(LocalDate dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public Plec getPlec() {
        return plec;
    }

    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getNumerKartyPacjenta() {
        return numerKartyPacjenta;
    }

    public void setNumerKartyPacjenta(String numerKartyPacjenta) {
        this.numerKartyPacjenta = numerKartyPacjenta;
    }
}