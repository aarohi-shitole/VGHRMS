package com.techvg.hrms.service;

import com.techvg.hrms.domain.WorkingHours;
import com.techvg.hrms.repository.WorkingHoursRepository;
import com.techvg.hrms.service.dto.WorkingHoursDTO;
import com.techvg.hrms.service.mapper.WorkingHoursMapper;

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
 * Service Implementation for managing {@link WorkingHours}.
 */
@Service
@Transactional
public class WorkingHoursService {

    private final Logger log = LoggerFactory.getLogger(WorkingHoursService.class);

    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursMapper workingHoursMapper;
    
    @Autowired
    private ValidationService validationService;

    public WorkingHoursService(WorkingHoursRepository workingHoursRepository, WorkingHoursMapper workingHoursMapper) {
        this.workingHoursRepository = workingHoursRepository;
        this.workingHoursMapper = workingHoursMapper;
    }

    /**
     * Save a workingHours.
     *
     * @param workingHoursDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkingHoursDTO save(WorkingHoursDTO workingHoursDTO) {
        log.debug("Request to save WorkingHours : {}", workingHoursDTO);     
        WorkingHours workingHours = workingHoursMapper.toEntity(workingHoursDTO);
        validationService.validateMethod(workingHours);
        workingHours = workingHoursRepository.save(workingHours);
        return workingHoursMapper.toDto(workingHours);
    }

    /**
     * Update a workingHours.
     *
     * @param workingHoursDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkingHoursDTO update(WorkingHoursDTO workingHoursDTO) {
        log.debug("Request to update WorkingHours : {}", workingHoursDTO);
        WorkingHours workingHours = workingHoursMapper.toEntity(workingHoursDTO);
        workingHours = workingHoursRepository.save(workingHours);
        validationService.validateMethod(workingHours);
        return workingHoursMapper.toDto(workingHours);
    }

    /**
     * Partially update a workingHours.
     *
     * @param workingHoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkingHoursDTO> partialUpdate(WorkingHoursDTO workingHoursDTO) {
        log.debug("Request to partially update WorkingHours : {}", workingHoursDTO);

        return workingHoursRepository
            .findById(workingHoursDTO.getId())
            .map(existingWorkingHours -> {
                workingHoursMapper.partialUpdate(existingWorkingHours, workingHoursDTO);

                return existingWorkingHours;
            })
            .map(workingHoursRepository::save)
            .map(workingHoursMapper::toDto);
    }

    /**
     * Get all the workingHours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkingHoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingHours");
        return workingHoursRepository.findAll(pageable).map(workingHoursMapper::toDto);
    }

    /**
     * Get one workingHours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkingHoursDTO> findOne(Long id) {
        log.debug("Request to get WorkingHours : {}", id);
        return workingHoursRepository.findById(id).map(workingHoursMapper::toDto);
    }

    /**
     * Delete the workingHours by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkingHours : {}", id);
        workingHoursRepository.deleteById(id);
    }
}
