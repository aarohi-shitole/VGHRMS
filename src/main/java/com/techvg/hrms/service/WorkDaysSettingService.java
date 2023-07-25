package com.techvg.hrms.service;

import com.techvg.hrms.domain.WorkDaysSetting;
import com.techvg.hrms.repository.WorkDaysSettingRepository;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
import com.techvg.hrms.service.mapper.WorkDaysSettingMapper;

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
 * Service Implementation for managing {@link WorkDaysSetting}.
 */
@Service
@Transactional
public class WorkDaysSettingService {

    private final Logger log = LoggerFactory.getLogger(WorkDaysSettingService.class);

    private final WorkDaysSettingRepository workDaysSettingRepository;

    private final WorkDaysSettingMapper workDaysSettingMapper;
    
    @Autowired
    private ValidationService validationService;

    public WorkDaysSettingService(WorkDaysSettingRepository workDaysSettingRepository, WorkDaysSettingMapper workDaysSettingMapper) {
        this.workDaysSettingRepository = workDaysSettingRepository;
        this.workDaysSettingMapper = workDaysSettingMapper;
    }

    /**
     * Save a workDaysSetting.
     *
     * @param workDaysSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkDaysSettingDTO save(WorkDaysSettingDTO workDaysSettingDTO) {
        log.debug("Request to save WorkDaysSetting : {}", workDaysSettingDTO);
        WorkDaysSetting workDaysSetting = workDaysSettingMapper.toEntity(workDaysSettingDTO);
        validationService.validateMethod(workDaysSetting);
        workDaysSetting = workDaysSettingRepository.save(workDaysSetting);
        return workDaysSettingMapper.toDto(workDaysSetting);
    }

    /**
     * Update a workDaysSetting.
     *
     * @param workDaysSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkDaysSettingDTO update(WorkDaysSettingDTO workDaysSettingDTO) {
        log.debug("Request to update WorkDaysSetting : {}", workDaysSettingDTO);
        WorkDaysSetting workDaysSetting = workDaysSettingMapper.toEntity(workDaysSettingDTO);
        validationService.validateMethod(workDaysSetting);
        workDaysSetting = workDaysSettingRepository.save(workDaysSetting);
        return workDaysSettingMapper.toDto(workDaysSetting);
    }

    /**
     * Partially update a workDaysSetting.
     *
     * @param workDaysSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkDaysSettingDTO> partialUpdate(WorkDaysSettingDTO workDaysSettingDTO) {
        log.debug("Request to partially update WorkDaysSetting : {}", workDaysSettingDTO);

        return workDaysSettingRepository
            .findById(workDaysSettingDTO.getId())
            .map(existingWorkDaysSetting -> {
                workDaysSettingMapper.partialUpdate(existingWorkDaysSetting, workDaysSettingDTO);

                return existingWorkDaysSetting;
            })
            .map(workDaysSettingRepository::save)
            .map(workDaysSettingMapper::toDto);
    }

    /**
     * Get all the workDaysSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkDaysSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkDaysSettings");
        return workDaysSettingRepository.findAll(pageable).map(workDaysSettingMapper::toDto);
    }

    /**
     * Get one workDaysSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkDaysSettingDTO> findOne(Long id) {
        log.debug("Request to get WorkDaysSetting : {}", id);
        return workDaysSettingRepository.findById(id).map(workDaysSettingMapper::toDto);
    }

    /**
     * Delete the workDaysSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkDaysSetting : {}", id);
        workDaysSettingRepository.deleteById(id);
    }
}
