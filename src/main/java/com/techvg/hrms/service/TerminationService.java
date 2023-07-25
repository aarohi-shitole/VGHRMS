package com.techvg.hrms.service;

import com.techvg.hrms.domain.Termination;
import com.techvg.hrms.repository.TerminationRepository;
import com.techvg.hrms.service.dto.TerminationDTO;
import com.techvg.hrms.service.mapper.TerminationMapper;

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
 * Service Implementation for managing {@link Termination}.
 */
@Service
@Transactional
public class TerminationService {

    private final Logger log = LoggerFactory.getLogger(TerminationService.class);

    private final TerminationRepository terminationRepository;

    private final TerminationMapper terminationMapper;
    
    @Autowired
    private ValidationService validationService;

    public TerminationService(TerminationRepository terminationRepository, TerminationMapper terminationMapper) {
        this.terminationRepository = terminationRepository;
        this.terminationMapper = terminationMapper;
    }

    /**
     * Save a termination.
     *
     * @param terminationDTO the entity to save.
     * @return the persisted entity.
     */
    public TerminationDTO save(TerminationDTO terminationDTO) {
        log.debug("Request to save Termination : {}", terminationDTO);
        Termination termination = terminationMapper.toEntity(terminationDTO);
        validationService.validateMethod(termination);
        termination = terminationRepository.save(termination);
        return terminationMapper.toDto(termination);
    }

    /**
     * Update a termination.
     *
     * @param terminationDTO the entity to save.
     * @return the persisted entity.
     */
    public TerminationDTO update(TerminationDTO terminationDTO) {
        log.debug("Request to update Termination : {}", terminationDTO);
        Termination termination = terminationMapper.toEntity(terminationDTO);
        validationService.validateMethod(termination);
        termination = terminationRepository.save(termination);
        return terminationMapper.toDto(termination);
    }

    /**
     * Partially update a termination.
     *
     * @param terminationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TerminationDTO> partialUpdate(TerminationDTO terminationDTO) {
        log.debug("Request to partially update Termination : {}", terminationDTO);

        return terminationRepository
            .findById(terminationDTO.getId())
            .map(existingTermination -> {
                terminationMapper.partialUpdate(existingTermination, terminationDTO);

                return existingTermination;
            })
            .map(terminationRepository::save)
            .map(terminationMapper::toDto);
    }

    /**
     * Get all the terminations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TerminationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Terminations");
        return terminationRepository.findAll(pageable).map(terminationMapper::toDto);
    }

    /**
     * Get one termination by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TerminationDTO> findOne(Long id) {
        log.debug("Request to get Termination : {}", id);
        return terminationRepository.findById(id).map(terminationMapper::toDto);
    }

    /**
     * Delete the termination by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Termination : {}", id);
        terminationRepository.deleteById(id);
    }
}
