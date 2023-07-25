package com.techvg.hrms.service;

import com.techvg.hrms.domain.ApprovalSetting;
import com.techvg.hrms.repository.ApprovalSettingRepository;
import com.techvg.hrms.service.dto.ApprovalSettingDTO;
import com.techvg.hrms.service.mapper.ApprovalSettingMapper;

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
 * Service Implementation for managing {@link ApprovalSetting}.
 */
@Service
@Transactional
public class ApprovalSettingService {

    private final Logger log = LoggerFactory.getLogger(ApprovalSettingService.class);

    private final ApprovalSettingRepository approvalSettingRepository;

    private final ApprovalSettingMapper approvalSettingMapper;
	 @Autowired
	    private ValidationService validationService;

    public ApprovalSettingService(ApprovalSettingRepository approvalSettingRepository, ApprovalSettingMapper approvalSettingMapper) {
        this.approvalSettingRepository = approvalSettingRepository;
        this.approvalSettingMapper = approvalSettingMapper;
    }

    /**
     * Save a approvalSetting.
     *
     * @param approvalSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalSettingDTO save(ApprovalSettingDTO approvalSettingDTO) {
        log.debug("Request to save ApprovalSetting : {}", approvalSettingDTO);
       
        ApprovalSetting approvalSetting = approvalSettingMapper.toEntity(approvalSettingDTO);
        validationService.validateMethod(approvalSetting);
        approvalSetting = approvalSettingRepository.save(approvalSetting);
        return approvalSettingMapper.toDto(approvalSetting);
    }

    /**
     * Update a approvalSetting.
     *
     * @param approvalSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalSettingDTO update(ApprovalSettingDTO approvalSettingDTO) {
        log.debug("Request to update ApprovalSetting : {}", approvalSettingDTO);
       
        ApprovalSetting approvalSetting = approvalSettingMapper.toEntity(approvalSettingDTO);
        validationService.validateMethod(approvalSetting);
        approvalSetting = approvalSettingRepository.save(approvalSetting);
        return approvalSettingMapper.toDto(approvalSetting);
    }

    /**
     * Partially update a approvalSetting.
     *
     * @param approvalSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApprovalSettingDTO> partialUpdate(ApprovalSettingDTO approvalSettingDTO) {
        log.debug("Request to partially update ApprovalSetting : {}", approvalSettingDTO);

        return approvalSettingRepository
            .findById(approvalSettingDTO.getId())
            .map(existingApprovalSetting -> {
                approvalSettingMapper.partialUpdate(existingApprovalSetting, approvalSettingDTO);

                return existingApprovalSetting;
            })
            .map(approvalSettingRepository::save)
            .map(approvalSettingMapper::toDto);
    }

    /**
     * Get all the approvalSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApprovalSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApprovalSettings");
        return approvalSettingRepository.findAll(pageable).map(approvalSettingMapper::toDto);
    }

    /**
     * Get one approvalSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApprovalSettingDTO> findOne(Long id) {
        log.debug("Request to get ApprovalSetting : {}", id);
        return approvalSettingRepository.findById(id).map(approvalSettingMapper::toDto);
    }

    /**
     * Delete the approvalSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApprovalSetting : {}", id);
        approvalSettingRepository.deleteById(id);
    }
}
