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

import com.techvg.hrms.domain.FamilyInfo;
import com.techvg.hrms.repository.FamilyInfoRepository;
import com.techvg.hrms.service.dto.ContactsDTO;
import com.techvg.hrms.service.dto.FamilyInfoDTO;
import com.techvg.hrms.service.mapper.FamilyInfoMapper;

/**
 * Service Implementation for managing {@link FamilyInfo}.
 */
@Service
@Transactional
public class FamilyInfoService {

	private final Logger log = LoggerFactory.getLogger(FamilyInfoService.class);

	private final FamilyInfoRepository familyInfoRepository;

	private final FamilyInfoMapper familyInfoMapper;

	private final ContactsService contactsService;
	  @Autowired
	    private ValidationService validationService;
	public FamilyInfoService(FamilyInfoRepository familyInfoRepository, FamilyInfoMapper familyInfoMapper,
			ContactsService contactsService) {
		this.familyInfoRepository = familyInfoRepository;
		this.familyInfoMapper = familyInfoMapper;
		this.contactsService = contactsService;
	}

	/**
	 * Save a familyInfo.
	 *
	 * @param familyInfoDTO the entity to save.
	 * @return the persisted entity.
	 */
	public FamilyInfoDTO save(FamilyInfoDTO familyInfoDTO) {
		log.debug("Request to save FamilyInfo : {}", familyInfoDTO);
		
		FamilyInfo familyInfo = familyInfoMapper.toEntity(familyInfoDTO);
		validationService.validateMethod(familyInfo);
		familyInfo = familyInfoRepository.save(familyInfo);
		// ----------------Save/update contact of employee-------------
		List<ContactsDTO> contactList = new ArrayList<>();
		if (familyInfoDTO.getContactList() != null) {
			contactList = contactsService.saveContacts(familyInfoDTO.getContactList(), familyInfo);
		}

		// -------------------------------------------------------------
		familyInfoDTO = familyInfoMapper.toDto(familyInfo);
		familyInfoDTO.setContactList(contactList);
		return familyInfoDTO;
	}

	/**
	 * Update a familyInfo.
	 *
	 * @param familyInfoDTO the entity to save.
	 * @return the persisted entity.
	 */
	public FamilyInfoDTO update(FamilyInfoDTO familyInfoDTO) {
		log.debug("Request to update FamilyInfo : {}", familyInfoDTO);
		
		FamilyInfo familyInfo = familyInfoMapper.toEntity(familyInfoDTO);
		validationService.validateMethod(familyInfo);
		// ----------------Save/update contact of employee-------------
		List<ContactsDTO> contactList = new ArrayList<>();
		if (familyInfoDTO.getContactList() != null) {
			contactList = contactsService.saveContacts(familyInfoDTO.getContactList(), familyInfo);
		}
// -----------------------------------------------------------------------
		familyInfo = familyInfoRepository.save(familyInfo);
		familyInfoDTO = familyInfoMapper.toDto(familyInfo);
		familyInfoDTO.setContactList(contactList);
		return familyInfoDTO;
	}

	/**
	 * Partially update a familyInfo.
	 *
	 * @param familyInfoDTO the entity to update partially.
	 * @return the persisted entity.
	 */
	public Optional<FamilyInfoDTO> partialUpdate(FamilyInfoDTO familyInfoDTO) {
		log.debug("Request to partially update FamilyInfo : {}", familyInfoDTO);

		return familyInfoRepository.findById(familyInfoDTO.getId()).map(existingFamilyInfo -> {
			familyInfoMapper.partialUpdate(existingFamilyInfo, familyInfoDTO);

			return existingFamilyInfo;
		}).map(familyInfoRepository::save).map(familyInfoMapper::toDto);
	}

	/**
	 * Get all the familyInfos.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<FamilyInfoDTO> findAll(Pageable pageable) {
		log.debug("Request to get all FamilyInfos");
		return familyInfoRepository.findAll(pageable).map(familyInfoMapper::toDto);
	}

	/**
	 * Get one familyInfo by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<FamilyInfoDTO> findOne(Long id) {
		log.debug("Request to get FamilyInfo : {}", id);
		return familyInfoRepository.findById(id).map(familyInfoMapper::toDto);
	}

	/**
	 * Delete the familyInfo by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete FamilyInfo : {}", id);
		familyInfoRepository.deleteById(id);
	}
}
