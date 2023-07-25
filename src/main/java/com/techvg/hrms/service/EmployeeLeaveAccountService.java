package com.techvg.hrms.service;

import com.techvg.hrms.domain.EmployeeLeaveAccount;
import com.techvg.hrms.repository.EmployeeLeaveAccountRepository;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.mapper.EmployeeLeaveAccountMapper;

import java.lang.reflect.Field;
import java.util.Optional;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeLeaveAccount}.
 */
@Service
@Transactional
public class EmployeeLeaveAccountService {

    private final Logger log = LoggerFactory.getLogger(EmployeeLeaveAccountService.class);

    private final EmployeeLeaveAccountRepository employeeLeaveAccountRepository;

    private final EmployeeLeaveAccountMapper employeeLeaveAccountMapper;
    @Autowired
    private ValidationService validationService;
    public EmployeeLeaveAccountService(
        EmployeeLeaveAccountRepository employeeLeaveAccountRepository,
        EmployeeLeaveAccountMapper employeeLeaveAccountMapper
    ) {
        this.employeeLeaveAccountRepository = employeeLeaveAccountRepository;
        this.employeeLeaveAccountMapper = employeeLeaveAccountMapper;
    }

    /**
     * Save a employeeLeaveAccount.
     *
     * @param employeeLeaveAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeLeaveAccountDTO save(EmployeeLeaveAccountDTO employeeLeaveAccountDTO) {
        log.debug("Request to save EmployeeLeaveAccount : {}", employeeLeaveAccountDTO);
       
        EmployeeLeaveAccount employeeLeaveAccount = employeeLeaveAccountMapper.toEntity(employeeLeaveAccountDTO);
        validationService.validateMethod(employeeLeaveAccount);
        employeeLeaveAccount = employeeLeaveAccountRepository.save(employeeLeaveAccount);
        return employeeLeaveAccountMapper.toDto(employeeLeaveAccount);
    }

    /**
     * Update a employeeLeaveAccount.
     *
     * @param employeeLeaveAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeLeaveAccountDTO update(EmployeeLeaveAccountDTO employeeLeaveAccountDTO) {
        log.debug("Request to update EmployeeLeaveAccount : {}", employeeLeaveAccountDTO);
       
        EmployeeLeaveAccount employeeLeaveAccount = employeeLeaveAccountMapper.toEntity(employeeLeaveAccountDTO);
        validationService.validateMethod(employeeLeaveAccount);
        employeeLeaveAccount = employeeLeaveAccountRepository.save(employeeLeaveAccount);
        return employeeLeaveAccountMapper.toDto(employeeLeaveAccount);
    }

    /**
     * Partially update a employeeLeaveAccount.
     *
     * @param employeeLeaveAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeLeaveAccountDTO> partialUpdate(EmployeeLeaveAccountDTO employeeLeaveAccountDTO) {
        log.debug("Request to partially update EmployeeLeaveAccount : {}", employeeLeaveAccountDTO);

        return employeeLeaveAccountRepository
            .findById(employeeLeaveAccountDTO.getId())
            .map(existingEmployeeLeaveAccount -> {
                employeeLeaveAccountMapper.partialUpdate(existingEmployeeLeaveAccount, employeeLeaveAccountDTO);

                return existingEmployeeLeaveAccount;
            })
            .map(employeeLeaveAccountRepository::save)
            .map(employeeLeaveAccountMapper::toDto);
    }

    /**
     * Get all the employeeLeaveAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeLeaveAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeLeaveAccounts");
        return employeeLeaveAccountRepository.findAll(pageable).map(employeeLeaveAccountMapper::toDto);
    }

    /**
     * Get all the employeeLeaveAccounts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmployeeLeaveAccountDTO> findAllWithEagerRelationships(Pageable pageable) {
        return employeeLeaveAccountRepository.findAllWithEagerRelationships(pageable).map(employeeLeaveAccountMapper::toDto);
    }

    /**
     * Get one employeeLeaveAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeLeaveAccountDTO> findOne(Long id) {
        log.debug("Request to get EmployeeLeaveAccount : {}", id);
        return employeeLeaveAccountRepository.findOneWithEagerRelationships(id).map(employeeLeaveAccountMapper::toDto);
    }

    /**
     * Delete the employeeLeaveAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeLeaveAccount : {}", id);
        employeeLeaveAccountRepository.deleteById(id);
    }
}
