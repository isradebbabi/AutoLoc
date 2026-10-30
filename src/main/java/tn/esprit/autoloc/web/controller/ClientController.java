package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.AutoLocService;

import java.util.List;

@RestController @RequestMapping("/clients") @RequiredArgsConstructor
public class ClientController {
    private final AutoLocService service;
    @GetMapping public List<Client> findAll() { return service.findClients(); }
    @GetMapping("/{id}") public Client findById(@PathVariable Long id) { return service.findClient(id); }
    @PostMapping public ResponseEntity<Client> create(@Valid @RequestBody Client value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @PutMapping("/{id}") public Client update(@PathVariable Long id, @Valid @RequestBody Client value) { return service.update(value, id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteClient(id); }
}
