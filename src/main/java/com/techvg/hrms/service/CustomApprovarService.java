package com.techvg.hrms.service;

import com.techvg.hrms.domain.CustomApprovar;
import com.techvg.hrms.repository.CustomApprovarRepository;
import com.techvg.hrms.service.dto.CustomApprovarDTO;
import com.techvg.hrms.service.mapper.CustomApprovarMapper;

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
 * Service Implementation for managing {@link CustomApprovar}.
 */
@Service
@Transactional
public class CustomApprovarService {

    private final Logger log = LoggerFactory.getLogger(CustomApprovarService.class);

    private final CustomApprovarRepository customApprovarRepository;

    private final CustomApprovarMapper customApprovarMapper;
	   @Autowired
	    private ValidationService validationService;
    public CustomApprovarService(CustomApprovarRepository customApprovarRepository, CustomApprovarMapper customApprovarMapper) {
        this.customApprovarRepository = customApprovarRepository;
        this.customApprovarMapper = customApprovarMapper;
    }

    /**
     * Save a customApprovar.
     *
     * @param customApprovarDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomApprovarDTO save(CustomApprovarDTO customApprovarDTO) {
        log.debug("Request to save CustomApprovar : {}", customApprovarDTO);
       
        CustomApprovar customApprovar = customApprovarMapper.toEntity(customApprovarDTO);
        validationService.validateMethod(customApprovar);
        customApprovar = customApprovarRepository.save(customApprovar);
        return customApprovarMapper.toDto(customApprovar);
    }

    /**
     * Update a customApprovar.
     *
     * @param customApprovarDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomApprovarDTO update(CustomApprovarDTO customApprovarDTO) {
        log.debug("Request to update CustomApprovar : {}", customApprovarDTO);
      
        CustomApprovar customApprovar = customApprovarMapper.toEntity(customApprovarDTO);
        validationService.validateMethod(customApprovar);
        customApprovar = customApprovarRepository.save(customApprovar);
        return customApprovarMapper.toDto(customApprovar);
    }

    /**
     * Partially update a customApprovar.
     *
     * @param customApprovarDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomApprovarDTO> partialUpdate(CustomApprovarDTO customApprovarDTO) {
        log.debug("Request to partially update CustomApprovar : {}", customApprovarDTO);

        return customApprovarRepository
            .findById(customApprovarDTO.getId())
            .map(existingCustomApprovar -> {
                customApprovarMapper.partialUpdate(existingCustomApprovar, customApprovarDTO);

                return existingCustomApprovar;
            })
            .map(customApprovarRepository::save)
            .map(customApprovarMapper::toDto);
    }

    /**
     * Get all the customApprovars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomApprovarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomApprovars");
        return customApprovarRepository.findAll(pageable).map(customApprovarMapper::toDto);
    }

    /**
     * Get one customApprovar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomApprovarDTO> findOne(Long id) {
        log.debug("Request to get CustomApprovar : {}", id);
        return customApprovarRepository.findById(id).map(customApprovarMapper::toDto);
    }

    /**
     * Delete the customApprovar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomApprovar : {}", id);
        customApprovarRepository.deleteById(id);
    }
}
