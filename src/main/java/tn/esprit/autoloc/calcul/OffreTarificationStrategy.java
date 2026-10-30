package tn.esprit.autoloc.calcul;

public interface OffreTarificationStrategy {
    double calculer(double tarifJournalier, int nombreJours);
}
