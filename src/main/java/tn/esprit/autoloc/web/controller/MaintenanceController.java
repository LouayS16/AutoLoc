package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "API de gestion des entretiens et maintenances de véhicules")
@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {

    @Autowired
    private IMaintenanceService maintenanceService;

    @Operation(summary = "Récupérer toutes les maintenances", description = "Retourne la liste complète de toutes les opérations de maintenance")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des maintenances récupérée avec succès")
    })
    @GetMapping
    public List<Maintenance> retrieveAllMaintenances() {
        return maintenanceService.retrieveAllMaintenances();
    }

    @Operation(summary = "Récupérer une maintenance par ID", description = "Retourne les détails d'une maintenance en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Maintenance trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Maintenance non trouvée")
    })
    @GetMapping("/{id}")
    public Maintenance retrieveMaintenance(@PathVariable("id") Long id) {
        return maintenanceService.retrieveMaintenance(id);
    }

    @Operation(summary = "Ajouter une maintenance", description = "Crée et enregistre une nouvelle opération de maintenance")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Maintenance ajoutée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de maintenance invalides")
    })
    @PostMapping
    public Maintenance addMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.addMaintenance(maintenance);
    }

    @Operation(summary = "Mettre à jour une maintenance", description = "Met à jour les détails d'une opération de maintenance existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Maintenance mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Maintenance non trouvée")
    })
    @PutMapping
    public Maintenance updateMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.updateMaintenance(maintenance);
    }

    @Operation(summary = "Supprimer une maintenance", description = "Supprime une opération de maintenance selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Maintenance supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Maintenance non trouvée")
    })
    @DeleteMapping("/{id}")
    public void removeMaintenance(@PathVariable("id") Long id) {
        maintenanceService.removeMaintenance(id);
    }
}
