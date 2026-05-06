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

    @Column(nullable = false, unique = true, length = 255)
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

    // gettery/settery
}
