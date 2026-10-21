package tn.esprit.maryem_laouiti_4cce10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @JsonIgnore
    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    private Contrat contrat;
}