package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ContratDTO;

import java.time.LocalDate;
import java.util.List;

public interface IContratService {
    List<ContratDTO> retrieveAllContrats();
    ContratDTO addContrat(ContratDTO contratDTO);
    ContratDTO updateContrat(ContratDTO contratDTO);
    ContratDTO retrieveContrat(Long idContrat);
    void removeContrat(Long idContrat);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
