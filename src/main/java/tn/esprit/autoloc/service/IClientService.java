package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ClientDTO;

import java.util.List;

public interface IClientService {
    List<ClientDTO> retrieveAllClients();
    ClientDTO addClient(ClientDTO c);
    ClientDTO updateClient(ClientDTO c);
    ClientDTO retrieveClient(Long idClient);
    void removeClient(Long idClient);
    List<ClientDTO> addClients(List<ClientDTO> clients);
}
