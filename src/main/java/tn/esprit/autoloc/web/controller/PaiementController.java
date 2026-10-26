package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@Tag(name = "Paiements", description = "API de gestion des paiements")
@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private IPaiementService paiementService;

    @Operation(summary = "Récupérer tous les paiements", description = "Retourne la liste complète de tous les paiements enregistrés")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des paiements récupérée avec succès")
    })
    @GetMapping
    public List<Paiement> retrieveAllPaiements() {
        return paiementService.retrieveAllPaiements();
    }

    @Operation(summary = "Récupérer un paiement par ID", description = "Retourne les détails d'un paiement en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paiement trouvé avec succès"),
        @ApiResponse(responseCode = "404", description = "Paiement non trouvé")
    })
    @GetMapping("/{id}")
    public Paiement retrievePaiement(@PathVariable("id") Long id) {
        return paiementService.retrievePaiement(id);
    }

    @Operation(summary = "Ajouter un paiement", description = "Crée et enregistre un nouveau paiement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paiement créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données de paiement invalides")
    })
    @PostMapping
    public Paiement addPaiement(@RequestBody Paiement paiement) {
        return paiementService.addPaiement(paiement);
    }

    @Operation(summary = "Mettre à jour un paiement", description = "Met à jour les informations d'un paiement existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paiement mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Paiement non trouvé")
    })
    @PutMapping({"", "/{id}"})
    public Paiement updatePaiement(@RequestBody Paiement paiement, @PathVariable(name = "id", required = false) Long id) {
        if (id != null) {
            paiement.setIdPaiement(id);
        }
        return paiementService.updatePaiement(paiement);
    }

    @Operation(summary = "Supprimer un paiement", description = "Supprime un paiement selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paiement supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Paiement non trouvé")
    })
    @DeleteMapping("/{id}")
    public void removePaiement(@PathVariable("id") Long id) {
        paiementService.removePaiement(id);
    }
}
