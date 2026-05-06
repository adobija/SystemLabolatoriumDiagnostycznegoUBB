package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TypZdarzenia")
public class TypZdarzenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_typ_zdarzenia")
    private Long id;

    @Column(name = "kod", nullable = false, unique = true, length = 50)
    private String kod;

    @Column(name = "opis", nullable = false, length = 200)
    private String opis;

    public TypZdarzenia() {}

    public TypZdarzenia(String kod, String opis) {
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