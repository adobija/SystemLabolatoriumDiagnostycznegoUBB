package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "Rola")
public class Rola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rola")
    private Long id;

    @Column(name = "kod", nullable = false, unique = true, length = 20)
    private String kod;

    @Column(name = "nazwa", nullable = false, length = 100)
    private String nazwa;

    // Konstruktor bezargumentowy (wymagany przez JPA)
    public Rola() {
    }

    // Konstruktor opcjonalny
    public Rola(String kod, String nazwa) {
        this.kod = kod;
        this.nazwa = nazwa;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}