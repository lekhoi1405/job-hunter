package com.lekhoi.recruitment.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.lekhoi.recruitment.domain.entity.Company;
public interface CompanyRepository extends JpaRepository<Company,Long>, JpaSpecificationExecutor<Company>{
    Page<Company> findAll(Specification<Company> specification, Pageable pageable);
}
 