package tn.esprit.autoloc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.calcul.OffreStrategyFactory;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.BadRequestException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@Transactional
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {
    private final IVehiculeRepository vehiculeRepository;
    private final OffreStrategyFactory offreStrategyFactory;

    public VehiculeServiceImpl(IVehiculeRepository vehiculeRepository,
                               OffreStrategyFactory offreStrategyFactory) {
        super(vehiculeRepository);
        this.vehiculeRepository = vehiculeRepository;
        this.offreStrategyFactory = offreStrategyFactory;
    }

    @Override protected String entityName() { return "Véhicule"; }
    @Override protected Long getId(Vehicule vehicule) { return vehicule.getId(); }

    @Override
    protected void copyFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vehicule> findPaged(Pageable pageable) { return vehiculeRepository.findAll(pageable); }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> findAvailable(String ville, CategorieVehicule categorie) {
        return vehiculeRepository.findByAgenceVilleIgnoreCaseAndCategorieAndStatut(
                ville, categorie, StatutVehicule.DISPONIBLE);
    }

    @Override
    public double calculTarif(
            Vehicule vehicule,
            int nbJours,
            VehiculeOffre offre
    ) {
        double tarifJournalier = vehicule.getTarifJournalier().doubleValue();
        return offreStrategyFactory.forOffer(offre)
                .calculer(tarifJournalier, nbJours);
    }
}
