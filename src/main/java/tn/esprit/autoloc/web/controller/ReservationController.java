package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.AutoLocService;

import java.util.List;

@RestController @RequestMapping("/reservations") @RequiredArgsConstructor
public class ReservationController {
    private final AutoLocService service;
    @GetMapping public List<Reservation> findAll() { return service.findReservations(); }
    @GetMapping("/{id}") public Reservation findById(@PathVariable Long id) { return service.findReservation(id); }
    @PostMapping public ResponseEntity<Reservation> create(@Valid @RequestBody Reservation value) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(value)); }
    @PutMapping("/{id}") public Reservation update(@PathVariable Long id, @Valid @RequestBody Reservation value) { return service.update(value, id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteReservation(id); }
}
