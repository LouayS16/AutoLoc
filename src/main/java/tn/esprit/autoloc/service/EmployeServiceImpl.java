package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeDTO;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EmployeRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeServiceImpl implements IEmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private EmployeMapper employeMapper;

    @Override
    public List<EmployeDTO> retrieveAllEmployes() {
        return employeRepository.findAll()
                .stream()
                .map(employeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeDTO addEmploye(EmployeDTO employeDTO) {
        if (employeDTO.getIdAgence() != null && !agenceRepository.existsById(employeDTO.getIdAgence())) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + employeDTO.getIdAgence());
        }
        Employe employe = employeMapper.toEntity(employeDTO);
        Employe saved = employeRepository.save(employe);
        return employeMapper.toDto(saved);
    }

    @Override
    public EmployeDTO updateEmploye(EmployeDTO employeDTO) {
        if (employeDTO.getIdEmploye() == null || !employeRepository.existsById(employeDTO.getIdEmploye())) {
            throw new ResourceNotFoundException("Employé non trouvé avec l'identifiant : " + employeDTO.getIdEmploye());
        }
        if (employeDTO.getIdAgence() != null && !agenceRepository.existsById(employeDTO.getIdAgence())) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'identifiant : " + employeDTO.getIdAgence());
        }
        Employe employe = employeMapper.toEntity(employeDTO);
        Employe updated = employeRepository.save(employe);
        return employeMapper.toDto(updated);
    }

    @Override
    public EmployeDTO retrieveEmploye(Long idEmploye) {
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new ResourceNotFoundException("Employé non trouvé avec l'identifiant : " + idEmploye));
        return employeMapper.toDto(employe);
    }

    @Override
    public void removeEmploye(Long idEmploye) {
        if (!employeRepository.existsById(idEmploye)) {
            throw new ResourceNotFoundException("Employé non trouvé avec l'identifiant : " + idEmploye);
        }
        employeRepository.deleteById(idEmploye);
    }
}
