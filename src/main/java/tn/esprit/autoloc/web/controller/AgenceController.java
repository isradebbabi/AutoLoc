package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences")
public class AgenceController {
    private final IAgenceService agenceService;

    @GetMapping
    @Operation(summary = "Lister les agences")
    public List<Agence> findAll() { return agenceService.findAll(); }

    @GetMapping("/{id}")
    public Agence findById(@PathVariable Long id) { return agenceService.findById(id); }

    @GetMapping("/{id}/simuler")
    @Operation(summary = "Simuler le tarif des véhicules d'une agence")
    public String simuler(@PathVariable Long id,
                          @RequestParam @Min(1) int jours,
                          @RequestParam VehiculeOffre offre) {
        Agence agence = agenceService.findById(id);
        return agenceService.simuler(agence, jours, offre);
    }

    @PostMapping
    public ResponseEntity<Agence> create(@Valid @RequestBody Agence agence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agenceService.create(agence));
    }

    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id, @Valid @RequestBody Agence agence) { return agenceService.update(id, agence); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (agenceService.hasVehicules(id)) {
            throw new IllegalArgumentException("Désaffectez les véhicules avant de supprimer l'agence.");
        }
        agenceService.deleteById(id);
    }
}
