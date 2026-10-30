package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules")
public class VehiculeController {
    private final IVehiculeService service;

    @GetMapping public List<Vehicule> findAll() { return service.findAll(); }
    @GetMapping("/paged") public Page<Vehicule> findPaged(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) { return service.findPaged(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))); }
    @GetMapping("/{id}") public Vehicule findById(@PathVariable Long id) { return service.findById(id); }
    @GetMapping("/disponibles") public List<Vehicule> available(@RequestParam String ville, @RequestParam CategorieVehicule categorie) { return service.findAvailable(ville, categorie); }
    @PostMapping public ResponseEntity<Vehicule> create(@Valid @RequestBody Vehicule value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @PutMapping("/{id}") public Vehicule update(@PathVariable Long id, @Valid @RequestBody Vehicule value) { return service.update(id, value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteById(id); }
}
