package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Probka")
public class Probka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_probki")
    private Long id;

    @Column(name = "kod_probki", nullable = false, unique = true, length = 50)
    private String kodProbki;

    @ManyToOne
    @JoinColumn(name = "id_typ_materialu")
    private TypMaterialu typMaterialu;

    @Column(name = "data_pobrania")
    private LocalDateTime dataPobrania;

    @Column(name = "miejsce_pobrania", length = 255)
    private String miejscePobrania;

    @ManyToOne
    @JoinColumn(name = "id_pacjenta", nullable = false)
    private Pacjent pacjent;

    @ManyToOne
    @JoinColumn(name = "pobrane_przez")
    private Lekarz pobranePrzez;

    public Probka() {}

    // gettery/settery
}