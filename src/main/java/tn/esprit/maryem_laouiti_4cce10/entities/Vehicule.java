package tn.esprit.maryem_laouiti_4cce10.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String modele;
    private String immatriculation;
    private double prixJour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @OneToMany(mappedBy = "vehicule",fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule",fetch = FetchType.LAZY)
    private List<Maintenance> maintenances = new ArrayList<>();

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    private List<Equipement> equipements = new ArrayList<>();
}