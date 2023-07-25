package com.techvg.hrms.service;

import com.techvg.hrms.domain.EmployeeSalaryComponent;
import com.techvg.hrms.repository.EmployeeSalaryComponentRepository;
import com.techvg.hrms.service.dto.EmployeeSalaryComponentDTO;
import com.techvg.hrms.service.mapper.EmployeeSalaryComponentMapper;

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
 * Service Implementation for managing {@link EmployeeSalaryComponent}.
 */
@Service
@Transactional
public class EmployeeSalaryComponentService {

	private final Logger log = LoggerFactory.getLogger(EmployeeSalaryComponentService.class);

	private final EmployeeSalaryComponentRepository employeeSalaryComponentRepository;

	private final EmployeeSalaryComponentMapper employeeSalaryComponentMapper;

	@Autowired
	private ValidationService validationService;

	public EmployeeSalaryComponentService(EmployeeSalaryComponentRepository employeeSalaryComponentRepository,
			EmployeeSalaryComponentMapper employeeSalaryComponentMapper) {
		this.employeeSalaryComponentRepository = employeeSalaryComponentRepository;
		this.employeeSalaryComponentMapper = employeeSalaryComponentMapper;
	}

	/**
	 * Save a employeeSalaryComponent.
	 *
	 * @param employeeSalaryComponentDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EmployeeSalaryComponentDTO save(EmployeeSalaryComponentDTO employeeSalaryComponentDTO) {
		log.debug("Request to save EmployeeSalaryComponent : {}", employeeSalaryComponentDTO);
	   EmployeeSalaryComponent employeeSalaryComponent = employeeSalaryComponentMapper
				.toEntity(employeeSalaryComponentDTO);
	   validationService.validateMethod(employeeSalaryComponent);
		employeeSalaryComponent = employeeSalaryComponentRepository.save(employeeSalaryComponent);
		return employeeSalaryComponentMapper.toDto(employeeSalaryComponent);
	}

	/**
	 * Update a EmployeeSalaryComponent.
	 *
	 * @param employeeSalaryComponentDTO the entity to save.
	 * @return the persisted entity.
	 */
	public EmployeeSalaryComponentDTO update(EmployeeSalaryComponentDTO employeeSalaryComponentDTO) {
		log.debug("Request to update EmployeeSalaryComponent : {}", employeeSalaryComponentDTO);

		EmployeeSalaryComponent employeeSalaryComponent = employeeSalaryComponentMapper
				.toEntity(employeeSalaryComponentDTO);
		validationService.validateMethod(employeeSalaryComponent);
		employeeSalaryComponent = employeeSalaryComponentRepository.save(employeeSalaryComponent);
		return employeeSalaryComponentMapper.toDto(employeeSalaryComponent);
	}

	/**
	 * Partially update a EmployeeSalaryComponent.
	 *
	 * @param employeeSalaryComponentDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<EmployeeSalaryComponentDTO> partialUpdate(EmployeeSalaryComponentDTO employeeSalaryComponentDTO) {
		log.debug("Request to partially update EmployeeSalaryComponent : {}", employeeSalaryComponentDTO);

		return employeeSalaryComponentRepository.findById(employeeSalaryComponentDTO.getId())
				.map(existingEmployeeSalaryComponent -> {
					employeeSalaryComponentMapper.partialUpdate(existingEmployeeSalaryComponent,
							employeeSalaryComponentDTO);

					return existingEmployeeSalaryComponent;
				}).map(employeeSalaryComponentRepository::save).map(employeeSalaryComponentMapper::toDto);
	}

	/**
	 * Get all the EmployeeSalaryComponents.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<EmployeeSalaryComponentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all EmployeeSalaryComponent");
		return employeeSalaryComponentRepository.findAll(pageable).map(employeeSalaryComponentMapper::toDto);
	}

	/**
	 * Get one EmployeeSalaryComponent by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<EmployeeSalaryComponentDTO> findOne(Long id) {
		log.debug("Request to get EmployeeSalaryComponent : {}", id);
		return employeeSalaryComponentRepository.findById(id).map(employeeSalaryComponentMapper::toDto);
	}

	/**
	 * Delete the EmployeeSalaryComponent by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete EmployeeSalaryComponent : {}", id);
		employeeSalaryComponentRepository.deleteById(id);
	}
}
