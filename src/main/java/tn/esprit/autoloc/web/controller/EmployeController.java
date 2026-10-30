package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.AutoLocService;

import java.util.List;

@RestController @RequestMapping("/employes") @RequiredArgsConstructor
public class EmployeController {
    private final AutoLocService service;
    @GetMapping public List<Employe> findAll() { return service.findEmployes(); }
    @GetMapping("/{id}") public Employe findById(@PathVariable Long id) { return service.findEmploye(id); }
    @PostMapping public ResponseEntity<Employe> create(@Valid @RequestBody Employe value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @PutMapping("/{id}") public Employe update(@PathVariable Long id, @Valid @RequestBody Employe value) { return service.update(value, id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteEmploye(id); }
}
