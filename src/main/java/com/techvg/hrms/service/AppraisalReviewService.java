package com.techvg.hrms.service;

import com.techvg.hrms.domain.AppraisalReview;
import com.techvg.hrms.repository.AppraisalReviewRepository;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.mapper.AppraisalReviewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppraisalReview}.
 */
@Service
@Transactional
public class AppraisalReviewService {

    private final Logger log = LoggerFactory.getLogger(AppraisalReviewService.class);

    private final AppraisalReviewRepository appraisalReviewRepository;

    private final AppraisalReviewMapper appraisalReviewMapper;

    public AppraisalReviewService(AppraisalReviewRepository appraisalReviewRepository, AppraisalReviewMapper appraisalReviewMapper) {
        this.appraisalReviewRepository = appraisalReviewRepository;
        this.appraisalReviewMapper = appraisalReviewMapper;
    }

    /**
     * Save a appraisalReview.
     *
     * @param appraisalReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalReviewDTO save(AppraisalReviewDTO appraisalReviewDTO) {
        log.debug("Request to save AppraisalReview : {}", appraisalReviewDTO);
        AppraisalReview appraisalReview = appraisalReviewMapper.toEntity(appraisalReviewDTO);
        appraisalReview = appraisalReviewRepository.save(appraisalReview);
        return appraisalReviewMapper.toDto(appraisalReview);
    }

    /**
     * Update a appraisalReview.
     *
     * @param appraisalReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalReviewDTO update(AppraisalReviewDTO appraisalReviewDTO) {
        log.debug("Request to update AppraisalReview : {}", appraisalReviewDTO);
        AppraisalReview appraisalReview = appraisalReviewMapper.toEntity(appraisalReviewDTO);
        appraisalReview = appraisalReviewRepository.save(appraisalReview);
        return appraisalReviewMapper.toDto(appraisalReview);
    }

    /**
     * Partially update a appraisalReview.
     *
     * @param appraisalReviewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppraisalReviewDTO> partialUpdate(AppraisalReviewDTO appraisalReviewDTO) {
        log.debug("Request to partially update AppraisalReview : {}", appraisalReviewDTO);

        return appraisalReviewRepository
            .findById(appraisalReviewDTO.getId())
            .map(existingAppraisalReview -> {
                appraisalReviewMapper.partialUpdate(existingAppraisalReview, appraisalReviewDTO);

                return existingAppraisalReview;
            })
            .map(appraisalReviewRepository::save)
            .map(appraisalReviewMapper::toDto);
    }

    /**
     * Get all the appraisalReviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppraisalReviews");
        return appraisalReviewRepository.findAll(pageable).map(appraisalReviewMapper::toDto);
    }

    /**
     * Get one appraisalReview by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppraisalReviewDTO> findOne(Long id) {
        log.debug("Request to get AppraisalReview : {}", id);
        return appraisalReviewRepository.findById(id).map(appraisalReviewMapper::toDto);
    }

    /**
     * Delete the appraisalReview by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppraisalReview : {}", id);
        appraisalReviewRepository.deleteById(id);
    }
}
