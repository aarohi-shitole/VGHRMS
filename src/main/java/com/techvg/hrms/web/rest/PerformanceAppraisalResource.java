package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.PerformanceAppraisalRepository;
import com.techvg.hrms.service.PerformanceAppraisalQueryService;
import com.techvg.hrms.service.PerformanceAppraisalService;
import com.techvg.hrms.service.criteria.PerformanceAppraisalCriteria;
import com.techvg.hrms.service.dto.PerformanceAppraisalDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.PerformanceAppraisal}.
 */
@RestController
@RequestMapping("/api")
public class PerformanceAppraisalResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceAppraisalResource.class);

    private static final String ENTITY_NAME = "performanceAppraisal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceAppraisalService performanceAppraisalService;

    private final PerformanceAppraisalRepository performanceAppraisalRepository;

    private final PerformanceAppraisalQueryService performanceAppraisalQueryService;

    public PerformanceAppraisalResource(
        PerformanceAppraisalService performanceAppraisalService,
        PerformanceAppraisalRepository performanceAppraisalRepository,
        PerformanceAppraisalQueryService performanceAppraisalQueryService
    ) {
        this.performanceAppraisalService = performanceAppraisalService;
        this.performanceAppraisalRepository = performanceAppraisalRepository;
        this.performanceAppraisalQueryService = performanceAppraisalQueryService;
    }

    /**
     * {@code POST  /performance-appraisals} : Create a new performanceAppraisal.
     *
     * @param performanceAppraisalDTO the performanceAppraisalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new performanceAppraisalDTO, or with status {@code 400 (Bad Request)} if the performanceAppraisal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/performance-appraisals")
    public ResponseEntity<PerformanceAppraisalDTO> createPerformanceAppraisal(@RequestBody PerformanceAppraisalDTO performanceAppraisalDTO)
        throws URISyntaxException {
        log.debug("REST request to save PerformanceAppraisal : {}", performanceAppraisalDTO);
        if (performanceAppraisalDTO.getId() != null) {
            throw new BadRequestAlertException("A new performanceAppraisal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerformanceAppraisalDTO result = performanceAppraisalService.save(performanceAppraisalDTO);
        return ResponseEntity
            .created(new URI("/api/performance-appraisals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /performance-appraisals/:id} : Updates an existing performanceAppraisal.
     *
     * @param id the id of the performanceAppraisalDTO to save.
     * @param performanceAppraisalDTO the performanceAppraisalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceAppraisalDTO,
     * or with status {@code 400 (Bad Request)} if the performanceAppraisalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the performanceAppraisalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/performance-appraisals/{id}")
    public ResponseEntity<PerformanceAppraisalDTO> updatePerformanceAppraisal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceAppraisalDTO performanceAppraisalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PerformanceAppraisal : {}, {}", id, performanceAppraisalDTO);
        if (performanceAppraisalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceAppraisalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceAppraisalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PerformanceAppraisalDTO result = performanceAppraisalService.update(performanceAppraisalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceAppraisalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /performance-appraisals/:id} : Partial updates given fields of an existing performanceAppraisal, field will ignore if it is null
     *
     * @param id the id of the performanceAppraisalDTO to save.
     * @param performanceAppraisalDTO the performanceAppraisalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceAppraisalDTO,
     * or with status {@code 400 (Bad Request)} if the performanceAppraisalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the performanceAppraisalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the performanceAppraisalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/performance-appraisals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerformanceAppraisalDTO> partialUpdatePerformanceAppraisal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceAppraisalDTO performanceAppraisalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerformanceAppraisal partially : {}, {}", id, performanceAppraisalDTO);
        if (performanceAppraisalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceAppraisalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceAppraisalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerformanceAppraisalDTO> result = performanceAppraisalService.partialUpdate(performanceAppraisalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceAppraisalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-appraisals} : get all the performanceAppraisals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of performanceAppraisals in body.
     */
    @GetMapping("/performance-appraisals")
    public ResponseEntity<List<PerformanceAppraisalDTO>> getAllPerformanceAppraisals(
        PerformanceAppraisalCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerformanceAppraisals by criteria: {}", criteria);
        Page<PerformanceAppraisalDTO> page = performanceAppraisalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-appraisals/count} : count all the performanceAppraisals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/performance-appraisals/count")
    public ResponseEntity<Long> countPerformanceAppraisals(PerformanceAppraisalCriteria criteria) {
        log.debug("REST request to count PerformanceAppraisals by criteria: {}", criteria);
        return ResponseEntity.ok().body(performanceAppraisalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-appraisals/:id} : get the "id" performanceAppraisal.
     *
     * @param id the id of the performanceAppraisalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the performanceAppraisalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/performance-appraisals/{id}")
    public ResponseEntity<PerformanceAppraisalDTO> getPerformanceAppraisal(@PathVariable Long id) {
        log.debug("REST request to get PerformanceAppraisal : {}", id);
        Optional<PerformanceAppraisalDTO> performanceAppraisalDTO = performanceAppraisalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceAppraisalDTO);
    }

    /**
     * {@code DELETE  /performance-appraisals/:id} : delete the "id" performanceAppraisal.
     *
     * @param id the id of the performanceAppraisalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/performance-appraisals/{id}")
    public ResponseEntity<Void> deletePerformanceAppraisal(@PathVariable Long id) {
        log.debug("REST request to delete PerformanceAppraisal : {}", id);
        performanceAppraisalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
