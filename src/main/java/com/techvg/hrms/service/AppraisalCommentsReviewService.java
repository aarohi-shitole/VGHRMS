package com.techvg.hrms.service;

import com.techvg.hrms.domain.AppraisalCommentsReview;
import com.techvg.hrms.repository.AppraisalCommentsReviewRepository;
import com.techvg.hrms.service.dto.AppraisalCommentsReviewDTO;
import com.techvg.hrms.service.mapper.AppraisalCommentsReviewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppraisalCommentsReview}.
 */
@Service
@Transactional
public class AppraisalCommentsReviewService {

    private final Logger log = LoggerFactory.getLogger(AppraisalCommentsReviewService.class);

    private final AppraisalCommentsReviewRepository appraisalCommentsReviewRepository;

    private final AppraisalCommentsReviewMapper appraisalCommentsReviewMapper;

    public AppraisalCommentsReviewService(
        AppraisalCommentsReviewRepository appraisalCommentsReviewRepository,
        AppraisalCommentsReviewMapper appraisalCommentsReviewMapper
    ) {
        this.appraisalCommentsReviewRepository = appraisalCommentsReviewRepository;
        this.appraisalCommentsReviewMapper = appraisalCommentsReviewMapper;
    }

    /**
     * Save a appraisalCommentsReview.
     *
     * @param appraisalCommentsReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalCommentsReviewDTO save(AppraisalCommentsReviewDTO appraisalCommentsReviewDTO) {
        log.debug("Request to save AppraisalCommentsReview : {}", appraisalCommentsReviewDTO);
        AppraisalCommentsReview appraisalCommentsReview = appraisalCommentsReviewMapper.toEntity(appraisalCommentsReviewDTO);
        appraisalCommentsReview = appraisalCommentsReviewRepository.save(appraisalCommentsReview);
        return appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);
    }

    /**
     * Update a appraisalCommentsReview.
     *
     * @param appraisalCommentsReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalCommentsReviewDTO update(AppraisalCommentsReviewDTO appraisalCommentsReviewDTO) {
        log.debug("Request to update AppraisalCommentsReview : {}", appraisalCommentsReviewDTO);
        AppraisalCommentsReview appraisalCommentsReview = appraisalCommentsReviewMapper.toEntity(appraisalCommentsReviewDTO);
        appraisalCommentsReview = appraisalCommentsReviewRepository.save(appraisalCommentsReview);
        return appraisalCommentsReviewMapper.toDto(appraisalCommentsReview);
    }

    /**
     * Partially update a appraisalCommentsReview.
     *
     * @param appraisalCommentsReviewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppraisalCommentsReviewDTO> partialUpdate(AppraisalCommentsReviewDTO appraisalCommentsReviewDTO) {
        log.debug("Request to partially update AppraisalCommentsReview : {}", appraisalCommentsReviewDTO);

        return appraisalCommentsReviewRepository
            .findById(appraisalCommentsReviewDTO.getId())
            .map(existingAppraisalCommentsReview -> {
                appraisalCommentsReviewMapper.partialUpdate(existingAppraisalCommentsReview, appraisalCommentsReviewDTO);

                return existingAppraisalCommentsReview;
            })
            .map(appraisalCommentsReviewRepository::save)
            .map(appraisalCommentsReviewMapper::toDto);
    }

    /**
     * Get all the appraisalCommentsReviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalCommentsReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppraisalCommentsReviews");
        return appraisalCommentsReviewRepository.findAll(pageable).map(appraisalCommentsReviewMapper::toDto);
    }

    /**
     * Get one appraisalCommentsReview by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppraisalCommentsReviewDTO> findOne(Long id) {
        log.debug("Request to get AppraisalCommentsReview : {}", id);
        return appraisalCommentsReviewRepository.findById(id).map(appraisalCommentsReviewMapper::toDto);
    }

    /**
     * Delete the appraisalCommentsReview by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppraisalCommentsReview : {}", id);
        appraisalCommentsReviewRepository.deleteById(id);
    }
}
