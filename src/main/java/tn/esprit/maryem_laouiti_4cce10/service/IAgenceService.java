package tn.esprit.maryem_laouiti_4cce10.service;

import java.util.List;
import tn.esprit.maryem_laouiti_4cce10.entities.Agence;

public interface IAgenceService {
    Agence create(Agence agence);
    Agence findById(Long id);
    List<Agence> findAll();
    Agence update(Long id, Agence agence);
    void deleteById(Long id);
}