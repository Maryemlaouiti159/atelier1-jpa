package tn.esprit.maryem_laouiti_4cce10.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Equipement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();
}