package tn.esprit.maryem_laouiti_4cce10.service;

import java.util.List;
import tn.esprit.maryem_laouiti_4cce10.entities.Client;

public interface IClientService {
    Client create(Client client);
    Client findById(Long id);
    List<Client> findAll();
    Client update(Long id, Client client);
    void deleteById(Long id);
}