package tn.esprit.maryem_laouiti_4cce10.service.paiement;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import tn.esprit.maryem_laouiti_4cce10.entities.ModePaiement;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;

@Component
public class PaiementVirement implements IModePaiement, IRemboursable {

    @Override
    public ModePaiement getType() {
        return ModePaiement.VIREMENT;
    }

    @Override
    public Paiement payer(double montant) {
        System.out.println("Attente du virement...");
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        p.setModePaiement(ModePaiement.VIREMENT.name());
        p.setMontant(montant);
        return p;
    }

    @Override
    public void rembourser(Paiement paiement) {
        System.out.println("Virement retour : " + paiement.getMontant());
    }
}