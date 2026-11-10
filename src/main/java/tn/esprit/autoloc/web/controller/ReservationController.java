package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@Tag(name = "Réservations", description = "API de gestion des réservations de véhicules")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @Operation(summary = "Récupérer toutes les réservations", description = "Retourne la liste complète de toutes les réservations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des réservations récupérée avec succès")
    })
    @GetMapping
    public List<ReservationDTO> retrieveAllReservations() {
        return reservationService.retrieveAllReservations();
    }

    @Operation(summary = "Récupérer une réservation par ID", description = "Retourne les détails d'une réservation selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réservation trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    @GetMapping("/{id}")
    public ReservationDTO retrieveReservation(@PathVariable("id") Long id) {
        return reservationService.retrieveReservation(id);
    }

    @Operation(summary = "Ajouter une réservation", description = "Crée et enregistre une nouvelle réservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Réservation créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de réservation invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO addReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.addReservation(reservationDTO);
    }

    @Operation(summary = "Mettre à jour une réservation", description = "Met à jour les informations d'une réservation existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réservation mise à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de réservation invalides"),
        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    @PutMapping({"", "/{id}"})
    public ReservationDTO updateReservation(@Valid @RequestBody ReservationDTO reservationDTO, @PathVariable(name = "id", required = false) Long id) {
        if (id != null) {
            reservationDTO.setIdReservation(id);
        }
        return reservationService.updateReservation(reservationDTO);
    }

    @Operation(summary = "Supprimer une réservation", description = "Supprime une réservation selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Réservation supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReservation(@PathVariable("id") Long id) {
        reservationService.removeReservation(id);
    }
}
