package com.techvg.hrms.service;

import com.techvg.hrms.domain.CustomLeavePolicy;
import com.techvg.hrms.repository.CustomLeavePolicyRepository;
import com.techvg.hrms.service.dto.CustomLeavePolicyDTO;
import com.techvg.hrms.service.mapper.CustomLeavePolicyMapper;

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
 * Service Implementation for managing {@link CustomLeavePolicy}.
 */
@Service
@Transactional
public class CustomLeavePolicyService {

    private final Logger log = LoggerFactory.getLogger(CustomLeavePolicyService.class);

    private final CustomLeavePolicyRepository customLeavePolicyRepository;

    private final CustomLeavePolicyMapper customLeavePolicyMapper;
    @Autowired
    private ValidationService validationService;

    public CustomLeavePolicyService(
        CustomLeavePolicyRepository customLeavePolicyRepository,
        CustomLeavePolicyMapper customLeavePolicyMapper
    ) {
        this.customLeavePolicyRepository = customLeavePolicyRepository;
        this.customLeavePolicyMapper = customLeavePolicyMapper;
    }

    /**
     * Save a customLeavePolicy.
     *
     * @param customLeavePolicyDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomLeavePolicyDTO save(CustomLeavePolicyDTO customLeavePolicyDTO) {
        log.debug("Request to save CustomLeavePolicy : {}", customLeavePolicyDTO);
       
        CustomLeavePolicy customLeavePolicy = customLeavePolicyMapper.toEntity(customLeavePolicyDTO);
        validationService.validateMethod(customLeavePolicy);
        customLeavePolicy = customLeavePolicyRepository.save(customLeavePolicy);
        return customLeavePolicyMapper.toDto(customLeavePolicy);
    }

    /**
     * Update a customLeavePolicy.
     *
     * @param customLeavePolicyDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomLeavePolicyDTO update(CustomLeavePolicyDTO customLeavePolicyDTO) {
        log.debug("Request to update CustomLeavePolicy : {}", customLeavePolicyDTO);
        CustomLeavePolicy customLeavePolicy = customLeavePolicyMapper.toEntity(customLeavePolicyDTO);
        validationService.validateMethod(customLeavePolicy);
        customLeavePolicy = customLeavePolicyRepository.save(customLeavePolicy);
        return customLeavePolicyMapper.toDto(customLeavePolicy);
    }

    /**
     * Partially update a customLeavePolicy.
     *
     * @param customLeavePolicyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomLeavePolicyDTO> partialUpdate(CustomLeavePolicyDTO customLeavePolicyDTO) {
        log.debug("Request to partially update CustomLeavePolicy : {}", customLeavePolicyDTO);

        return customLeavePolicyRepository
            .findById(customLeavePolicyDTO.getId())
            .map(existingCustomLeavePolicy -> {
                customLeavePolicyMapper.partialUpdate(existingCustomLeavePolicy, customLeavePolicyDTO);

                return existingCustomLeavePolicy;
            })
            .map(customLeavePolicyRepository::save)
            .map(customLeavePolicyMapper::toDto);
    }

    /**
     * Get all the customLeavePolicies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomLeavePolicyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomLeavePolicies");
        return customLeavePolicyRepository.findAll(pageable).map(customLeavePolicyMapper::toDto);
    }

    /**
     * Get one customLeavePolicy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomLeavePolicyDTO> findOne(Long id) {
        log.debug("Request to get CustomLeavePolicy : {}", id);
        return customLeavePolicyRepository.findById(id).map(customLeavePolicyMapper::toDto);
    }

    /**
     * Delete the customLeavePolicy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomLeavePolicy : {}", id);
        customLeavePolicyRepository.deleteById(id);
    }
}
