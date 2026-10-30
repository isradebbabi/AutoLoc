package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("WEEKEND")
public class WeekendOffreStrategy implements OffreTarificationStrategy {
    @Override
    public double calculer(double tarifJournalier, int nombreJours) {
        return tarifJournalier * 0.80 * nombreJours;
    }
}
