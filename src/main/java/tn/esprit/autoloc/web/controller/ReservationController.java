package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
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
    public List<Reservation> retrieveAllReservations() {
        return reservationService.retrieveAllReservations();
    }

    @Operation(summary = "Récupérer une réservation par ID", description = "Retourne les détails d'une réservation selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réservation trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    @GetMapping("/{id}")
    public Reservation retrieveReservation(@PathVariable("id") Long id) {
        return reservationService.retrieveReservation(id);
    }

    @Operation(summary = "Ajouter une réservation", description = "Crée et enregistre une nouvelle réservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réservation créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de réservation invalides")
    })
    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @Operation(summary = "Mettre à jour une réservation", description = "Met à jour les informations d'une réservation existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réservation mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    @PutMapping
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @Operation(summary = "Supprimer une réservation", description = "Supprime une réservation selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Réservation supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    @DeleteMapping("/{id}")
    public void removeReservation(@PathVariable("id") Long id) {
        reservationService.removeReservation(id);
    }
}
