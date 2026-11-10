package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Tag(name = "Véhicules", description = "API de gestion du parc de véhicules")
@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    @Autowired
    private IVehiculeService vehiculeService;

    @Operation(summary = "Récupérer tous les véhicules", description = "Retourne la liste complète de tous les véhicules du parc")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée avec succès")
    })
    @GetMapping
    public List<VehiculeDTO> retrieveAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @Operation(summary = "Récupérer un véhicule par ID", description = "Retourne les détails d'un véhicule selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Véhicule trouvé avec succès"),
        @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @GetMapping("/{id}")
    public VehiculeDTO retrieveVehicule(@PathVariable("id") Long id) {
        return vehiculeService.retrieveVehicule(id);
    }

    @Operation(summary = "Ajouter un véhicule", description = "Crée et enregistre un nouveau véhicule dans le parc")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Véhicule créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de véhicule invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO addVehicule(@Valid @RequestBody VehiculeDTO vehiculeDTO) {
        return vehiculeService.addVehicule(vehiculeDTO);
    }

    @Operation(summary = "Mettre à jour un véhicule", description = "Met à jour les informations d'un véhicule existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Véhicule mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de véhicule invalides"),
        @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @PutMapping({"", "/{id}"})
    public VehiculeDTO updateVehicule(@Valid @RequestBody VehiculeDTO vehiculeDTO, @PathVariable(name = "id", required = false) Long id) {
        if (id != null) {
            vehiculeDTO.setIdVehicule(id);
        }
        return vehiculeService.updateVehicule(vehiculeDTO);
    }

    @Operation(summary = "Supprimer un véhicule", description = "Supprime un véhicule selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Véhicule supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeVehicule(@PathVariable("id") Long id) {
        vehiculeService.removeVehicule(id);
    }
}
