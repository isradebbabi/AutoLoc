package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;

import java.util.List;

/** Service de consultation des paiements. */
public interface IPaiementService {
    List<Paiement> findAll();
    Paiement findById(Long id);
    List<Paiement> findByContratId(Long contratId);
}
