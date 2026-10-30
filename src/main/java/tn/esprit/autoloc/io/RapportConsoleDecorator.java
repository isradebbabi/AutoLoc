package tn.esprit.autoloc.io;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;

@Component("CONSOLE")
public class RapportConsoleDecorator implements Rapport {
    private final Rapport rapport;

    public RapportConsoleDecorator(@Qualifier("rapportBase") Rapport rapport) {
        this.rapport = rapport;
    }

    @Override
    public String entete(Agence agence) {
        String entete = rapport.entete(agence);
        System.out.print(entete);
        return entete;
    }

    @Override
    public double total(double total) {
        return rapport.total(total);
    }
}
