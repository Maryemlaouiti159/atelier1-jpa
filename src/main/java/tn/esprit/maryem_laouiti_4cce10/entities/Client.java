package tn.esprit.maryem_laouiti_4cce10.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Getter @Setter @NoArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY,cascade = PERSIST)
    private List<Reservation> reservations = new ArrayList<>();
}