package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.AutoLocService;

import java.util.List;

@RestController @RequestMapping("/maintenances") @RequiredArgsConstructor
public class MaintenanceController {
    private final AutoLocService service;
    @GetMapping public List<Maintenance> findAll() { return service.findMaintenances(); }
    @GetMapping("/{id}") public Maintenance findById(@PathVariable Long id) { return service.findMaintenance(id); }
    @PostMapping public ResponseEntity<Maintenance> create(@Valid @RequestBody Maintenance value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteMaintenance(id); }
}
