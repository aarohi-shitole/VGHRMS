package com.techvg.hrms.service;

import com.techvg.hrms.domain.Tds;
import com.techvg.hrms.repository.TdsRepository;
import com.techvg.hrms.service.dto.TdsDTO;
import com.techvg.hrms.service.mapper.TdsMapper;

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
 * Service Implementation for managing {@link Tds}.
 */
@Service
@Transactional
public class TdsService {

    private final Logger log = LoggerFactory.getLogger(TdsService.class);

    private final TdsRepository tdsRepository;

    private final TdsMapper tdsMapper;
    
    @Autowired
    private ValidationService validationService;

    public TdsService(TdsRepository tdsRepository, TdsMapper tdsMapper) {
        this.tdsRepository = tdsRepository;
        this.tdsMapper = tdsMapper;
    }

    /**
     * Save a tds.
     *
     * @param tdsDTO the entity to save.
     * @return the persisted entity.
     */
    public TdsDTO save(TdsDTO tdsDTO) {
        log.debug("Request to save Tds : {}", tdsDTO);
        Tds tds = tdsMapper.toEntity(tdsDTO);
        validationService.validateMethod(tds);
        tds = tdsRepository.save(tds);
        return tdsMapper.toDto(tds);
    }

    /**
     * Update a tds.
     *
     * @param tdsDTO the entity to save.
     * @return the persisted entity.
     */
    public TdsDTO update(TdsDTO tdsDTO) {
        log.debug("Request to update Tds : {}", tdsDTO);
        Tds tds = tdsMapper.toEntity(tdsDTO);
        validationService.validateMethod(tds);
        tds = tdsRepository.save(tds);
        return tdsMapper.toDto(tds);
    }

    /**
     * Partially update a tds.
     *
     * @param tdsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TdsDTO> partialUpdate(TdsDTO tdsDTO) {
        log.debug("Request to partially update Tds : {}", tdsDTO);

        return tdsRepository
            .findById(tdsDTO.getId())
            .map(existingTds -> {
                tdsMapper.partialUpdate(existingTds, tdsDTO);

                return existingTds;
            })
            .map(tdsRepository::save)
            .map(tdsMapper::toDto);
    }

    /**
     * Get all the tds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TdsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tds");
        return tdsRepository.findAll(pageable).map(tdsMapper::toDto);
    }

    /**
     * Get one tds by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TdsDTO> findOne(Long id) {
        log.debug("Request to get Tds : {}", id);
        return tdsRepository.findById(id).map(tdsMapper::toDto);
    }

    /**
     * Delete the tds by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tds : {}", id);
        tdsRepository.deleteById(id);
    }
}
