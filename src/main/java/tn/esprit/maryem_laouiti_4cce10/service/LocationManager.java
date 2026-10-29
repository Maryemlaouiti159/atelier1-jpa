package tn.esprit.maryem_laouiti_4cce10.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.maryem_laouiti_4cce10.entities.Contrat;
import tn.esprit.maryem_laouiti_4cce10.entities.ModePaiement;
import tn.esprit.maryem_laouiti_4cce10.entities.Paiement;
import tn.esprit.maryem_laouiti_4cce10.entities.Reservation;
import tn.esprit.maryem_laouiti_4cce10.repository.IContratRepository;
import tn.esprit.maryem_laouiti_4cce10.repository.IReservationRepository;
import tn.esprit.maryem_laouiti_4cce10.service.location.legacy.SmsGateway;
import tn.esprit.maryem_laouiti_4cce10.service.location.legacy.SmtpClient;

/**
 * Confirmation des reservations.
 * Ecrit en urgence par le stagiaire. NE PAS MODIFIER : ca marche.
 *
 * Fichier fourni pour l'Atelier 6. Adaptez uniquement les noms des
 * getters/setters si votre modele (Atelier 2) utilise d'autres noms
 * (ex. getIdReservation() au lieu de getId()).
 */
@Service
public class LocationManager {

    @Autowired private IReservationRepository reservationRepository;
    @Autowired private IContratRepository contratRepository;

    public Contrat confirmerReservation(Long reservationId, String modePaiement,
                                        String codePromo, String canal) {

        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) throw new RuntimeException("Reservation introuvable");

        // ---------- 1. disponibilite ----------
        for (Reservation x : reservationRepository.findAll()) {
            if (x.getVehicule().getId().equals(r.getVehicule().getId())
                    && !x.getId().equals(r.getId())
                    && !(r.getDateFin().isBefore(x.getDateDebut())
                    || r.getDateDebut().isAfter(x.getDateFin()))) {
                throw new RuntimeException("Vehicule indisponible");
            }
        }

        // ---------- 2. tarif de base ----------
        long j = ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()) + 1;
        double m = j * r.getVehicule().getTarifJournalier();

        // ---------- 3. remises ----------
        String t = r.getClient().getType();
        if (t.equals("PARTICULIER")) {
            if (j >= 7) m = m * 0.90;
        } else if (t.equals("ENTREPRISE")) {
            m = m * 0.85;
        } else if (t.equals("VIP")) {
            m = m * 0.80;
        }
        if (codePromo != null && codePromo.equals("BIENVENUE")) {
            m = m - 20;
        }

        // ---------- 4. paiement ----------
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        if (modePaiement.equals("CARTE")) {
            System.out.println("Debit de la carte bancaire...");
            m = m * 1.01; // 1 % de frais
            p.setModePaiement(ModePaiement.CARTE.name());
        } else if (modePaiement.equals("VIREMENT")) {
            System.out.println("Attente du virement...");
            p.setModePaiement(ModePaiement.VIREMENT.name());
        } else if (modePaiement.equals("ESPECES")) {
            System.out.println("Encaissement en especes a l'agence");
            p.setModePaiement(ModePaiement.ESPECES.name());
        } else {
            throw new RuntimeException("Mode de paiement inconnu : " + modePaiement);
        }
        p.setMontant(m);

        // ---------- 5. contrat ----------
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(m);
        c.setValide(true);
        c.getPaiements().add(p);
        p.setContrat(c);
        Contrat res = contratRepository.save(c);

        // ---------- 6. notification ----------
        if (canal.equals("EMAIL")) {
            SmtpClient smtp = new SmtpClient("smtp.autoloc.tn", 25);
            smtp.send(r.getClient().getEmail(), "Contrat " + res.getId() + " confirme");
        } else if (canal.equals("SMS")) {
            SmsGateway sms = new SmsGateway("https://sms.autoloc.tn");
            sms.send(r.getClient().getTelephone(), "Contrat " + res.getId() + " confirme");
        }

        return res;
    }
}