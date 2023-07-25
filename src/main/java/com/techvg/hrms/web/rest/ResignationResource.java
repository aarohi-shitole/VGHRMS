package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.ResignationRepository;
import com.techvg.hrms.service.ResignationHelper;
import com.techvg.hrms.service.ResignationQueryService;
import com.techvg.hrms.service.ResignationService;
import com.techvg.hrms.service.criteria.ResignationCriteria;
import com.techvg.hrms.service.dto.ResignationDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import com.techvg.hrms.web.rest.errors.BusinessRuleException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Resignation}.
 */
@RestController
@RequestMapping("/api")
public class ResignationResource {

	private final Logger log = LoggerFactory.getLogger(ResignationResource.class);

	private static final String ENTITY_NAME = "resignation";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ResignationService resignationService;

	private final ResignationRepository resignationRepository;

	private final ResignationQueryService resignationQueryService;

	@Autowired
	private ResignationHelper resignationHelper;

	public ResignationResource(ResignationService resignationService, ResignationRepository resignationRepository,
			ResignationQueryService resignationQueryService) {
		this.resignationService = resignationService;
		this.resignationRepository = resignationRepository;
		this.resignationQueryService = resignationQueryService;
	}

	/**
	 * {@code POST  /resignations} : Create a new resignation.
	 *
	 * @param resignationDTO the resignationDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new resignationDTO, or with status {@code 400 (Bad Request)}
	 *         if the resignation has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/resignations")
	public ResponseEntity<ResignationDTO> createResignation(@RequestBody ResignationDTO resignationDTO)
			throws URISyntaxException {
		log.debug("REST request to save Resignation : {}", resignationDTO);
		if (resignationDTO.getId() != null) {
			throw new BadRequestAlertException("A new resignation cannot already have an ID", ENTITY_NAME, "idexists");
		}
	Boolean resignExist = resignationHelper.checkResignationApplied(resignationDTO);
		
	if(resignExist) {
		throw new BusinessRuleException("Resignation request is not acepted", "Employee with id: " + resignationDTO.getEmployeeId()+ " is already applied for resignation");
	}
		ResignationDTO result = resignationService.save(resignationDTO);

		resignationHelper.createResignationApplicationApproval(result);
		return ResponseEntity
				.created(new URI("/api/resignations/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /resignations/:id} : Updates an existing resignation.
	 *
	 * @param id             the id of the resignationDTO to save.
	 * @param resignationDTO the resignationDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated resignationDTO, or with status {@code 400 (Bad Request)}
	 *         if the resignationDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the resignationDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/resignations/{id}")
	public ResponseEntity<ResignationDTO> updateResignation(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ResignationDTO resignationDTO) throws URISyntaxException {
		log.debug("REST request to update Resignation : {}, {}", id, resignationDTO);
		if (resignationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, resignationDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!resignationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		if (resignationDTO.getResignStatus().equalsIgnoreCase("Cancelled")) {
			resignationDTO = resignationHelper.cancelResignation(resignationDTO);
		}

		ResignationDTO result = resignationService.update(resignationDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				resignationDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /resignations/:id} : Partial updates given fields of an
	 * existing resignation, field will ignore if it is null
	 *
	 * @param id             the id of the resignationDTO to save.
	 * @param resignationDTO the resignationDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated resignationDTO, or with status {@code 400 (Bad Request)}
	 *         if the resignationDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the resignationDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the resignationDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/resignations/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<ResignationDTO> partialUpdateResignation(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody ResignationDTO resignationDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update Resignation partially : {}, {}", id, resignationDTO);
		if (resignationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, resignationDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!resignationRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<ResignationDTO> result = resignationService.partialUpdate(resignationDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, resignationDTO.getId().toString()));
	}

	/**
	 * {@code GET  /resignations} : get all the resignations.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of resignations in body.
	 */
	@GetMapping("/resignations")
	public ResponseEntity<List<ResignationDTO>> getAllResignations(ResignationCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Resignations by criteria: {}", criteria);
		Page<ResignationDTO> page = resignationQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /resignations/count} : count all the resignations.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/resignations/count")
	public ResponseEntity<Long> countResignations(ResignationCriteria criteria) {
		log.debug("REST request to count Resignations by criteria: {}", criteria);
		return ResponseEntity.ok().body(resignationQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /resignations/:id} : get the "id" resignation.
	 *
	 * @param id the id of the resignationDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the resignationDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/resignations/{id}")
	public ResponseEntity<ResignationDTO> getResignation(@PathVariable Long id) {
		log.debug("REST request to get Resignation : {}", id);
		Optional<ResignationDTO> resignationDTO = resignationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(resignationDTO);
	}

	/**
	 * {@code DELETE  /resignations/:id} : delete the "id" resignation.
	 *
	 * @param id the id of the resignationDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/resignations/{id}")
	public ResponseEntity<Void> deleteResignation(@PathVariable Long id) {
		log.debug("REST request to delete Resignation : {}", id);
		resignationService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	@GetMapping("/resignation/approvar-list")
	public ResponseEntity<HashMap<Long, Long>> getResignationApprovars(@RequestParam Long resignationApplicationId,
			@RequestParam Long approvarEmpId) {
		log.debug("REST request to get Resignation : {}", resignationApplicationId);
		if (!resignationRepository.existsById(resignationApplicationId)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<ResignationDTO> resignationDTO = resignationService.findOne(resignationApplicationId);

		// Find the active approvar's for resignation application
		HashMap<Long, Long> approvarListMap = resignationHelper.findApprovarList(resignationDTO.get().getEmployeeId(),
				approvarEmpId);

		LinkedHashMap<Long, Long> approvarSortedMap = resignationHelper.getSortedMap(approvarListMap);

		// Find passed approvar present in list
		boolean isApprovar = resignationHelper.checkApprovarPresent(approvarEmpId, approvarListMap);
		return ResponseEntity.ok(approvarSortedMap);
	}

}
