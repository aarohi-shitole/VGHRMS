package com.techvg.hrms.service;

import com.techvg.hrms.domain.LeavePolicy;
import com.techvg.hrms.repository.LeavePolicyRepository;
import com.techvg.hrms.service.dto.LeavePolicyDTO;
import com.techvg.hrms.service.mapper.LeavePolicyMapper;

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
 * Service Implementation for managing {@link LeavePolicy}.
 */
@Service
@Transactional
public class LeavePolicyService {

    private final Logger log = LoggerFactory.getLogger(LeavePolicyService.class);

    private final LeavePolicyRepository leavePolicyRepository;

    private final LeavePolicyMapper leavePolicyMapper;
	  @Autowired
      private ValidationService validationService;
    public LeavePolicyService(LeavePolicyRepository leavePolicyRepository, LeavePolicyMapper leavePolicyMapper) {
        this.leavePolicyRepository = leavePolicyRepository;
        this.leavePolicyMapper = leavePolicyMapper;
    }

    /**
     * Save a leavePolicy.
     *
     * @param leavePolicyDTO the entity to save.
     * @return the persisted entity.
     */
    public LeavePolicyDTO save(LeavePolicyDTO leavePolicyDTO) {
        log.debug("Request to save LeavePolicy : {}", leavePolicyDTO);
      
        LeavePolicy leavePolicy = leavePolicyMapper.toEntity(leavePolicyDTO);
        validationService.validateMethod(leavePolicy);
        leavePolicy = leavePolicyRepository.save(leavePolicy);
        return leavePolicyMapper.toDto(leavePolicy);
    }

    /**
     * Update a leavePolicy.
     *
     * @param leavePolicyDTO the entity to save.
     * @return the persisted entity.
     */
    public LeavePolicyDTO update(LeavePolicyDTO leavePolicyDTO) {
        log.debug("Request to update LeavePolicy : {}", leavePolicyDTO);
       
        LeavePolicy leavePolicy = leavePolicyMapper.toEntity(leavePolicyDTO);
        validationService.validateMethod(leavePolicy);
        leavePolicy = leavePolicyRepository.save(leavePolicy);
        return leavePolicyMapper.toDto(leavePolicy);
    }

    /**
     * Partially update a leavePolicy.
     *
     * @param leavePolicyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LeavePolicyDTO> partialUpdate(LeavePolicyDTO leavePolicyDTO) {
        log.debug("Request to partially update LeavePolicy : {}", leavePolicyDTO);

        return leavePolicyRepository
            .findById(leavePolicyDTO.getId())
            .map(existingLeavePolicy -> {
                leavePolicyMapper.partialUpdate(existingLeavePolicy, leavePolicyDTO);

                return existingLeavePolicy;
            })
            .map(leavePolicyRepository::save)
            .map(leavePolicyMapper::toDto);
    }

    /**
     * Get all the leavePolicies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeavePolicyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeavePolicies");
        return leavePolicyRepository.findAll(pageable).map(leavePolicyMapper::toDto);
    }

    /**
     * Get all the leavePolicies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LeavePolicyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return leavePolicyRepository.findAllWithEagerRelationships(pageable).map(leavePolicyMapper::toDto);
    }

    /**
     * Get one leavePolicy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeavePolicyDTO> findOne(Long id) {
        log.debug("Request to get LeavePolicy : {}", id);
        return leavePolicyRepository.findOneWithEagerRelationships(id).map(leavePolicyMapper::toDto);
    }

    /**
     * Delete the leavePolicy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeavePolicy : {}", id);
        leavePolicyRepository.deleteById(id);
    }
}
