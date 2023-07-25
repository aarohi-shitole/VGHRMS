package com.techvg.hrms.service;

import com.techvg.hrms.domain.AppraisalEvaluation;
import com.techvg.hrms.repository.AppraisalEvaluationRepository;
import com.techvg.hrms.service.dto.AppraisalEvaluationDTO;
import com.techvg.hrms.service.mapper.AppraisalEvaluationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppraisalEvaluation}.
 */
@Service
@Transactional
public class AppraisalEvaluationService {

    private final Logger log = LoggerFactory.getLogger(AppraisalEvaluationService.class);

    private final AppraisalEvaluationRepository appraisalEvaluationRepository;

    private final AppraisalEvaluationMapper appraisalEvaluationMapper;

    public AppraisalEvaluationService(
        AppraisalEvaluationRepository appraisalEvaluationRepository,
        AppraisalEvaluationMapper appraisalEvaluationMapper
    ) {
        this.appraisalEvaluationRepository = appraisalEvaluationRepository;
        this.appraisalEvaluationMapper = appraisalEvaluationMapper;
    }

    /**
     * Save a appraisalEvaluation.
     *
     * @param appraisalEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalEvaluationDTO save(AppraisalEvaluationDTO appraisalEvaluationDTO) {
        log.debug("Request to save AppraisalEvaluation : {}", appraisalEvaluationDTO);
        AppraisalEvaluation appraisalEvaluation = appraisalEvaluationMapper.toEntity(appraisalEvaluationDTO);
        appraisalEvaluation = appraisalEvaluationRepository.save(appraisalEvaluation);
        return appraisalEvaluationMapper.toDto(appraisalEvaluation);
    }

    /**
     * Update a appraisalEvaluation.
     *
     * @param appraisalEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalEvaluationDTO update(AppraisalEvaluationDTO appraisalEvaluationDTO) {
        log.debug("Request to update AppraisalEvaluation : {}", appraisalEvaluationDTO);
        AppraisalEvaluation appraisalEvaluation = appraisalEvaluationMapper.toEntity(appraisalEvaluationDTO);
        appraisalEvaluation = appraisalEvaluationRepository.save(appraisalEvaluation);
        return appraisalEvaluationMapper.toDto(appraisalEvaluation);
    }

    /**
     * Partially update a appraisalEvaluation.
     *
     * @param appraisalEvaluationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppraisalEvaluationDTO> partialUpdate(AppraisalEvaluationDTO appraisalEvaluationDTO) {
        log.debug("Request to partially update AppraisalEvaluation : {}", appraisalEvaluationDTO);

        return appraisalEvaluationRepository
            .findById(appraisalEvaluationDTO.getId())
            .map(existingAppraisalEvaluation -> {
                appraisalEvaluationMapper.partialUpdate(existingAppraisalEvaluation, appraisalEvaluationDTO);

                return existingAppraisalEvaluation;
            })
            .map(appraisalEvaluationRepository::save)
            .map(appraisalEvaluationMapper::toDto);
    }

    /**
     * Get all the appraisalEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppraisalEvaluations");
        return appraisalEvaluationRepository.findAll(pageable).map(appraisalEvaluationMapper::toDto);
    }

    /**
     * Get one appraisalEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppraisalEvaluationDTO> findOne(Long id) {
        log.debug("Request to get AppraisalEvaluation : {}", id);
        return appraisalEvaluationRepository.findById(id).map(appraisalEvaluationMapper::toDto);
    }

    /**
     * Delete the appraisalEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppraisalEvaluation : {}", id);
        appraisalEvaluationRepository.deleteById(id);
    }
}
