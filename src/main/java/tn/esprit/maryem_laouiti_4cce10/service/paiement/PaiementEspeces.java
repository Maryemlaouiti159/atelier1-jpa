package tn.esprit.maryem_laouiti_4cce10.service.paiement;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import tn.esprit.maryem_laouiti_4cce10.entities.ModePaiement;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;

@Component
public class PaiementEspeces implements IModePaiement {

    @Override
    public ModePaiement getType() {
        return ModePaiement.ESPECES;
    }

    @Override
    public Paiement payer(double montant) {
        System.out.println("Encaissement en especes a l'agence");
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        p.setModePaiement(ModePaiement.ESPECES.name());
        p.setMontant(montant);
        return p;
    }
}