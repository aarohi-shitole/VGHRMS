package com.techvg.hrms.service;

import com.techvg.hrms.domain.TaxExempSection;
import com.techvg.hrms.repository.TaxExempSectionRepository;
import com.techvg.hrms.service.dto.TaxExempSectionDTO;
import com.techvg.hrms.service.mapper.TaxExempSectionMapper;

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
 * Service Implementation for managing {@link TaxExempSection}.
 */
@Service
@Transactional
public class TaxExempSectionService {

    private final Logger log = LoggerFactory.getLogger(TaxExempSectionService.class);

    private final TaxExempSectionRepository taxExempSectionRepository;

    private final TaxExempSectionMapper taxExempSectionMapper;
    
    @Autowired
    private ValidationService validationService;

    public TaxExempSectionService(TaxExempSectionRepository taxExempSectionRepository, TaxExempSectionMapper taxExempSectionMapper) {
        this.taxExempSectionRepository = taxExempSectionRepository;
        this.taxExempSectionMapper = taxExempSectionMapper;
    }

    /**
     * Save a taxExempSection.
     *
     * @param taxExempSectionDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxExempSectionDTO save(TaxExempSectionDTO taxExempSectionDTO) {
        log.debug("Request to save TaxExempSection : {}", taxExempSectionDTO);
        TaxExempSection taxExempSection = taxExempSectionMapper.toEntity(taxExempSectionDTO);
        validationService.validateMethod(taxExempSection);
        taxExempSection = taxExempSectionRepository.save(taxExempSection);
        return taxExempSectionMapper.toDto(taxExempSection);
    }

    /**
     * Update a taxExempSection.
     *
     * @param taxExempSectionDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxExempSectionDTO update(TaxExempSectionDTO taxExempSectionDTO) {
        log.debug("Request to update TaxExempSection : {}", taxExempSectionDTO);
        TaxExempSection taxExempSection = taxExempSectionMapper.toEntity(taxExempSectionDTO);
        validationService.validateMethod(taxExempSection);
        taxExempSection = taxExempSectionRepository.save(taxExempSection);
        return taxExempSectionMapper.toDto(taxExempSection);
    }

    /**
     * Partially update a taxExempSection.
     *
     * @param taxExempSectionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaxExempSectionDTO> partialUpdate(TaxExempSectionDTO taxExempSectionDTO) {
        log.debug("Request to partially update TaxExempSection : {}", taxExempSectionDTO);

        return taxExempSectionRepository
            .findById(taxExempSectionDTO.getId())
            .map(existingTaxExempSection -> {
                taxExempSectionMapper.partialUpdate(existingTaxExempSection, taxExempSectionDTO);

                return existingTaxExempSection;
            })
            .map(taxExempSectionRepository::save)
            .map(taxExempSectionMapper::toDto);
    }

    /**
     * Get all the taxExempSections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxExempSectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaxExempSections");
        return taxExempSectionRepository.findAll(pageable).map(taxExempSectionMapper::toDto);
    }

    /**
     * Get one taxExempSection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaxExempSectionDTO> findOne(Long id) {
        log.debug("Request to get TaxExempSection : {}", id);
        return taxExempSectionRepository.findById(id).map(taxExempSectionMapper::toDto);
    }

    /**
     * Delete the taxExempSection by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaxExempSection : {}", id);
        taxExempSectionRepository.deleteById(id);
    }
}
