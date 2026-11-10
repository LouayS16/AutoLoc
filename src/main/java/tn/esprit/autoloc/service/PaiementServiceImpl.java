package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.ContratRepository;
import tn.esprit.autoloc.repository.PaiementRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementServiceImpl implements IPaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private PaiementMapper paiementMapper;

    @Override
    public List<PaiementDTO> retrieveAllPaiements() {
        return paiementRepository.findAll()
                .stream()
                .map(paiementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaiementDTO addPaiement(PaiementDTO paiementDTO) {
        if (paiementDTO.getIdContrat() != null && !contratRepository.existsById(paiementDTO.getIdContrat())) {
            throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + paiementDTO.getIdContrat());
        }
        Paiement paiement = paiementMapper.toEntity(paiementDTO);
        Paiement saved = paiementRepository.save(paiement);
        return paiementMapper.toDto(saved);
    }

    @Override
    public PaiementDTO updatePaiement(PaiementDTO paiementDTO) {
        if (paiementDTO.getIdPaiement() == null || !paiementRepository.existsById(paiementDTO.getIdPaiement())) {
            throw new ResourceNotFoundException("Paiement non trouvé avec l'identifiant : " + paiementDTO.getIdPaiement());
        }
        if (paiementDTO.getIdContrat() != null && !contratRepository.existsById(paiementDTO.getIdContrat())) {
            throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + paiementDTO.getIdContrat());
        }
        Paiement existing = paiementRepository.findById(paiementDTO.getIdPaiement()).orElseThrow(
                () -> new ResourceNotFoundException("Paiement non trouvé avec l'identifiant : " + paiementDTO.getIdPaiement())
        );

        if (paiementDTO.getMontant() != null) {
            existing.setMontant(paiementDTO.getMontant());
        }
        if (paiementDTO.getDatePaiement() != null) {
            existing.setDatePaiement(paiementDTO.getDatePaiement());
        }
        if (paiementDTO.getModePaiement() != null) {
            existing.setModePaiement(paiementDTO.getModePaiement());
        }
        if (paiementDTO.getIdContrat() != null) {
            Contrat contrat = contratRepository.findById(paiementDTO.getIdContrat()).orElseThrow(
                    () -> new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + paiementDTO.getIdContrat())
            );
            existing.setContrat(contrat);
        }

        Paiement updated = paiementRepository.save(existing);
        return paiementMapper.toDto(updated);
    }

    @Override
    public PaiementDTO retrievePaiement(Long idPaiement) {
        Paiement paiement = paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé avec l'identifiant : " + idPaiement));
        return paiementMapper.toDto(paiement);
    }

    @Override
    public void removePaiement(Long idPaiement) {
        if (!paiementRepository.existsById(idPaiement)) {
            throw new ResourceNotFoundException("Paiement non trouvé avec l'identifiant : " + idPaiement);
        }
        paiementRepository.deleteById(idPaiement);
    }
}
