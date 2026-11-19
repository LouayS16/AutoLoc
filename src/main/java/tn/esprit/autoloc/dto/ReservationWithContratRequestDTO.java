package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationWithContratRequestDTO {

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    private StatutReservation statut;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long idVehicule;

    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long idClient;

    private LocalDate dateSignature;

    private BigDecimal montantTotal;

    private Boolean valide;
}
