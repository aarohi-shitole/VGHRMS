package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AppraisalEvaluationRepository;
import com.techvg.hrms.service.AppraisalEvaluationQueryService;
import com.techvg.hrms.service.AppraisalEvaluationService;
import com.techvg.hrms.service.criteria.AppraisalEvaluationCriteria;
import com.techvg.hrms.service.dto.AppraisalEvaluationDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AppraisalEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class AppraisalEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(AppraisalEvaluationResource.class);

    private static final String ENTITY_NAME = "appraisalEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppraisalEvaluationService appraisalEvaluationService;

    private final AppraisalEvaluationRepository appraisalEvaluationRepository;

    private final AppraisalEvaluationQueryService appraisalEvaluationQueryService;

    public AppraisalEvaluationResource(
        AppraisalEvaluationService appraisalEvaluationService,
        AppraisalEvaluationRepository appraisalEvaluationRepository,
        AppraisalEvaluationQueryService appraisalEvaluationQueryService
    ) {
        this.appraisalEvaluationService = appraisalEvaluationService;
        this.appraisalEvaluationRepository = appraisalEvaluationRepository;
        this.appraisalEvaluationQueryService = appraisalEvaluationQueryService;
    }

    /**
     * {@code POST  /appraisal-evaluations} : Create a new appraisalEvaluation.
     *
     * @param appraisalEvaluationDTO the appraisalEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appraisalEvaluationDTO, or with status {@code 400 (Bad Request)} if the appraisalEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appraisal-evaluations")
    public ResponseEntity<AppraisalEvaluationDTO> createAppraisalEvaluation(@RequestBody AppraisalEvaluationDTO appraisalEvaluationDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppraisalEvaluation : {}", appraisalEvaluationDTO);
        if (appraisalEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new appraisalEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppraisalEvaluationDTO result = appraisalEvaluationService.save(appraisalEvaluationDTO);
        return ResponseEntity
            .created(new URI("/api/appraisal-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appraisal-evaluations/:id} : Updates an existing appraisalEvaluation.
     *
     * @param id the id of the appraisalEvaluationDTO to save.
     * @param appraisalEvaluationDTO the appraisalEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalEvaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appraisalEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appraisal-evaluations/{id}")
    public ResponseEntity<AppraisalEvaluationDTO> updateAppraisalEvaluation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalEvaluationDTO appraisalEvaluationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppraisalEvaluation : {}, {}", id, appraisalEvaluationDTO);
        if (appraisalEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalEvaluationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalEvaluationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppraisalEvaluationDTO result = appraisalEvaluationService.update(appraisalEvaluationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /appraisal-evaluations/:id} : Partial updates given fields of an existing appraisalEvaluation, field will ignore if it is null
     *
     * @param id the id of the appraisalEvaluationDTO to save.
     * @param appraisalEvaluationDTO the appraisalEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalEvaluationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appraisalEvaluationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appraisalEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appraisal-evaluations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppraisalEvaluationDTO> partialUpdateAppraisalEvaluation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalEvaluationDTO appraisalEvaluationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppraisalEvaluation partially : {}, {}", id, appraisalEvaluationDTO);
        if (appraisalEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalEvaluationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalEvaluationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppraisalEvaluationDTO> result = appraisalEvaluationService.partialUpdate(appraisalEvaluationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalEvaluationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appraisal-evaluations} : get all the appraisalEvaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appraisalEvaluations in body.
     */
    @GetMapping("/appraisal-evaluations")
    public ResponseEntity<List<AppraisalEvaluationDTO>> getAllAppraisalEvaluations(
        AppraisalEvaluationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AppraisalEvaluations by criteria: {}", criteria);
        Page<AppraisalEvaluationDTO> page = appraisalEvaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appraisal-evaluations/count} : count all the appraisalEvaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/appraisal-evaluations/count")
    public ResponseEntity<Long> countAppraisalEvaluations(AppraisalEvaluationCriteria criteria) {
        log.debug("REST request to count AppraisalEvaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(appraisalEvaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appraisal-evaluations/:id} : get the "id" appraisalEvaluation.
     *
     * @param id the id of the appraisalEvaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appraisalEvaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appraisal-evaluations/{id}")
    public ResponseEntity<AppraisalEvaluationDTO> getAppraisalEvaluation(@PathVariable Long id) {
        log.debug("REST request to get AppraisalEvaluation : {}", id);
        Optional<AppraisalEvaluationDTO> appraisalEvaluationDTO = appraisalEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appraisalEvaluationDTO);
    }

    /**
     * {@code DELETE  /appraisal-evaluations/:id} : delete the "id" appraisalEvaluation.
     *
     * @param id the id of the appraisalEvaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appraisal-evaluations/{id}")
    public ResponseEntity<Void> deleteAppraisalEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete AppraisalEvaluation : {}", id);
        appraisalEvaluationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
