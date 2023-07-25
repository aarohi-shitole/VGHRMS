package com.techvg.hrms.service;

import com.techvg.hrms.domain.TimeSheet;
import com.techvg.hrms.repository.TimeSheetRepository;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import com.techvg.hrms.service.mapper.TimeSheetMapper;

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
 * Service Implementation for managing {@link TimeSheet}.
 */
@Service
@Transactional
public class TimeSheetService {

    private final Logger log = LoggerFactory.getLogger(TimeSheetService.class);

    private final TimeSheetRepository timeSheetRepository;

    private final TimeSheetMapper timeSheetMapper;
    
    @Autowired
    private ValidationService validationService;

    public TimeSheetService(TimeSheetRepository timeSheetRepository, TimeSheetMapper timeSheetMapper) {
        this.timeSheetRepository = timeSheetRepository;
        this.timeSheetMapper = timeSheetMapper;
    }

    /**
     * Save a timeSheet.
     *
     * @param timeSheetDTO the entity to save.
     * @return the persisted entity.
     */
    public TimeSheetDTO save(TimeSheetDTO timeSheetDTO) {
        log.debug("Request to save TimeSheet : {}", timeSheetDTO);
        TimeSheet timeSheet = timeSheetMapper.toEntity(timeSheetDTO);
        validationService.validateMethod(timeSheet);
        timeSheet = timeSheetRepository.save(timeSheet);
        return timeSheetMapper.toDto(timeSheet);
    }

    /**
     * Update a timeSheet.
     *
     * @param timeSheetDTO the entity to save.
     * @return the persisted entity.
     */
    public TimeSheetDTO update(TimeSheetDTO timeSheetDTO) {
        log.debug("Request to update TimeSheet : {}", timeSheetDTO);
        TimeSheet timeSheet = timeSheetMapper.toEntity(timeSheetDTO);
        validationService.validateMethod(timeSheet);
        timeSheet = timeSheetRepository.save(timeSheet);
        return timeSheetMapper.toDto(timeSheet);
    }

    /**
     * Partially update a timeSheet.
     *
     * @param timeSheetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TimeSheetDTO> partialUpdate(TimeSheetDTO timeSheetDTO) {
        log.debug("Request to partially update TimeSheet : {}", timeSheetDTO);

        return timeSheetRepository
            .findById(timeSheetDTO.getId())
            .map(existingTimeSheet -> {
                timeSheetMapper.partialUpdate(existingTimeSheet, timeSheetDTO);

                return existingTimeSheet;
            })
            .map(timeSheetRepository::save)
            .map(timeSheetMapper::toDto);
    }

    /**
     * Get all the timeSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeSheetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeSheets");
        return timeSheetRepository.findAll(pageable).map(timeSheetMapper::toDto);
    }

    /**
     * Get one timeSheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TimeSheetDTO> findOne(Long id) {
        log.debug("Request to get TimeSheet : {}", id);
        return timeSheetRepository.findById(id).map(timeSheetMapper::toDto);
    }

    /**
     * Delete the timeSheet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TimeSheet : {}", id);
        timeSheetRepository.deleteById(id);
    }
}
