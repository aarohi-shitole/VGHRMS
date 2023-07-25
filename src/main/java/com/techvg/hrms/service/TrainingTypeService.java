package com.techvg.hrms.service;

import com.techvg.hrms.domain.TrainingType;
import com.techvg.hrms.repository.TrainingTypeRepository;
import com.techvg.hrms.service.dto.TrainingTypeDTO;
import com.techvg.hrms.service.mapper.TrainingTypeMapper;

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
 * Service Implementation for managing {@link TrainingType}.
 */
@Service
@Transactional
public class TrainingTypeService {

    private final Logger log = LoggerFactory.getLogger(TrainingTypeService.class);

    private final TrainingTypeRepository trainingTypeRepository;

    private final TrainingTypeMapper trainingTypeMapper;
    
    @Autowired
    private ValidationService validationService;

    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    /**
     * Save a trainingType.
     *
     * @param trainingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainingTypeDTO save(TrainingTypeDTO trainingTypeDTO) {
        log.debug("Request to save TrainingType : {}", trainingTypeDTO);
        TrainingType trainingType = trainingTypeMapper.toEntity(trainingTypeDTO);
        validationService.validateMethod(trainingType);
        trainingType = trainingTypeRepository.save(trainingType);
        return trainingTypeMapper.toDto(trainingType);
    }

    /**
     * Update a trainingType.
     *
     * @param trainingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainingTypeDTO update(TrainingTypeDTO trainingTypeDTO) {
        log.debug("Request to update TrainingType : {}", trainingTypeDTO);
        TrainingType trainingType = trainingTypeMapper.toEntity(trainingTypeDTO);
        validationService.validateMethod(trainingType);
        trainingType = trainingTypeRepository.save(trainingType);
        return trainingTypeMapper.toDto(trainingType);
    }

    /**
     * Partially update a trainingType.
     *
     * @param trainingTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrainingTypeDTO> partialUpdate(TrainingTypeDTO trainingTypeDTO) {
        log.debug("Request to partially update TrainingType : {}", trainingTypeDTO);

        return trainingTypeRepository
            .findById(trainingTypeDTO.getId())
            .map(existingTrainingType -> {
                trainingTypeMapper.partialUpdate(existingTrainingType, trainingTypeDTO);

                return existingTrainingType;
            })
            .map(trainingTypeRepository::save)
            .map(trainingTypeMapper::toDto);
    }

    /**
     * Get all the trainingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainingTypes");
        return trainingTypeRepository.findAll(pageable).map(trainingTypeMapper::toDto);
    }

    /**
     * Get one trainingType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrainingTypeDTO> findOne(Long id) {
        log.debug("Request to get TrainingType : {}", id);
        return trainingTypeRepository.findById(id).map(trainingTypeMapper::toDto);
    }

    /**
     * Delete the trainingType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TrainingType : {}", id);
        trainingTypeRepository.deleteById(id);
    }
}
