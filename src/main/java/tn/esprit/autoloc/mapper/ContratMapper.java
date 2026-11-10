package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ContratDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ContratMapper {

    public ContratDTO toDto(Contrat contrat) {
        if (contrat == null) {
            return null;
        }
        return ContratDTO.builder()
                .idContrat(contrat.getIdContrat())
                .dateSignature(contrat.getDateSignature())
                .montantTotal(contrat.getMontantTotal())
                .valide(contrat.getValide())
                .idReservation(contrat.getReservation() != null ? contrat.getReservation().getIdReservation() : null)
                .paiementIds(contrat.getPaiements() != null
                        ? contrat.getPaiements().stream().map(Paiement::getIdPaiement).collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public Contrat toEntity(ContratDTO dto) {
        if (dto == null) {
            return null;
        }
        Contrat contrat = new Contrat();
        contrat.setIdContrat(dto.getIdContrat());
        contrat.setDateSignature(dto.getDateSignature());
        contrat.setMontantTotal(dto.getMontantTotal());
        contrat.setValide(dto.getValide());

        if (dto.getIdReservation() != null) {
            Reservation reservation = new Reservation();
            reservation.setIdReservation(dto.getIdReservation());
            contrat.setReservation(reservation);
        }

        return contrat;
    }
}

