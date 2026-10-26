package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.PaiementRepository;

import java.util.List;

@Service
public class PaiementServiceImpl implements IPaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Override
    public List<Paiement> retrieveAllPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement addPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement updatePaiement(Paiement paiement) {
        if (paiement.getIdPaiement() != null) {
            Paiement existing = paiementRepository.findById(paiement.getIdPaiement()).orElse(null);
            if (existing != null) {
                if (paiement.getMontant() != null) {
                    existing.setMontant(paiement.getMontant());
                }
                if (paiement.getDatePaiement() != null) {
                    existing.setDatePaiement(paiement.getDatePaiement());
                }
                if (paiement.getModePaiement() != null) {
                    existing.setModePaiement(paiement.getModePaiement());
                }
                if (paiement.getContrat() != null && paiement.getContrat().getIdContrat() != null) {
                    existing.setContrat(paiement.getContrat());
                }
                return paiementRepository.save(existing);
            }
        }
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement retrievePaiement(Long idPaiement) {
        return paiementRepository.findById(idPaiement).orElse(null);
    }

    @Override
    public void removePaiement(Long idPaiement) {
        paiementRepository.deleteById(idPaiement);
    }
}
