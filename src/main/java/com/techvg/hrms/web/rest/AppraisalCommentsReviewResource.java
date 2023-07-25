package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AppraisalCommentsReviewRepository;
import com.techvg.hrms.service.AppraisalCommentsReviewQueryService;
import com.techvg.hrms.service.AppraisalCommentsReviewService;
import com.techvg.hrms.service.criteria.AppraisalCommentsReviewCriteria;
import com.techvg.hrms.service.dto.AppraisalCommentsReviewDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AppraisalCommentsReview}.
 */
@RestController
@RequestMapping("/api")
public class AppraisalCommentsReviewResource {

    private final Logger log = LoggerFactory.getLogger(AppraisalCommentsReviewResource.class);

    private static final String ENTITY_NAME = "appraisalCommentsReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppraisalCommentsReviewService appraisalCommentsReviewService;

    private final AppraisalCommentsReviewRepository appraisalCommentsReviewRepository;

    private final AppraisalCommentsReviewQueryService appraisalCommentsReviewQueryService;

    public AppraisalCommentsReviewResource(
        AppraisalCommentsReviewService appraisalCommentsReviewService,
        AppraisalCommentsReviewRepository appraisalCommentsReviewRepository,
        AppraisalCommentsReviewQueryService appraisalCommentsReviewQueryService
    ) {
        this.appraisalCommentsReviewService = appraisalCommentsReviewService;
        this.appraisalCommentsReviewRepository = appraisalCommentsReviewRepository;
        this.appraisalCommentsReviewQueryService = appraisalCommentsReviewQueryService;
    }

    /**
     * {@code POST  /appraisal-comments-reviews} : Create a new appraisalCommentsReview.
     *
     * @param appraisalCommentsReviewDTO the appraisalCommentsReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appraisalCommentsReviewDTO, or with status {@code 400 (Bad Request)} if the appraisalCommentsReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appraisal-comments-reviews")
    public ResponseEntity<AppraisalCommentsReviewDTO> createAppraisalCommentsReview(
        @RequestBody AppraisalCommentsReviewDTO appraisalCommentsReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AppraisalCommentsReview : {}", appraisalCommentsReviewDTO);
        if (appraisalCommentsReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new appraisalCommentsReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppraisalCommentsReviewDTO result = appraisalCommentsReviewService.save(appraisalCommentsReviewDTO);
        return ResponseEntity
            .created(new URI("/api/appraisal-comments-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appraisal-comments-reviews/:id} : Updates an existing appraisalCommentsReview.
     *
     * @param id the id of the appraisalCommentsReviewDTO to save.
     * @param appraisalCommentsReviewDTO the appraisalCommentsReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalCommentsReviewDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalCommentsReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appraisalCommentsReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appraisal-comments-reviews/{id}")
    public ResponseEntity<AppraisalCommentsReviewDTO> updateAppraisalCommentsReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalCommentsReviewDTO appraisalCommentsReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppraisalCommentsReview : {}, {}", id, appraisalCommentsReviewDTO);
        if (appraisalCommentsReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalCommentsReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalCommentsReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppraisalCommentsReviewDTO result = appraisalCommentsReviewService.update(appraisalCommentsReviewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalCommentsReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /appraisal-comments-reviews/:id} : Partial updates given fields of an existing appraisalCommentsReview, field will ignore if it is null
     *
     * @param id the id of the appraisalCommentsReviewDTO to save.
     * @param appraisalCommentsReviewDTO the appraisalCommentsReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalCommentsReviewDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalCommentsReviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appraisalCommentsReviewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appraisalCommentsReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appraisal-comments-reviews/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppraisalCommentsReviewDTO> partialUpdateAppraisalCommentsReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalCommentsReviewDTO appraisalCommentsReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppraisalCommentsReview partially : {}, {}", id, appraisalCommentsReviewDTO);
        if (appraisalCommentsReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalCommentsReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalCommentsReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppraisalCommentsReviewDTO> result = appraisalCommentsReviewService.partialUpdate(appraisalCommentsReviewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalCommentsReviewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appraisal-comments-reviews} : get all the appraisalCommentsReviews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appraisalCommentsReviews in body.
     */
    @GetMapping("/appraisal-comments-reviews")
    public ResponseEntity<List<AppraisalCommentsReviewDTO>> getAllAppraisalCommentsReviews(
        AppraisalCommentsReviewCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AppraisalCommentsReviews by criteria: {}", criteria);
        Page<AppraisalCommentsReviewDTO> page = appraisalCommentsReviewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appraisal-comments-reviews/count} : count all the appraisalCommentsReviews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/appraisal-comments-reviews/count")
    public ResponseEntity<Long> countAppraisalCommentsReviews(AppraisalCommentsReviewCriteria criteria) {
        log.debug("REST request to count AppraisalCommentsReviews by criteria: {}", criteria);
        return ResponseEntity.ok().body(appraisalCommentsReviewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appraisal-comments-reviews/:id} : get the "id" appraisalCommentsReview.
     *
     * @param id the id of the appraisalCommentsReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appraisalCommentsReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appraisal-comments-reviews/{id}")
    public ResponseEntity<AppraisalCommentsReviewDTO> getAppraisalCommentsReview(@PathVariable Long id) {
        log.debug("REST request to get AppraisalCommentsReview : {}", id);
        Optional<AppraisalCommentsReviewDTO> appraisalCommentsReviewDTO = appraisalCommentsReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appraisalCommentsReviewDTO);
    }

    /**
     * {@code DELETE  /appraisal-comments-reviews/:id} : delete the "id" appraisalCommentsReview.
     *
     * @param id the id of the appraisalCommentsReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appraisal-comments-reviews/{id}")
    public ResponseEntity<Void> deleteAppraisalCommentsReview(@PathVariable Long id) {
        log.debug("REST request to delete AppraisalCommentsReview : {}", id);
        appraisalCommentsReviewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
