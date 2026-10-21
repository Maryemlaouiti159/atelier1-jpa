package tn.esprit.maryem_laouiti_4cce10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;

    @JsonIgnore
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();
}