package com.techvg.hrms.service;

import com.techvg.hrms.domain.PayrollAdditions;
import com.techvg.hrms.repository.PayrollAdditionsRepository;
import com.techvg.hrms.service.dto.PayrollAdditionsDTO;
import com.techvg.hrms.service.mapper.PayrollAdditionsMapper;

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
 * Service Implementation for managing {@link PayrollAdditions}.
 */
@Service
@Transactional
public class PayrollAdditionsService {

    private final Logger log = LoggerFactory.getLogger(PayrollAdditionsService.class);

    private final PayrollAdditionsRepository payrollAdditionsRepository;

    private final PayrollAdditionsMapper payrollAdditionsMapper;
    @Autowired
    private ValidationService validationService;

    public PayrollAdditionsService(PayrollAdditionsRepository payrollAdditionsRepository, PayrollAdditionsMapper payrollAdditionsMapper) {
        this.payrollAdditionsRepository = payrollAdditionsRepository;
        this.payrollAdditionsMapper = payrollAdditionsMapper;
    }

    /**
     * Save a payrollAdditions.
     *
     * @param payrollAdditionsDTO the entity to save.
     * @return the persisted entity.
     */
    public PayrollAdditionsDTO save(PayrollAdditionsDTO payrollAdditionsDTO) {
        log.debug("Request to save PayrollAdditions : {}", payrollAdditionsDTO);
        
        PayrollAdditions payrollAdditions = payrollAdditionsMapper.toEntity(payrollAdditionsDTO);
        validationService.validateMethod(payrollAdditions);
        payrollAdditions = payrollAdditionsRepository.save(payrollAdditions);
        return payrollAdditionsMapper.toDto(payrollAdditions);
    }

    /**
     * Update a payrollAdditions.
     *
     * @param payrollAdditionsDTO the entity to save.
     * @return the persisted entity.
     */
    public PayrollAdditionsDTO update(PayrollAdditionsDTO payrollAdditionsDTO) {
        log.debug("Request to update PayrollAdditions : {}", payrollAdditionsDTO);
       
        PayrollAdditions payrollAdditions = payrollAdditionsMapper.toEntity(payrollAdditionsDTO);
        validationService.validateMethod(payrollAdditions);
        payrollAdditions = payrollAdditionsRepository.save(payrollAdditions);
        return payrollAdditionsMapper.toDto(payrollAdditions);
    }

    /**
     * Partially update a payrollAdditions.
     *
     * @param payrollAdditionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PayrollAdditionsDTO> partialUpdate(PayrollAdditionsDTO payrollAdditionsDTO) {
        log.debug("Request to partially update PayrollAdditions : {}", payrollAdditionsDTO);

        return payrollAdditionsRepository
            .findById(payrollAdditionsDTO.getId())
            .map(existingPayrollAdditions -> {
                payrollAdditionsMapper.partialUpdate(existingPayrollAdditions, payrollAdditionsDTO);

                return existingPayrollAdditions;
            })
            .map(payrollAdditionsRepository::save)
            .map(payrollAdditionsMapper::toDto);
    }

    /**
     * Get all the payrollAdditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PayrollAdditionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PayrollAdditions");
        return payrollAdditionsRepository.findAll(pageable).map(payrollAdditionsMapper::toDto);
    }

    /**
     * Get one payrollAdditions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PayrollAdditionsDTO> findOne(Long id) {
        log.debug("Request to get PayrollAdditions : {}", id);
        return payrollAdditionsRepository.findById(id).map(payrollAdditionsMapper::toDto);
    }

    /**
     * Delete the payrollAdditions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PayrollAdditions : {}", id);
        payrollAdditionsRepository.deleteById(id);
    }
}
