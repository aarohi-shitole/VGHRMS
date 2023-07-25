package com.techvg.hrms.service;

import com.techvg.hrms.domain.TaxSlab;
import com.techvg.hrms.repository.TaxSlabRepository;
import com.techvg.hrms.service.dto.TaxSlabDTO;
import com.techvg.hrms.service.mapper.TaxSlabMapper;

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
 * Service Implementation for managing {@link TaxSlab}.
 */
@Service
@Transactional
public class TaxSlabService {

    private final Logger log = LoggerFactory.getLogger(TaxSlabService.class);

    private final TaxSlabRepository taxSlabRepository;

    private final TaxSlabMapper taxSlabMapper;
    
    @Autowired
    private ValidationService validationService;

    public TaxSlabService(TaxSlabRepository taxSlabRepository, TaxSlabMapper taxSlabMapper) {
        this.taxSlabRepository = taxSlabRepository;
        this.taxSlabMapper = taxSlabMapper;
    }

    /**
     * Save a taxSlab.
     *
     * @param taxSlabDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxSlabDTO save(TaxSlabDTO taxSlabDTO) {
        log.debug("Request to save TaxSlab : {}", taxSlabDTO);
        TaxSlab taxSlab = taxSlabMapper.toEntity(taxSlabDTO);
        validationService.validateMethod(taxSlab);
        taxSlab = taxSlabRepository.save(taxSlab);
        return taxSlabMapper.toDto(taxSlab);
    }

    /**
     * Update a taxSlab.
     *
     * @param taxSlabDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxSlabDTO update(TaxSlabDTO taxSlabDTO) {
        log.debug("Request to update TaxSlab : {}", taxSlabDTO);
        TaxSlab taxSlab = taxSlabMapper.toEntity(taxSlabDTO);
        validationService.validateMethod(taxSlab);
        taxSlab = taxSlabRepository.save(taxSlab);
        return taxSlabMapper.toDto(taxSlab);
    }

    /**
     * Partially update a taxSlab.
     *
     * @param taxSlabDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaxSlabDTO> partialUpdate(TaxSlabDTO taxSlabDTO) {
        log.debug("Request to partially update TaxSlab : {}", taxSlabDTO);

        return taxSlabRepository
            .findById(taxSlabDTO.getId())
            .map(existingTaxSlab -> {
                taxSlabMapper.partialUpdate(existingTaxSlab, taxSlabDTO);

                return existingTaxSlab;
            })
            .map(taxSlabRepository::save)
            .map(taxSlabMapper::toDto);
    }

    /**
     * Get all the taxSlabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxSlabDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaxSlabs");
        return taxSlabRepository.findAll(pageable).map(taxSlabMapper::toDto);
    }

    /**
     * Get one taxSlab by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaxSlabDTO> findOne(Long id) {
        log.debug("Request to get TaxSlab : {}", id);
        return taxSlabRepository.findById(id).map(taxSlabMapper::toDto);
    }

    /**
     * Delete the taxSlab by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaxSlab : {}", id);
        taxSlabRepository.deleteById(id);
    }
}
