package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

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
}
