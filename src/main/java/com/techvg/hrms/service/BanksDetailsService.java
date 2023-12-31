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

import com.techvg.hrms.domain.BanksDetails;
import com.techvg.hrms.repository.BanksDetailsRepository;
import com.techvg.hrms.service.dto.BanksDetailsDTO;
import com.techvg.hrms.service.mapper.BanksDetailsMapper;

/**
 * Service Implementation for managing {@link BanksDetails}.
 */
@Service
@Transactional
public class BanksDetailsService {

	private final Logger log = LoggerFactory.getLogger(BanksDetailsService.class);

	private final BanksDetailsRepository banksDetailsRepository;

	private final BanksDetailsMapper banksDetailsMapper;
	
	@Autowired
	private ValidationService validationService;

	public BanksDetailsService(BanksDetailsRepository banksDetailsRepository, BanksDetailsMapper banksDetailsMapper) {
		this.banksDetailsRepository = banksDetailsRepository;
		this.banksDetailsMapper = banksDetailsMapper;
	}

	/**
	 * Save a banksDetails.
	 *
	 * @param banksDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public BanksDetailsDTO save(BanksDetailsDTO banksDetailsDTO) {
		log.debug("Request to save BanksDetails : {}", banksDetailsDTO);
		
		BanksDetails banksDetails = banksDetailsMapper.toEntity(banksDetailsDTO);
		 validationService.validateMethod(banksDetails);
		banksDetails = banksDetailsRepository.save(banksDetails);
		return banksDetailsMapper.toDto(banksDetails);
	}

	/**
	 * Update a banksDetails.
	 *
	 * @param banksDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public BanksDetailsDTO update(BanksDetailsDTO banksDetailsDTO) {
		log.debug("Request to update BanksDetails : {}", banksDetailsDTO);
		 
		BanksDetails banksDetails = banksDetailsMapper.toEntity(banksDetailsDTO);
		validationService.validateMethod(banksDetails);
		banksDetails = banksDetailsRepository.save(banksDetails);
		return banksDetailsMapper.toDto(banksDetails);
	}

	/**
	 * Partially update a banksDetails.
	 *
	 * @param banksDetailsDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<BanksDetailsDTO> partialUpdate(BanksDetailsDTO banksDetailsDTO) {
		log.debug("Request to partially update BanksDetails : {}", banksDetailsDTO);

		return banksDetailsRepository.findById(banksDetailsDTO.getId()).map(existingBanksDetails -> {
			banksDetailsMapper.partialUpdate(existingBanksDetails, banksDetailsDTO);

			return existingBanksDetails;
		}).map(banksDetailsRepository::save).map(banksDetailsMapper::toDto);
	}

	/**
	 * Get all the banksDetails.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<BanksDetailsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all BanksDetails");
		return banksDetailsRepository.findAll(pageable).map(banksDetailsMapper::toDto);
	}

	/**
	 * Get one banksDetails by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<BanksDetailsDTO> findOne(Long id) {
		log.debug("Request to get BanksDetails : {}", id);
		return banksDetailsRepository.findById(id).map(banksDetailsMapper::toDto);
	}

	/**
	 * Delete the banksDetails by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete BanksDetails : {}", id);
		banksDetailsRepository.deleteById(id);
	}
}
