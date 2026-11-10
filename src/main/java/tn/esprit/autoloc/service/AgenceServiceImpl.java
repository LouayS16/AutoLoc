package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgenceServiceImpl implements IAgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private AgenceMapper agenceMapper;

    @Override
    public List<AgenceDTO> retrieveAllAgences() {
        return agenceRepository.findAll()
                .stream()
                .map(agenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgenceDTO addAgence(AgenceDTO agenceDTO) {
        Agence agence = agenceMapper.toEntity(agenceDTO);
        Agence saved = agenceRepository.save(agence);
        return agenceMapper.toDto(saved);
    }

    @Override
    public AgenceDTO updateAgence(AgenceDTO agenceDTO) {
        if (agenceDTO.getIdAgence() == null || !agenceRepository.existsById(agenceDTO.getIdAgence())) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + agenceDTO.getIdAgence());
        }
        Agence agence = agenceMapper.toEntity(agenceDTO);
        Agence updated = agenceRepository.save(agence);
        return agenceMapper.toDto(updated);
    }

    @Override
    public AgenceDTO retrieveAgence(Long idAgence) {
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + idAgence));
        return agenceMapper.toDto(agence);
    }

    @Override
    public void removeAgence(Long idAgence) {
        if (!agenceRepository.existsById(idAgence)) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + idAgence);
        }
        agenceRepository.deleteById(idAgence);
    }
}
