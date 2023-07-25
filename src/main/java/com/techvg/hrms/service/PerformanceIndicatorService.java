package com.techvg.hrms.service;

import com.techvg.hrms.domain.PerformanceIndicator;
import com.techvg.hrms.repository.PerformanceIndicatorRepository;
import com.techvg.hrms.service.dto.PerformanceIndicatorDTO;
import com.techvg.hrms.service.mapper.PerformanceIndicatorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PerformanceIndicator}.
 */
@Service
@Transactional
public class PerformanceIndicatorService {

    private final Logger log = LoggerFactory.getLogger(PerformanceIndicatorService.class);

    private final PerformanceIndicatorRepository performanceIndicatorRepository;

    private final PerformanceIndicatorMapper performanceIndicatorMapper;

    public PerformanceIndicatorService(
        PerformanceIndicatorRepository performanceIndicatorRepository,
        PerformanceIndicatorMapper performanceIndicatorMapper
    ) {
        this.performanceIndicatorRepository = performanceIndicatorRepository;
        this.performanceIndicatorMapper = performanceIndicatorMapper;
    }

    /**
     * Save a performanceIndicator.
     *
     * @param performanceIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceIndicatorDTO save(PerformanceIndicatorDTO performanceIndicatorDTO) {
        log.debug("Request to save PerformanceIndicator : {}", performanceIndicatorDTO);
        PerformanceIndicator performanceIndicator = performanceIndicatorMapper.toEntity(performanceIndicatorDTO);
        performanceIndicator = performanceIndicatorRepository.save(performanceIndicator);
        return performanceIndicatorMapper.toDto(performanceIndicator);
    }

    /**
     * Update a performanceIndicator.
     *
     * @param performanceIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceIndicatorDTO update(PerformanceIndicatorDTO performanceIndicatorDTO) {
        log.debug("Request to update PerformanceIndicator : {}", performanceIndicatorDTO);
        PerformanceIndicator performanceIndicator = performanceIndicatorMapper.toEntity(performanceIndicatorDTO);
        performanceIndicator = performanceIndicatorRepository.save(performanceIndicator);
        return performanceIndicatorMapper.toDto(performanceIndicator);
    }

    /**
     * Partially update a performanceIndicator.
     *
     * @param performanceIndicatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceIndicatorDTO> partialUpdate(PerformanceIndicatorDTO performanceIndicatorDTO) {
        log.debug("Request to partially update PerformanceIndicator : {}", performanceIndicatorDTO);

        return performanceIndicatorRepository
            .findById(performanceIndicatorDTO.getId())
            .map(existingPerformanceIndicator -> {
                performanceIndicatorMapper.partialUpdate(existingPerformanceIndicator, performanceIndicatorDTO);

                return existingPerformanceIndicator;
            })
            .map(performanceIndicatorRepository::save)
            .map(performanceIndicatorMapper::toDto);
    }

    /**
     * Get all the performanceIndicators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceIndicatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PerformanceIndicators");
        return performanceIndicatorRepository.findAll(pageable).map(performanceIndicatorMapper::toDto);
    }

    /**
     * Get one performanceIndicator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceIndicatorDTO> findOne(Long id) {
        log.debug("Request to get PerformanceIndicator : {}", id);
        return performanceIndicatorRepository.findById(id).map(performanceIndicatorMapper::toDto);
    }

    /**
     * Delete the performanceIndicator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceIndicator : {}", id);
        performanceIndicatorRepository.deleteById(id);
    }
}
