package tn.esprit.maryem_laouiti_4cce10.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter @Setter @NoArgsConstructor
public class Contrat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateSignature;
    private double montantTotal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat",fetch = FetchType.LAZY, cascade = ALL)
    private List<Paiement> paiements = new ArrayList<>();
}