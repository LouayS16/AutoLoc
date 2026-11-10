package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ReservationDTO;

import java.util.List;

public interface IReservationService {
    List<ReservationDTO> retrieveAllReservations();
    ReservationDTO addReservation(ReservationDTO reservationDTO);
    ReservationDTO updateReservation(ReservationDTO reservationDTO);
    ReservationDTO retrieveReservation(Long idReservation);
    void removeReservation(Long idReservation);
}
