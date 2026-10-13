package tn.esprit.maryem_laouiti_4cce10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.maryem_laouiti_4cce10.entities.Equipement;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}