package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.OverTimeRepository;
import com.techvg.hrms.service.OverTimeQueryService;
import com.techvg.hrms.service.OverTimeService;
import com.techvg.hrms.service.criteria.OverTimeCriteria;
import com.techvg.hrms.service.dto.OverTimeDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.OverTime}.
 */
@RestController
@RequestMapping("/api")
public class OverTimeResource {

    private final Logger log = LoggerFactory.getLogger(OverTimeResource.class);

    private static final String ENTITY_NAME = "overTime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OverTimeService overTimeService;

    private final OverTimeRepository overTimeRepository;

    private final OverTimeQueryService overTimeQueryService;

    public OverTimeResource(
        OverTimeService overTimeService,
        OverTimeRepository overTimeRepository,
        OverTimeQueryService overTimeQueryService
    ) {
        this.overTimeService = overTimeService;
        this.overTimeRepository = overTimeRepository;
        this.overTimeQueryService = overTimeQueryService;
    }

    /**
     * {@code POST  /over-times} : Create a new overTime.
     *
     * @param overTimeDTO the overTimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new overTimeDTO, or with status {@code 400 (Bad Request)} if the overTime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/over-times")
    public ResponseEntity<OverTimeDTO> createOverTime(@RequestBody OverTimeDTO overTimeDTO) throws URISyntaxException {
        log.debug("REST request to save OverTime : {}", overTimeDTO);
        if (overTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new overTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OverTimeDTO result = overTimeService.save(overTimeDTO);
        return ResponseEntity
            .created(new URI("/api/over-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /over-times/:id} : Updates an existing overTime.
     *
     * @param id the id of the overTimeDTO to save.
     * @param overTimeDTO the overTimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overTimeDTO,
     * or with status {@code 400 (Bad Request)} if the overTimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the overTimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/over-times/{id}")
    public ResponseEntity<OverTimeDTO> updateOverTime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OverTimeDTO overTimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OverTime : {}, {}", id, overTimeDTO);
        if (overTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overTimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overTimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OverTimeDTO result = overTimeService.update(overTimeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overTimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /over-times/:id} : Partial updates given fields of an existing overTime, field will ignore if it is null
     *
     * @param id the id of the overTimeDTO to save.
     * @param overTimeDTO the overTimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated overTimeDTO,
     * or with status {@code 400 (Bad Request)} if the overTimeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the overTimeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the overTimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/over-times/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OverTimeDTO> partialUpdateOverTime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OverTimeDTO overTimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OverTime partially : {}, {}", id, overTimeDTO);
        if (overTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, overTimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!overTimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OverTimeDTO> result = overTimeService.partialUpdate(overTimeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, overTimeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /over-times} : get all the overTimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of overTimes in body.
     */
    @GetMapping("/over-times")
    public ResponseEntity<List<OverTimeDTO>> getAllOverTimes(
        OverTimeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OverTimes by criteria: {}", criteria);
        Page<OverTimeDTO> page = overTimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /over-times/count} : count all the overTimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/over-times/count")
    public ResponseEntity<Long> countOverTimes(OverTimeCriteria criteria) {
        log.debug("REST request to count OverTimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(overTimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /over-times/:id} : get the "id" overTime.
     *
     * @param id the id of the overTimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the overTimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/over-times/{id}")
    public ResponseEntity<OverTimeDTO> getOverTime(@PathVariable Long id) {
        log.debug("REST request to get OverTime : {}", id);
        Optional<OverTimeDTO> overTimeDTO = overTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(overTimeDTO);
    }

    /**
     * {@code DELETE  /over-times/:id} : delete the "id" overTime.
     *
     * @param id the id of the overTimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/over-times/{id}")
    public ResponseEntity<Void> deleteOverTime(@PathVariable Long id) {
        log.debug("REST request to delete OverTime : {}", id);
        overTimeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
