package tn.esprit.maryem_laouiti_4cce10.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maryem_laouiti_4cce10.entities.Vehicule;
import tn.esprit.maryem_laouiti_4cce10.repositery.IVehiculeRepositery;
import tn.esprit.maryem_laouiti_4cce10.service.IVehiculeServices;

import java.util.List;
@RequiredArgsConstructor
@Service
public class VehiculeServicesImpl implements IVehiculeServices {
    private IVehiculeRepositery vehiculeRepositery;

    @Override
    public Vehicule create (Vehicule vehicule){
        return vehiculeRepositery.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) throws Exception {
        return vehiculeRepositery.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepositery.findAll();
    }

    @Override
    public void deleteById(Long id) {
        vehiculeRepositery.deleteById(id);

    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        return vehiculeRepositery.save(vehicule);
    }
}
