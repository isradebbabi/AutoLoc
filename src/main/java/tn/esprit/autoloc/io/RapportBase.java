package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;

@Component("rapportBase")
public class RapportBase implements Rapport {
    @Override
    public String entete(Agence agence) {
        return "Id: " + agence.getId()
                + ", Nom: " + agence.getNom()
                + ", Tel: " + agence.getTelephone() + "\n";
    }

    @Override
    public double total(double total) {
        return total;
    }
}
