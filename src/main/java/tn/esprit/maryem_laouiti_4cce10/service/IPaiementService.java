package tn.esprit.maryem_laouiti_4cce10.service;

import java.util.List;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;

public interface IPaiementService {
    Paiement findById(Long id);
    List<Paiement> findAll();
}