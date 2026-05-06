package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ZlecenieBadania")
public class ZlecenieBadania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zlecenia")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pacjenta", nullable = false)
    private Pacjent pacjent;

    @ManyToOne
    @JoinColumn(name = "id_lekarza", nullable = false)
    private Lekarz lekarz;

    @Column(name = "data_zlecenia")
    private LocalDateTime dataZlecenia;

    @ManyToOne
    @JoinColumn(name = "id_status_zlecenia", nullable = false)
    private StatusZlecenia status;

    @Column(name = "uwagi_kliniczne", length = 2000)
    private String uwagiKliniczne;

    public ZlecenieBadania() {}

    // gettery/settery
}