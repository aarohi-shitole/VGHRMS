package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AppraisalEvaluationParameterRepository;
import com.techvg.hrms.service.AppraisalEvaluationParameterQueryService;
import com.techvg.hrms.service.AppraisalEvaluationParameterService;
import com.techvg.hrms.service.criteria.AppraisalEvaluationParameterCriteria;
import com.techvg.hrms.service.dto.AppraisalEvaluationParameterDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AppraisalEvaluationParameter}.
 */
@RestController
@RequestMapping("/api")
public class AppraisalEvaluationParameterResource {

    private final Logger log = LoggerFactory.getLogger(AppraisalEvaluationParameterResource.class);

    private static final String ENTITY_NAME = "appraisalEvaluationParameter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppraisalEvaluationParameterService appraisalEvaluationParameterService;

    private final AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository;

    private final AppraisalEvaluationParameterQueryService appraisalEvaluationParameterQueryService;

    public AppraisalEvaluationParameterResource(
        AppraisalEvaluationParameterService appraisalEvaluationParameterService,
        AppraisalEvaluationParameterRepository appraisalEvaluationParameterRepository,
        AppraisalEvaluationParameterQueryService appraisalEvaluationParameterQueryService
    ) {
        this.appraisalEvaluationParameterService = appraisalEvaluationParameterService;
        this.appraisalEvaluationParameterRepository = appraisalEvaluationParameterRepository;
        this.appraisalEvaluationParameterQueryService = appraisalEvaluationParameterQueryService;
    }

    /**
     * {@code POST  /appraisal-evaluation-parameters} : Create a new appraisalEvaluationParameter.
     *
     * @param appraisalEvaluationParameterDTO the appraisalEvaluationParameterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appraisalEvaluationParameterDTO, or with status {@code 400 (Bad Request)} if the appraisalEvaluationParameter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appraisal-evaluation-parameters")
    public ResponseEntity<AppraisalEvaluationParameterDTO> createAppraisalEvaluationParameter(
        @RequestBody AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AppraisalEvaluationParameter : {}", appraisalEvaluationParameterDTO);
        if (appraisalEvaluationParameterDTO.getId() != null) {
            throw new BadRequestAlertException("A new appraisalEvaluationParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppraisalEvaluationParameterDTO result = appraisalEvaluationParameterService.save(appraisalEvaluationParameterDTO);
        return ResponseEntity
            .created(new URI("/api/appraisal-evaluation-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appraisal-evaluation-parameters/:id} : Updates an existing appraisalEvaluationParameter.
     *
     * @param id the id of the appraisalEvaluationParameterDTO to save.
     * @param appraisalEvaluationParameterDTO the appraisalEvaluationParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalEvaluationParameterDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalEvaluationParameterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appraisalEvaluationParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appraisal-evaluation-parameters/{id}")
    public ResponseEntity<AppraisalEvaluationParameterDTO> updateAppraisalEvaluationParameter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppraisalEvaluationParameter : {}, {}", id, appraisalEvaluationParameterDTO);
        if (appraisalEvaluationParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalEvaluationParameterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalEvaluationParameterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppraisalEvaluationParameterDTO result = appraisalEvaluationParameterService.update(appraisalEvaluationParameterDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalEvaluationParameterDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /appraisal-evaluation-parameters/:id} : Partial updates given fields of an existing appraisalEvaluationParameter, field will ignore if it is null
     *
     * @param id the id of the appraisalEvaluationParameterDTO to save.
     * @param appraisalEvaluationParameterDTO the appraisalEvaluationParameterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalEvaluationParameterDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalEvaluationParameterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appraisalEvaluationParameterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appraisalEvaluationParameterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appraisal-evaluation-parameters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppraisalEvaluationParameterDTO> partialUpdateAppraisalEvaluationParameter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalEvaluationParameterDTO appraisalEvaluationParameterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppraisalEvaluationParameter partially : {}, {}", id, appraisalEvaluationParameterDTO);
        if (appraisalEvaluationParameterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalEvaluationParameterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalEvaluationParameterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppraisalEvaluationParameterDTO> result = appraisalEvaluationParameterService.partialUpdate(
            appraisalEvaluationParameterDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalEvaluationParameterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appraisal-evaluation-parameters} : get all the appraisalEvaluationParameters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appraisalEvaluationParameters in body.
     */
    @GetMapping("/appraisal-evaluation-parameters")
    public ResponseEntity<List<AppraisalEvaluationParameterDTO>> getAllAppraisalEvaluationParameters(
        AppraisalEvaluationParameterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AppraisalEvaluationParameters by criteria: {}", criteria);
        Page<AppraisalEvaluationParameterDTO> page = appraisalEvaluationParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appraisal-evaluation-parameters/count} : count all the appraisalEvaluationParameters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/appraisal-evaluation-parameters/count")
    public ResponseEntity<Long> countAppraisalEvaluationParameters(AppraisalEvaluationParameterCriteria criteria) {
        log.debug("REST request to count AppraisalEvaluationParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(appraisalEvaluationParameterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appraisal-evaluation-parameters/:id} : get the "id" appraisalEvaluationParameter.
     *
     * @param id the id of the appraisalEvaluationParameterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appraisalEvaluationParameterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appraisal-evaluation-parameters/{id}")
    public ResponseEntity<AppraisalEvaluationParameterDTO> getAppraisalEvaluationParameter(@PathVariable Long id) {
        log.debug("REST request to get AppraisalEvaluationParameter : {}", id);
        Optional<AppraisalEvaluationParameterDTO> appraisalEvaluationParameterDTO = appraisalEvaluationParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appraisalEvaluationParameterDTO);
    }

    /**
     * {@code DELETE  /appraisal-evaluation-parameters/:id} : delete the "id" appraisalEvaluationParameter.
     *
     * @param id the id of the appraisalEvaluationParameterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appraisal-evaluation-parameters/{id}")
    public ResponseEntity<Void> deleteAppraisalEvaluationParameter(@PathVariable Long id) {
        log.debug("REST request to delete AppraisalEvaluationParameter : {}", id);
        appraisalEvaluationParameterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
