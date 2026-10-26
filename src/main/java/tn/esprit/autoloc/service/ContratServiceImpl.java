package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.ContratRepository;

import java.util.List;

@Service
public class ContratServiceImpl implements IContratService {

    @Autowired
    private ContratRepository contratRepository;

    @Override
    public List<Contrat> retrieveAllContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat addContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat updateContrat(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            Contrat existing = contratRepository.findById(contrat.getIdContrat()).orElse(null);
            if (existing != null) {
                if (contrat.getDateSignature() != null) {
                    existing.setDateSignature(contrat.getDateSignature());
                }
                if (contrat.getMontantTotal() != null) {
                    existing.setMontantTotal(contrat.getMontantTotal());
                }
                if (contrat.getValide() != null) {
                    existing.setValide(contrat.getValide());
                }
                if (contrat.getReservation() != null && contrat.getReservation().getIdReservation() != null) {
                    existing.setReservation(contrat.getReservation());
                }
                return contratRepository.save(existing);
            }
        }
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat retrieveContrat(Long idContrat) {
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public void removeContrat(Long idContrat) {
        contratRepository.deleteById(idContrat);
    }
}
