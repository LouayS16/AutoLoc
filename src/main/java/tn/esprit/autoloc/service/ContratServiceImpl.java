package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.ContratRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContratServiceImpl implements IContratService {

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ContratMapper contratMapper;

    @Override
    public List<ContratDTO> retrieveAllContrats() {
        return contratRepository.findAll()
                .stream()
                .map(contratMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContratDTO addContrat(ContratDTO contratDTO) {
        if (contratDTO.getIdReservation() != null && !reservationRepository.existsById(contratDTO.getIdReservation())) {
            throw new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + contratDTO.getIdReservation());
        }
        Contrat contrat = contratMapper.toEntity(contratDTO);
        Contrat saved = contratRepository.save(contrat);
        return contratMapper.toDto(saved);
    }

    @Override
    public ContratDTO updateContrat(ContratDTO contratDTO) {
        if (contratDTO.getIdContrat() == null || !contratRepository.existsById(contratDTO.getIdContrat())) {
            throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + contratDTO.getIdContrat());
        }
        if (contratDTO.getIdReservation() != null && !reservationRepository.existsById(contratDTO.getIdReservation())) {
            throw new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + contratDTO.getIdReservation());
        }
        Contrat existing = contratRepository.findById(contratDTO.getIdContrat()).orElseThrow(
                () -> new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + contratDTO.getIdContrat())
        );

        if (contratDTO.getDateSignature() != null) {
            existing.setDateSignature(contratDTO.getDateSignature());
        }
        if (contratDTO.getMontantTotal() != null) {
            existing.setMontantTotal(contratDTO.getMontantTotal());
        }
        if (contratDTO.getValide() != null) {
            existing.setValide(contratDTO.getValide());
        }
        if (contratDTO.getIdReservation() != null) {
            Reservation reservation = reservationRepository.findById(contratDTO.getIdReservation())
                    .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + contratDTO.getIdReservation()));
            existing.setReservation(reservation);
        }

        Contrat updated = contratRepository.save(existing);
        return contratMapper.toDto(updated);
    }

    @Override
    public ContratDTO retrieveContrat(Long idContrat) {
        Contrat contrat = contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + idContrat));
        return contratMapper.toDto(contrat);
    }

    @Override
    public void removeContrat(Long idContrat) {
        if (!contratRepository.existsById(idContrat)) {
            throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + idContrat);
        }
        contratRepository.deleteById(idContrat);
    }

    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        Reservation reservation = reservationRepository.findFirstByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée pour la période du " + dateDebut + " au " + dateFin));

        Contrat contrat = contratRepository.findByReservation(reservation).orElse(new Contrat());
        contrat.setReservation(reservation);
        contrat.setDateSignature(dateSignature != null ? dateSignature : LocalDate.now());
        contrat.setValide(Boolean.TRUE);

        long nbJours = ChronoUnit.DAYS.between(dateDebut, dateFin);
        if (nbJours <= 0) {
            nbJours = 1;
        }

        BigDecimal tarifJournalier = (reservation.getVehicule() != null && reservation.getVehicule().getTarifJournalier() != null)
                ? reservation.getVehicule().getTarifJournalier()
                : BigDecimal.ZERO;

        contrat.setMontantTotal(tarifJournalier.multiply(BigDecimal.valueOf(nbJours)));

        Contrat saved = contratRepository.save(contrat);
        return contratMapper.toDto(saved);
    }
}
