package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import tn.esprit.autoloc.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    @Override
    @EntityGraph(attributePaths = {"client", "vehicule", "contrat"})
    List<Reservation> findAll();

    @Override
    @EntityGraph(attributePaths = {"client", "vehicule", "contrat"})
    Optional<Reservation> findById(Long id);

    List<Reservation> findByClientId(Long clientId);
    List<Reservation> findByVehiculeId(Long vehiculeId);
}
