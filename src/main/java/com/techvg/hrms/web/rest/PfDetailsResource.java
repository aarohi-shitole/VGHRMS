package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.PfDetailsRepository;
import com.techvg.hrms.service.PfDetailsQueryService;
import com.techvg.hrms.service.PfDetailsService;
import com.techvg.hrms.service.criteria.PfDetailsCriteria;
import com.techvg.hrms.service.dto.PfDetailsDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.PfDetails}.
 */
@RestController
@RequestMapping("/api")
public class PfDetailsResource {

	private final Logger log = LoggerFactory.getLogger(PfDetailsResource.class);

	private static final String ENTITY_NAME = "pfDetails";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PfDetailsService pfDetailsService;

	private final PfDetailsRepository pfDetailsRepository;

	private final PfDetailsQueryService pfDetailsQueryService;

	public PfDetailsResource(PfDetailsService pfDetailsService, PfDetailsRepository pfDetailsRepository,
			PfDetailsQueryService pfDetailsQueryService) {
		this.pfDetailsService = pfDetailsService;
		this.pfDetailsRepository = pfDetailsRepository;
		this.pfDetailsQueryService = pfDetailsQueryService;
	}

	/**
	 * {@code POST  /pf-details} : Create a new pfDetails.
	 *
	 * @param pfDetailsDTO the pfDetailsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new pfDetailsDTO, or with status {@code 400 (Bad Request)}
	 *         if the pfDetails has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/pf-details")
	public ResponseEntity<PfDetailsDTO> createPfDetails(@RequestBody PfDetailsDTO pfDetailsDTO)
			throws URISyntaxException {
		log.debug("REST request to save PfDetails : {}", pfDetailsDTO);
		if (pfDetailsDTO.getId() != null) {
			throw new BadRequestAlertException("A new pfDetails cannot already have an ID", ENTITY_NAME, "idexists");
		}
		
		 // Check if same employee id for pf details are already exists
	    if (pfDetailsService.existsByEmployeeId(pfDetailsDTO.getEmployeeId())) {
	        throw new BadRequestAlertException("Cannot add multiple pf details for same employee id", ENTITY_NAME, "employeeIdexists");
	    }
		
		PfDetailsDTO result = pfDetailsService.save(pfDetailsDTO);
		return ResponseEntity
				.created(new URI("/api/pf-details/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /pf-details/:id} : Updates an existing pfDetails.
	 *
	 * @param id           the id of the pfDetailsDTO to save.
	 * @param pfDetailsDTO the pfDetailsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pfDetailsDTO, or with status {@code 400 (Bad Request)} if
	 *         the pfDetailsDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the pfDetailsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/pf-details/{id}")
	public ResponseEntity<PfDetailsDTO> updatePfDetails(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody PfDetailsDTO pfDetailsDTO) throws URISyntaxException {
		log.debug("REST request to update PfDetails : {}, {}", id, pfDetailsDTO);
		if (pfDetailsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, pfDetailsDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!pfDetailsRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		PfDetailsDTO result = pfDetailsService.update(pfDetailsDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pfDetailsDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /pf-details/:id} : Partial updates given fields of an existing
	 * pfDetails, field will ignore if it is null
	 *
	 * @param id           the id of the pfDetailsDTO to save.
	 * @param pfDetailsDTO the pfDetailsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated pfDetailsDTO, or with status {@code 400 (Bad Request)} if
	 *         the pfDetailsDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the pfDetailsDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the pfDetailsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/pf-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<PfDetailsDTO> partialUpdatePfDetails(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody PfDetailsDTO pfDetailsDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update PfDetails partially : {}, {}", id, pfDetailsDTO);
		if (pfDetailsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, pfDetailsDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!pfDetailsRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<PfDetailsDTO> result = pfDetailsService.partialUpdate(pfDetailsDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, pfDetailsDTO.getId().toString()));
	}

	/**
	 * {@code GET  /pf-details} : get all the pfDetails.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of pfDetails in body.
	 */
	@GetMapping("/pf-details")
	public ResponseEntity<List<PfDetailsDTO>> getAllPfDetails(PfDetailsCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get PfDetails by criteria: {}", criteria);
		Page<PfDetailsDTO> page = pfDetailsQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /pf-details/count} : count all the pfDetails.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/pf-details/count")
	public ResponseEntity<Long> countPfDetails(PfDetailsCriteria criteria) {
		log.debug("REST request to count PfDetails by criteria: {}", criteria);
		return ResponseEntity.ok().body(pfDetailsQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /pf-details/:id} : get the "id" pfDetails.
	 *
	 * @param id the id of the pfDetailsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the pfDetailsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/pf-details/{id}")
	public ResponseEntity<PfDetailsDTO> getPfDetails(@PathVariable Long id) {
		log.debug("REST request to get PfDetails : {}", id);
		Optional<PfDetailsDTO> pfDetailsDTO = pfDetailsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(pfDetailsDTO);
	}

	/**
	 * {@code DELETE  /pf-details/:id} : delete the "id" pfDetails.
	 *
	 * @param id the id of the pfDetailsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/pf-details/{id}")
	public ResponseEntity<Void> deletePfDetails(@PathVariable Long id) {
		log.debug("REST request to delete PfDetails : {}", id);
		pfDetailsService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
