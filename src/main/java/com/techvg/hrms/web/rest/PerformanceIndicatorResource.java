package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.PerformanceIndicatorRepository;
import com.techvg.hrms.service.PerformanceIndicatorQueryService;
import com.techvg.hrms.service.PerformanceIndicatorService;
import com.techvg.hrms.service.criteria.PerformanceIndicatorCriteria;
import com.techvg.hrms.service.dto.PerformanceIndicatorDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.PerformanceIndicator}.
 */
@RestController
@RequestMapping("/api")
public class PerformanceIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceIndicatorResource.class);

    private static final String ENTITY_NAME = "performanceIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceIndicatorService performanceIndicatorService;

    private final PerformanceIndicatorRepository performanceIndicatorRepository;

    private final PerformanceIndicatorQueryService performanceIndicatorQueryService;

    public PerformanceIndicatorResource(
        PerformanceIndicatorService performanceIndicatorService,
        PerformanceIndicatorRepository performanceIndicatorRepository,
        PerformanceIndicatorQueryService performanceIndicatorQueryService
    ) {
        this.performanceIndicatorService = performanceIndicatorService;
        this.performanceIndicatorRepository = performanceIndicatorRepository;
        this.performanceIndicatorQueryService = performanceIndicatorQueryService;
    }

    /**
     * {@code POST  /performance-indicators} : Create a new performanceIndicator.
     *
     * @param performanceIndicatorDTO the performanceIndicatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new performanceIndicatorDTO, or with status {@code 400 (Bad Request)} if the performanceIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/performance-indicators")
    public ResponseEntity<PerformanceIndicatorDTO> createPerformanceIndicator(@RequestBody PerformanceIndicatorDTO performanceIndicatorDTO)
        throws URISyntaxException {
        log.debug("REST request to save PerformanceIndicator : {}", performanceIndicatorDTO);
        if (performanceIndicatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new performanceIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerformanceIndicatorDTO result = performanceIndicatorService.save(performanceIndicatorDTO);
        return ResponseEntity
            .created(new URI("/api/performance-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /performance-indicators/:id} : Updates an existing performanceIndicator.
     *
     * @param id the id of the performanceIndicatorDTO to save.
     * @param performanceIndicatorDTO the performanceIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the performanceIndicatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the performanceIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/performance-indicators/{id}")
    public ResponseEntity<PerformanceIndicatorDTO> updatePerformanceIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceIndicatorDTO performanceIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PerformanceIndicator : {}, {}", id, performanceIndicatorDTO);
        if (performanceIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PerformanceIndicatorDTO result = performanceIndicatorService.update(performanceIndicatorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceIndicatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /performance-indicators/:id} : Partial updates given fields of an existing performanceIndicator, field will ignore if it is null
     *
     * @param id the id of the performanceIndicatorDTO to save.
     * @param performanceIndicatorDTO the performanceIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated performanceIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the performanceIndicatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the performanceIndicatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the performanceIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/performance-indicators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerformanceIndicatorDTO> partialUpdatePerformanceIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerformanceIndicatorDTO performanceIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerformanceIndicator partially : {}, {}", id, performanceIndicatorDTO);
        if (performanceIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, performanceIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!performanceIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerformanceIndicatorDTO> result = performanceIndicatorService.partialUpdate(performanceIndicatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceIndicatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-indicators} : get all the performanceIndicators.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of performanceIndicators in body.
     */
    @GetMapping("/performance-indicators")
    public ResponseEntity<List<PerformanceIndicatorDTO>> getAllPerformanceIndicators(
        PerformanceIndicatorCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerformanceIndicators by criteria: {}", criteria);
        Page<PerformanceIndicatorDTO> page = performanceIndicatorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-indicators/count} : count all the performanceIndicators.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/performance-indicators/count")
    public ResponseEntity<Long> countPerformanceIndicators(PerformanceIndicatorCriteria criteria) {
        log.debug("REST request to count PerformanceIndicators by criteria: {}", criteria);
        return ResponseEntity.ok().body(performanceIndicatorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-indicators/:id} : get the "id" performanceIndicator.
     *
     * @param id the id of the performanceIndicatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the performanceIndicatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/performance-indicators/{id}")
    public ResponseEntity<PerformanceIndicatorDTO> getPerformanceIndicator(@PathVariable Long id) {
        log.debug("REST request to get PerformanceIndicator : {}", id);
        Optional<PerformanceIndicatorDTO> performanceIndicatorDTO = performanceIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceIndicatorDTO);
    }

    /**
     * {@code DELETE  /performance-indicators/:id} : delete the "id" performanceIndicator.
     *
     * @param id the id of the performanceIndicatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/performance-indicators/{id}")
    public ResponseEntity<Void> deletePerformanceIndicator(@PathVariable Long id) {
        log.debug("REST request to delete PerformanceIndicator : {}", id);
        performanceIndicatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
