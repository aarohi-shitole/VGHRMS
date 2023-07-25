package com.techvg.hrms.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.repository.WorkExperienceRepository;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.dto.WorkExperienceDTO;
import com.techvg.hrms.service.mapper.WorkExperienceMapper;

/**
 * Service Implementation for managing {@link WorkExperience}.
 */
@Service
@Transactional
public class WorkExperienceService {

    private final Logger log = LoggerFactory.getLogger(WorkExperienceService.class);

    private final WorkExperienceRepository workExperienceRepository;

    private final WorkExperienceMapper workExperienceMapper;
    
    private final AddressService addressService;

    @Autowired
    private ValidationService validationService;
    
    
    public WorkExperienceService(WorkExperienceRepository workExperienceRepository, WorkExperienceMapper workExperienceMapper, AddressService addressService) {
        this.workExperienceRepository = workExperienceRepository;
        this.workExperienceMapper = workExperienceMapper;
        this.addressService = addressService;
    }

    /**
     * Save a workExperience.
     *
     * @param workExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkExperienceDTO save(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to save WorkExperience : {}", workExperienceDTO);
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
        validationService.validateMethod(workExperience);
        workExperience = workExperienceRepository.save(workExperience);
        
      // ----------------Save/ update address of Work Experience-------------
 		List<AddressDTO> addressList = new ArrayList<>();
 		
 		if (workExperienceDTO.getAddress() != null) {
 			addressList.add(workExperienceDTO.getAddress());
 			addressList = addressService.saveAddress(addressList, workExperience);
 		}
 		// -----------------------------------------------------------------------
         workExperienceDTO= workExperienceMapper.toDto(workExperience);
         if(!addressList.isEmpty()) {
         for(AddressDTO addressObj :addressList) {
        	 workExperienceDTO.setAddress(addressObj);
         }
         }
        
        return workExperienceDTO ;
    }

    /**
     * Update a workExperience.
     *
     * @param workExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkExperienceDTO update(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to update WorkExperience : {}", workExperienceDTO);
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
        validationService.validateMethod(workExperience);
     // ----------------Update address of work experience-------------
        AddressDTO addressObj = null;
         if(workExperienceDTO.getAddress() != null) {
        	   AddressDTO addressDTO = workExperienceDTO.getAddress();
            	 addressDTO.setRefTableId(workExperienceDTO.getId());
            	 addressDTO.setRefTable(WorkExperience.class.getSimpleName());
            	 addressDTO.setCompanyId(workExperienceDTO.getCompanyId());
                  addressObj = addressService.save(addressDTO);
         }
   // -----------------------------------------------------------------------
        workExperience = workExperienceRepository.save(workExperience);
        
        workExperienceDTO= workExperienceMapper.toDto(workExperience);
        workExperienceDTO.setAddress(addressObj);
        return workExperienceDTO;
    }

    /**
     * Partially update a workExperience.
     *
     * @param workExperienceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkExperienceDTO> partialUpdate(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to partially update WorkExperience : {}", workExperienceDTO);

        return workExperienceRepository
            .findById(workExperienceDTO.getId())
            .map(existingWorkExperience -> {
                workExperienceMapper.partialUpdate(existingWorkExperience, workExperienceDTO);

                return existingWorkExperience;
            })
            .map(workExperienceRepository::save)
            .map(workExperienceMapper::toDto);
    }

    /**
     * Get all the workExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkExperienceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkExperiences");
        return workExperienceRepository.findAll(pageable).map(workExperienceMapper::toDto);
    }

    /**
     * Get one workExperience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkExperienceDTO> findOne(Long id) {
        log.debug("Request to get WorkExperience : {}", id);
        return workExperienceRepository.findById(id).map(workExperienceMapper::toDto);
    }

    /**
     * Delete the workExperience by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkExperience : {}", id);
        workExperienceRepository.deleteById(id);
    }
}
