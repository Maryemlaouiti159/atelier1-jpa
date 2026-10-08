package tn.esprit.maryem_laouiti_4cce10.service;

import tn.esprit.maryem_laouiti_4cce10.entities.Vehicule;

import java.util.List;

public interface IVehiculeServices {
    Vehicule create (Vehicule vehicule);
    Vehicule findById (Long id) throws Exception;
    List<Vehicule> findAll();
    void deleteById (Long id);
    Vehicule update( Vehicule vehicule);
}
