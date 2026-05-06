package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Laboratorium")
public class Laboratorium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_laboratorium")
    private Long id;

    @Column(nullable = false, length = 255)
    private String nazwa;

    @Column(length = 255)
    private String adres;

    @Column(length = 20)
    private String telefon;

    public Laboratorium() {}

}
