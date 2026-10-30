package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OffreStrategyFactory {
    private final Map<String, OffreTarificationStrategy> strategies;

    public OffreStrategyFactory(Map<String, OffreTarificationStrategy> strategies) {
        this.strategies = strategies;
    }

    public OffreTarificationStrategy forOffer(VehiculeOffre offre) {
        if (offre == null) {
            throw new IllegalArgumentException("L'offre est obligatoire.");
        }

        OffreTarificationStrategy strategy = strategies.get(offre.name());
        if (strategy == null) {
            throw new IllegalArgumentException("Aucune stratégie pour l'offre " + offre);
        }
        return strategy;
    }
}
