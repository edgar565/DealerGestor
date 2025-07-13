/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.entity.ClientEntity;
import com.edgarsannic.dealergestor.domain.model.Client;
import com.edgarsannic.dealergestor.domain.repository.ClientRepository;
import com.edgarsannic.dealergestor.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final ModelMapperUtil modelMapperUtil;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapperUtil modelMapperUtil) {
        this.clientRepository = clientRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Client findClientById(Long id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return modelMapperUtil.toModel(clientEntity);
    }

    @Override
    public Client findClientByName(String name) {
        return modelMapperUtil.toModel(clientRepository.findByName(name));
    }

    @Override
    public Client saveClient(Client model) {
        if (clientRepository.existsByPhone(model.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(model.getName());
        clientEntity.setPhone(model.getPhone());
        clientEntity.setVehicleEntities(new ArrayList<>());

        return modelMapperUtil.toModel(clientRepository.save(clientEntity));
    }

    @Override
    public Client updateClient(Long id, Client model) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        clientEntity.setName(model.getName());
        clientEntity.setPhone(model.getPhone());
        return modelMapperUtil.toModel(clientRepository.save(clientEntity));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}