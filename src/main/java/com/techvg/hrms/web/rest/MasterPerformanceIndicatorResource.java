package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.MasterPerformanceIndicatorRepository;
import com.techvg.hrms.service.MasterPerformanceIndicatorQueryService;
import com.techvg.hrms.service.MasterPerformanceIndicatorService;
import com.techvg.hrms.service.criteria.MasterPerformanceIndicatorCriteria;
import com.techvg.hrms.service.dto.MasterPerformanceIndicatorDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.MasterPerformanceIndicator}.
 */
@RestController
@RequestMapping("/api")
public class MasterPerformanceIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(MasterPerformanceIndicatorResource.class);

    private static final String ENTITY_NAME = "masterPerformanceIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MasterPerformanceIndicatorService masterPerformanceIndicatorService;

    private final MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository;

    private final MasterPerformanceIndicatorQueryService masterPerformanceIndicatorQueryService;

    public MasterPerformanceIndicatorResource(
        MasterPerformanceIndicatorService masterPerformanceIndicatorService,
        MasterPerformanceIndicatorRepository masterPerformanceIndicatorRepository,
        MasterPerformanceIndicatorQueryService masterPerformanceIndicatorQueryService
    ) {
        this.masterPerformanceIndicatorService = masterPerformanceIndicatorService;
        this.masterPerformanceIndicatorRepository = masterPerformanceIndicatorRepository;
        this.masterPerformanceIndicatorQueryService = masterPerformanceIndicatorQueryService;
    }

    /**
     * {@code POST  /master-performance-indicators} : Create a new masterPerformanceIndicator.
     *
     * @param masterPerformanceIndicatorDTO the masterPerformanceIndicatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new masterPerformanceIndicatorDTO, or with status {@code 400 (Bad Request)} if the masterPerformanceIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/master-performance-indicators")
    public ResponseEntity<MasterPerformanceIndicatorDTO> createMasterPerformanceIndicator(
        @RequestBody MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MasterPerformanceIndicator : {}", masterPerformanceIndicatorDTO);
        if (masterPerformanceIndicatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new masterPerformanceIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MasterPerformanceIndicatorDTO result = masterPerformanceIndicatorService.save(masterPerformanceIndicatorDTO);
        return ResponseEntity
            .created(new URI("/api/master-performance-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /master-performance-indicators/:id} : Updates an existing masterPerformanceIndicator.
     *
     * @param id the id of the masterPerformanceIndicatorDTO to save.
     * @param masterPerformanceIndicatorDTO the masterPerformanceIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterPerformanceIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the masterPerformanceIndicatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the masterPerformanceIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/master-performance-indicators/{id}")
    public ResponseEntity<MasterPerformanceIndicatorDTO> updateMasterPerformanceIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MasterPerformanceIndicator : {}, {}", id, masterPerformanceIndicatorDTO);
        if (masterPerformanceIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, masterPerformanceIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!masterPerformanceIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MasterPerformanceIndicatorDTO result = masterPerformanceIndicatorService.update(masterPerformanceIndicatorDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterPerformanceIndicatorDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /master-performance-indicators/:id} : Partial updates given fields of an existing masterPerformanceIndicator, field will ignore if it is null
     *
     * @param id the id of the masterPerformanceIndicatorDTO to save.
     * @param masterPerformanceIndicatorDTO the masterPerformanceIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterPerformanceIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the masterPerformanceIndicatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the masterPerformanceIndicatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the masterPerformanceIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/master-performance-indicators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MasterPerformanceIndicatorDTO> partialUpdateMasterPerformanceIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MasterPerformanceIndicatorDTO masterPerformanceIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MasterPerformanceIndicator partially : {}, {}", id, masterPerformanceIndicatorDTO);
        if (masterPerformanceIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, masterPerformanceIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!masterPerformanceIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MasterPerformanceIndicatorDTO> result = masterPerformanceIndicatorService.partialUpdate(masterPerformanceIndicatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterPerformanceIndicatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /master-performance-indicators} : get all the masterPerformanceIndicators.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masterPerformanceIndicators in body.
     */
    @GetMapping("/master-performance-indicators")
    public ResponseEntity<List<MasterPerformanceIndicatorDTO>> getAllMasterPerformanceIndicators(
        MasterPerformanceIndicatorCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MasterPerformanceIndicators by criteria: {}", criteria);
        Page<MasterPerformanceIndicatorDTO> page = masterPerformanceIndicatorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /master-performance-indicators/count} : count all the masterPerformanceIndicators.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/master-performance-indicators/count")
    public ResponseEntity<Long> countMasterPerformanceIndicators(MasterPerformanceIndicatorCriteria criteria) {
        log.debug("REST request to count MasterPerformanceIndicators by criteria: {}", criteria);
        return ResponseEntity.ok().body(masterPerformanceIndicatorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /master-performance-indicators/:id} : get the "id" masterPerformanceIndicator.
     *
     * @param id the id of the masterPerformanceIndicatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the masterPerformanceIndicatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/master-performance-indicators/{id}")
    public ResponseEntity<MasterPerformanceIndicatorDTO> getMasterPerformanceIndicator(@PathVariable Long id) {
        log.debug("REST request to get MasterPerformanceIndicator : {}", id);
        Optional<MasterPerformanceIndicatorDTO> masterPerformanceIndicatorDTO = masterPerformanceIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(masterPerformanceIndicatorDTO);
    }

    /**
     * {@code DELETE  /master-performance-indicators/:id} : delete the "id" masterPerformanceIndicator.
     *
     * @param id the id of the masterPerformanceIndicatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/master-performance-indicators/{id}")
    public ResponseEntity<Void> deleteMasterPerformanceIndicator(@PathVariable Long id) {
        log.debug("REST request to delete MasterPerformanceIndicator : {}", id);
        masterPerformanceIndicatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
