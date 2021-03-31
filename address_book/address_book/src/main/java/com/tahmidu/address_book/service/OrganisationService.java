package com.tahmidu.address_book.service;

import com.tahmidu.address_book.model.Organisation;
import com.tahmidu.address_book.repository.IOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganisationService implements IOrganisationService{

    private final IOrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(IOrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @Override
    public List<Organisation> findALl() {
        return organisationRepository.findAll();
    }

    @Override
    public void save(Organisation organisation) {
        organisationRepository.save(organisation);
    }

    @Override
    public void removeById(Long id) {
        organisationRepository.deleteById(id);
    }

    @Override
    public Optional<Organisation> findById(Long id) {
        return organisationRepository.findById(id);
    }
}
