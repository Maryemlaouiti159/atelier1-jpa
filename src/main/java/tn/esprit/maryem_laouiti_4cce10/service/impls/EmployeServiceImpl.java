package tn.esprit.maryem_laouiti_4cce10.service.impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maryem_laouiti_4cce10.entities.Employe;
import tn.esprit.maryem_laouiti_4cce10.exception.ResourceNotFoundException;
import tn.esprit.maryem_laouiti_4cce10.repository.IEmployeRepository;
import tn.esprit.maryem_laouiti_4cce10.service.IEmployeService;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {
        if (employe.getId() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }
        verifier(employe);
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        verifier(employe);
        Employe existant = findById(id);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setPoste(employe.getPoste());
        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void verifier(Employe employe) {
        if (employe.getNom() == null || employe.getNom().isBlank()
                || employe.getPoste() == null || employe.getPoste().isBlank()) {
            throw new IllegalArgumentException("Le nom et le poste de l'employé sont obligatoires");
        }
    }
}