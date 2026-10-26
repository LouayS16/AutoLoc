package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@Tag(name = "Équipements", description = "API de gestion des équipements de véhicules")
@RestController
@RequestMapping("/api/equipements")
public class EquipementController {

    @Autowired
    private IEquipementService equipementService;

    @Operation(summary = "Récupérer tous les équipements", description = "Retourne la liste complète de tous les équipements disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des équipements récupérée avec succès")
    })
    @GetMapping
    public List<Equipement> retrieveAllEquipements() {
        return equipementService.retrieveAllEquipements();
    }

    @Operation(summary = "Récupérer un équipement par ID", description = "Retourne les détails d'un équipement en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Équipement trouvé avec succès"),
        @ApiResponse(responseCode = "404", description = "Équipement non trouvé")
    })
    @GetMapping("/{id}")
    public Equipement retrieveEquipement(@PathVariable("id") Long id) {
        return equipementService.retrieveEquipement(id);
    }

    @Operation(summary = "Ajouter un équipement", description = "Crée et enregistre un nouvel équipement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Équipement créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'équipement invalides")
    })
    @PostMapping
    public Equipement addEquipement(@RequestBody Equipement equipement) {
        return equipementService.addEquipement(equipement);
    }

    @Operation(summary = "Mettre à jour un équipement", description = "Met à jour les informations d'un équipement existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Équipement mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Équipement non trouvé")
    })
    @PutMapping
    public Equipement updateEquipement(@RequestBody Equipement equipement) {
        return equipementService.updateEquipement(equipement);
    }

    @Operation(summary = "Supprimer un équipement", description = "Supprime un équipement selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Équipement supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Équipement non trouvé")
    })
    @DeleteMapping("/{id}")
    public void removeEquipement(@PathVariable("id") Long id) {
        equipementService.removeEquipement(id);
    }
}
