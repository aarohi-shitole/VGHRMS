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

import com.techvg.hrms.domain.PersonalDetails;
import com.techvg.hrms.repository.PersonalDetailsRepository;
import com.techvg.hrms.service.dto.PersonalDetailsDTO;
import com.techvg.hrms.service.mapper.PersonalDetailsMapper;

/**
 * Service Implementation for managing {@link PersonalDetails}.
 */
@Service
@Transactional
public class PersonalDetailsService {

	private final Logger log = LoggerFactory.getLogger(PersonalDetailsService.class);

	private final PersonalDetailsRepository personalDetailsRepository;

	private final PersonalDetailsMapper personalDetailsMapper;
	@Autowired
    private ValidationService validationService;

	public PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository,
			PersonalDetailsMapper personalDetailsMapper) {
		this.personalDetailsRepository = personalDetailsRepository;
		this.personalDetailsMapper = personalDetailsMapper;
	}

	/**
	 * Save a personalDetails.
	 *
	 * @param personalDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) {
		log.debug("Request to save PersonalDetails : {}", personalDetailsDTO);
		PersonalDetails personalDetails = personalDetailsMapper.toEntity(personalDetailsDTO);
		validationService.validateMethod(personalDetails);
		personalDetails = personalDetailsRepository.save(personalDetails);
		return personalDetailsMapper.toDto(personalDetails);
	}

	/**
	 * Update a personalDetails.
	 *
	 * @param personalDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PersonalDetailsDTO update(PersonalDetailsDTO personalDetailsDTO) {
		log.debug("Request to update PersonalDetails : {}", personalDetailsDTO);
		PersonalDetails personalDetails = personalDetailsMapper.toEntity(personalDetailsDTO);
		validationService.validateMethod(personalDetails);
		personalDetails = personalDetailsRepository.save(personalDetails);
		return personalDetailsMapper.toDto(personalDetails);
	}

	/**
	 * Partially update a personalDetails.
	 *
	 * @param personalDetailsDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<PersonalDetailsDTO> partialUpdate(PersonalDetailsDTO personalDetailsDTO) {
		log.debug("Request to partially update PersonalDetails : {}", personalDetailsDTO);

		return personalDetailsRepository.findById(personalDetailsDTO.getId()).map(existingPersonalDetails -> {
			personalDetailsMapper.partialUpdate(existingPersonalDetails, personalDetailsDTO);

			return existingPersonalDetails;
		}).map(personalDetailsRepository::save).map(personalDetailsMapper::toDto);
	}

	/**
	 * Get all the personalDetails.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<PersonalDetailsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PersonalDetails");
		return personalDetailsRepository.findAll(pageable).map(personalDetailsMapper::toDto);
	}

	/**
	 * Get one personalDetails by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<PersonalDetailsDTO> findOne(Long id) {
		log.debug("Request to get PersonalDetails : {}", id);
		return personalDetailsRepository.findById(id).map(personalDetailsMapper::toDto);
	}

	/**
	 * Delete the personalDetails by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete PersonalDetails : {}", id);
		personalDetailsRepository.deleteById(id);
	}
}
