package com.techvg.hrms.service;

import com.techvg.hrms.domain.AppraisalDetails;
import com.techvg.hrms.repository.AppraisalDetailsRepository;
import com.techvg.hrms.service.dto.AppraisalDetailsDTO;
import com.techvg.hrms.service.mapper.AppraisalDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppraisalDetails}.
 */
@Service
@Transactional
public class AppraisalDetailsService {

    private final Logger log = LoggerFactory.getLogger(AppraisalDetailsService.class);

    private final AppraisalDetailsRepository appraisalDetailsRepository;

    private final AppraisalDetailsMapper appraisalDetailsMapper;

    public AppraisalDetailsService(AppraisalDetailsRepository appraisalDetailsRepository, AppraisalDetailsMapper appraisalDetailsMapper) {
        this.appraisalDetailsRepository = appraisalDetailsRepository;
        this.appraisalDetailsMapper = appraisalDetailsMapper;
    }

    /**
     * Save a AppraisalDetails.
     *
     * @param AppraisalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalDetailsDTO save(AppraisalDetailsDTO appraisalDetailsDTO) {
        log.debug("Request to save AppraisalDetails : {}", appraisalDetailsDTO);
        AppraisalDetails appraisalDetails = appraisalDetailsMapper.toEntity(appraisalDetailsDTO);
        appraisalDetails = appraisalDetailsRepository.save(appraisalDetails);
        return appraisalDetailsMapper.toDto(appraisalDetails);
    }

    /**
     * Update a AppraisalDetails.
     *
     * @param AppraisalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public AppraisalDetailsDTO update(AppraisalDetailsDTO appraisalDetailsDTO) {
        log.debug("Request to update AppraisalDetails : {}", appraisalDetailsDTO);
        AppraisalDetails appraisalDetails = appraisalDetailsMapper.toEntity(appraisalDetailsDTO);
        appraisalDetails = appraisalDetailsRepository.save(appraisalDetails);
        return appraisalDetailsMapper.toDto(appraisalDetails);
    }

    /**
     * Partially update a appraisalDetails.
     *
     * @param appraisalDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppraisalDetailsDTO> partialUpdate(AppraisalDetailsDTO appraisalDetailsDTO) {
        log.debug("Request to partially update appraisalDetails : {}", appraisalDetailsDTO);

        return appraisalDetailsRepository
            .findById(appraisalDetailsDTO.getId())
            .map(existingAppraisalDetails -> {
                appraisalDetailsMapper.partialUpdate(existingAppraisalDetails, appraisalDetailsDTO);

                return existingAppraisalDetails;
            })
            .map(appraisalDetailsRepository::save)
            .map(appraisalDetailsMapper::toDto);
    }

    /**
     * Get all the appraisalDetailss.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppraisalDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppraisalDetailss");
        return appraisalDetailsRepository.findAll(pageable).map(appraisalDetailsMapper::toDto);
    }

    /**
     * Get one appraisalDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppraisalDetailsDTO> findOne(Long id) {
        log.debug("Request to get AppraisalDetails : {}", id);
        return appraisalDetailsRepository.findById(id).map(appraisalDetailsMapper::toDto);
    }

    /**
     * Delete the appraisalDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppraisalDetails : {}", id);
        appraisalDetailsRepository.deleteById(id);
    }
}
