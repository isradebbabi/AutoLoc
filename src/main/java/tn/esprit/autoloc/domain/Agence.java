package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Builder
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String nom;
    @NotBlank private String ville;
    private String adresse;
    private String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonManagedReference("agence-vehicules")
    @Builder.Default
    private Set<Vehicule> vehicules = new HashSet<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonManagedReference("agence-employes")
    @Builder.Default
    private Set<Employe> employes = new HashSet<>();

    public void addVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
        vehicule.setAgence(this);
    }

    public void removeVehicule(Vehicule vehicule) {
        vehicules.remove(vehicule);
        if (vehicule != null && vehicule.getAgence() == this) vehicule.setAgence(null);
    }
}
