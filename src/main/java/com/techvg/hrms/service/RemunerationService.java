package com.techvg.hrms.service;

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

import com.techvg.hrms.domain.Remuneration;
import com.techvg.hrms.repository.RemunerationRepository;
import com.techvg.hrms.service.dto.RemunerationDTO;
import com.techvg.hrms.service.mapper.RemunerationMapper;

/**
 * Service Implementation for managing {@link Remuneration}.
 */
@Service
@Transactional
public class RemunerationService {

	private final Logger log = LoggerFactory.getLogger(RemunerationService.class);

	private final RemunerationRepository remunerationRepository;

	private final RemunerationMapper remunerationMapper;
	
	@Autowired
    private ValidationService validationService;

	public RemunerationService(RemunerationRepository remunerationRepository, RemunerationMapper remunerationMapper) {
		this.remunerationRepository = remunerationRepository;
		this.remunerationMapper = remunerationMapper;
	}

	/**
	 * Save a remuneration.
	 *
	 * @param remunerationDTO the entity to save.
	 * @return the persisted entity.
	 */
	public RemunerationDTO save(RemunerationDTO remunerationDTO) {
		log.debug("Request to save Remuneration : {}", remunerationDTO);
		Remuneration remuneration = remunerationMapper.toEntity(remunerationDTO);
		validationService.validateMethod(remuneration);
		remuneration = remunerationRepository.save(remuneration);
		return remunerationMapper.toDto(remuneration);
	}

	/**
	 * Update a remuneration.
	 *
	 * @param remunerationDTO the entity to save.
	 * @return the persisted entity.
	 */
	public RemunerationDTO update(RemunerationDTO remunerationDTO) {
		log.debug("Request to update Remuneration : {}", remunerationDTO);
		Remuneration remuneration = remunerationMapper.toEntity(remunerationDTO);
		validationService.validateMethod(remuneration);
		remuneration = remunerationRepository.save(remuneration);
		return remunerationMapper.toDto(remuneration);
	}

	/**
	 * Partially update a remuneration.
	 *
	 * @param remunerationDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<RemunerationDTO> partialUpdate(RemunerationDTO remunerationDTO) {
		log.debug("Request to partially update Remuneration : {}", remunerationDTO);

		return remunerationRepository.findById(remunerationDTO.getId()).map(existingRemuneration -> {
			remunerationMapper.partialUpdate(existingRemuneration, remunerationDTO);

			return existingRemuneration;
		}).map(remunerationRepository::save).map(remunerationMapper::toDto);
	}

	/**
	 * Get all the remunerations.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<RemunerationDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Remunerations");
		return remunerationRepository.findAll(pageable).map(remunerationMapper::toDto);
	}

	/**
	 * Get one remuneration by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<RemunerationDTO> findOne(Long id) {
		log.debug("Request to get Remuneration : {}", id);
		return remunerationRepository.findById(id).map(remunerationMapper::toDto);
	}

	/**
	 * Delete the remuneration by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Remuneration : {}", id);
		remunerationRepository.deleteById(id);
	}
}
