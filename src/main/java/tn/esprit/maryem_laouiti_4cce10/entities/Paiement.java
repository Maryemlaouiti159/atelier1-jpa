package tn.esprit.maryem_laouiti_4cce10.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double montant;
    private LocalDate datePaiement;
    private String modePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;
}