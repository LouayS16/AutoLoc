package tn.esprit.autoloc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientDTO;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.web.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<ClientDTO> retrieveAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO addClient(ClientDTO c) {
        Client client = clientMapper.toEntity(c);
        Client saved = clientRepository.save(client);
        return clientMapper.toDto(saved);
    }

    @Override
    public ClientDTO updateClient(ClientDTO c) {
        if (c.getIdClient() == null || !clientRepository.existsById(c.getIdClient())) {
            throw new ResourceNotFoundException("Client non trouvé avec l'identifiant : " + c.getIdClient());
        }
        Client client = clientMapper.toEntity(c);
        Client updated = clientRepository.save(client);
        return clientMapper.toDto(updated);
    }

    @Override
    public ClientDTO retrieveClient(Long idClient) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'identifiant : " + idClient));
        return clientMapper.toDto(client);
    }

    @Override
    public void removeClient(Long idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new ResourceNotFoundException("Client non trouvé avec l'identifiant : " + idClient);
        }
        clientRepository.deleteById(idClient);
    }

    @Override
    public List<ClientDTO> addClients(List<ClientDTO> clients) {
        List<Client> clientEntities = clients.stream()
                .map(clientMapper::toEntity)
                .collect(Collectors.toList());
        List<Client> saved = clientRepository.saveAll(clientEntities);
        return saved.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }
}
