package com.techvg.hrms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.hrms.repository.ContactsRepository;
import com.techvg.hrms.service.ContactsQueryService;
import com.techvg.hrms.service.ContactsService;
import com.techvg.hrms.service.criteria.ContactsCriteria;
import com.techvg.hrms.service.dto.ContactsDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.Contacts}.
 */
@RestController
@RequestMapping("/api")
public class ContactsResource {

	private final Logger log = LoggerFactory.getLogger(ContactsResource.class);

	private static final String ENTITY_NAME = "contacts";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ContactsService contactsService;

	private final ContactsRepository contactsRepository;

	private final ContactsQueryService contactsQueryService;

	public ContactsResource(ContactsService contactsService, ContactsRepository contactsRepository,
			ContactsQueryService contactsQueryService) {
		this.contactsService = contactsService;
		this.contactsRepository = contactsRepository;
		this.contactsQueryService = contactsQueryService;
	}

	/**
	 * {@code POST  /contact} : Create a new contacts.
	 *
	 * @param contactsDTO the contactsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new contactsDTO, or with status {@code 400 (Bad Request)} if
	 *         the contacts has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/contact")
	public ResponseEntity<ContactsDTO> createContacts(@RequestBody ContactsDTO contactsDTO) throws URISyntaxException {
		log.debug("REST request to save Contacts : {}", contactsDTO);
		if (contactsDTO.getId() != null) {
			throw new BadRequestAlertException("A new contacts cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ContactsDTO result = contactsService.save(contactsDTO);
		return ResponseEntity
				.created(new URI("/api/contacts/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /contact/:id} : Updates an existing contacts.
	 *
	 * @param id          the id of the contactsDTO to save.
	 * @param contactsDTO the contactsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated contactsDTO, or with status {@code 400 (Bad Request)} if
	 *         the contactsDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the contactsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/contact/{id}")
	public ResponseEntity<ContactsDTO> updateContacts(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ContactsDTO contactsDTO) throws URISyntaxException {
		log.debug("REST request to update Contacts : {}, {}", id, contactsDTO);
		if (contactsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, contactsDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!contactsRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		ContactsDTO result = contactsService.update(contactsDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactsDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /contact/:id} : Partial updates given fields of an existing
	 * contacts, field will ignore if it is null
	 *
	 * @param id          the id of the contactsDTO to save.
	 * @param contactsDTO the contactsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated contactsDTO, or with status {@code 400 (Bad Request)} if
	 *         the contactsDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the contactsDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the contactsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/contact/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<ContactsDTO> partialUpdateContacts(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody ContactsDTO contactsDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update Contacts partially : {}, {}", id, contactsDTO);
		if (contactsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, contactsDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!contactsRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<ContactsDTO> result = contactsService.partialUpdate(contactsDTO);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactsDTO.getId().toString()));
	}

	/**
	 * {@code GET  /contact} : get all the contacts.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of contacts in body.
	 */
	@GetMapping("/contact")
	public ResponseEntity<List<ContactsDTO>> getAllContacts(ContactsCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Contacts by criteria: {}", criteria);
		Page<ContactsDTO> page = contactsQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /contact/count} : count all the contacts.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/contact/count")
	public ResponseEntity<Long> countContacts(ContactsCriteria criteria) {
		log.debug("REST request to count Contacts by criteria: {}", criteria);
		return ResponseEntity.ok().body(contactsQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /contact/:id} : get the "id" contacts.
	 *
	 * @param id the id of the contactsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the contactsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/contact/{id}")
	public ResponseEntity<ContactsDTO> getContacts(@PathVariable Long id) {
		log.debug("REST request to get Contacts : {}", id);
		Optional<ContactsDTO> contactsDTO = contactsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(contactsDTO);
	}

	/**
	 * {@code DELETE  /contact/:id} : delete the "id" contacts.
	 *
	 * @param id the id of the contactsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/contact/{id}")
	public ResponseEntity<Void> deleteContacts(@PathVariable Long id) {
		log.debug("REST request to delete Contacts : {}", id);
		contactsService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

}
