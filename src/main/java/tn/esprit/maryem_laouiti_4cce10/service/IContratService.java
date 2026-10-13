package tn.esprit.maryem_laouiti_4cce10.service;

import java.util.List;
import tn.esprit.maryem_laouiti_4cce10.entities.Contrat;

public interface IContratService {
    Contrat create(Contrat contrat);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat contrat);
    void deleteById(Long id);
}