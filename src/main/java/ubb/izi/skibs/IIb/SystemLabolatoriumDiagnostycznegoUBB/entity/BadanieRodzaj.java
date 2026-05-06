package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BadanieRodzaj")
public class BadanieRodzaj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_badania_rodzaj")
    private Long id;

    @Column(name = "kod_badania", nullable = false, unique = true, length = 50)
    private String kodBadania;

    @Column(nullable = false, length = 255)
    private String nazwa;

    @Column(length = 1000)
    private String opis;

    @Column(length = 50)
    private String jednostka;

    @Column(name = "wartosc_min_referencyjna", precision = 10, scale = 2)
    private BigDecimal wartoscMin;

    @Column(name = "wartosc_max_referencyjna", precision = 10, scale = 2)
    private BigDecimal wartoscMax;

    @Column(precision = 10, scale = 2)
    private BigDecimal cena;

    @ManyToOne
    @JoinColumn(name = "id_typ_materialu")
    private TypMaterialu typMaterialu;

    public BadanieRodzaj() {}

}
