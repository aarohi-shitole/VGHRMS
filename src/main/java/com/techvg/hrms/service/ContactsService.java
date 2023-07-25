package com.techvg.hrms.service;

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

import com.techvg.hrms.domain.Contacts;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.FamilyInfo;
import com.techvg.hrms.repository.ContactsRepository;
import com.techvg.hrms.service.dto.ContactsDTO;
import com.techvg.hrms.service.mapper.ContactsMapper;

/**
 * Service Implementation for managing {@link Contacts}.
 */
@Service
@Transactional
public class ContactsService {

	private final Logger log = LoggerFactory.getLogger(ContactsService.class);

	private final ContactsRepository contactsRepository;

	private final ContactsMapper contactsMapper;
	
	@Autowired
    private ValidationService validationService;

	public ContactsService(ContactsRepository contactsRepository, ContactsMapper contactsMapper) {
		this.contactsRepository = contactsRepository;
		this.contactsMapper = contactsMapper;
	}

	/**
	 * Save a contacts.
	 *
	 * @param contactsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ContactsDTO save(ContactsDTO contactsDTO) {
		log.debug("Request to save Contacts : {}", contactsDTO);
			Contacts contacts = contactsMapper.toEntity(contactsDTO);
			validationService.validateMethod(contacts);
		contacts = contactsRepository.save(contacts);
		return contactsMapper.toDto(contacts);
	}

	/**
	 * Update a contacts.
	 *
	 * @param contactsDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ContactsDTO update(ContactsDTO contactsDTO) {
		log.debug("Request to update Contacts : {}", contactsDTO);
		Contacts contacts = contactsMapper.toEntity(contactsDTO);
		validationService.validateMethod(contacts);
		contacts = contactsRepository.save(contacts);
		return contactsMapper.toDto(contacts);
	}

	/**
	 * Partially update a contacts.
	 *
	 * @param contactsDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<ContactsDTO> partialUpdate(ContactsDTO contactsDTO) {
		log.debug("Request to partially update Contacts : {}", contactsDTO);

		return contactsRepository.findById(contactsDTO.getId()).map(existingContacts -> {
			contactsMapper.partialUpdate(existingContacts, contactsDTO);

			return existingContacts;
		}).map(contactsRepository::save).map(contactsMapper::toDto);
	}

	/**
	 * Get all the contacts.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<ContactsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Contacts");
		return contactsRepository.findAll(pageable).map(contactsMapper::toDto);
	}

	/**
	 * Get one contacts by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<ContactsDTO> findOne(Long id) {
		log.debug("Request to get Contacts : {}", id);
		return contactsRepository.findById(id).map(contactsMapper::toDto);
	}

	/**
	 * Delete the contacts by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Contacts : {}", id);
		contactsRepository.deleteById(id);
	}

	/**
	 * Save the contacts by contacts list.
	 *
	 * @param ContactDTO list and Object the of the entity.
	 * @return the List of entity.
	 */
	public List<ContactsDTO> saveContacts(List<ContactsDTO> list, Object obj) {
		List<ContactsDTO> contactsList = new ArrayList<ContactsDTO>();
		Long refTableId = null;
		String refTable = null;
		Long companyId = null;

		if (obj instanceof Employee) {
			Employee empObj = (Employee) obj;
			refTableId = empObj.getId();
			refTable = empObj.getClass().getSimpleName();
			companyId = empObj.getCompanyId();

		} else if (obj instanceof FamilyInfo) {
			FamilyInfo familyObj = (FamilyInfo) obj;
			refTableId = familyObj.getId();
			refTable = familyObj.getClass().getSimpleName();
			companyId = familyObj.getCompanyId();

		} else {
			throw new IllegalArgumentException("Unsupported object type: " + obj.getClass().getName());
		}
		for (ContactsDTO contactObj : list) {
			contactObj.setRefTableId(refTableId);
			contactObj.setRefTable(refTable);
			contactObj.setCompanyId(companyId);
			ContactsDTO contactsDTO = this.save(contactObj);
			contactsList.add(contactsDTO);
		}
		return contactsList;
	}
}
