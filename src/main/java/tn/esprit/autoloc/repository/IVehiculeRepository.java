package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.*;

import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByAgenceVilleIgnoreCaseAndCategorieAndStatut(
            String ville, CategorieVehicule categorie, StatutVehicule statut);
    List<Vehicule> findByStatut(StatutVehicule statut);
}
