package com.techvg.hrms.service;

import com.techvg.hrms.domain.PerformanceAppraisal;
import com.techvg.hrms.repository.PerformanceAppraisalRepository;
import com.techvg.hrms.service.dto.PerformanceAppraisalDTO;
import com.techvg.hrms.service.mapper.PerformanceAppraisalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PerformanceAppraisal}.
 */
@Service
@Transactional
public class PerformanceAppraisalService {

    private final Logger log = LoggerFactory.getLogger(PerformanceAppraisalService.class);

    private final PerformanceAppraisalRepository performanceAppraisalRepository;

    private final PerformanceAppraisalMapper performanceAppraisalMapper;

    public PerformanceAppraisalService(
        PerformanceAppraisalRepository performanceAppraisalRepository,
        PerformanceAppraisalMapper performanceAppraisalMapper
    ) {
        this.performanceAppraisalRepository = performanceAppraisalRepository;
        this.performanceAppraisalMapper = performanceAppraisalMapper;
    }

    /**
     * Save a performanceAppraisal.
     *
     * @param performanceAppraisalDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceAppraisalDTO save(PerformanceAppraisalDTO performanceAppraisalDTO) {
        log.debug("Request to save PerformanceAppraisal : {}", performanceAppraisalDTO);
        PerformanceAppraisal performanceAppraisal = performanceAppraisalMapper.toEntity(performanceAppraisalDTO);
        performanceAppraisal = performanceAppraisalRepository.save(performanceAppraisal);
        return performanceAppraisalMapper.toDto(performanceAppraisal);
    }

    /**
     * Update a performanceAppraisal.
     *
     * @param performanceAppraisalDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceAppraisalDTO update(PerformanceAppraisalDTO performanceAppraisalDTO) {
        log.debug("Request to update PerformanceAppraisal : {}", performanceAppraisalDTO);
        PerformanceAppraisal performanceAppraisal = performanceAppraisalMapper.toEntity(performanceAppraisalDTO);
        performanceAppraisal = performanceAppraisalRepository.save(performanceAppraisal);
        return performanceAppraisalMapper.toDto(performanceAppraisal);
    }

    /**
     * Partially update a performanceAppraisal.
     *
     * @param performanceAppraisalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceAppraisalDTO> partialUpdate(PerformanceAppraisalDTO performanceAppraisalDTO) {
        log.debug("Request to partially update PerformanceAppraisal : {}", performanceAppraisalDTO);

        return performanceAppraisalRepository
            .findById(performanceAppraisalDTO.getId())
            .map(existingPerformanceAppraisal -> {
                performanceAppraisalMapper.partialUpdate(existingPerformanceAppraisal, performanceAppraisalDTO);

                return existingPerformanceAppraisal;
            })
            .map(performanceAppraisalRepository::save)
            .map(performanceAppraisalMapper::toDto);
    }

    /**
     * Get all the performanceAppraisals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceAppraisalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PerformanceAppraisals");
        return performanceAppraisalRepository.findAll(pageable).map(performanceAppraisalMapper::toDto);
    }

    /**
     * Get one performanceAppraisal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceAppraisalDTO> findOne(Long id) {
        log.debug("Request to get PerformanceAppraisal : {}", id);
        return performanceAppraisalRepository.findById(id).map(performanceAppraisalMapper::toDto);
    }

    /**
     * Delete the performanceAppraisal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceAppraisal : {}", id);
        performanceAppraisalRepository.deleteById(id);
    }
}
