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

import com.techvg.hrms.domain.Education;
import com.techvg.hrms.repository.EducationRepository;
import com.techvg.hrms.service.dto.EducationDTO;
import com.techvg.hrms.service.mapper.EducationMapper;

/**
 * Service Implementation for managing {@link Education}.
 */
@Service
@Transactional
public class EducationService {

	private final Logger log = LoggerFactory.getLogger(EducationService.class);

	private final EducationRepository educationRepository;

	private final EducationMapper educationMapper;
    @Autowired
	private ValidationService validationService;
	
	public EducationService(EducationRepository educationRepository, EducationMapper educationMapper) {
		this.educationRepository = educationRepository;
		this.educationMapper = educationMapper;
	}

	/**
	 * Save a education.
	 *
	 * @param educationDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EducationDTO save(EducationDTO educationDTO) {
		log.debug("Request to save Education : {}", educationDTO);
		
		Education education = educationMapper.toEntity(educationDTO);
		validationService.validateMethod(education);
		education = educationRepository.save(education);
		return educationMapper.toDto(education);
	}

	/**
	 * Update a education.
	 *
	 * @param educationDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EducationDTO update(EducationDTO educationDTO) {
		log.debug("Request to update Education : {}", educationDTO);
	
		Education education = educationMapper.toEntity(educationDTO);
		validationService.validateMethod(education);
		education = educationRepository.save(education);
		return educationMapper.toDto(education);
	}

	/**
	 * Partially update a education.
	 *
	 * @param educationDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<EducationDTO> partialUpdate(EducationDTO educationDTO) {
		log.debug("Request to partially update Education : {}", educationDTO);

		return educationRepository.findById(educationDTO.getId()).map(existingEducation -> {
			educationMapper.partialUpdate(existingEducation, educationDTO);

			return existingEducation;
		}).map(educationRepository::save).map(educationMapper::toDto);
	}

	/**
	 * Get all the educations.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<EducationDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Educations");
		return educationRepository.findAll(pageable).map(educationMapper::toDto);
	}

	/**
	 * Get one education by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<EducationDTO> findOne(Long id) {
		log.debug("Request to get Education : {}", id);
		return educationRepository.findById(id).map(educationMapper::toDto);
	}

	/**
	 * Delete the education by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Education : {}", id);
		educationRepository.deleteById(id);
	}
}
