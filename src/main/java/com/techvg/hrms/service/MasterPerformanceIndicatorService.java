package com.techvg.hrms.service;

import com.techvg.hrms.domain.MasterPerformanceIndicator;
import com.techvg.hrms.repository.MasterPerformanceIndicatorRepository;
import com.techvg.hrms.service.dto.MasterPerformanceIndicatorDTO;
import com.techvg.hrms.service.mapper.MasterPerformanceIndicatorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MasterPerformanceIndicator}.
 */
@Service
@Transactional
public class MasterPerformanceIndicatorService {

    private final Logger log = LoggerFactory.getLogger(MasterPerformanceIndicatorService.class);

    private final MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository;

    private final MasterPerformanceIndicatorMapper masterPerformanceIndicatorMapper;

    public MasterPerformanceIndicatorService(
        MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository,
        MasterPerformanceIndicatorMapper masterPerformanceIndicatorMapper
    ) {
        this.masterPerformanceIndicatorRepository = masterPerformanceIndicatorRepository;
        this.masterPerformanceIndicatorMapper = masterPerformanceIndicatorMapper;
    }

    /**
     * Save a masterPerformanceIndicator.
     *
     * @param masterPerformanceIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    public MasterPerformanceIndicatorDTO save(MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO) {
        log.debug("Request to save MasterPerformanceIndicator : {}", masterPerformanceIndicatorDTO);
        MasterPerformanceIndicator masterPerformanceIndicator = masterPerformanceIndicatorMapper.toEntity(masterPerformanceIndicatorDTO);
        masterPerformanceIndicator = masterPerformanceIndicatorRepository.save(masterPerformanceIndicator);
        return masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);
    }

    /**
     * Update a masterPerformanceIndicator.
     *
     * @param masterPerformanceIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    public MasterPerformanceIndicatorDTO update(MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO) {
        log.debug("Request to update MasterPerformanceIndicator : {}", masterPerformanceIndicatorDTO);
        MasterPerformanceIndicator masterPerformanceIndicator = masterPerformanceIndicatorMapper.toEntity(masterPerformanceIndicatorDTO);
        masterPerformanceIndicator = masterPerformanceIndicatorRepository.save(masterPerformanceIndicator);
        return masterPerformanceIndicatorMapper.toDto(masterPerformanceIndicator);
    }

    /**
     * Partially update a masterPerformanceIndicator.
     *
     * @param masterPerformanceIndicatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MasterPerformanceIndicatorDTO> partialUpdate(MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO) {
        log.debug("Request to partially update MasterPerformanceIndicator : {}", masterPerformanceIndicatorDTO);

        return masterPerformanceIndicatorRepository
            .findById(masterPerformanceIndicatorDTO.getId())
            .map(existingMasterPerformanceIndicator -> {
                masterPerformanceIndicatorMapper.partialUpdate(existingMasterPerformanceIndicator, masterPerformanceIndicatorDTO);

                return existingMasterPerformanceIndicator;
            })
            .map(masterPerformanceIndicatorRepository::save)
            .map(masterPerformanceIndicatorMapper::toDto);
    }

    /**
     * Get all the masterPerformanceIndicators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterPerformanceIndicatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MasterPerformanceIndicators");
        return masterPerformanceIndicatorRepository.findAll(pageable).map(masterPerformanceIndicatorMapper::toDto);
    }

    /**
     * Get one masterPerformanceIndicator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MasterPerformanceIndicatorDTO> findOne(Long id) {
        log.debug("Request to get MasterPerformanceIndicator : {}", id);
        return masterPerformanceIndicatorRepository.findById(id).map(masterPerformanceIndicatorMapper::toDto);
    }

    /**
     * Delete the masterPerformanceIndicator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MasterPerformanceIndicator : {}", id);
        masterPerformanceIndicatorRepository.deleteById(id);
    }
}
