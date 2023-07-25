package com.techvg.hrms.service;

import com.techvg.hrms.domain.AssetApplication;
import com.techvg.hrms.repository.AssetApplicationRepository;
import com.techvg.hrms.service.dto.AssetApplicationDTO;
import com.techvg.hrms.service.mapper.AssetApplicationMapper;

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
 * Service Implementation for managing {@link AssetApplication}.
 */
@Service
@Transactional
public class AssetApplicationService {

    private final Logger log = LoggerFactory.getLogger(AssetApplicationService.class);

    private final AssetApplicationRepository assetApplicationRepository;

    private final AssetApplicationMapper assetApplicationMapper;
    @Autowired
    private ValidationService validationService;

    public AssetApplicationService(AssetApplicationRepository assetApplicationRepository, AssetApplicationMapper assetApplicationMapper) {
        this.assetApplicationRepository = assetApplicationRepository;
        this.assetApplicationMapper = assetApplicationMapper;
    }

    /**
     * Save a assetApplication.
     *
     * @param assetApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetApplicationDTO save(AssetApplicationDTO assetApplicationDTO) {
        log.debug("Request to save AssetApplication : {}", assetApplicationDTO);
       
        AssetApplication assetApplication = assetApplicationMapper.toEntity(assetApplicationDTO);
        validationService.validateMethod(assetApplication);
        assetApplication = assetApplicationRepository.save(assetApplication);
        return assetApplicationMapper.toDto(assetApplication);
    }

    /**
     * Update a assetApplication.
     *
     * @param assetApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetApplicationDTO update(AssetApplicationDTO assetApplicationDTO) {
        log.debug("Request to update AssetApplication : {}", assetApplicationDTO);
       
        AssetApplication assetApplication = assetApplicationMapper.toEntity(assetApplicationDTO);
        validationService.validateMethod(assetApplication);
        assetApplication = assetApplicationRepository.save(assetApplication);
        return assetApplicationMapper.toDto(assetApplication);
    }

    /**
     * Partially update a assetApplication.
     *
     * @param assetApplicationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssetApplicationDTO> partialUpdate(AssetApplicationDTO assetApplicationDTO) {
        log.debug("Request to partially update AssetApplication : {}", assetApplicationDTO);

        return assetApplicationRepository
            .findById(assetApplicationDTO.getId())
            .map(existingAssetApplication -> {
                assetApplicationMapper.partialUpdate(existingAssetApplication, assetApplicationDTO);

                return existingAssetApplication;
            })
            .map(assetApplicationRepository::save)
            .map(assetApplicationMapper::toDto);
    }

    /**
     * Get all the assetApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetApplications");
        return assetApplicationRepository.findAll(pageable).map(assetApplicationMapper::toDto);
    }

    /**
     * Get one assetApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssetApplicationDTO> findOne(Long id) {
        log.debug("Request to get AssetApplication : {}", id);
        return assetApplicationRepository.findById(id).map(assetApplicationMapper::toDto);
    }

    /**
     * Delete the assetApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssetApplication : {}", id);
        assetApplicationRepository.deleteById(id);
    }
}
