package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@Tag(name = "Contrats", description = "API de gestion des contrats de location")
@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    @Autowired
    private IContratService contratService;

    @Operation(summary = "Récupérer tous les contrats", description = "Retourne la liste complète de tous les contrats de location")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des contrats récupérée avec succès")
    })
    @GetMapping
    public List<Contrat> retrieveAllContrats() {
        return contratService.retrieveAllContrats();
    }

    @Operation(summary = "Récupérer un contrat par ID", description = "Retourne les détails d'un contrat en fonction de son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrat trouvé avec succès"),
        @ApiResponse(responseCode = "404", description = "Contrat non trouvé")
    })
    @GetMapping("/{id}")
    public Contrat retrieveContrat(@PathVariable("id") Long id) {
        return contratService.retrieveContrat(id);
    }

    @Operation(summary = "Ajouter un nouveau contrat", description = "Crée et enregistre un nouveau contrat de location")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrat créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données du contrat invalides")
    })
    @PostMapping
    public Contrat addContrat(@RequestBody Contrat contrat) {
        return contratService.addContrat(contrat);
    }

    @Operation(summary = "Mettre à jour un contrat", description = "Met à jour les informations d'un contrat existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrat mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Contrat non trouvé")
    })
    @PutMapping({"", "/{id}"})
    public Contrat updateContrat(@RequestBody Contrat contrat, @PathVariable(name = "id", required = false) Long id) {
        if (id != null) {
            contrat.setIdContrat(id);
        }
        return contratService.updateContrat(contrat);
    }

    @Operation(summary = "Supprimer un contrat", description = "Supprime un contrat selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contrat supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Contrat non trouvé")
    })
    @DeleteMapping("/{id}")
    public void removeContrat(@PathVariable("id") Long id) {
        contratService.removeContrat(id);
    }
}
