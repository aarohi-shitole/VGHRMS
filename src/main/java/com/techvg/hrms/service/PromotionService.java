package com.techvg.hrms.service;

import com.techvg.hrms.domain.Promotion;
import com.techvg.hrms.repository.PromotionRepository;
import com.techvg.hrms.service.dto.PromotionDTO;
import com.techvg.hrms.service.mapper.PromotionMapper;

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
 * Service Implementation for managing {@link Promotion}.
 */
@Service
@Transactional
public class PromotionService {

    private final Logger log = LoggerFactory.getLogger(PromotionService.class);

    private final PromotionRepository promotionRepository;

    private final PromotionMapper promotionMapper;
    
    @Autowired
    private ValidationService validationService;

    public PromotionService(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    /**
     * Save a promotion.
     *
     * @param promotionDTO the entity to save.
     * @return the persisted entity.
     */
    public PromotionDTO save(PromotionDTO promotionDTO) {
        log.debug("Request to save Promotion : {}", promotionDTO);
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        validationService.validateMethod(promotion);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDto(promotion);
    }

    /**
     * Update a promotion.
     *
     * @param promotionDTO the entity to save.
     * @return the persisted entity.
     */
    public PromotionDTO update(PromotionDTO promotionDTO) {
        log.debug("Request to update Promotion : {}", promotionDTO);
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        validationService.validateMethod(promotion);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDto(promotion);
    }

    /**
     * Partially update a promotion.
     *
     * @param promotionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PromotionDTO> partialUpdate(PromotionDTO promotionDTO) {
        log.debug("Request to partially update Promotion : {}", promotionDTO);

        return promotionRepository
            .findById(promotionDTO.getId())
            .map(existingPromotion -> {
                promotionMapper.partialUpdate(existingPromotion, promotionDTO);

                return existingPromotion;
            })
            .map(promotionRepository::save)
            .map(promotionMapper::toDto);
    }

    /**
     * Get all the promotions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PromotionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Promotions");
        return promotionRepository.findAll(pageable).map(promotionMapper::toDto);
    }

    /**
     * Get one promotion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PromotionDTO> findOne(Long id) {
        log.debug("Request to get Promotion : {}", id);
        return promotionRepository.findById(id).map(promotionMapper::toDto);
    }

    /**
     * Delete the promotion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Promotion : {}", id);
        promotionRepository.deleteById(id);
    }
}
