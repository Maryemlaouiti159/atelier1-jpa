package tn.esprit.maryem_laouiti_4cce10.service.impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maryem_laouiti_4cce10.entities.Vehicule;
import tn.esprit.maryem_laouiti_4cce10.exception.ResourceNotFoundException;
import tn.esprit.maryem_laouiti_4cce10.repository.IVehiculeRepository;
import tn.esprit.maryem_laouiti_4cce10.service.IVehiculeService;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getId() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        verifier(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        verifier(vehicule);
        Vehicule existant = findById(id);
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setPrixJour(vehicule.getPrixJour());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void verifier(Vehicule vehicule) {
        if (vehicule.getPrixJour() < 0) {
            throw new IllegalArgumentException("Le prix par jour ne peut pas être négatif");
        }
    }
}