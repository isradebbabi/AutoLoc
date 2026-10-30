package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("LONGUE_DUREE")
public class LongueDureeOffreStrategy implements OffreTarificationStrategy {
    @Override
    public double calculer(double tarifJournalier, int nombreJours) {
        double total = tarifJournalier * 0.75 * nombreJours;
        return total > 2000 ? total * 0.95 : total;
    }
}
