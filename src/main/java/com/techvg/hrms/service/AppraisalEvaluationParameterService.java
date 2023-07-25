package com.techvg.hrms.service;

import com.techvg.hrms.domain.AppraisalEvaluationParameter;
import com.techvg.hrms.repository.AppraisalEvaluationParameterRepository;
import com.techvg.hrms.service.dto.AppraisalEvaluationParameterDTO;
import com.techvg.hrms.service.mapper.AppraisalEvaluationParameterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppraisalEvaluationParameter}.
 */
@Service
@Transactional
public class AppraisalEvaluationParameterService {

    private final Logger log = LoggerFactory.getLogger(AppraisalEvaluationParameterService.class);

    private final AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository;

    private final AppraisalEvaluationParameterMapper appraisalEvaluationParameterMapper;

    public AppraisalEvaluationParameterService(
        AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository,
        AppraisalEvaluationParameterMapper appraisalEvaluationParameterMapper
    ) {
        this.appraisalEvaluationParameterRepository = appraisalEvaluationParameterRepository;
        this.appraisalEvaluationParameterMapper = appraisalEvaluationParameterMapper;
    }

    /**
     * Save a appraisalEvaluationParameter.
     *
     * @param appraisalEvaluationParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalEvaluationParameterDTO save(AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO) {
        log.debug("Request to save AppraisalEvaluationParameter : {}", appraisalEvaluationParameterDTO);
        AppraisalEvaluationParameter appraisalEvaluationParameter = appraisalEvaluationParameterMapper.toEntity(
            appraisalEvaluationParameterDTO
        );
        appraisalEvaluationParameter = appraisalEvaluationParameterRepository.save(appraisalEvaluationParameter);
        return appraisalEvaluationParameterMapper.toDto(appraisalEvaluationParameter);
    }

    /**
     * Update a appraisalEvaluationParameter.
     *
     * @param appraisalEvaluationParameterDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalEvaluationParameterDTO update(AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO) {
        log.debug("Request to update AppraisalEvaluationParameter : {}", appraisalEvaluationParameterDTO);
        AppraisalEvaluationParameter appraisalEvaluationParameter = appraisalEvaluationParameterMapper.toEntity(
            appraisalEvaluationParameterDTO
        );
        appraisalEvaluationParameter = appraisalEvaluationParameterRepository.save(appraisalEvaluationParameter);
        return appraisalEvaluationParameterMapper.toDto(appraisalEvaluationParameter);
    }

    /**
     * Partially update a appraisalEvaluationParameter.
     *
     * @param appraisalEvaluationParameterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppraisalEvaluationParameterDTO> partialUpdate(AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO) {
        log.debug("Request to partially update AppraisalEvaluationParameter : {}", appraisalEvaluationParameterDTO);

        return appraisalEvaluationParameterRepository
            .findById(appraisalEvaluationParameterDTO.getId())
            .map(existingAppraisalEvaluationParameter -> {
                appraisalEvaluationParameterMapper.partialUpdate(existingAppraisalEvaluationParameter, appraisalEvaluationParameterDTO);

                return existingAppraisalEvaluationParameter;
            })
            .map(appraisalEvaluationParameterRepository::save)
            .map(appraisalEvaluationParameterMapper::toDto);
    }

    /**
     * Get all the appraisalEvaluationParameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalEvaluationParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppraisalEvaluationParameters");
        return appraisalEvaluationParameterRepository.findAll(pageable).map(appraisalEvaluationParameterMapper::toDto);
    }

    /**
     * Get one appraisalEvaluationParameter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppraisalEvaluationParameterDTO> findOne(Long id) {
        log.debug("Request to get AppraisalEvaluationParameter : {}", id);
        return appraisalEvaluationParameterRepository.findById(id).map(appraisalEvaluationParameterMapper::toDto);
    }

    /**
     * Delete the appraisalEvaluationParameter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppraisalEvaluationParameter : {}", id);
        appraisalEvaluationParameterRepository.deleteById(id);
    }
}
