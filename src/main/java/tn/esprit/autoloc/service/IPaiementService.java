package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.PaiementDTO;

import java.util.List;

public interface IPaiementService {
    List<PaiementDTO> retrieveAllPaiements();
    PaiementDTO addPaiement(PaiementDTO paiementDTO);
    PaiementDTO updatePaiement(PaiementDTO paiementDTO);
    PaiementDTO retrievePaiement(Long idPaiement);
    void removePaiement(Long idPaiement);
}
