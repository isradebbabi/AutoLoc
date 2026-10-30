package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.io.Rapport;
import tn.esprit.autoloc.io.RapportFactory;
import tn.esprit.autoloc.io.RapportType;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService {
    private final IAgenceRepository agenceRepository;
    private final IVehiculeService vehiculeService;
    private final RapportFactory rapportFactory;

    public AgenceServiceImpl(IAgenceRepository agenceRepository,
                             IVehiculeService vehiculeService,
                             RapportFactory rapportFactory) {
        super(agenceRepository);
        this.agenceRepository = agenceRepository;
        this.vehiculeService = vehiculeService;
        this.rapportFactory = rapportFactory;
    }

    @Override protected String entityName() { return "Agence"; }
    @Override protected Long getId(Agence agence) { return agence.getId(); }

    @Override
    protected void copyFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setVille(source.getVille());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasVehicules(Long id) {
        return !findById(id).getVehicules().isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        if (agence == null) {
            throw new IllegalArgumentException("L'agence est obligatoire.");
        }
        if (nbJours <= 0) {
            throw new IllegalArgumentException("Le nombre de jours doit être supérieur à zéro.");
        }
        if (offre == null) {
            throw new IllegalArgumentException("L'offre est obligatoire.");
        }

        if (agence.getVehicules().isEmpty()) {
            throw new UnsupportedOperationException("La simulation nécessite au moins un véhicule affecté à l'agence.");
        }

        Rapport rapport = rapportFactory.create(RapportType.CONSOLE);
        StringBuilder stringBuilder = new StringBuilder(rapport.entete(agence));

        double total = 0;
        for (Vehicule vehicule : agence.getVehicules()) {
            total += vehiculeService.calculTarif(vehicule, nbJours, offre);
        }

        stringBuilder.append("Total de vehicules: ")
                .append(rapport.total(total))
                .append("\n");
        return stringBuilder.toString();
    }
}
