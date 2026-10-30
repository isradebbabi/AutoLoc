package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.exception.BadRequestException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AutoLocService {
    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;
    private final IClientRepository clientRepository;
    private final IEmployeRepository employeRepository;
    private final IReservationRepository reservationRepository;
    private final IContratRepository contratRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final IAgenceRepository agenceRepository;

    public Equipement create(Equipement value) { return equipementRepository.save(value); }
    public List<Equipement> findEquipements() { return equipementRepository.findAll(); }
    public Equipement findEquipement(Long id) { return equipementRepository.findById(id).orElseThrow(() -> missing("Équipement", id)); }
    public Equipement update(Equipement value, Long id) { Equipement e=findEquipement(id); e.setLibelle(value.getLibelle()); return e; }
    public void deleteEquipement(Long id) { equipementRepository.delete(findEquipement(id)); }

    public Client create(Client value) { return clientRepository.save(value); }
    public List<Client> findClients() { return clientRepository.findAll(); }
    public Client findClient(Long id) { return clientRepository.findById(id).orElseThrow(() -> missing("Client", id)); }
    public Client update(Client value, Long id) { Client c=findClient(id); c.setNom(value.getNom()); c.setPrenom(value.getPrenom()); c.setEmail(value.getEmail()); c.setTelephone(value.getTelephone()); c.setNumPermis(value.getNumPermis()); return c; }
    public void deleteClient(Long id) { clientRepository.delete(findClient(id)); }

    public Employe create(Employe value) { attachAgence(value); return employeRepository.save(value); }
    public List<Employe> findEmployes() { return employeRepository.findAll(); }
    public Employe findEmploye(Long id) { return employeRepository.findById(id).orElseThrow(() -> missing("Employé", id)); }
    public Employe update(Employe value, Long id) { Employe e=findEmploye(id); e.setNom(value.getNom()); e.setPrenom(value.getPrenom()); e.setRole(value.getRole()); attachAgence(value); e.setAgence(value.getAgence()); return e; }
    public void deleteEmploye(Long id) { employeRepository.delete(findEmploye(id)); }

    public Reservation create(Reservation value) {
        attachReservationRelations(value);
        if (value.getDateFin().isBefore(value.getDateDebut())) throw new BadRequestException("La date de fin doit être après la date de début.");
        return reservationRepository.save(value);
    }
    public List<Reservation> findReservations() { return reservationRepository.findAll(); }
    public Reservation findReservation(Long id) { return reservationRepository.findById(id).orElseThrow(() -> missing("Réservation", id)); }
    public Reservation update(Reservation value, Long id) { Reservation r=findReservation(id); r.setDateDebut(value.getDateDebut()); r.setDateFin(value.getDateFin()); r.setStatut(value.getStatut()); attachReservationRelations(value); r.setClient(value.getClient()); r.setVehicule(value.getVehicule()); return r; }
    public void deleteReservation(Long id) { reservationRepository.delete(findReservation(id)); }

    public Contrat create(Contrat value) { if (value.getReservation()!=null && value.getReservation().getId()!=null) value.setReservation(reservationRepository.getReferenceById(value.getReservation().getId())); if (value.getPaiements()!=null) value.getPaiements().forEach(p -> p.setContrat(value)); return contratRepository.save(value); }
    public List<Contrat> findContrats() { return contratRepository.findAll(); }
    public Contrat findContrat(Long id) { return contratRepository.findById(id).orElseThrow(() -> missing("Contrat", id)); }
    public Contrat update(Contrat value, Long id) { Contrat c=findContrat(id); c.setMontantTotal(value.getMontantTotal()); c.setValide(value.isValide()); return c; }
    public void deleteContrat(Long id) { contratRepository.delete(findContrat(id)); }

    public Maintenance create(Maintenance value) { if (value.getVehicule()!=null && value.getVehicule().getId()!=null) value.setVehicule(vehiculeRepository.getReferenceById(value.getVehicule().getId())); return maintenanceRepository.save(value); }
    public List<Maintenance> findMaintenances() { return maintenanceRepository.findAll(); }
    public Maintenance findMaintenance(Long id) { return maintenanceRepository.findById(id).orElseThrow(() -> missing("Maintenance", id)); }
    public void deleteMaintenance(Long id) { maintenanceRepository.delete(findMaintenance(id)); }

    private void attachAgence(Employe value) { if (value.getAgence()!=null && value.getAgence().getId()!=null) value.setAgence(agenceRepository.getReferenceById(value.getAgence().getId())); }
    private void attachReservationRelations(Reservation value) { if (value.getClient()==null || value.getClient().getId()==null || value.getVehicule()==null || value.getVehicule().getId()==null) throw new BadRequestException("Une réservation doit contenir un client et un véhicule existants."); value.setClient(clientRepository.getReferenceById(value.getClient().getId())); value.setVehicule(vehiculeRepository.getReferenceById(value.getVehicule().getId())); }
    private ResourceNotFoundException missing(String type, Long id) { return new ResourceNotFoundException(type + " introuvable avec l'id " + id); }
}
