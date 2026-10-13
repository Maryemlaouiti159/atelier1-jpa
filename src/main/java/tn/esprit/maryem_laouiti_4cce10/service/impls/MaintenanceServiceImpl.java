package tn.esprit.maryem_laouiti_4cce10.service.impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maryem_laouiti_4cce10.entities.Maintenance;
import tn.esprit.maryem_laouiti_4cce10.exception.ResourceNotFoundException;
import tn.esprit.maryem_laouiti_4cce10.repository.IMaintenanceRepository;
import tn.esprit.maryem_laouiti_4cce10.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        if (maintenance.getId() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        verifier(maintenance);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        verifier(maintenance);
        Maintenance existante = findById(id);
        existante.setDateMaintenance(maintenance.getDateMaintenance());
        existante.setDescription(maintenance.getDescription());
        existante.setCout(maintenance.getCout());
        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void verifier(Maintenance maintenance) {
        if (maintenance.getCout() < 0) {
            throw new IllegalArgumentException("Le coût ne peut pas être négatif");
        }
    }
}