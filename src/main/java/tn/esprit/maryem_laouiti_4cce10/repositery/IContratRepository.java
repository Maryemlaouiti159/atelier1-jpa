package tn.esprit.maryem_laouiti_4cce10.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.maryem_laouiti_4cce10.entities.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}