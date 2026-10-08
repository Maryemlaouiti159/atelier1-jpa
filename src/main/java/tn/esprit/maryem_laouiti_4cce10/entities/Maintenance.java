package tn.esprit.maryem_laouiti_4cce10.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Getter @Setter @NoArgsConstructor
public class Maintenance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateMaintenance;
    private String description;
    private double cout;

    @ManyToOne(fetch = FetchType.LAZY, cascade = PERSIST)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
}