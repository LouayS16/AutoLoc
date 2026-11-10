package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.VehiculeDTO;

import java.util.List;

public interface IVehiculeService {
    List<VehiculeDTO> retrieveAllVehicules();
    VehiculeDTO addVehicule(VehiculeDTO vehiculeDTO);
    VehiculeDTO updateVehicule(VehiculeDTO vehiculeDTO);
    VehiculeDTO retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
}
