package com.techvg.hrms.service;

import com.techvg.hrms.domain.PerformanceReview;
import com.techvg.hrms.repository.PerformanceReviewRepository;
import com.techvg.hrms.service.dto.PerformanceReviewDTO;
import com.techvg.hrms.service.mapper.PerformanceReviewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PerformanceReview}.
 */
@Service
@Transactional
public class PerformanceReviewService {

    private final Logger log = LoggerFactory.getLogger(PerformanceReviewService.class);

    private final PerformanceReviewRepository performanceReviewRepository;

    private final PerformanceReviewMapper performanceReviewMapper;

    public PerformanceReviewService(
        PerformanceReviewRepository performanceReviewRepository,
        PerformanceReviewMapper performanceReviewMapper
    ) {
        this.performanceReviewRepository = performanceReviewRepository;
        this.performanceReviewMapper = performanceReviewMapper;
    }

    /**
     * Save a performanceReview.
     *
     * @param performanceReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceReviewDTO save(PerformanceReviewDTO performanceReviewDTO) {
        log.debug("Request to save PerformanceReview : {}", performanceReviewDTO);
        PerformanceReview performanceReview = performanceReviewMapper.toEntity(performanceReviewDTO);
        performanceReview = performanceReviewRepository.save(performanceReview);
        return performanceReviewMapper.toDto(performanceReview);
    }

    /**
     * Update a performanceReview.
     *
     * @param performanceReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public PerformanceReviewDTO update(PerformanceReviewDTO performanceReviewDTO) {
        log.debug("Request to update PerformanceReview : {}", performanceReviewDTO);
        PerformanceReview performanceReview = performanceReviewMapper.toEntity(performanceReviewDTO);
        performanceReview = performanceReviewRepository.save(performanceReview);
        return performanceReviewMapper.toDto(performanceReview);
    }

    /**
     * Partially update a performanceReview.
     *
     * @param performanceReviewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerformanceReviewDTO> partialUpdate(PerformanceReviewDTO performanceReviewDTO) {
        log.debug("Request to partially update PerformanceReview : {}", performanceReviewDTO);

        return performanceReviewRepository
            .findById(performanceReviewDTO.getId())
            .map(existingPerformanceReview -> {
                performanceReviewMapper.partialUpdate(existingPerformanceReview, performanceReviewDTO);

                return existingPerformanceReview;
            })
            .map(performanceReviewRepository::save)
            .map(performanceReviewMapper::toDto);
    }

    /**
     * Get all the performanceReviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PerformanceReviews");
        return performanceReviewRepository.findAll(pageable).map(performanceReviewMapper::toDto);
    }

    /**
     * Get one performanceReview by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceReviewDTO> findOne(Long id) {
        log.debug("Request to get PerformanceReview : {}", id);
        return performanceReviewRepository.findById(id).map(performanceReviewMapper::toDto);
    }

    /**
     * Delete the performanceReview by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceReview : {}", id);
        performanceReviewRepository.deleteById(id);
    }
}
