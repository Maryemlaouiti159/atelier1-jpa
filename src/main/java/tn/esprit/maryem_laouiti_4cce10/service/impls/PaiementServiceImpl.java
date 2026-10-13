package tn.esprit.maryem_laouiti_4cce10.service.impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;
import tn.esprit.maryem_laouiti_4cce10.exception.ResourceNotFoundException;
import tn.esprit.maryem_laouiti_4cce10.repository.IPaiementRepository;
import tn.esprit.maryem_laouiti_4cce10.service.IPaiementService;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
}