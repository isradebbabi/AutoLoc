package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(unique = true, nullable = false)
    private String immatriculation;
    @NotBlank private String marque;
    @NotBlank private String modele;
    @NotNull @Enumerated(EnumType.STRING) private CategorieVehicule categorie;
    @NotNull @DecimalMin("0.0") private BigDecimal tarifJournalier;
    @NotNull @Enumerated(EnumType.STRING) @Builder.Default
    private StatutVehicule statut = StatutVehicule.DISPONIBLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    @JsonBackReference("agence-vehicules")
    private Agence agence;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id"))
    @Builder.Default
    @JsonIgnore
    private Set<Equipement> equipements = new HashSet<>();

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonManagedReference("vehicule-maintenances")
    @Builder.Default
    private List<Maintenance> maintenances = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
    @JsonManagedReference("vehicule-reservations")
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}
