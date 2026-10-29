package tn.esprit.maryem_laouiti_4cce10.service.paiement;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import tn.esprit.maryem_laouiti_4cce10.entities.ModePaiement;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;

@Component
public class PaiementCarte implements IModePaiement, IRemboursable {

    @Override
    public ModePaiement getType() {
        return ModePaiement.CARTE;
    }

    @Override
    public Paiement payer(double montant) {
        System.out.println("Debit de la carte bancaire...");
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        p.setModePaiement(ModePaiement.CARTE.name());
        p.setMontant(montant * 1.01); // 1 % de frais
        return p;
    }

    @Override
    public void rembourser(Paiement paiement) {
        System.out.println("Remboursement sur la carte bancaire : " + paiement.getMontant());
    }
}