package com.techvg.hrms.service;

import com.techvg.hrms.domain.Trainer;
import com.techvg.hrms.repository.TrainerRepository;
import com.techvg.hrms.service.dto.TrainerDTO;
import com.techvg.hrms.service.mapper.TrainerMapper;

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
 * Service Implementation for managing {@link Trainer}.
 */
@Service
@Transactional
public class TrainerService {

    private final Logger log = LoggerFactory.getLogger(TrainerService.class);

    private final TrainerRepository trainerRepository;

    private final TrainerMapper trainerMapper;
    
    @Autowired
    private ValidationService validationService;

    public TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    /**
     * Save a trainer.
     *
     * @param trainerDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainerDTO save(TrainerDTO trainerDTO) {
        log.debug("Request to save Trainer : {}", trainerDTO);
        Trainer trainer = trainerMapper.toEntity(trainerDTO);
        validationService.validateMethod(trainer);
        trainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(trainer);
    }

    /**
     * Update a trainer.
     *
     * @param trainerDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainerDTO update(TrainerDTO trainerDTO) {
        log.debug("Request to update Trainer : {}", trainerDTO);
        Trainer trainer = trainerMapper.toEntity(trainerDTO);
        validationService.validateMethod(trainer);
        trainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(trainer);
    }

    /**
     * Partially update a trainer.
     *
     * @param trainerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrainerDTO> partialUpdate(TrainerDTO trainerDTO) {
        log.debug("Request to partially update Trainer : {}", trainerDTO);

        return trainerRepository
            .findById(trainerDTO.getId())
            .map(existingTrainer -> {
                trainerMapper.partialUpdate(existingTrainer, trainerDTO);

                return existingTrainer;
            })
            .map(trainerRepository::save)
            .map(trainerMapper::toDto);
    }

    /**
     * Get all the trainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainers");
        return trainerRepository.findAll(pageable).map(trainerMapper::toDto);
    }

    /**
     * Get one trainer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrainerDTO> findOne(Long id) {
        log.debug("Request to get Trainer : {}", id);
        return trainerRepository.findById(id).map(trainerMapper::toDto);
    }

    /**
     * Delete the trainer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Trainer : {}", id);
        trainerRepository.deleteById(id);
    }
}
