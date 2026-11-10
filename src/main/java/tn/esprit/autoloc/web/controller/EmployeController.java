package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EmployeDTO;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@Tag(name = "Employés", description = "API de gestion des employés")
@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private IEmployeService employeService;

    @Operation(summary = "Récupérer tous les employés", description = "Retourne la liste complète de tous les employés")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès")
    })
    @GetMapping
    public List<EmployeDTO> retrieveAllEmployes() {
        return employeService.retrieveAllEmployes();
    }

    @Operation(summary = "Récupérer un employé par ID", description = "Retourne les détails d'un employé selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employé trouvé avec succès"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    @GetMapping("/{id}")
    public EmployeDTO retrieveEmploye(@PathVariable("id") Long id) {
        return employeService.retrieveEmploye(id);
    }

    @Operation(summary = "Ajouter un employé", description = "Crée et enregistre un nouvel employé")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Employé créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'employé invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDTO addEmploye(@Valid @RequestBody EmployeDTO employeDTO) {
        return employeService.addEmploye(employeDTO);
    }

    @Operation(summary = "Mettre à jour un employé", description = "Met à jour les informations d'un employé existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employé mis à jour avec succès"),
        @ApiResponse(responseCode = "400", description = "Données d'employé invalides"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    @PutMapping({"", "/{id}"})
    public EmployeDTO updateEmploye(@Valid @RequestBody EmployeDTO employeDTO, @PathVariable(name = "id", required = false) Long id) {
        if (id != null) {
            employeDTO.setIdEmploye(id);
        }
        return employeService.updateEmploye(employeDTO);
    }

    @Operation(summary = "Supprimer un employé", description = "Supprime un employé selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Employé supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmploye(@PathVariable("id") Long id) {
        employeService.removeEmploye(id);
    }
}
