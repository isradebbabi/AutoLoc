package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.AutoLocService;

import java.util.List;

@RestController @RequestMapping("/equipements") @RequiredArgsConstructor
public class EquipementController {
    private final AutoLocService service;
    @GetMapping public List<Equipement> findAll() { return service.findEquipements(); }
    @GetMapping("/{id}") public Equipement findById(@PathVariable Long id) { return service.findEquipement(id); }
    @PostMapping public ResponseEntity<Equipement> create(@Valid @RequestBody Equipement value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @PutMapping("/{id}") public Equipement update(@PathVariable Long id, @Valid @RequestBody Equipement value) { return service.update(value, id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteEquipement(id); }
}
