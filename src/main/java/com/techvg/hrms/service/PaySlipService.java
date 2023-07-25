package com.techvg.hrms.service;

import com.techvg.hrms.domain.PaySlip;
import com.techvg.hrms.repository.PaySlipRepository;
import com.techvg.hrms.service.dto.PaySlipDTO;
import com.techvg.hrms.service.mapper.PaySlipMapper;

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
 * Service Implementation for managing {@link PaySlip}.
 */
@Service
@Transactional
public class PaySlipService {

    private final Logger log = LoggerFactory.getLogger(PaySlipService.class);

    private final PaySlipRepository paySlipRepository;

    private final PaySlipMapper paySlipMapper;
    @Autowired
    private ValidationService validationService;

    public PaySlipService(PaySlipRepository paySlipRepository, PaySlipMapper paySlipMapper) {
        this.paySlipRepository = paySlipRepository;
        this.paySlipMapper = paySlipMapper;
    }

    /**
     * Save a paySlip.
     *
     * @param paySlipDTO the entity to save.
     * @return the persisted entity.
     */
    public PaySlipDTO save(PaySlipDTO paySlipDTO) {
        log.debug("Request to save PaySlip : {}", paySlipDTO);
      
        PaySlip paySlip = paySlipMapper.toEntity(paySlipDTO);
        validationService.validateMethod(paySlip);
        paySlip = paySlipRepository.save(paySlip);
        return paySlipMapper.toDto(paySlip);
    }

    /**
     * Update a paySlip.
     *
     * @param paySlipDTO the entity to save.
     * @return the persisted entity.
     */
    public PaySlipDTO update(PaySlipDTO paySlipDTO) {
        log.debug("Request to update PaySlip : {}", paySlipDTO);
    
        PaySlip paySlip = paySlipMapper.toEntity(paySlipDTO);
        validationService.validateMethod(paySlip);
        paySlip = paySlipRepository.save(paySlip);
        return paySlipMapper.toDto(paySlip);
    }

    /**
     * Partially update a paySlip.
     *
     * @param paySlipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaySlipDTO> partialUpdate(PaySlipDTO paySlipDTO) {
        log.debug("Request to partially update PaySlip : {}", paySlipDTO);

        return paySlipRepository
            .findById(paySlipDTO.getId())
            .map(existingPaySlip -> {
                paySlipMapper.partialUpdate(existingPaySlip, paySlipDTO);

                return existingPaySlip;
            })
            .map(paySlipRepository::save)
            .map(paySlipMapper::toDto);
    }

    /**
     * Get all the paySlips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaySlipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaySlips");
        return paySlipRepository.findAll(pageable).map(paySlipMapper::toDto);
    }

    /**
     * Get one paySlip by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaySlipDTO> findOne(Long id) {
        log.debug("Request to get PaySlip : {}", id);
        return paySlipRepository.findById(id).map(paySlipMapper::toDto);
    }

    /**
     * Delete the paySlip by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaySlip : {}", id);
        paySlipRepository.deleteById(id);
    }
}
