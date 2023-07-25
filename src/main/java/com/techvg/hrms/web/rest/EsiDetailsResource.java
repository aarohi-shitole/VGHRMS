package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.EsiDetailsRepository;
import com.techvg.hrms.service.EsiDetailsQueryService;
import com.techvg.hrms.service.EsiDetailsService;
import com.techvg.hrms.service.criteria.EsiDetailsCriteria;
import com.techvg.hrms.service.dto.EsiDetailsDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.EsiDetails}.
 */
@RestController
@RequestMapping("/api")
public class EsiDetailsResource {

	private final Logger log = LoggerFactory.getLogger(EsiDetailsResource.class);

	private static final String ENTITY_NAME = "esiDetails";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final EsiDetailsService esiDetailsService;

	private final EsiDetailsRepository esiDetailsRepository;

	private final EsiDetailsQueryService esiDetailsQueryService;

	public EsiDetailsResource(EsiDetailsService esiDetailsService, EsiDetailsRepository esiDetailsRepository,
			EsiDetailsQueryService esiDetailsQueryService) {
		this.esiDetailsService = esiDetailsService;
		this.esiDetailsRepository = esiDetailsRepository;
		this.esiDetailsQueryService = esiDetailsQueryService;
	}

	/**
	 * {@code POST  /esi-details} : Create a new esiDetails.
	 *
	 * @param esiDetailsDTO the esiDetailsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new esiDetailsDTO, or with status {@code 400 (Bad Request)}
	 *         if the esiDetails has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/esi-details")
	public ResponseEntity<EsiDetailsDTO> createEsiDetails(@RequestBody EsiDetailsDTO esiDetailsDTO)
	        throws URISyntaxException {
	    log.debug("REST request to save EsiDetails: {}", esiDetailsDTO);
	    if (esiDetailsDTO.getId() != null) {
	        throw new BadRequestAlertException("A new esiDetails cannot already have an ID", ENTITY_NAME, "idexists");
	    }
	    
	    // Check if same employee id for esic details are already exists
	    if (esiDetailsService.existsByEmployeeId(esiDetailsDTO.getEmployeeId())) {
	        throw new BadRequestAlertException("Cannot add multiple esic details for same employee id", ENTITY_NAME, "employeeIdexists");
	    }
	    
	    EsiDetailsDTO result = esiDetailsService.save(esiDetailsDTO);
	    return ResponseEntity
	            .created(new URI("/api/esi-details/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
	            .body(result);
	}

	
	/**
	 * {@code PUT  /esi-details/:id} : Updates an existing esiDetails.
	 *
	 * @param id            the id of the esiDetailsDTO to save.
	 * @param esiDetailsDTO the esiDetailsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated esiDetailsDTO, or with status {@code 400 (Bad Request)}
	 *         if the esiDetailsDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the esiDetailsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/esi-details/{id}")
	public ResponseEntity<EsiDetailsDTO> updateEsiDetails(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody EsiDetailsDTO esiDetailsDTO) throws URISyntaxException {
		log.debug("REST request to update EsiDetails : {}, {}", id, esiDetailsDTO);
		if (esiDetailsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, esiDetailsDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!esiDetailsRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		EsiDetailsDTO result = esiDetailsService.update(esiDetailsDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				esiDetailsDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /esi-details/:id} : Partial updates given fields of an existing
	 * esiDetails, field will ignore if it is null
	 *
	 * @param id            the id of the esiDetailsDTO to save.
	 * @param esiDetailsDTO the esiDetailsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated esiDetailsDTO, or with status {@code 400 (Bad Request)}
	 *         if the esiDetailsDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the esiDetailsDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the esiDetailsDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/esi-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<EsiDetailsDTO> partialUpdateEsiDetails(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody EsiDetailsDTO esiDetailsDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update EsiDetails partially : {}, {}", id, esiDetailsDTO);
		if (esiDetailsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, esiDetailsDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!esiDetailsRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<EsiDetailsDTO> result = esiDetailsService.partialUpdate(esiDetailsDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, esiDetailsDTO.getId().toString()));
	}

	/**
	 * {@code GET  /esi-details} : get all the esiDetails.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of esiDetails in body.
	 */
	@GetMapping("/esi-details")
	public ResponseEntity<List<EsiDetailsDTO>> getAllEsiDetails(EsiDetailsCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get EsiDetails by criteria: {}", criteria);
		Page<EsiDetailsDTO> page = esiDetailsQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /esi-details/count} : count all the esiDetails.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/esi-details/count")
	public ResponseEntity<Long> countEsiDetails(EsiDetailsCriteria criteria) {
		log.debug("REST request to count EsiDetails by criteria: {}", criteria);
		return ResponseEntity.ok().body(esiDetailsQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /esi-details/:id} : get the "id" esiDetails.
	 *
	 * @param id the id of the esiDetailsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the esiDetailsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/esi-details/{id}")
	public ResponseEntity<EsiDetailsDTO> getEsiDetails(@PathVariable Long id) {
		log.debug("REST request to get EsiDetails : {}", id);
		Optional<EsiDetailsDTO> esiDetailsDTO = esiDetailsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(esiDetailsDTO);
	}

	/**
	 * {@code DELETE  /esi-details/:id} : delete the "id" esiDetails.
	 *
	 * @param id the id of the esiDetailsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/esi-details/{id}")
	public ResponseEntity<Void> deleteEsiDetails(@PathVariable Long id) {
		log.debug("REST request to delete EsiDetails : {}", id);
		esiDetailsService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
