package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RapportFactory {
    private final Map<String, Rapport> rapports;

    public RapportFactory(Map<String, Rapport> rapports) {
        this.rapports = rapports;
    }

    public Rapport create(RapportType type) {
        Rapport rapport = rapports.get(type.name());
        if (rapport == null) {
            throw new IllegalArgumentException("Aucun rapport pour le type " + type);
        }
        return rapport;
    }
}
