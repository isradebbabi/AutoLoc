package tn.esprit.autoloc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

public interface IVehiculeService extends IGenericService<Vehicule, Long> {
    Page<Vehicule> findPaged(Pageable pageable);
    List<Vehicule> findAvailable(String ville, CategorieVehicule categorie);
    double calculTarif(Vehicule vehicule, int nombreJours, VehiculeOffre offre);
}
