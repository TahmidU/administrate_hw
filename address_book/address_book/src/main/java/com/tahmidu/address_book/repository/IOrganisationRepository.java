package com.tahmidu.address_book.repository;

import com.tahmidu.address_book.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrganisationRepository extends JpaRepository<Organisation, Long> {
}
