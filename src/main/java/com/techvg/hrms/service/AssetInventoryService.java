package com.techvg.hrms.service;

import com.techvg.hrms.domain.AssetInventory;
import com.techvg.hrms.repository.AssetInventoryRepository;
import com.techvg.hrms.service.dto.AssetInventoryDTO;
import com.techvg.hrms.service.mapper.AssetInventoryMapper;

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
 * Service Implementation for managing {@link AssetInventory}.
 */
@Service
@Transactional
public class AssetInventoryService {

    private final Logger log = LoggerFactory.getLogger(AssetInventoryService.class);

    private final AssetInventoryRepository assetInventoryRepository;

    private final AssetInventoryMapper assetInventoryMapper;
    @Autowired
    private ValidationService validationService;


    public AssetInventoryService(AssetInventoryRepository assetInventoryRepository, AssetInventoryMapper assetInventoryMapper) {
        this.assetInventoryRepository = assetInventoryRepository;
        this.assetInventoryMapper = assetInventoryMapper;
    }

    /**
     * Save a assetInventory.
     *
     * @param assetInventoryDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetInventoryDTO save(AssetInventoryDTO assetInventoryDTO) {
        log.debug("Request to save AssetInventory : {}", assetInventoryDTO);
       
        AssetInventory assetInventory = assetInventoryMapper.toEntity(assetInventoryDTO);
        validationService.validateMethod(assetInventory);
        assetInventory = assetInventoryRepository.save(assetInventory);
        return assetInventoryMapper.toDto(assetInventory);
    }

    /**
     * Update a assetInventory.
     *
     * @param assetInventoryDTO the entity to save.
     * @return the persisted entity.
     */
    public AssetInventoryDTO update(AssetInventoryDTO assetInventoryDTO) {
        log.debug("Request to update AssetInventory : {}", assetInventoryDTO);
      
        AssetInventory assetInventory = assetInventoryMapper.toEntity(assetInventoryDTO);
        validationService.validateMethod(assetInventory);
        assetInventory = assetInventoryRepository.save(assetInventory);
        return assetInventoryMapper.toDto(assetInventory);
    }

    /**
     * Partially update a assetInventory.
     *
     * @param assetInventoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssetInventoryDTO> partialUpdate(AssetInventoryDTO assetInventoryDTO) {
        log.debug("Request to partially update AssetInventory : {}", assetInventoryDTO);

        return assetInventoryRepository
            .findById(assetInventoryDTO.getId())
            .map(existingAssetInventory -> {
                assetInventoryMapper.partialUpdate(existingAssetInventory, assetInventoryDTO);

                return existingAssetInventory;
            })
            .map(assetInventoryRepository::save)
            .map(assetInventoryMapper::toDto);
    }

    /**
     * Get all the assetInventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetInventoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetInventories");
        return assetInventoryRepository.findAll(pageable).map(assetInventoryMapper::toDto);
    }

    /**
     * Get one assetInventory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssetInventoryDTO> findOne(Long id) {
        log.debug("Request to get AssetInventory : {}", id);
        return assetInventoryRepository.findById(id).map(assetInventoryMapper::toDto);
    }

    /**
     * Delete the assetInventory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssetInventory : {}", id);
        assetInventoryRepository.deleteById(id);
    }
}
