package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.AutoLocService;

import java.util.List;

@RestController @RequestMapping("/contrats") @RequiredArgsConstructor
public class ContratController {
    private final AutoLocService service;
    @GetMapping public List<Contrat> findAll() { return service.findContrats(); }
    @GetMapping("/{id}") public Contrat findById(@PathVariable Long id) { return service.findContrat(id); }
    @PostMapping public ResponseEntity<Contrat> create(@Valid @RequestBody Contrat value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @PutMapping("/{id}") public Contrat update(@PathVariable Long id, @Valid @RequestBody Contrat value) { return service.update(value, id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteContrat(id); }
}
