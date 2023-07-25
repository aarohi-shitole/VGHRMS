package com.techvg.hrms.web.rest;

//import com.techvg.hrms.domain.Approvar;

import com.techvg.hrms.repository.ApprovalSettingRepository;
import com.techvg.hrms.service.ApprovalLevelQueryService;
import com.techvg.hrms.service.ApprovalLevelService;
import com.techvg.hrms.service.ApprovalSettingQueryService;
import com.techvg.hrms.service.ApprovalSettingService;
import com.techvg.hrms.service.criteria.ApprovalLevelCriteria;
import com.techvg.hrms.service.criteria.ApprovalSettingCriteria;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.service.dto.ApprovalSettingDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.ApprovalSetting}.
 */
@RestController
@RequestMapping("/api")
public class ApprovalSettingResource {

	private final Logger log = LoggerFactory.getLogger(ApprovalSettingResource.class);

	private static final String ENTITY_NAME = "approvalSetting";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ApprovalSettingService approvalSettingService;

	@Autowired
	private ApprovalLevelService approvalLevelService;
	
	@Autowired
	private ApprovalLevelQueryService approvalLevelQueryService;
	

	private final ApprovalSettingRepository approvalSettingRepository;

	private final ApprovalSettingQueryService approvalSettingQueryService;

	public ApprovalSettingResource(ApprovalSettingService approvalSettingService,
			ApprovalSettingRepository approvalSettingRepository,
			ApprovalSettingQueryService approvalSettingQueryService) {
		this.approvalSettingService = approvalSettingService;
		this.approvalSettingRepository = approvalSettingRepository;
		this.approvalSettingQueryService = approvalSettingQueryService;
	}

	/**
	 * {@code POST  /approval-settings} : Create a new approvalSetting.
	 *
	 * @param approvalSettingDTO the approvalSettingDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new approvalSettingDTO, or with status
	 *         {@code 400 (Bad Request)} if the approvalSetting has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/approval-settings")
	public ResponseEntity<ApprovalSettingDTO> createApprovalSetting(@RequestBody ApprovalSettingDTO approvalSettingDTO)
			throws URISyntaxException {
		log.debug("REST request to save ApprovalSetting : {}", approvalSettingDTO);

		if (approvalSettingDTO.getId() != null) {
			throw new BadRequestAlertException("A new approvalSetting cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		ApprovalSettingDTO result = approvalSettingService.save(approvalSettingDTO);

//		ArrayList<Approvar> approvarList = new ArrayList<Approvar>();
//		approvarList.addAll(approvalSettingDTO.getApprovars());
//
//		if (approvarList != null) {
//			System.out.println("if (approvarList != null) approvarList :" + approvarList);
//
//			for (Approvar approvar : approvarList) {
//
//				ApprovalLevelDTO approvalLevel = new ApprovalLevelDTO();
//
//				 if (approvar.roleName != null || approvar.sequence != null || result.getId()
//				 != null) {
//				Long seq=approvar.sequence;
//				String name=approvar.roleName;
//				Long approvalSettingId=result.getId();
//				System.out.println("approvar.sequence : " +seq);
//				System.out.println("approvar.roleName : " + name);
//				System.out.println("result.getId(): "+ approvalSettingId);
//				approvalLevel.setSquence(seq);
//				approvalLevel.setName(name);
//				approvalLevel.setApprovalSettingId(approvalSettingId);
//				approvalLevel.setCompanyId(approvalSettingDTO.getCompanyId());
//				approvalLevel.setLastModified(approvalSettingDTO.getLastModified());
//				approvalLevel.setStatus(approvalSettingDTO.getStatus());
//				approvalLevel.setLastModifiedBy(approvalSettingDTO.getLastModifiedBy());
//				
//				System.out.println("\n approvalLevelDTO :"+approvalLevel);
//
//				approvalLevelService.save(approvalLevel);
//				 }
//			}
//		}

		return ResponseEntity
				.created(new URI("/api/approval-settings/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /approval-settings/:id} : Updates an existing approvalSetting.
	 *
	 * @param id                 the id of the approvalSettingDTO to save.
	 * @param approvalSettingDTO the approvalSettingDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated approvalSettingDTO, or with status
	 *         {@code 400 (Bad Request)} if the approvalSettingDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         approvalSettingDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/approval-settings/{id}")
	public ResponseEntity<ApprovalSettingDTO> updateApprovalSetting(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ApprovalSettingDTO approvalSettingDTO) throws URISyntaxException {
		log.debug("REST request to update ApprovalSetting : {}, {}", id, approvalSettingDTO);
		if (approvalSettingDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, approvalSettingDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!approvalSettingRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		
//		ApprovalLevelCriteria criteria=new ApprovalLevelCriteria();
//		Long appSettingId=approvalSettingDTO.getId();
//		LongFilter settingId=new LongFilter();
//		settingId.setEquals(appSettingId);
//		criteria.approvalSettingId();
//		
//		List<ApprovalLevelDTO> approvalLevelList=approvalLevelQueryService.findByCriteria(criteria);
//		if(approvalLevelList!=null) {
//			for(ApprovalLevelDTO level:approvalLevelList) {
//				level.setStatus("I");
//				approvalLevelService.update(level);				
//			}
//		}
//		
//		ArrayList<Approvar> approvarList = new ArrayList<Approvar>();
//		approvarList.addAll(approvalSettingDTO.getApprovars());
//
//		if (approvarList != null) {
//			System.out.println("if (approvarList != null) approvarList :" + approvarList);
//
//			for (Approvar approvar : approvarList) {
//
//				ApprovalLevelDTO approvalLevel = new ApprovalLevelDTO();
//
//				 if (approvar.roleName != null || approvar.sequence != null ) {
//				Long seq=approvar.sequence;
//				String name=approvar.roleName;
//				Long approvalSettingId=approvalSettingDTO.getId();
//				System.out.println("approvar.sequence : " +seq);
//				System.out.println("approvar.roleName : " + name);
//				System.out.println("approvalSettingId: "+ approvalSettingId);
//				approvalLevel.setSquence(seq);
//				approvalLevel.setName(name);
//				approvalLevel.setApprovalSettingId(approvalSettingId);
//				approvalLevel.setCompanyId(approvalSettingDTO.getCompanyId());
//				approvalLevel.setLastModified(approvalSettingDTO.getLastModified());
//				approvalLevel.setStatus(approvalSettingDTO.getStatus());
//				approvalLevel.setLastModifiedBy(approvalSettingDTO.getLastModifiedBy());
//				
//				System.out.println("\n approvalLevelDTO :"+approvalLevel);
//
//				approvalLevelService.update(approvalLevel);
//				 }
//			}
//		}
		
		ApprovalSettingDTO result = approvalSettingService.update(approvalSettingDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				approvalSettingDTO.getId().toString())).body(result);
	}

	/**
	 * {@code PATCH  /approval-settings/:id} : Partial updates given fields of an
	 * existing approvalSetting, field will ignore if it is null
	 *
	 * @param id                 the id of the approvalSettingDTO to save.
	 * @param approvalSettingDTO the approvalSettingDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated approvalSettingDTO, or with status
	 *         {@code 400 (Bad Request)} if the approvalSettingDTO is not valid, or
	 *         with status {@code 404 (Not Found)} if the approvalSettingDTO is not
	 *         found, or with status {@code 500 (Internal Server Error)} if the
	 *         approvalSettingDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/approval-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<ApprovalSettingDTO> partialUpdateApprovalSetting(
			@PathVariable(value = "id", required = false) final Long id,
			@RequestBody ApprovalSettingDTO approvalSettingDTO) throws URISyntaxException {
		log.debug("REST request to partial update ApprovalSetting partially : {}, {}", id, approvalSettingDTO);
		if (approvalSettingDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, approvalSettingDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!approvalSettingRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<ApprovalSettingDTO> result = approvalSettingService.partialUpdate(approvalSettingDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, approvalSettingDTO.getId().toString()));
	}

	/**
	 * {@code GET  /approval-settings} : get all the approvalSettings.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of approvalSettings in body.
	 */
	@GetMapping("/approval-settings")
	public ResponseEntity<List<ApprovalSettingDTO>> getAllApprovalSettings(ApprovalSettingCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get ApprovalSettings by criteria: {}", criteria);
		Page<ApprovalSettingDTO> page = approvalSettingQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /approval-settings/count} : count all the approvalSettings.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/approval-settings/count")
	public ResponseEntity<Long> countApprovalSettings(ApprovalSettingCriteria criteria) {
		log.debug("REST request to count ApprovalSettings by criteria: {}", criteria);
		return ResponseEntity.ok().body(approvalSettingQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /approval-settings/:id} : get the "id" approvalSetting.
	 *
	 * @param id the id of the approvalSettingDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the approvalSettingDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/approval-settings/{id}")
	public ResponseEntity<ApprovalSettingDTO> getApprovalSetting(@PathVariable Long id) {
		log.debug("REST request to get ApprovalSetting : {}", id);
		Optional<ApprovalSettingDTO> approvalSettingDTO = approvalSettingService.findOne(id);
		return ResponseUtil.wrapOrNotFound(approvalSettingDTO);
	}

	/**
	 * {@code DELETE  /approval-settings/:id} : delete the "id" approvalSetting.
	 *
	 * @param id the id of the approvalSettingDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/approval-settings/{id}")
	public ResponseEntity<Void> deleteApprovalSetting(@PathVariable Long id) {
		log.debug("REST request to delete ApprovalSetting : {}", id);
		approvalSettingService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
