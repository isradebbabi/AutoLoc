package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.calcul.VehiculeOffre;

public interface IAgenceService extends IGenericService<Agence, Long> {
    boolean hasVehicules(Long id);
    String simuler(Agence agence, int nbJours, VehiculeOffre offre);
}
