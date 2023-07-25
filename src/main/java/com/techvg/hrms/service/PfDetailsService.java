package com.techvg.hrms.service;

import com.techvg.hrms.domain.Address;
import com.techvg.hrms.domain.PfDetails;
import com.techvg.hrms.repository.PfDetailsRepository;
import com.techvg.hrms.service.dto.PfDetailsDTO;
import com.techvg.hrms.service.mapper.PfDetailsMapper;

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
 * Service Implementation for managing {@link PfDetails}.
 */
@Service
@Transactional
public class PfDetailsService {

	private final Logger log = LoggerFactory.getLogger(PfDetailsService.class);

	private final PfDetailsRepository pfDetailsRepository;

	private final PfDetailsMapper pfDetailsMapper;
	
	@Autowired
	private ValidationService validationService;

	public PfDetailsService(PfDetailsRepository pfDetailsRepository, PfDetailsMapper pfDetailsMapper) {
		this.pfDetailsRepository = pfDetailsRepository;
		this.pfDetailsMapper = pfDetailsMapper;
	}

	/**
	 * Save a pfDetails.
	 *
	 * @param pfDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PfDetailsDTO save(PfDetailsDTO pfDetailsDTO) {
		log.debug("Request to save PfDetails : {}", pfDetailsDTO);
		PfDetails pfDetails = pfDetailsMapper.toEntity(pfDetailsDTO);
		validationService.validateMethod(pfDetails);
		pfDetails = pfDetailsRepository.save(pfDetails);
		return pfDetailsMapper.toDto(pfDetails);
	}

	/**
	 * Update a pfDetails.
	 *
	 * @param pfDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PfDetailsDTO update(PfDetailsDTO pfDetailsDTO) {
		log.debug("Request to update PfDetails : {}", pfDetailsDTO);
		PfDetails pfDetails = pfDetailsMapper.toEntity(pfDetailsDTO);
		validationService.validateMethod(pfDetails);
		pfDetails = pfDetailsRepository.save(pfDetails);
		return pfDetailsMapper.toDto(pfDetails);
	}

	/**
	 * Partially update a pfDetails.
	 *
	 * @param pfDetailsDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<PfDetailsDTO> partialUpdate(PfDetailsDTO pfDetailsDTO) {
		log.debug("Request to partially update PfDetails : {}", pfDetailsDTO);

		return pfDetailsRepository.findById(pfDetailsDTO.getId()).map(existingPfDetails -> {
			pfDetailsMapper.partialUpdate(existingPfDetails, pfDetailsDTO);

			return existingPfDetails;
		}).map(pfDetailsRepository::save).map(pfDetailsMapper::toDto);
	}

	/**
	 * Get all the pfDetails.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<PfDetailsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PfDetails");
		return pfDetailsRepository.findAll(pageable).map(pfDetailsMapper::toDto);
	}

	/**
	 * Get one pfDetails by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<PfDetailsDTO> findOne(Long id) {
		log.debug("Request to get PfDetails : {}", id);
		return pfDetailsRepository.findById(id).map(pfDetailsMapper::toDto);
	}
	
	/**
	 * Get employee id for pf.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	  public boolean existsByEmployeeId(Long employeeId) {
	        return pfDetailsRepository.existsByEmployeeId(employeeId);
	    }


	/**
	 * Delete the pfDetails by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete PfDetails : {}", id);
		pfDetailsRepository.deleteById(id);
	}
}
