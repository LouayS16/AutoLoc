package tn.esprit.autoloc.dto;

import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {

    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;

    // Informations véhicule
    private Long idVehicule;
    private String immatriculationVehicule;
    private String marqueVehicule;
    private String modeleVehicule;

    // Informations client
    private Long idClient;
    private String nomClient;
    private String prenomClient;

    // Informations contrat associé
    private Long idContrat;
    private LocalDate dateSignatureContrat;
    private BigDecimal montantTotalContrat;
    private Boolean contratValide;
}
