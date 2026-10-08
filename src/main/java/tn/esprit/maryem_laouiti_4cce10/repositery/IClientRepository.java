package tn.esprit.maryem_laouiti_4cce10.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.maryem_laouiti_4cce10.entities.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}