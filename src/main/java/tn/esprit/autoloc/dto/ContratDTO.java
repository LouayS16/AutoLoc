package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {

    private Long idContrat;

    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le montant total ne peut pas être négatif")
    private BigDecimal montantTotal;

    @NotNull(message = "Le statut de validation est obligatoire")
    private Boolean valide;

    @NotNull(message = "L'identifiant de la réservation est obligatoire")
    private Long idReservation;

    private List<Long> paiementIds;
}

