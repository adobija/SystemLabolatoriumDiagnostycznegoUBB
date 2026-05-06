package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "ZleceniePozycja")
public class ZleceniePozycja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pozycji")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_zlecenia", nullable = false)
    private ZlecenieBadania zlecenie;

    @ManyToOne
    @JoinColumn(name = "id_badania_rodzaj", nullable = false)
    private BadanieRodzaj badanieRodzaj;

    @ManyToOne
    @JoinColumn(name = "id_probki")
    private Probka probka;

    @ManyToOne
    @JoinColumn(name = "id_status_pozycji", nullable = false)
    private StatusPozycji status;

    public ZleceniePozycja() {}

}
