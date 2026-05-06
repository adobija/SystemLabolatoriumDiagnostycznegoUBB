package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Plec")
public class Plec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plec")
    private Long id;

    @Column(name = "kod", nullable = false, unique = true, length = 10)
    private String kod;

    @Column(name = "opis", nullable = false, length = 50)
    private String opis;

    public Plec() {}

    public Plec(String kod, String opis) {
        this.kod = kod;
        this.opis = opis;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKod() { return kod; }
    public void setKod(String kod) { this.kod = kod; }

    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
}