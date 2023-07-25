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

import com.techvg.hrms.domain.EsiDetails;
import com.techvg.hrms.repository.EsiDetailsRepository;
import com.techvg.hrms.service.dto.EsiDetailsDTO;
import com.techvg.hrms.service.mapper.EsiDetailsMapper;

/**
 * Service Implementation for managing {@link EsiDetails}.
 */
@Service
@Transactional
public class EsiDetailsService {

	private final Logger log = LoggerFactory.getLogger(EsiDetailsService.class);

	private final EsiDetailsRepository esiDetailsRepository;

	private final EsiDetailsMapper esiDetailsMapper;
    @Autowired
    private ValidationService validationService;
	public EsiDetailsService(EsiDetailsRepository esiDetailsRepository, EsiDetailsMapper esiDetailsMapper) {
		this.esiDetailsRepository = esiDetailsRepository;
		this.esiDetailsMapper = esiDetailsMapper;
	}

	/**
	 * Save a esiDetails.
	 *
	 * @param esiDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EsiDetailsDTO save(EsiDetailsDTO esiDetailsDTO) {
		log.debug("Request to save EsiDetails : {}", esiDetailsDTO);
		 
		EsiDetails esiDetails = esiDetailsMapper.toEntity(esiDetailsDTO);
		validationService.validateMethod(esiDetails);
		esiDetails = esiDetailsRepository.save(esiDetails);
		return esiDetailsMapper.toDto(esiDetails);
	}

	/**
	 * Update a esiDetails.
	 *
	 * @param esiDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EsiDetailsDTO update(EsiDetailsDTO esiDetailsDTO) {
		log.debug("Request to update EsiDetails : {}", esiDetailsDTO);
		
		EsiDetails esiDetails = esiDetailsMapper.toEntity(esiDetailsDTO);
		validationService.validateMethod(esiDetails);
		esiDetails = esiDetailsRepository.save(esiDetails);
		return esiDetailsMapper.toDto(esiDetails);
	}

	/**
	 * Partially update a esiDetails.
	 *
	 * @param esiDetailsDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<EsiDetailsDTO> partialUpdate(EsiDetailsDTO esiDetailsDTO) {
		log.debug("Request to partially update EsiDetails : {}", esiDetailsDTO);

		return esiDetailsRepository.findById(esiDetailsDTO.getId()).map(existingEsiDetails -> {
			esiDetailsMapper.partialUpdate(existingEsiDetails, esiDetailsDTO);

			return existingEsiDetails;
		}).map(esiDetailsRepository::save).map(esiDetailsMapper::toDto);
	}

	/**
	 * Get all the esiDetails.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<EsiDetailsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all EsiDetails");
		return esiDetailsRepository.findAll(pageable).map(esiDetailsMapper::toDto);
	}

	/**
	 * Get one esiDetails by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<EsiDetailsDTO> findOne(Long id) {
		log.debug("Request to get EsiDetails : {}", id);
		return esiDetailsRepository.findById(id).map(esiDetailsMapper::toDto);
	}
	

	/**
	 * Get employee id for esiDetails .
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	  public boolean existsByEmployeeId(Long employeeId) {
	        return esiDetailsRepository.existsByEmployeeId(employeeId);
	    }

	/**
	 * Delete the esiDetails by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete EsiDetails : {}", id);
		esiDetailsRepository.deleteById(id);
	}
}
