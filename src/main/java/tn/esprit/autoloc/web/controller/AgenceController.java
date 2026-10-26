package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@Tag(name = "Agences", description = "API de gestion des agences de location")
@RestController
@RequestMapping("/api/agences")
public class AgenceController {

    @Autowired
    private IAgenceService agenceService;

    @Operation(summary = "Récupérer toutes les agences", description = "Retourne la liste complète de toutes les agences")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des agences récupérée avec succès")
    })
    @GetMapping
    public List<Agence> retrieveAllAgences() {
        return agenceService.retrieveAllAgences();
    }

    @Operation(summary = "Récupérer une agence par ID", description = "Retourne les détails d'une agence en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agence trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @GetMapping("/{id}")
    public Agence retrieveAgence(@PathVariable("id") Long id) {
        return agenceService.retrieveAgence(id);
    }

    @Operation(summary = "Ajouter une nouvelle agence", description = "Crée et enregistre une nouvelle agence")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agence créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'agence invalides")
    })
    @PostMapping
    public Agence addAgence(@RequestBody Agence agence) {
        return agenceService.addAgence(agence);
    }

    @Operation(summary = "Mettre à jour une agence", description = "Met à jour les informations d'une agence existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agence mise à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @PutMapping
    public Agence updateAgence(@RequestBody Agence agence) {
        return agenceService.updateAgence(agence);
    }

    @Operation(summary = "Supprimer une agence", description = "Supprime une agence selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agence supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @DeleteMapping("/{id}")
    public void removeAgence(@PathVariable("id") Long id) {
        agenceService.removeAgence(id);
    }
}
