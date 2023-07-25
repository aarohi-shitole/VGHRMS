package com.techvg.hrms.service;

import com.techvg.hrms.domain.EmploymentType;
import com.techvg.hrms.repository.EmploymentTypeRepository;
import com.techvg.hrms.service.dto.EmploymentTypeDTO;
import com.techvg.hrms.service.mapper.EmploymentTypeMapper;

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
 * Service Implementation for managing {@link EmploymentType}.
 */
@Service
@Transactional
public class EmploymentTypeService {

    private final Logger log = LoggerFactory.getLogger(EmploymentTypeService.class);

    private final EmploymentTypeRepository employmentTypeRepository;

    private final EmploymentTypeMapper employmentTypeMapper;
    @Autowired
    private ValidationService validationService;

    public EmploymentTypeService(EmploymentTypeRepository employmentTypeRepository, EmploymentTypeMapper employmentTypeMapper) {
        this.employmentTypeRepository = employmentTypeRepository;
        this.employmentTypeMapper = employmentTypeMapper;
    }

    /**
     * Save a employmentType.
     *
     * @param employmentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmploymentTypeDTO save(EmploymentTypeDTO employmentTypeDTO) {
        log.debug("Request to save EmploymentType : {}", employmentTypeDTO);
        EmploymentType employmentType = employmentTypeMapper.toEntity(employmentTypeDTO);
        validationService.validateMethod(employmentType);
        employmentType = employmentTypeRepository.save(employmentType);
        return employmentTypeMapper.toDto(employmentType);
    }

    /**
     * Update a employmentType.
     *
     * @param employmentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmploymentTypeDTO update(EmploymentTypeDTO employmentTypeDTO) {
        log.debug("Request to update EmploymentType : {}", employmentTypeDTO);
        EmploymentType employmentType = employmentTypeMapper.toEntity(employmentTypeDTO);
        validationService.validateMethod(employmentType);
        employmentType = employmentTypeRepository.save(employmentType);
        return employmentTypeMapper.toDto(employmentType);
    }

    /**
     * Partially update a employmentType.
     *
     * @param employmentTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmploymentTypeDTO> partialUpdate(EmploymentTypeDTO employmentTypeDTO) {
        log.debug("Request to partially update EmploymentType : {}", employmentTypeDTO);

        return employmentTypeRepository
            .findById(employmentTypeDTO.getId())
            .map(existingEmploymentType -> {
                employmentTypeMapper.partialUpdate(existingEmploymentType, employmentTypeDTO);

                return existingEmploymentType;
            })
            .map(employmentTypeRepository::save)
            .map(employmentTypeMapper::toDto);
    }

    /**
     * Get all the employmentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmploymentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmploymentTypes");
        return employmentTypeRepository.findAll(pageable).map(employmentTypeMapper::toDto);
    }

    /**
     * Get one employmentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmploymentTypeDTO> findOne(Long id) {
        log.debug("Request to get EmploymentType : {}", id);
        return employmentTypeRepository.findById(id).map(employmentTypeMapper::toDto);
    }

    /**
     * Delete the employmentType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmploymentType : {}", id);
        employmentTypeRepository.deleteById(id);
    }
}
