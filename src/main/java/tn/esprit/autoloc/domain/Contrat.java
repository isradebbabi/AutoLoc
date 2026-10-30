package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Contrat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateSignature;
    @NotNull @DecimalMin("0.0") private BigDecimal montantTotal;
    private boolean valide;

    @OneToOne(optional = false)
    @JoinColumn(name = "reservation_id", unique = true, nullable = false)
    @JsonBackReference("reservation-contract")
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("contract-payments")
    @Builder.Default
    private List<Paiement> paiements = new ArrayList<>();

    @PrePersist
    void initializeDate() {
        if (dateSignature == null) dateSignature = LocalDate.now();
    }
}
