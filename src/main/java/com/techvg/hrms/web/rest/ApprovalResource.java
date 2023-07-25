package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.ApprovalRepository;
import com.techvg.hrms.service.ApprovalHelper;
import com.techvg.hrms.service.ApprovalQueryService;
import com.techvg.hrms.service.ApprovalService;
import com.techvg.hrms.service.criteria.ApprovalCriteria;

import com.techvg.hrms.service.dto.ApprovalDTO;

import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.Approval}.
 */
@RestController
@RequestMapping("/api")
public class ApprovalResource {

	private final Logger log = LoggerFactory.getLogger(ApprovalResource.class);

	private static final String ENTITY_NAME = "approval";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ApprovalService approvalService;

	private final ApprovalRepository approvalRepository;

	private final ApprovalQueryService approvalQueryService;

	// For API /approvals/apply-status
	@Autowired
	public ApprovalHelper approvalHelper;

	public ApprovalResource(ApprovalService approvalService, ApprovalRepository approvalRepository,
			ApprovalQueryService approvalQueryService) {
		this.approvalService = approvalService;
		this.approvalRepository = approvalRepository;
		this.approvalQueryService = approvalQueryService;
	}

	/**
	 * {@code POST  /approvals} : Create a new approval.
	 *
	 * @param approvalDTO the approvalDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new approvalDTO, or with status {@code 400 (Bad Request)} if
	 *         the approval has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/approvals")
	public ResponseEntity<ApprovalDTO> createApproval(@RequestBody ApprovalDTO approvalDTO) throws URISyntaxException {
		log.debug("REST request to save Approval : {}", approvalDTO);
		if (approvalDTO.getId() != null) {
			throw new BadRequestAlertException("A new approval cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ApprovalDTO result = approvalService.save(approvalDTO);
		return ResponseEntity
				.created(new URI("/api/leave-application-approvals/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /approvals/:id} : Updates an existing Approval.
	 *
	 * @param id          the id of the approvalDTO to save.
	 * @param approvalDTO the approvalDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated approvalDTO, or with status {@code 400 (Bad Request)} if
	 *         the approvalDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the approvalDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/approvals/{id}")
	public ResponseEntity<ApprovalDTO> updateApproval(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ApprovalDTO approvalDTO) throws URISyntaxException {
		log.debug("REST request to update Approval : {}, {}", id, approvalDTO);
		if (approvalDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, approvalDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!approvalRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		ApprovalDTO result = approvalService.update(approvalDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /approvals/:id} : Partial updates given fields of an existing
	 * approval, field will ignore if it is null
	 *
	 * @param id          the id of the ApprovalDTO to save.
	 * @param approvalDTO the approvalDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated approvalDTO, or with status {@code 400 (Bad Request)} if
	 *         the approvalDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the approvalDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the approvalDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/approvals/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<ApprovalDTO> partialUpdateApproval(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody ApprovalDTO approvalDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update approval partially : {}, {}", id, approvalDTO);
		if (approvalDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, approvalDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!approvalRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<ApprovalDTO> result = approvalService.partialUpdate(approvalDTO);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalDTO.getId().toString()));
	}

	/**
	 * {@code GET  /approvals} : get all the approvals.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of approvals in body.
	 */
	@GetMapping("/approvals")
	public ResponseEntity<List<ApprovalDTO>> getAllApprovals(ApprovalCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Approvals by criteria: {}", criteria);
		Page<ApprovalDTO> page = approvalQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /approvals/count} : count all the Approvals.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/approvals/count")
	public ResponseEntity<Long> countApprovals(ApprovalCriteria criteria) {
		log.debug("REST request to count Approvals by criteria: {}", criteria);
		return ResponseEntity.ok().body(approvalQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /approvals/:id} : get the "id" approval.
	 *
	 * @param id the id of the approvalDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the approvalDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/approvals/{id}")
	public ResponseEntity<ApprovalDTO> getApproval(@PathVariable Long id) {
		log.debug("REST request to get Approval : {}", id);
		Optional<ApprovalDTO> approvalDTO = approvalService.findOne(id);
		return ResponseUtil.wrapOrNotFound(approvalDTO);
	}

	/**
	 * {@code DELETE  /approvals/:id} : delete the "id" Approval.
	 *
	 * @param id the id of the ApprovalDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/approvals/{id}")
	public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
		log.debug("REST request to delete Approval : {}", id);
		approvalService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	@PutMapping("/approvals/apply-status")
	public ResponseEntity<ApprovalDTO> checkApproval(@RequestBody ApprovalDTO approvalDTO) {
		log.debug("REST request to get Approval : {}", approvalDTO);

		ApprovalDTO approvalObj = approvalHelper.updateApproval(approvalDTO);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalDTO.getId().toString()))
				.body(approvalObj);
	}

	@PutMapping("/resignation-approvals/apply-status")
	public ResponseEntity<ApprovalDTO> checkResignationApproval(@RequestBody ApprovalDTO approvalDTO) {
		log.debug("REST request to get Approval : {}", approvalDTO);

		ApprovalDTO approvalObj = approvalHelper.updateResignationApproval(approvalDTO);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalDTO.getId().toString()))
				.body(approvalObj);
	}
	
	@PutMapping("/review-approvals/apply-status")
	public ResponseEntity<ApprovalDTO> checkReviewApproval(@RequestBody ApprovalDTO approvalDTO) {
		log.debug("REST request to get Approval : {}", approvalDTO);

		ApprovalDTO approvalObj = approvalHelper.updateReviewApproval(approvalDTO);

		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalDTO.getId().toString()))
				.body(approvalObj);
	}

}
