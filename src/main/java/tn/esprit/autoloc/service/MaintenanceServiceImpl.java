package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceDTO;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.MaintenanceRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements IMaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    @Override
    public List<MaintenanceDTO> retrieveAllMaintenances() {
        return maintenanceRepository.findAll()
                .stream()
                .map(maintenanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceDTO addMaintenance(MaintenanceDTO maintenanceDTO) {
        if (maintenanceDTO.getIdVehicule() != null && !vehiculeRepository.existsById(maintenanceDTO.getIdVehicule())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + maintenanceDTO.getIdVehicule());
        }
        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO);
        Maintenance saved = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toDto(saved);
    }

    @Override
    public MaintenanceDTO updateMaintenance(MaintenanceDTO maintenanceDTO) {
        if (maintenanceDTO.getIdMaintenance() == null || !maintenanceRepository.existsById(maintenanceDTO.getIdMaintenance())) {
            throw new ResourceNotFoundException("Maintenance non trouvée avec l'identifiant : " + maintenanceDTO.getIdMaintenance());
        }
        if (maintenanceDTO.getIdVehicule() != null && !vehiculeRepository.existsById(maintenanceDTO.getIdVehicule())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + maintenanceDTO.getIdVehicule());
        }
        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO);
        Maintenance updated = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toDto(updated);
    }

    @Override
    public MaintenanceDTO retrieveMaintenance(Long idMaintenance) {
        Maintenance maintenance = maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance non trouvée avec l'identifiant : " + idMaintenance));
        return maintenanceMapper.toDto(maintenance);
    }

    @Override
    public void removeMaintenance(Long idMaintenance) {
        if (!maintenanceRepository.existsById(idMaintenance)) {
            throw new ResourceNotFoundException("Maintenance non trouvée avec l'identifiant : " + idMaintenance);
        }
        maintenanceRepository.deleteById(idMaintenance);
    }
}
