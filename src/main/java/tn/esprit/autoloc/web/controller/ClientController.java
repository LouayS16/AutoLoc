package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@Tag(name = "Clients", description = "API de gestion des clients")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @Operation(summary = "Récupérer tous les clients", description = "Retourne la liste de tous les clients enregistrés")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des clients récupérée avec succès")
    })
    @GetMapping
    public List<Client> retrieveAllClients() {
        return clientService.retrieveAllClients();
    }

    @Operation(summary = "Récupérer un client par ID", description = "Retourne les détails d'un client spécifique selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client trouvé avec succès"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @GetMapping("/{id}")
    public Client retrieveClient(@PathVariable("id") Long id) {
        return clientService.retrieveClient(id);
    }

    @Operation(summary = "Ajouter un client", description = "Crée et enregistre un nouveau client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données du client invalides")
    })
    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @Operation(summary = "Mettre à jour un client", description = "Met à jour les informations d'un client existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @PutMapping
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @Operation(summary = "Supprimer un client", description = "Supprime un client selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @DeleteMapping("/{id}")
    public void removeClient(@PathVariable("id") Long id) {
        clientService.removeClient(id);
    }

    @Operation(summary = "Ajouter une liste de clients", description = "Crée et enregistre plusieurs clients en une seule requête")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clients créés avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PostMapping("/batch")
    public List<Client> addClients(@RequestBody List<Client> clients) {
        return clientService.addClients(clients);
    }
}
