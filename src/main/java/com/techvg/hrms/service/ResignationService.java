package com.techvg.hrms.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.hrms.domain.Resignation;
import com.techvg.hrms.repository.ResignationRepository;
import com.techvg.hrms.service.dto.ResignationDTO;
import com.techvg.hrms.service.mapper.ResignationMapper;

/**
 * Service Implementation for managing {@link Resignation}.
 */
@Service
@Transactional
public class ResignationService {

    private final Logger log = LoggerFactory.getLogger(ResignationService.class);

    private final ResignationRepository resignationRepository;

    private final ResignationMapper resignationMapper;
    
    @Autowired
    private ValidationService validationService;
    
    public ResignationService(ResignationRepository resignationRepository, ResignationMapper resignationMapper) {
        this.resignationRepository = resignationRepository;
        this.resignationMapper = resignationMapper;
    }

    /**
     * Save a resignation.
     *
     * @param resignationDTO the entity to save.
     * @return the persisted entity.
     */
    public ResignationDTO save(ResignationDTO resignationDTO) {
        log.debug("Request to save Resignation : {}", resignationDTO);
        Resignation resignation = resignationMapper.toEntity(resignationDTO);
        validationService.validateMethod(resignation);
        resignation = resignationRepository.save(resignation);
        return resignationMapper.toDto(resignation);
    }

    /**
     * Update a resignation.
     *
     * @param resignationDTO the entity to save.
     * @return the persisted entity.
     */
    public ResignationDTO update(ResignationDTO resignationDTO) {
        log.debug("Request to update Resignation : {}", resignationDTO);
        Resignation resignation = resignationMapper.toEntity(resignationDTO);
        validationService.validateMethod(resignation);
        resignation = resignationRepository.save(resignation);
        return resignationMapper.toDto(resignation);
    }

    /**
     * Partially update a resignation.
     *
     * @param resignationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResignationDTO> partialUpdate(ResignationDTO resignationDTO) {
        log.debug("Request to partially update Resignation : {}", resignationDTO);

        return resignationRepository
            .findById(resignationDTO.getId())
            .map(existingResignation -> {
                resignationMapper.partialUpdate(existingResignation, resignationDTO);

                return existingResignation;
            })
            .map(resignationRepository::save)
            .map(resignationMapper::toDto);
    }

    /**
     * Get all the resignations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResignationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Resignations");
        return resignationRepository.findAll(pageable).map(resignationMapper::toDto);
    }

    /**
     * Get one resignation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResignationDTO> findOne(Long id) {
        log.debug("Request to get Resignation : {}", id);
        return resignationRepository.findById(id).map(resignationMapper::toDto);
    }

    /**
     * Delete the resignation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Resignation : {}", id);
        resignationRepository.deleteById(id);
    }

    
    
}
