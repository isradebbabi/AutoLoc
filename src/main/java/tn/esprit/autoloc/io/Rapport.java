package tn.esprit.autoloc.io;

import tn.esprit.autoloc.domain.Agence;

public interface Rapport {
    String entete(Agence agence);

    double total(double total);
}
