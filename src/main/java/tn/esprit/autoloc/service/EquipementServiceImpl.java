package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipementServiceImpl implements IEquipementService {

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private EquipementMapper equipementMapper;

    @Override
    public List<EquipementDTO> retrieveAllEquipements() {
        return equipementRepository.findAll()
                .stream()
                .map(equipementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EquipementDTO addEquipement(EquipementDTO equipementDTO) {
        Equipement equipement = equipementMapper.toEntity(equipementDTO);
        Equipement saved = equipementRepository.save(equipement);
        return equipementMapper.toDto(saved);
    }

    @Override
    public EquipementDTO updateEquipement(EquipementDTO equipementDTO) {
        if (equipementDTO.getIdEquipement() == null || !equipementRepository.existsById(equipementDTO.getIdEquipement())) {
            throw new ResourceNotFoundException("Équipement non trouvé avec l'identifiant : " + equipementDTO.getIdEquipement());
        }
        Equipement equipement = equipementMapper.toEntity(equipementDTO);
        Equipement updated = equipementRepository.save(equipement);
        return equipementMapper.toDto(updated);
    }

    @Override
    public EquipementDTO retrieveEquipement(Long idEquipement) {
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement non trouvé avec l'identifiant : " + idEquipement));
        return equipementMapper.toDto(equipement);
    }

    @Override
    public void removeEquipement(Long idEquipement) {
        if (!equipementRepository.existsById(idEquipement)) {
            throw new ResourceNotFoundException("Équipement non trouvé avec l'identifiant : " + idEquipement);
        }
        equipementRepository.deleteById(idEquipement);
    }
}
