package com.techvg.hrms.service;

import com.techvg.hrms.domain.TaxRegime;
import com.techvg.hrms.repository.TaxRegimeRepository;
import com.techvg.hrms.service.dto.TaxRegimeDTO;
import com.techvg.hrms.service.mapper.TaxRegimeMapper;

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
 * Service Implementation for managing {@link TaxRegime}.
 */
@Service
@Transactional
public class TaxRegimeService {

    private final Logger log = LoggerFactory.getLogger(TaxRegimeService.class);

    private final TaxRegimeRepository taxRegimeRepository;

    private final TaxRegimeMapper taxRegimeMapper;
    
    @Autowired
    private ValidationService validationService;

    public TaxRegimeService(TaxRegimeRepository taxRegimeRepository, TaxRegimeMapper taxRegimeMapper) {
        this.taxRegimeRepository = taxRegimeRepository;
        this.taxRegimeMapper = taxRegimeMapper;
    }

    /**
     * Save a taxRegime.
     *
     * @param taxRegimeDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxRegimeDTO save(TaxRegimeDTO taxRegimeDTO) {
        log.debug("Request to save TaxRegime : {}", taxRegimeDTO);
        TaxRegime taxRegime = taxRegimeMapper.toEntity(taxRegimeDTO);
        validationService.validateMethod(taxRegime);
        taxRegime = taxRegimeRepository.save(taxRegime);
        return taxRegimeMapper.toDto(taxRegime);
    }

    /**
     * Update a taxRegime.
     *
     * @param taxRegimeDTO the entity to save.
     * @return the persisted entity.
     */
    public TaxRegimeDTO update(TaxRegimeDTO taxRegimeDTO) {
        log.debug("Request to update TaxRegime : {}", taxRegimeDTO);
        TaxRegime taxRegime = taxRegimeMapper.toEntity(taxRegimeDTO);
        validationService.validateMethod(taxRegime);
        taxRegime = taxRegimeRepository.save(taxRegime);
        return taxRegimeMapper.toDto(taxRegime);
    }

    /**
     * Partially update a taxRegime.
     *
     * @param taxRegimeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaxRegimeDTO> partialUpdate(TaxRegimeDTO taxRegimeDTO) {
        log.debug("Request to partially update TaxRegime : {}", taxRegimeDTO);

        return taxRegimeRepository
            .findById(taxRegimeDTO.getId())
            .map(existingTaxRegime -> {
                taxRegimeMapper.partialUpdate(existingTaxRegime, taxRegimeDTO);

                return existingTaxRegime;
            })
            .map(taxRegimeRepository::save)
            .map(taxRegimeMapper::toDto);
    }

    /**
     * Get all the taxRegimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxRegimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaxRegimes");
        return taxRegimeRepository.findAll(pageable).map(taxRegimeMapper::toDto);
    }

    /**
     * Get one taxRegime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaxRegimeDTO> findOne(Long id) {
        log.debug("Request to get TaxRegime : {}", id);
        return taxRegimeRepository.findById(id).map(taxRegimeMapper::toDto);
    }

    /**
     * Delete the taxRegime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaxRegime : {}", id);
        taxRegimeRepository.deleteById(id);
    }
}
