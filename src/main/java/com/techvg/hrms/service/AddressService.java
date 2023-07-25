package com.techvg.hrms.service;

import com.techvg.hrms.domain.Address;
import com.techvg.hrms.repository.AddressRepository;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.mapper.AddressMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.hrms.domain.Address;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.repository.AddressRepository;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.mapper.AddressMapper;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
@Transactional
public class AddressService {

	private final Logger log = LoggerFactory.getLogger(AddressService.class);

	private final AddressRepository addressRepository;

	private final AddressMapper addressMapper;
	 @Autowired
	    private ValidationService validationService;

	public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
		this.addressRepository = addressRepository;
		this.addressMapper = addressMapper;
	}

	/**
	 * Save a address.
	 *
	 * @param addressDTO the entity to save.
	 * @return the persisted entity.
	 */
	public AddressDTO save(AddressDTO addressDTO) {
		log.debug("Request to save Address : {}", addressDTO);
		
		Address address = addressMapper.toEntity(addressDTO);
		  validationService.validateMethod(address);
		address = addressRepository.save(address);
		return addressMapper.toDto(address);
	}

	/**
	 * Update a address.
	 *
	 * @param addressDTO the entity to save.
	 * @return the persisted entity.
	 */
	public AddressDTO update(AddressDTO addressDTO) {
		log.debug("Request to update Address : {}", addressDTO);
		 
		Address address = addressMapper.toEntity(addressDTO);
		  validationService.validateMethod(address);
		address = addressRepository.save(address);
		return addressMapper.toDto(address);
	}

	/**
	 * Partially update a address.
	 *
	 * @param addressDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<AddressDTO> partialUpdate(AddressDTO addressDTO) {
		log.debug("Request to partially update Address : {}", addressDTO);

		return addressRepository.findById(addressDTO.getId()).map(existingAddress -> {
			addressMapper.partialUpdate(existingAddress, addressDTO);

			return existingAddress;
		}).map(addressRepository::save).map(addressMapper::toDto);
	}

	/**
	 * Get all the addresses.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<AddressDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Addresses");
		return addressRepository.findAll(pageable).map(addressMapper::toDto);

	}

	/**
	 * Get one address by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<AddressDTO> findOne(Long id) {
		log.debug("Request to get Address : {}", id);
		return addressRepository.findById(id).map(addressMapper::toDto);
	}

	/**
	 * Delete the address by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Address : {}", id);
		addressRepository.deleteById(id);
	}

	/**
	 * Save the address by address list.
	 *
	 * @param addressDTO list and Object the of the entity.
	 * @return the List of entity.
	 */

	public List<AddressDTO> saveAddress(List<AddressDTO> list, Object obj) {
		List<AddressDTO> addressList = new ArrayList<AddressDTO>();
		Long refTableId = null;
		String refTable = null;
		Long companyId = null;

		if (obj instanceof Employee) {
			Employee empObj = (Employee) obj;
			refTableId = empObj.getId();
			refTable = empObj.getClass().getSimpleName();
			companyId = empObj.getCompanyId();

		} else if (obj instanceof WorkExperience) {
			WorkExperience empObj = (WorkExperience) obj;
			refTableId = empObj.getId();
			refTable = empObj.getClass().getSimpleName();
			companyId = empObj.getCompanyId();

		} else {
			throw new IllegalArgumentException("Unsupported object type: " + obj.getClass().getName());
		}
		for (AddressDTO addressObj : list) {
			addressObj.setRefTableId(refTableId);
			addressObj.setRefTable(refTable);
			addressObj.setCompanyId(companyId);
			AddressDTO addressDTO = this.save(addressObj);
			addressList.add(addressDTO);
		}
		return addressList;
	}

}
