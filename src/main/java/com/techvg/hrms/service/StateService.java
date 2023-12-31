package com.techvg.hrms.service;

import com.techvg.hrms.domain.State;
import com.techvg.hrms.repository.StateRepository;
import com.techvg.hrms.service.dto.StateDTO;
import com.techvg.hrms.service.mapper.StateMapper;

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
 * Service Implementation for managing {@link State}.
 */
@Service
@Transactional
public class StateService {

    private final Logger log = LoggerFactory.getLogger(StateService.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;
    
    @Autowired
    private ValidationService validationService;

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    /**
     * Save a state.
     *
     * @param stateDTO the entity to save.
     * @return the persisted entity.
     */
    public StateDTO save(StateDTO stateDTO) {
        log.debug("Request to save State : {}", stateDTO);
        State state = stateMapper.toEntity(stateDTO);
        validationService.validateMethod(state);
        state = stateRepository.save(state);
        return stateMapper.toDto(state);
    }

    /**
     * Update a state.
     *
     * @param stateDTO the entity to save.
     * @return the persisted entity.
     */
    public StateDTO update(StateDTO stateDTO) {
        log.debug("Request to update State : {}", stateDTO);
        State state = stateMapper.toEntity(stateDTO);
        validationService.validateMethod(state);
        state = stateRepository.save(state);
        return stateMapper.toDto(state);
    }

    /**
     * Partially update a state.
     *
     * @param stateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StateDTO> partialUpdate(StateDTO stateDTO) {
        log.debug("Request to partially update State : {}", stateDTO);

        return stateRepository
            .findById(stateDTO.getId())
            .map(existingState -> {
                stateMapper.partialUpdate(existingState, stateDTO);

                return existingState;
            })
            .map(stateRepository::save)
            .map(stateMapper::toDto);
    }

    /**
     * Get all the states.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all States");
        return stateRepository.findAll(pageable).map(stateMapper::toDto);
    }

    /**
     * Get one state by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StateDTO> findOne(Long id) {
        log.debug("Request to get State : {}", id);
        return stateRepository.findById(id).map(stateMapper::toDto);
    }

    /**
     * Delete the state by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);
        stateRepository.deleteById(id);
    }
}
