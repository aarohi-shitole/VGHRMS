package com.techvg.hrms.service;

import com.techvg.hrms.domain.ApprovalLevel;
import com.techvg.hrms.repository.ApprovalLevelRepository;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.service.mapper.ApprovalLevelMapper;

import java.lang.reflect.Field;
import java.util.Optional;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ApprovalLevel}.
 */
@Service
@Transactional
public class ApprovalLevelService {

    private final Logger log = LoggerFactory.getLogger(ApprovalLevelService.class);

    private final ApprovalLevelRepository approvalLevelRepository;

    private final ApprovalLevelMapper approvalLevelMapper;
    
	 @Autowired
	    private ValidationService validationService;
	 
    public ApprovalLevelService(ApprovalLevelRepository approvalLevelRepository, ApprovalLevelMapper approvalLevelMapper) {
        this.approvalLevelRepository = approvalLevelRepository;
        this.approvalLevelMapper = approvalLevelMapper;
    }

    /**
     * Save a approvalLevel.
     *
     * @param approvalLevelDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalLevelDTO save(ApprovalLevelDTO approvalLevelDTO) {
        log.debug("Request to save ApprovalLevel : {}", approvalLevelDTO);
      
        System.out.println("\n approvalLevelDTO :"+approvalLevelDTO);
        ApprovalLevel approvalLevel = approvalLevelMapper.toEntity(approvalLevelDTO);
        validationService.validateMethod(approvalLevel);
        System.out.println("\n approvalLevelDTO after toEntity :"+approvalLevelDTO);
        approvalLevel = approvalLevelRepository.save(approvalLevel);
        return approvalLevelMapper.toDto(approvalLevel);
    }

    /**
     * Update a approvalLevel.
     *
     * @param approvalLevelDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalLevelDTO update(ApprovalLevelDTO approvalLevelDTO) {
        log.debug("Request to update ApprovalLevel : {}", approvalLevelDTO);
       
        ApprovalLevel approvalLevel = approvalLevelMapper.toEntity(approvalLevelDTO);
        validationService.validateMethod(approvalLevel);
        approvalLevel = approvalLevelRepository.save(approvalLevel);
        return approvalLevelMapper.toDto(approvalLevel);
    }

    /**
     * Partially update a approvalLevel.
     *
     * @param approvalLevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApprovalLevelDTO> partialUpdate(ApprovalLevelDTO approvalLevelDTO) {
        log.debug("Request to partially update ApprovalLevel : {}", approvalLevelDTO);

        return approvalLevelRepository
            .findById(approvalLevelDTO.getId())
            .map(existingApprovalLevel -> {
                approvalLevelMapper.partialUpdate(existingApprovalLevel, approvalLevelDTO);

                return existingApprovalLevel;
            })
            .map(approvalLevelRepository::save)
            .map(approvalLevelMapper::toDto);
    }

    /**
     * Get all the approvalLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApprovalLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApprovalLevels");
        return approvalLevelRepository.findAll(pageable).map(approvalLevelMapper::toDto);
    }

    /**
     * Get one approvalLevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApprovalLevelDTO> findOne(Long id) {
        log.debug("Request to get ApprovalLevel : {}", id);
        return approvalLevelRepository.findById(id).map(approvalLevelMapper::toDto);
    }

    /**
     * Delete the approvalLevel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApprovalLevel : {}", id);
        approvalLevelRepository.deleteById(id);
    }
}
