package tn.esprit.maryem_laouiti_4cce10.service.paiement;

import tn.esprit.maryem_laouiti_4cce10.entities.ModePaiement;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;

public interface IModePaiement {
    ModePaiement getType();
    Paiement payer(double montant);
}