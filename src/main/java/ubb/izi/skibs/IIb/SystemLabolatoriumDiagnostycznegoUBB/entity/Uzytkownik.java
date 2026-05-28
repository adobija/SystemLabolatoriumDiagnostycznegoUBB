package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Uzytkownik")
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzytkownika")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "haslo_hash", nullable = false, length = 255)
    private String hasloHash;

    @ManyToOne
    @JoinColumn(name = "id_rola", nullable = false)
    private Rola rola;

    @Column(name = "data_utworzenia")
    private LocalDateTime dataUtworzenia;

    public Uzytkownik() {}

    public Uzytkownik(String email, String hasloHash, Rola rola) {
        this.email = email;
        this.hasloHash = hasloHash;
        this.rola = rola;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHasloHash() {
        return hasloHash;
    }

    public void setHasloHash(String hasloHash) {
        this.hasloHash = hasloHash;
    }

    public Rola getRola() {
        return rola;
    }

    public void setRola(Rola rola) {
        this.rola = rola;
    }

    public LocalDateTime getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(LocalDateTime dataUtworzenia) {
        this.dataUtworzenia = dataUtworzenia;
    }
// gettery/settery
}
