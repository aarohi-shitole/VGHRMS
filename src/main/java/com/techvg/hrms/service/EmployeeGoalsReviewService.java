package com.techvg.hrms.service;

import com.techvg.hrms.domain.EmployeeGoalsReview;
import com.techvg.hrms.repository.EmployeeGoalsReviewRepository;
import com.techvg.hrms.service.dto.EmployeeGoalsReviewDTO;
import com.techvg.hrms.service.mapper.EmployeeGoalsReviewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeGoalsReview}.
 */
@Service
@Transactional
public class EmployeeGoalsReviewService {

    private final Logger log = LoggerFactory.getLogger(EmployeeGoalsReviewService.class);

    private final EmployeeGoalsReviewRepository employeeGoalsReviewRepository;

    private final EmployeeGoalsReviewMapper employeeGoalsReviewMapper;

    public EmployeeGoalsReviewService(
        EmployeeGoalsReviewRepository employeeGoalsReviewRepository,
        EmployeeGoalsReviewMapper employeeGoalsReviewMapper
    ) {
        this.employeeGoalsReviewRepository = employeeGoalsReviewRepository;
        this.employeeGoalsReviewMapper = employeeGoalsReviewMapper;
    }

    /**
     * Save a employeeGoalsReview.
     *
     * @param employeeGoalsReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeGoalsReviewDTO save(EmployeeGoalsReviewDTO employeeGoalsReviewDTO) {
        log.debug("Request to save EmployeeGoalsReview : {}", employeeGoalsReviewDTO);
        EmployeeGoalsReview employeeGoalsReview = employeeGoalsReviewMapper.toEntity(employeeGoalsReviewDTO);
        employeeGoalsReview = employeeGoalsReviewRepository.save(employeeGoalsReview);
        return employeeGoalsReviewMapper.toDto(employeeGoalsReview);
    }

    /**
     * Update a employeeGoalsReview.
     *
     * @param employeeGoalsReviewDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeGoalsReviewDTO update(EmployeeGoalsReviewDTO employeeGoalsReviewDTO) {
        log.debug("Request to update EmployeeGoalsReview : {}", employeeGoalsReviewDTO);
        EmployeeGoalsReview employeeGoalsReview = employeeGoalsReviewMapper.toEntity(employeeGoalsReviewDTO);
        employeeGoalsReview = employeeGoalsReviewRepository.save(employeeGoalsReview);
        return employeeGoalsReviewMapper.toDto(employeeGoalsReview);
    }

    /**
     * Partially update a employeeGoalsReview.
     *
     * @param employeeGoalsReviewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeeGoalsReviewDTO> partialUpdate(EmployeeGoalsReviewDTO employeeGoalsReviewDTO) {
        log.debug("Request to partially update EmployeeGoalsReview : {}", employeeGoalsReviewDTO);

        return employeeGoalsReviewRepository
            .findById(employeeGoalsReviewDTO.getId())
            .map(existingEmployeeGoalsReview -> {
                employeeGoalsReviewMapper.partialUpdate(existingEmployeeGoalsReview, employeeGoalsReviewDTO);

                return existingEmployeeGoalsReview;
            })
            .map(employeeGoalsReviewRepository::save)
            .map(employeeGoalsReviewMapper::toDto);
    }

    /**
     * Get all the employeeGoalsReviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeGoalsReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeGoalsReviews");
        return employeeGoalsReviewRepository.findAll(pageable).map(employeeGoalsReviewMapper::toDto);
    }

    /**
     * Get one employeeGoalsReview by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeGoalsReviewDTO> findOne(Long id) {
        log.debug("Request to get EmployeeGoalsReview : {}", id);
        return employeeGoalsReviewRepository.findById(id).map(employeeGoalsReviewMapper::toDto);
    }

    /**
     * Delete the employeeGoalsReview by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeGoalsReview : {}", id);
        employeeGoalsReviewRepository.deleteById(id);
    }
}
