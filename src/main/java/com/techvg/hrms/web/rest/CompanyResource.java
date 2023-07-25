package com.techvg.hrms.web.rest;

import com.techvg.hrms.document.management.DocumentManagementService;
import com.techvg.hrms.domain.LeaveLevelList;
import com.techvg.hrms.repository.CompanyRepository;
import com.techvg.hrms.service.BranchQueryService;
import com.techvg.hrms.service.CompanyQueryService;
import com.techvg.hrms.service.CompanyService;
import com.techvg.hrms.service.DepartmentQueryService;
import com.techvg.hrms.service.RegionQueryService;
import com.techvg.hrms.service.criteria.BranchCriteria;
import com.techvg.hrms.service.criteria.CompanyCriteria;
import com.techvg.hrms.service.criteria.DepartmentCriteria;
import com.techvg.hrms.service.criteria.MasterLookupCriteria;
import com.techvg.hrms.service.criteria.RegionCriteria;
import com.techvg.hrms.service.dto.BranchDTO;
import com.techvg.hrms.service.dto.CompanyDTO;
import com.techvg.hrms.service.dto.DepartmentDTO;
import com.techvg.hrms.service.dto.RegionDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Company}.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

	private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

	private static final String ENTITY_NAME = "company";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CompanyService companyService;

	private final CompanyRepository companyRepository;

	private final CompanyQueryService companyQueryService;

	@Autowired
	private DepartmentQueryService departmentQueryService;

	@Autowired
	private BranchQueryService branchQueryService;

	@Autowired
	private RegionQueryService regionQueryService;

	public CompanyResource(CompanyService companyService, CompanyRepository companyRepository,
			CompanyQueryService companyQueryService) {
		this.companyService = companyService;
		this.companyRepository = companyRepository;
		this.companyQueryService = companyQueryService;
	}

	/**
	 * {@code POST  /companies} : Create a new company.
	 *
	 * @param companyDTO the companyDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new companyDTO, or with status {@code 400 (Bad Request)} if
	 *         the company has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/companies")
	public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) throws URISyntaxException {
		log.debug("REST request to save Company : {}", companyDTO);
		if (companyDTO.getId() != null) {
			throw new BadRequestAlertException("A new company cannot already have an ID", ENTITY_NAME, "idexists");
		}
		CompanyDTO result = companyService.save(companyDTO);
		return ResponseEntity
				.created(new URI("/api/companies/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /companies/:id} : Updates an existing company.
	 *
	 * @param id         the id of the companyDTO to save.
	 * @param companyDTO the companyDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated companyDTO, or with status {@code 400 (Bad Request)} if
	 *         the companyDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the companyDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/companies/{id}")
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody CompanyDTO companyDTO) throws URISyntaxException {
		log.debug("REST request to update Company : {}, {}", id, companyDTO);
		if (companyDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, companyDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!companyRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		CompanyDTO result = companyService.update(companyDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PATCH  /companies/:id} : Partial updates given fields of an existing
	 * company, field will ignore if it is null
	 *
	 * @param id         the id of the companyDTO to save.
	 * @param companyDTO the companyDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated companyDTO, or with status {@code 400 (Bad Request)} if
	 *         the companyDTO is not valid, or with status {@code 404 (Not Found)}
	 *         if the companyDTO is not found, or with status
	 *         {@code 500 (Internal Server Error)} if the companyDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/companies/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<CompanyDTO> partialUpdateCompany(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody CompanyDTO companyDTO) throws URISyntaxException {
		log.debug("REST request to partial update Company partially : {}, {}", id, companyDTO);
		if (companyDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, companyDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!companyRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<CompanyDTO> result = companyService.partialUpdate(companyDTO);

		return ResponseUtil.wrapOrNotFound(result,
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyDTO.getId().toString()));
	}

	/**
	 * {@code GET  /companies} : get all the companies.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of companies in body.
	 */
	@GetMapping("/companies")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies(CompanyCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Companies by criteria: {}", criteria);
		Page<CompanyDTO> page = companyQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /companies/count} : count all the companies.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/companies/count")
	public ResponseEntity<Long> countCompanies(CompanyCriteria criteria) {
		log.debug("REST request to count Companies by criteria: {}", criteria);
		return ResponseEntity.ok().body(companyQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /companies/:id} : get the "id" company.
	 *
	 * @param id the id of the companyDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the companyDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/companies/{id}")
	public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
		log.debug("REST request to get Company : {}", id);

		Optional<CompanyDTO> companyDTO = companyService.findOne(id);
		return ResponseUtil.wrapOrNotFound(companyDTO);

	}

	/**
	 * {@code DELETE  /companies/:id} : delete the "id" company.
	 *
	 * @param id the id of the companyDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/companies/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
		log.debug("REST request to delete Company : {}", id);
		companyService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	@GetMapping("/leaveLevels/{companyId}")
	public ResponseEntity<List<LeaveLevelList>> getLeaveLevelList(@PathVariable Long companyId) {
		log.debug("REST request to get company: {}", companyId);
		
		List<LeaveLevelList> leaveLevelList = new ArrayList<LeaveLevelList>();				
		LeaveLevelList LeaveLevel = new LeaveLevelList();

		String leaveSettingLevelName = null;
		Optional<CompanyDTO> companyDTO = companyService.findOne(companyId);
		if (companyDTO != null && !companyDTO.isEmpty()) {
			leaveSettingLevelName = companyDTO.get().getLeaveSettingLevel();
			log.debug("leaveSettingLevelName " + leaveSettingLevelName);
		}

		LongFilter cId = new LongFilter();
		cId.setEquals(companyId);
		
		if (leaveSettingLevelName.contains("Department")) {
			DepartmentCriteria criteria=new DepartmentCriteria();			
			criteria.setCompanyId(cId);
			
			log.debug("Department criteria : "+criteria.toString());
			
			List<DepartmentDTO> list=departmentQueryService.findByCriteria(criteria);
			if(list!=null) {
				for(DepartmentDTO department:list) {
					log.debug("Department DTO : "+department.toString());
					LeaveLevel = new LeaveLevelList();
					LeaveLevel.id=department.getId();
					LeaveLevel.value=department.getName();	
					LeaveLevel.level=leaveSettingLevelName;
					leaveLevelList.add(LeaveLevel);
				}
			}
		}
		else if (leaveSettingLevelName.contains("Branch")) {
			BranchCriteria criteria=new BranchCriteria();
			criteria.setCompanyId(cId);
			
			log.debug("BranchCriteria : "+criteria.toString());
			
			List<BranchDTO> list=branchQueryService.findByCriteria(criteria);
			if(list!=null) {
				for(BranchDTO branchDTO:list) {
					log.debug("branch DTO : "+branchDTO.toString());
					LeaveLevel = new LeaveLevelList();
					LeaveLevel.id=branchDTO.getId();
					LeaveLevel.value=branchDTO.getBranchName();	
					LeaveLevel.level=leaveSettingLevelName;
					leaveLevelList.add(LeaveLevel);
				}
			}
		}
		
		else if (leaveSettingLevelName.contains("Region")) {
			RegionCriteria criteria=new RegionCriteria();
			criteria.setCompanyId(cId);
			
			log.debug("RegionCriteria : "+criteria.toString());
			
			List<RegionDTO> list=regionQueryService.findByCriteria(criteria);
			if(list!=null) {
				for(RegionDTO regionDTO:list) {
					log.debug("RegionDTO : "+regionDTO.toString());
					LeaveLevel = new LeaveLevelList();
					LeaveLevel.id=regionDTO.getId();
					LeaveLevel.value=regionDTO.getRegionName();	
					LeaveLevel.level=leaveSettingLevelName;
					leaveLevelList.add(LeaveLevel);
				}
			}
		}
		else if (leaveSettingLevelName.contains("Company")) {			
			Optional<CompanyDTO> company=companyService.findOne(companyId);
			if(company!=null && !company.isEmpty()) {
				log.debug("CompanyDTO : "+company.toString());
				LeaveLevel = new LeaveLevelList();
				LeaveLevel.id=company.get().getId();
				LeaveLevel.value=company.get().getCompanyName();	
				LeaveLevel.level=leaveSettingLevelName;
				leaveLevelList.add(LeaveLevel);
			}
		}

		return ResponseEntity.ok().body(leaveLevelList);
	}

}
