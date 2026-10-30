package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import tn.esprit.autoloc.domain.Client;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {
    @Override
    @EntityGraph(attributePaths = "reservations")
    List<Client> findAll();

    @Override
    @EntityGraph(attributePaths = "reservations")
    Optional<Client> findById(Long id);
}
