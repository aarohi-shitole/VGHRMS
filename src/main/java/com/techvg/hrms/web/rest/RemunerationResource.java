package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.RemunerationRepository;
import com.techvg.hrms.service.RemunerationQueryService;
import com.techvg.hrms.service.RemunerationService;
import com.techvg.hrms.service.criteria.RemunerationCriteria;
import com.techvg.hrms.service.dto.RemunerationDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Remuneration}.
 */
@RestController
@RequestMapping("/api")
public class RemunerationResource {

	private final Logger log = LoggerFactory.getLogger(RemunerationResource.class);

	private static final String ENTITY_NAME = "remuneration";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final RemunerationService remunerationService;

	private final RemunerationRepository remunerationRepository;

	private final RemunerationQueryService remunerationQueryService;

	public RemunerationResource(RemunerationService remunerationService, RemunerationRepository remunerationRepository,
			RemunerationQueryService remunerationQueryService) {
		this.remunerationService = remunerationService;
		this.remunerationRepository = remunerationRepository;
		this.remunerationQueryService = remunerationQueryService;
	}

	/**
	 * {@code POST  /remunerations} : Create a new remuneration.
	 *
	 * @param remunerationDTO the remunerationDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new remunerationDTO, or with status
	 *         {@code 400 (Bad Request)} if the remuneration has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/remunerations")
	public ResponseEntity<RemunerationDTO> createRemuneration(@RequestBody RemunerationDTO remunerationDTO)
			throws URISyntaxException {
		log.debug("REST request to save Remuneration : {}", remunerationDTO);
		if (remunerationDTO.getId() != null) {
			throw new BadRequestAlertException("A new remuneration cannot already have an ID", ENTITY_NAME, "idexists");
		}
		RemunerationDTO result = remunerationService.save(remunerationDTO);
		return ResponseEntity
				.created(new URI("/api/remunerations/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /remunerations/:id} : Updates an existing remuneration.
	 *
	 * @param id              the id of the remunerationDTO to save.
	 * @param remunerationDTO the remunerationDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated remunerationDTO, or with status {@code 400 (Bad Request)}
	 *         if the remunerationDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the remunerationDTO couldn't
	 *         be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/remunerations/{id}")
	public ResponseEntity<RemunerationDTO> updateRemuneration(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody RemunerationDTO remunerationDTO)
			throws URISyntaxException {
		log.debug("REST request to update Remuneration : {}, {}", id, remunerationDTO);
		if (remunerationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, remunerationDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!remunerationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		RemunerationDTO result = remunerationService.update(remunerationDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				remunerationDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /remunerations/:id} : Partial updates given fields of an
	 * existing remuneration, field will ignore if it is null
	 *
	 * @param id              the id of the remunerationDTO to save.
	 * @param remunerationDTO the remunerationDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated remunerationDTO, or with status {@code 400 (Bad Request)}
	 *         if the remunerationDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the remunerationDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the remunerationDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/remunerations/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<RemunerationDTO> partialUpdateRemuneration(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody RemunerationDTO remunerationDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update Remuneration partially : {}, {}", id, remunerationDTO);
		if (remunerationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, remunerationDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!remunerationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<RemunerationDTO> result = remunerationService.partialUpdate(remunerationDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, remunerationDTO.getId().toString()));
	}

	/**
	 * {@code GET  /remunerations} : get all the remunerations.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of remunerations in body.
	 */
	@GetMapping("/remunerations")
	public ResponseEntity<List<RemunerationDTO>> getAllRemunerations(RemunerationCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Remunerations by criteria: {}", criteria);
		Page<RemunerationDTO> page = remunerationQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /remunerations/count} : count all the remunerations.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/remunerations/count")
	public ResponseEntity<Long> countRemunerations(RemunerationCriteria criteria) {
		log.debug("REST request to count Remunerations by criteria: {}", criteria);
		return ResponseEntity.ok().body(remunerationQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /remunerations/:id} : get the "id" remuneration.
	 *
	 * @param id the id of the remunerationDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the remunerationDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/remunerations/{id}")
	public ResponseEntity<RemunerationDTO> getRemuneration(@PathVariable Long id) {
		log.debug("REST request to get Remuneration : {}", id);
		Optional<RemunerationDTO> remunerationDTO = remunerationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(remunerationDTO);
	}

	/**
	 * {@code DELETE  /remunerations/:id} : delete the "id" remuneration.
	 *
	 * @param id the id of the remunerationDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/remunerations/{id}")
	public ResponseEntity<Void> deleteRemuneration(@PathVariable Long id) {
		log.debug("REST request to delete Remuneration : {}", id);
		remunerationService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
