package com.techvg.hrms.service;

import com.techvg.hrms.domain.OverTime;
import com.techvg.hrms.repository.OverTimeRepository;
import com.techvg.hrms.service.dto.OverTimeDTO;
import com.techvg.hrms.service.mapper.OverTimeMapper;

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
 * Service Implementation for managing {@link OverTime}.
 */
@Service
@Transactional
public class OverTimeService {

    private final Logger log = LoggerFactory.getLogger(OverTimeService.class);

    private final OverTimeRepository overTimeRepository;

    private final OverTimeMapper overTimeMapper;
    
    @Autowired
    private ValidationService validationService;

    public OverTimeService(OverTimeRepository overTimeRepository, OverTimeMapper overTimeMapper) {
        this.overTimeRepository = overTimeRepository;
        this.overTimeMapper = overTimeMapper;
    }

    /**
     * Save a overTime.
     *
     * @param overTimeDTO the entity to save.
     * @return the persisted entity.
     */
    public OverTimeDTO save(OverTimeDTO overTimeDTO) {
        log.debug("Request to save OverTime : {}", overTimeDTO);
      
        OverTime overTime = overTimeMapper.toEntity(overTimeDTO);
        validationService.validateMethod(overTime);
        overTime = overTimeRepository.save(overTime);
        return overTimeMapper.toDto(overTime);
    }

    /**
     * Update a overTime.
     *
     * @param overTimeDTO the entity to save.
     * @return the persisted entity.
     */
    public OverTimeDTO update(OverTimeDTO overTimeDTO) {
        log.debug("Request to update OverTime : {}", overTimeDTO);
       
        OverTime overTime = overTimeMapper.toEntity(overTimeDTO);
        validationService.validateMethod(overTime);
        overTime = overTimeRepository.save(overTime);
        return overTimeMapper.toDto(overTime);
    }

    /**
     * Partially update a overTime.
     *
     * @param overTimeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OverTimeDTO> partialUpdate(OverTimeDTO overTimeDTO) {
        log.debug("Request to partially update OverTime : {}", overTimeDTO);

        return overTimeRepository
            .findById(overTimeDTO.getId())
            .map(existingOverTime -> {
                overTimeMapper.partialUpdate(existingOverTime, overTimeDTO);

                return existingOverTime;
            })
            .map(overTimeRepository::save)
            .map(overTimeMapper::toDto);
    }

    /**
     * Get all the overTimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OverTimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OverTimes");
        return overTimeRepository.findAll(pageable).map(overTimeMapper::toDto);
    }

    /**
     * Get one overTime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OverTimeDTO> findOne(Long id) {
        log.debug("Request to get OverTime : {}", id);
        return overTimeRepository.findById(id).map(overTimeMapper::toDto);
    }

    /**
     * Delete the overTime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OverTime : {}", id);
        overTimeRepository.deleteById(id);
    }
}
