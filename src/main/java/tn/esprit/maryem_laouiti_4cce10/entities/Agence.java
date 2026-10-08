package tn.esprit.maryem_laouiti_4cce10.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;

    @OneToMany(mappedBy = "agence",fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();

    @OneToMany(mappedBy = "agence",fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();
}