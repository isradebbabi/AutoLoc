package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import tn.esprit.autoloc.domain.Agence;

import java.util.List;
import java.util.Optional;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {
    @Override
    @EntityGraph(attributePaths = {"vehicules", "employes"})
    List<Agence> findAll();

    @Override
    @EntityGraph(attributePaths = {"vehicules", "employes"})
    Optional<Agence> findById(Long id);
}
