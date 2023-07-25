package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AppraisalReviewRepository;
import com.techvg.hrms.service.AppraisalReviewHelper;
import com.techvg.hrms.service.AppraisalReviewQueryService;
import com.techvg.hrms.service.AppraisalReviewService;
import com.techvg.hrms.service.criteria.AppraisalReviewCriteria;
import com.techvg.hrms.service.dto.AppraisalReviewDTO;
import com.techvg.hrms.service.dto.PerformanceReviewDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AppraisalReview}.
 */
@RestController
@RequestMapping("/api")
public class AppraisalReviewResource {

    private final Logger log = LoggerFactory.getLogger(AppraisalReviewResource.class);

    private static final String ENTITY_NAME = "appraisalReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppraisalReviewService appraisalReviewService;

    private final AppraisalReviewRepository appraisalReviewRepository;

    private final AppraisalReviewQueryService appraisalReviewQueryService;
    
    @Autowired
   	private AppraisalReviewHelper appraisalReviewHelper;

    public AppraisalReviewResource(
        AppraisalReviewService appraisalReviewService,
        AppraisalReviewRepository appraisalReviewRepository,
        AppraisalReviewQueryService appraisalReviewQueryService
    ) {
        this.appraisalReviewService = appraisalReviewService;
        this.appraisalReviewRepository = appraisalReviewRepository;
        this.appraisalReviewQueryService = appraisalReviewQueryService;
    }

    /**
     * {@code POST  /appraisal-reviews} : Create a new appraisalReview.
     *
     * @param appraisalReviewDTO the appraisalReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appraisalReviewDTO, or with status {@code 400 (Bad Request)} if the appraisalReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appraisal-reviews")
    public ResponseEntity<AppraisalReviewDTO> createAppraisalReview(@RequestBody AppraisalReviewDTO appraisalReviewDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppraisalReview : {}", appraisalReviewDTO);
        if (appraisalReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new appraisalReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Boolean reviewExist = appraisalReviewHelper.checkReviewApplied(appraisalReviewDTO);

    	if(reviewExist) {
    		throw new BusinessRuleException("Appraisal Review request is not acepted", "Employee with id: " + appraisalReviewDTO.getEmployeId()+ " is already applied for appraisal review");
    	}

        AppraisalReviewDTO result = appraisalReviewService.save(appraisalReviewDTO);
        
        appraisalReviewHelper.createAppraisalReviewApproval(result);
        return ResponseEntity
            .created(new URI("/api/appraisal-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appraisal-reviews/:id} : Updates an existing appraisalReview.
     *
     * @param id the id of the appraisalReviewDTO to save.
     * @param appraisalReviewDTO the appraisalReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalReviewDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appraisalReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appraisal-reviews/{id}")
    public ResponseEntity<AppraisalReviewDTO> updateAppraisalReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalReviewDTO appraisalReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppraisalReview : {}, {}", id, appraisalReviewDTO);
        if (appraisalReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        if (appraisalReviewDTO.getAppraisalStatus().equalsIgnoreCase("Cancelled")) {
        	appraisalReviewDTO = appraisalReviewHelper.cancelAppraisalReviewForm(appraisalReviewDTO);
		}
        
        AppraisalReviewDTO result = appraisalReviewService.update(appraisalReviewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /appraisal-reviews/:id} : Partial updates given fields of an existing appraisalReview, field will ignore if it is null
     *
     * @param id the id of the appraisalReviewDTO to save.
     * @param appraisalReviewDTO the appraisalReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalReviewDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalReviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appraisalReviewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appraisalReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appraisal-reviews/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppraisalReviewDTO> partialUpdateAppraisalReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalReviewDTO appraisalReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppraisalReview partially : {}, {}", id, appraisalReviewDTO);
        if (appraisalReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppraisalReviewDTO> result = appraisalReviewService.partialUpdate(appraisalReviewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalReviewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appraisal-reviews} : get all the appraisalReviews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appraisalReviews in body.
     */
    @GetMapping("/appraisal-reviews")
    public ResponseEntity<List<AppraisalReviewDTO>> getAllAppraisalReviews(
        AppraisalReviewCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AppraisalReviews by criteria: {}", criteria);
        Page<AppraisalReviewDTO> page = appraisalReviewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appraisal-reviews/count} : count all the appraisalReviews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/appraisal-reviews/count")
    public ResponseEntity<Long> countAppraisalReviews(AppraisalReviewCriteria criteria) {
        log.debug("REST request to count AppraisalReviews by criteria: {}", criteria);
        return ResponseEntity.ok().body(appraisalReviewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appraisal-reviews/:id} : get the "id" appraisalReview.
     *
     * @param id the id of the appraisalReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appraisalReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appraisal-reviews/{id}")
    public ResponseEntity<AppraisalReviewDTO> getAppraisalReview(@PathVariable Long id) {
        log.debug("REST request to get AppraisalReview : {}", id);
        Optional<AppraisalReviewDTO> appraisalReviewDTO = appraisalReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appraisalReviewDTO);
    }

    /**
     * {@code DELETE  /appraisal-reviews/:id} : delete the "id" appraisalReview.
     *
     * @param id the id of the appraisalReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appraisal-reviews/{id}")
    public ResponseEntity<Void> deleteAppraisalReview(@PathVariable Long id) {
        log.debug("REST request to delete AppraisalReview : {}", id);
        appraisalReviewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    @GetMapping("/appraisal-review/approvar-list")
   	public ResponseEntity<HashMap<Long, Long>> getReviewApprovars(@RequestParam Long reviewId,
   			@RequestParam Long approvarEmpId) {
   		log.debug("REST request to get Resignation : {}", reviewId);
   		if (!appraisalReviewRepository.existsById(reviewId)) {
   			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
   		}

   		Optional<AppraisalReviewDTO> appraisalReviewDTO = appraisalReviewService.findOne(reviewId);

   		// Find the active approvar's for review application
   		HashMap<Long, Long> approvarListMap = appraisalReviewHelper.findApprovarList(appraisalReviewDTO.get().getEmployeId(),
   				approvarEmpId);

   		LinkedHashMap<Long, Long> approvarSortedMap = appraisalReviewHelper.getSortedMap(approvarListMap);

   		// Find passed approvar present in list
   		boolean isApprovar = appraisalReviewHelper.checkApprovarPresent(approvarEmpId, approvarListMap);
   		return ResponseEntity.ok(approvarSortedMap);
   	}
}
