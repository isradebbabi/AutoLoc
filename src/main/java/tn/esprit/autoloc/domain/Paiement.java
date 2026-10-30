package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @DecimalMin("0.0") private BigDecimal montant;
    private LocalDate datePaiement;
    @NotNull @Enumerated(EnumType.STRING) private ModePaiement modePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrat_id", nullable = false)
    @JsonBackReference("contract-payments")
    private Contrat contrat;

    @PrePersist
    void initializeDate() {
        if (datePaiement == null) datePaiement = LocalDate.now();
    }
}
