package tn.esprit.maryem_laouiti_4cce10.service.impls;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.maryem_laouiti_4cce10.entities.Contrat;
import tn.esprit.maryem_laouiti_4cce10.exception.ResourceNotFoundException;
import tn.esprit.maryem_laouiti_4cce10.repository.IContratRepository;
import tn.esprit.maryem_laouiti_4cce10.service.IContratService;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getId() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        verifierMontant(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        verifierMontant(contrat);
        Contrat existant = findById(id);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private void verifierMontant(Contrat contrat) {
        if (contrat.getMontantTotal() < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }
    }
}