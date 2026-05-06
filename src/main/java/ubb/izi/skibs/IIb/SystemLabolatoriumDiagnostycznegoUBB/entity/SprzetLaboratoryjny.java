package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SprzetLaboratoryjny")
public class SprzetLaboratoryjny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sprzetu")
    private Long id;

    @Column(nullable = false, length = 255)
    private String nazwa;

    @Column(length = 100)
    private String typ;

    @Column(name = "numer_seryjny", length = 100)
    private String numerSeryjny;

    @ManyToOne
    @JoinColumn(name = "id_laboratorium")
    private Laboratorium laboratorium;

    public SprzetLaboratoryjny() {}

    // gettery/settery
}