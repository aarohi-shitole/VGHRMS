package com.techvg.hrms.service;

import com.techvg.hrms.domain.EmployeeExemption;
import com.techvg.hrms.repository.EmployeeExemptionRepository;
import com.techvg.hrms.service.dto.EmployeeExemptionDTO;
import com.techvg.hrms.service.mapper.EmployeeExemptionMapper;

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
 * Service Implementation for managing {@link EmployeeExemption}.
 */
@Service
@Transactional
public class EmployeeExemptionService {

    private final Logger log = LoggerFactory.getLogger(EmployeeExemptionService.class);

    private final EmployeeExemptionRepository employeeExemptionRepository;

    private final EmployeeExemptionMapper employeeExemptionMapper;
    @Autowired
    private ValidationService validationService;
    public EmployeeExemptionService(
        EmployeeExemptionRepository employeeExemptionRepository,
        EmployeeExemptionMapper employeeExemptionMapper
    ) {
        this.employeeExemptionRepository = employeeExemptionRepository;
        this.employeeExemptionMapper = employeeExemptionMapper;
    }

    /**
     * Save a employeeExemption.
     *
     * @param employeeExemptionDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeExemptionDTO save(EmployeeExemptionDTO employeeExemptionDTO) {
        log.debug("Request to save EmployeeExemption : {}", employeeExemptionDTO);
       
        EmployeeExemption employeeExemption = employeeExemptionMapper.toEntity(employeeExemptionDTO);
    	validationService.validateMethod(employeeExemption);
        employeeExemption = employeeExemptionRepository.save(employeeExemption);
        return employeeExemptionMapper.toDto(employeeExemption);
    }

    /**
     * Update a employeeExemption.
     *
     * @param employeeExemptionDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeExemptionDTO update(EmployeeExemptionDTO employeeExemptionDTO) {
        log.debug("Request to update EmployeeExemption : {}", employeeExemptionDTO);
       
        EmployeeExemption employeeExemption = employeeExemptionMapper.toEntity(employeeExemptionDTO);
        validationService.validateMethod(employeeExemption);
        employeeExemption = employeeExemptionRepository.save(employeeExemption);
        return employeeExemptionMapper.toDto(employeeExemption);
    }

    /**
     * Partially update a employeeExemption.
     *
     * @param employeeExemptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeExemptionDTO> partialUpdate(EmployeeExemptionDTO employeeExemptionDTO) {
        log.debug("Request to partially update EmployeeExemption : {}", employeeExemptionDTO);

        return employeeExemptionRepository
            .findById(employeeExemptionDTO.getId())
            .map(existingEmployeeExemption -> {
                employeeExemptionMapper.partialUpdate(existingEmployeeExemption, employeeExemptionDTO);

                return existingEmployeeExemption;
            })
            .map(employeeExemptionRepository::save)
            .map(employeeExemptionMapper::toDto);
    }

    /**
     * Get all the employeeExemptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeExemptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeExemptions");
        return employeeExemptionRepository.findAll(pageable).map(employeeExemptionMapper::toDto);
    }

    /**
     * Get one employeeExemption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeExemptionDTO> findOne(Long id) {
        log.debug("Request to get EmployeeExemption : {}", id);
        return employeeExemptionRepository.findById(id).map(employeeExemptionMapper::toDto);
    }

    /**
     * Delete the employeeExemption by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeExemption : {}", id);
        employeeExemptionRepository.deleteById(id);
    }
}
