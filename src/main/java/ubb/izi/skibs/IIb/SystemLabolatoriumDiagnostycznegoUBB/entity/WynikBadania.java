package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Wynik_Badania")
public class WynikBadania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_wyniku")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pacjenta", nullable = false)
    private Pacjent pacjent;

    @ManyToOne
    @JoinColumn(name = "id_badania_rodzaj", nullable = false)
    private BadanieRodzaj badanieRodzaj;

    @ManyToOne
    @JoinColumn(name = "id_zlecenia")
    private ZlecenieBadania zlecenie;

    @Column(name = "wartosc_liczbowa", precision = 10, scale = 2)
    private BigDecimal wartoscLiczbowa;

    @Column(name = "wartosc_opisowa", length = 2000)
    private String wartoscOpisowa;

    @Column(length = 50)
    private String jednostka;

    @Column(name = "flaga_nieprawidlowy", nullable = false)
    private Boolean flagaNieprawidlowy = false;

    @Column(name = "data_wykonania", nullable = false)
    private LocalDateTime dataWykonania;

    @ManyToOne
    @JoinColumn(name = "wykonane_przez")
    private Uzytkownik wykonanePrzez;

    @Column(name = "komentarz_diagnosty", length = 2000)
    private String komentarzDiagnosty;

    public WynikBadania() {
    }

    public Long getId() {
        return id;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BadanieRodzaj getBadanieRodzaj() {
        return badanieRodzaj;
    }

    public void setBadanieRodzaj(BadanieRodzaj badanieRodzaj) {
        this.badanieRodzaj = badanieRodzaj;
    }

    public ZlecenieBadania getZlecenie() {
        return zlecenie;
    }

    public void setZlecenie(ZlecenieBadania zlecenie) {
        this.zlecenie = zlecenie;
    }

    public BigDecimal getWartoscLiczbowa() {
        return wartoscLiczbowa;
    }

    public void setWartoscLiczbowa(BigDecimal wartoscLiczbowa) {
        this.wartoscLiczbowa = wartoscLiczbowa;
    }

    public String getWartoscOpisowa() {
        return wartoscOpisowa;
    }

    public void setWartoscOpisowa(String wartoscOpisowa) {
        this.wartoscOpisowa = wartoscOpisowa;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public Boolean getFlagaNieprawidlowy() {
        return flagaNieprawidlowy;
    }

    public void setFlagaNieprawidlowy(Boolean flagaNieprawidlowy) {
        this.flagaNieprawidlowy = flagaNieprawidlowy;
    }

    public LocalDateTime getDataWykonania() {
        return dataWykonania;
    }

    public void setDataWykonania(LocalDateTime dataWykonania) {
        this.dataWykonania = dataWykonania;
    }

    public Uzytkownik getWykonanePrzez() {
        return wykonanePrzez;
    }

    public void setWykonanePrzez(Uzytkownik wykonanePrzez) {
        this.wykonanePrzez = wykonanePrzez;
    }

    public String getKomentarzDiagnosty() {
        return komentarzDiagnosty;
    }

    public void setKomentarzDiagnosty(String komentarzDiagnosty) {
        this.komentarzDiagnosty = komentarzDiagnosty;
    }
}