package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculeServiceImpl implements IVehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private VehiculeMapper vehiculeMapper;

    @Override
    public List<VehiculeDTO> retrieveAllVehicules() {
        return vehiculeRepository.findAll()
                .stream()
                .map(vehiculeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehiculeDTO addVehicule(VehiculeDTO vehiculeDTO) {
        if (vehiculeDTO.getIdAgence() != null && !agenceRepository.existsById(vehiculeDTO.getIdAgence())) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + vehiculeDTO.getIdAgence());
        }
        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDTO);
        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    public VehiculeDTO updateVehicule(VehiculeDTO vehiculeDTO) {
        if (vehiculeDTO.getIdVehicule() == null || !vehiculeRepository.existsById(vehiculeDTO.getIdVehicule())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + vehiculeDTO.getIdVehicule());
        }
        if (vehiculeDTO.getIdAgence() != null && !agenceRepository.existsById(vehiculeDTO.getIdAgence())) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + vehiculeDTO.getIdAgence());
        }
        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDTO);
        Vehicule updated = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(updated);
    }

    @Override
    public VehiculeDTO retrieveVehicule(Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + idVehicule));
        return vehiculeMapper.toDto(vehicule);
    }

    @Override
    public void removeVehicule(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec l'identifiant : " + idVehicule);
        }
        vehiculeRepository.deleteById(idVehicule);
    }
}
