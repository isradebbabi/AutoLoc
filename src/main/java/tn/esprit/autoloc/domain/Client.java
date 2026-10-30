package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String nom;
    @NotBlank private String prenom;
    @Email @NotBlank @Column(unique = true, nullable = false) private String email;
    private String telephone;
    private String numPermis;
    private LocalDate dateInscription;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonManagedReference("client-reservations")
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    @PrePersist
    void initializeDate() {
        if (dateInscription == null) dateInscription = LocalDate.now();
    }
}
