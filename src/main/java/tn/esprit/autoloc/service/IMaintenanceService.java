package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.MaintenanceDTO;

import java.util.List;

public interface IMaintenanceService {
    List<MaintenanceDTO> retrieveAllMaintenances();
    MaintenanceDTO addMaintenance(MaintenanceDTO maintenanceDTO);
    MaintenanceDTO updateMaintenance(MaintenanceDTO maintenanceDTO);
    MaintenanceDTO retrieveMaintenance(Long idMaintenance);
    void removeMaintenance(Long idMaintenance);
}
