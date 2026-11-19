package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.dto.ReservationResponseDTO;
import tn.esprit.autoloc.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.repository.ContratRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<ReservationDTO> retrieveAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        if (reservationDTO.getIdVehicule() != null && !vehiculeRepository.existsById(reservationDTO.getIdVehicule())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + reservationDTO.getIdVehicule());
        }
        if (reservationDTO.getIdClient() != null && !clientRepository.existsById(reservationDTO.getIdClient())) {
            throw new ResourceNotFoundException("Client non trouvé avec l'identifiant : " + reservationDTO.getIdClient());
        }
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toDto(saved);
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {
        if (reservationDTO.getIdReservation() == null || !reservationRepository.existsById(reservationDTO.getIdReservation())) {
            throw new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + reservationDTO.getIdReservation());
        }
        if (reservationDTO.getIdVehicule() != null && !vehiculeRepository.existsById(reservationDTO.getIdVehicule())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + reservationDTO.getIdVehicule());
        }
        if (reservationDTO.getIdClient() != null && !clientRepository.existsById(reservationDTO.getIdClient())) {
            throw new ResourceNotFoundException("Client non trouvé avec l'identifiant : " + reservationDTO.getIdClient());
        }
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation updated = reservationRepository.save(reservation);
        return reservationMapper.toDto(updated);
    }

    @Override
    public ReservationDTO retrieveReservation(Long idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + idReservation));
        return reservationMapper.toDto(reservation);
    }

    @Override
    public void removeReservation(Long idReservation) {
        if (!reservationRepository.existsById(idReservation)) {
            throw new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + idReservation);
        }
        reservationRepository.deleteById(idReservation);
    }

    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'identifiant : " + idReservation));

        Client client = clientRepository.findFirstByNomAndPrenom(nom, prenom)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec le nom : '" + nom + "' et prénom : '" + prenom + "'"));

        reservation.setClient(client);
        Reservation savedReservation = reservationRepository.save(reservation);

        Contrat contrat = contratRepository.findByReservation(savedReservation).orElse(null);
        return reservationMapper.toResponseDto(savedReservation, contrat);
    }

    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.getIdVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + requestDTO.getIdVehicule()));

        Client client = clientRepository.findById(requestDTO.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'identifiant : " + requestDTO.getIdClient()));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.getDateDebut());
        reservation.setDateFin(requestDTO.getDateFin());
        reservation.setStatut(requestDTO.getStatut() != null ? requestDTO.getStatut() : StatutReservation.CONFIRMEE);
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
        Reservation savedReservation = reservationRepository.save(reservation);

        BigDecimal montantTotal = requestDTO.getMontantTotal();
        if (montantTotal == null) {
            long nbJours = ChronoUnit.DAYS.between(requestDTO.getDateDebut(), requestDTO.getDateFin());
            if (nbJours <= 0) {
                nbJours = 1;
            }
            BigDecimal tarif = vehicule.getTarifJournalier() != null ? vehicule.getTarifJournalier() : BigDecimal.ZERO;
            montantTotal = tarif.multiply(BigDecimal.valueOf(nbJours));
        }

        Contrat contrat = new Contrat();
        contrat.setReservation(savedReservation);
        contrat.setDateSignature(requestDTO.getDateSignature() != null ? requestDTO.getDateSignature() : LocalDate.now());
        contrat.setValide(requestDTO.getValide() != null ? requestDTO.getValide() : Boolean.TRUE);
        contrat.setMontantTotal(montantTotal);

        Contrat savedContrat = contratRepository.save(contrat);

        return reservationMapper.toResponseDto(savedReservation, savedContrat);
    }
}
