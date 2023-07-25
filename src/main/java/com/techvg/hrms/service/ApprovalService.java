package com.techvg.hrms.service;

import com.techvg.hrms.domain.Approval;
import com.techvg.hrms.repository.ApprovalRepository;
import com.techvg.hrms.service.dto.ApprovalDTO;
import com.techvg.hrms.service.mapper.ApprovalMapper;

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
 * Service Implementation for managing {@link Approval}.
 */
@Service
@Transactional
public class ApprovalService {

    private final Logger log = LoggerFactory.getLogger(ApprovalService.class);

    private final ApprovalRepository approvalRepository;

    private final ApprovalMapper approvalMapper;
	 @Autowired
	    private ValidationService validationService;
	 
    public ApprovalService(ApprovalRepository approvalRepository, ApprovalMapper approvalMapper) {
        this.approvalRepository = approvalRepository;
        this.approvalMapper = approvalMapper;
    }

    /**
     * Save a Approval.
     *
     * @param ApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalDTO save(ApprovalDTO approvalDTO) {
        log.debug("Request to save Approval : {}", approvalDTO);
       
        Approval approval = approvalMapper.toEntity(approvalDTO);
        validationService.validateMethod(approval);
        approval = approvalRepository.save(approval);
        return approvalMapper.toDto(approval);
    }

    /**
     * Update a Approval.
     *
     * @param ApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    public ApprovalDTO update(ApprovalDTO approvalDTO) {
        log.debug("Request to update Approval : {}", approvalDTO);
       
        Approval approval = approvalMapper.toEntity(approvalDTO);
        validationService.validateMethod(approval);
        approval = approvalRepository.save(approval);
        return approvalMapper.toDto(approval);
    }

    /**
     * Partially update a approval.
     *
     * @param approvalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApprovalDTO> partialUpdate(ApprovalDTO approvalDTO) {
        log.debug("Request to partially update Approval : {}", approvalDTO);

        return approvalRepository
            .findById(approvalDTO.getId())
            .map(existingApproval -> {
                approvalMapper.partialUpdate(existingApproval, approvalDTO);

                return existingApproval;
            })
            .map(approvalRepository::save)
            .map(approvalMapper::toDto);
    }

    /**
     * Get all the approvals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApprovalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Approvals");
        return approvalRepository.findAll(pageable).map(approvalMapper::toDto);
    }

    /**
     * Get one approval by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApprovalDTO> findOne(Long id) {
        log.debug("Request to get Approval : {}", id);
        return approvalRepository.findById(id).map(approvalMapper::toDto);
    }

    /**
     * Delete the approval by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Approval : {}", id);
        approvalRepository.deleteById(id);
    }
}
