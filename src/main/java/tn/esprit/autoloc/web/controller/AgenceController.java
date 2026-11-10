package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.AgenceDTO;
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
    public List<AgenceDTO> retrieveAllAgences() {
        return agenceService.retrieveAllAgences();
    }

    @Operation(summary = "Récupérer une agence par ID", description = "Retourne les détails d'une agence en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agence trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @GetMapping("/{id}")
    public AgenceDTO retrieveAgence(@PathVariable("id") Long id) {
        return agenceService.retrieveAgence(id);
    }

    @Operation(summary = "Ajouter une nouvelle agence", description = "Crée et enregistre une nouvelle agence")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agence créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'agence invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDTO addAgence(@Valid @RequestBody AgenceDTO agenceDTO) {
        return agenceService.addAgence(agenceDTO);
    }

    @Operation(summary = "Mettre à jour une agence", description = "Met à jour les informations d'une agence existante")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agence mise à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'agence invalides"),
        @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @PutMapping({"", "/{id}"})
    public AgenceDTO updateAgence(@Valid @RequestBody AgenceDTO agenceDTO, @PathVariable(name = "id", required = false) Long id) {
        if (id != null) {
            agenceDTO.setIdAgence(id);
        }
        return agenceService.updateAgence(agenceDTO);
    }

    @Operation(summary = "Supprimer une agence", description = "Supprime une agence selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Agence supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAgence(@PathVariable("id") Long id) {
        agenceService.removeAgence(id);
    }
}
