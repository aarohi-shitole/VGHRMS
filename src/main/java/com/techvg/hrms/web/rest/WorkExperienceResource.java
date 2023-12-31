package com.techvg.hrms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.repository.WorkExperienceRepository;
import com.techvg.hrms.service.AddressQueryService;
import com.techvg.hrms.service.WorkExperienceQueryService;
import com.techvg.hrms.service.WorkExperienceService;
import com.techvg.hrms.service.criteria.AddressCriteria;
import com.techvg.hrms.service.criteria.WorkExperienceCriteria;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.dto.WorkExperienceDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.WorkExperience}.
 */
@RestController
@RequestMapping("/api")
public class WorkExperienceResource {

	private final Logger log = LoggerFactory.getLogger(WorkExperienceResource.class);

	private static final String ENTITY_NAME = "workExperience";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final WorkExperienceService workExperienceService;

	private final WorkExperienceRepository workExperienceRepository;

	private final WorkExperienceQueryService workExperienceQueryService;

	private final AddressQueryService addressQueryService;

	public WorkExperienceResource(WorkExperienceService workExperienceService,
			WorkExperienceRepository workExperienceRepository, WorkExperienceQueryService workExperienceQueryService,
			AddressQueryService addressQueryService) {
		this.workExperienceService = workExperienceService;
		this.workExperienceRepository = workExperienceRepository;
		this.workExperienceQueryService = workExperienceQueryService;
		this.addressQueryService = addressQueryService;
	}

	/**
	 * {@code POST  /work-experiences} : Create a new workExperience.
	 *
	 * @param workExperienceDTO the workExperienceDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new workExperienceDTO, or with status
	 *         {@code 400 (Bad Request)} if the workExperience has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-experiences")
	public ResponseEntity<WorkExperienceDTO> createWorkExperience(@RequestBody WorkExperienceDTO workExperienceDTO)
			throws URISyntaxException {
		log.debug("REST request to save WorkExperience : {}", workExperienceDTO);
		if (workExperienceDTO.getId() != null) {
			throw new BadRequestAlertException("A new workExperience cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		WorkExperienceDTO result = workExperienceService.save(workExperienceDTO);
		return ResponseEntity
				.created(new URI("/api/work-experiences/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /work-experiences/:id} : Updates an existing workExperience.
	 *
	 * @param id                the id of the workExperienceDTO to save.
	 * @param workExperienceDTO the workExperienceDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workExperienceDTO, or with status
	 *         {@code 400 (Bad Request)} if the workExperienceDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         workExperienceDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/work-experiences/{id}")
	public ResponseEntity<WorkExperienceDTO> updateWorkExperience(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody WorkExperienceDTO workExperienceDTO) throws URISyntaxException {
		log.debug("REST request to update WorkExperience : {}, {}", id, workExperienceDTO);
		if (workExperienceDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, workExperienceDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!workExperienceRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		WorkExperienceDTO result = workExperienceService.update(workExperienceDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				workExperienceDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /work-experiences/:id} : Partial updates given fields of an
	 * existing workExperience, field will ignore if it is null
	 *
	 * @param id                the id of the workExperienceDTO to save.
	 * @param workExperienceDTO the workExperienceDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated workExperienceDTO, or with status
	 *         {@code 400 (Bad Request)} if the workExperienceDTO is not valid, or
	 *         with status {@code 404 (Not Found)} if the workExperienceDTO is not
	 *         found, or with status {@code 500 (Internal Server Error)} if the
	 *         workExperienceDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/work-experiences/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<WorkExperienceDTO> partialUpdateWorkExperience(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody WorkExperienceDTO workExperienceDTO) throws URISyntaxException {
		log.debug("REST request to partial update WorkExperience partially : {}, {}", id, workExperienceDTO);
		if (workExperienceDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, workExperienceDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!workExperienceRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<WorkExperienceDTO> result = workExperienceService.partialUpdate(workExperienceDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, workExperienceDTO.getId().toString()));
	}

	/**
	 * {@code GET  /work-experiences} : get all the workExperiences.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of workExperiences in body.
	 */
	@GetMapping("/work-experiences")
	public ResponseEntity<List<WorkExperienceDTO>> getAllWorkExperiences(WorkExperienceCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get WorkExperiences by criteria: {}", criteria);
		Page<WorkExperienceDTO> page = workExperienceQueryService.findByCriteria(criteria, pageable);
		ArrayList<WorkExperienceDTO> workExpTemp = new ArrayList<>();
		for (WorkExperienceDTO workExpObj : page.getContent()) {
			AddressCriteria addCriteria = new AddressCriteria();

			LongFilter refIdFilter = new LongFilter();
			refIdFilter.setEquals(workExpObj.getId());
			addCriteria.setRefTableId(refIdFilter);

			StringFilter refTable = new StringFilter();
			refTable.setContains(WorkExperience.class.getSimpleName());
			addCriteria.setRefTable(refTable);

			refIdFilter = new LongFilter();
			refIdFilter.setEquals(workExpObj.getCompanyId());
			addCriteria.setCompanyId(refIdFilter);

			refTable = new StringFilter();
			refTable.setEquals("A");
			addCriteria.setStatus(refTable);

			List<AddressDTO> addList = addressQueryService.findByCriteria(addCriteria);

			for (AddressDTO addressObj : addList) {
				workExpObj.setAddress(addressObj);
			}
			workExpTemp.add(workExpObj);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(workExpTemp);
	}

	/**
	 * {@code GET  /work-experiences/count} : count all the workExperiences.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/work-experiences/count")
	public ResponseEntity<Long> countWorkExperiences(WorkExperienceCriteria criteria) {
		log.debug("REST request to count WorkExperiences by criteria: {}", criteria);
		return ResponseEntity.ok().body(workExperienceQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /work-experiences/:id} : get the "id" workExperience.
	 *
	 * @param id the id of the workExperienceDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the workExperienceDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/work-experiences/{id}")
	public ResponseEntity<WorkExperienceDTO> getWorkExperience(@PathVariable Long id) {
		log.debug("REST request to get WorkExperience : {}", id);
		Optional<WorkExperienceDTO> workExperienceDTO = workExperienceService.findOne(id);
//-----------Bind address details of Work experience----

		AddressCriteria addCriteria = new AddressCriteria();
		LongFilter refIdFilter = new LongFilter();
		refIdFilter.setEquals(workExperienceDTO.get().getId());
		addCriteria.setRefTableId(refIdFilter);

		StringFilter refTable = new StringFilter();
		refTable.setContains(WorkExperience.class.getSimpleName());
		addCriteria.setRefTable(refTable);

		refIdFilter = new LongFilter();
		refIdFilter.setEquals(workExperienceDTO.get().getCompanyId());
		addCriteria.setCompanyId(refIdFilter);

		refTable = new StringFilter();
		refTable.setEquals("A");
		addCriteria.setStatus(refTable);

		List<AddressDTO> addList = addressQueryService.findByCriteria(addCriteria);
		for (AddressDTO addressObj : addList) {
			workExperienceDTO.get().setAddress(addressObj);
		}
		// -------------------------------------------------------------------

		return ResponseUtil.wrapOrNotFound(workExperienceDTO);
	}

	/**
	 * {@code DELETE  /work-experiences/:id} : delete the "id" workExperience.
	 *
	 * @param id the id of the workExperienceDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/work-experiences/{id}")
	public ResponseEntity<Void> deleteWorkExperience(@PathVariable Long id) {
		log.debug("REST request to delete WorkExperience : {}", id);
		workExperienceService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code POST  /work-experienceslist} : Create a new workExperience by using
	 * list.
	 *
	 * @param workExperienceDTOList the workExperienceDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 200 ok} and with body
	 *         the new workExperienceDTOList.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/work-experienceslist")
	public ResponseEntity<List<WorkExperienceDTO>> createWorkExperienceList(
			@RequestBody List<WorkExperienceDTO> workExperienceDTOList) throws URISyntaxException {
		log.debug("REST request to save WorkExperience : {}", workExperienceDTOList);
		ArrayList<WorkExperienceDTO> resultList = new ArrayList<>();

		for (WorkExperienceDTO workExperienceDTO : workExperienceDTOList) {

			WorkExperienceDTO result = workExperienceService.save(workExperienceDTO);
			resultList.add(result);
		}

		return ResponseEntity.ok().body(resultList);
	}

}
