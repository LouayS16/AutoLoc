package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.dto.ReservationResponseDTO;

@Component
public class ReservationMapper {

    public ReservationDTO toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        String nomClient = null;
        if (reservation.getClient() != null) {
            nomClient = (reservation.getClient().getPrenom() != null ? reservation.getClient().getPrenom() + " " : "")
                    + (reservation.getClient().getNom() != null ? reservation.getClient().getNom() : "");
            nomClient = nomClient.trim();
        }

        return ReservationDTO.builder()
                .idReservation(reservation.getIdReservation())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .statut(reservation.getStatut())
                .idVehicule(reservation.getVehicule() != null ? reservation.getVehicule().getIdVehicule() : null)
                .idClient(reservation.getClient() != null ? reservation.getClient().getIdClient() : null)
                .immatriculationVehicule(reservation.getVehicule() != null ? reservation.getVehicule().getImmatriculation() : null)
                .nomClient(nomClient)
                .build();
    }

    public Reservation toEntity(ReservationDTO dto) {
        if (dto == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setIdReservation(dto.getIdReservation());
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());

        if (dto.getIdVehicule() != null) {
            Vehicule vehicule = new Vehicule();
            vehicule.setIdVehicule(dto.getIdVehicule());
            reservation.setVehicule(vehicule);
        }

        if (dto.getIdClient() != null) {
            Client client = new Client();
            client.setIdClient(dto.getIdClient());
            reservation.setClient(client);
        }

        return reservation;
    }

    public ReservationResponseDTO toResponseDto(Reservation reservation) {
        return toResponseDto(reservation, null);
    }

    public ReservationResponseDTO toResponseDto(Reservation reservation, Contrat contrat) {
        if (reservation == null) {
            return null;
        }
        ReservationResponseDTO.ReservationResponseDTOBuilder builder = ReservationResponseDTO.builder()
                .idReservation(reservation.getIdReservation())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .statut(reservation.getStatut());

        if (reservation.getVehicule() != null) {
            builder.idVehicule(reservation.getVehicule().getIdVehicule())
                    .immatriculationVehicule(reservation.getVehicule().getImmatriculation())
                    .marqueVehicule(reservation.getVehicule().getMarque())
                    .modeleVehicule(reservation.getVehicule().getModele());
        }

        if (reservation.getClient() != null) {
            builder.idClient(reservation.getClient().getIdClient())
                    .nomClient(reservation.getClient().getNom())
                    .prenomClient(reservation.getClient().getPrenom());
        }

        if (contrat != null) {
            builder.idContrat(contrat.getIdContrat())
                    .dateSignatureContrat(contrat.getDateSignature())
                    .montantTotalContrat(contrat.getMontantTotal())
                    .contratValide(contrat.getValide());
        }

        return builder.build();
    }
}
