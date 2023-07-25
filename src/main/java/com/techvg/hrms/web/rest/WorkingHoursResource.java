package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.WorkingHoursRepository;
import com.techvg.hrms.service.WorkingHoursQueryService;
import com.techvg.hrms.service.WorkingHoursService;
import com.techvg.hrms.service.criteria.WorkingHoursCriteria;
import com.techvg.hrms.service.dto.WorkingHoursDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.WorkingHours}.
 */
@RestController
@RequestMapping("/api")
public class WorkingHoursResource {

    private final Logger log = LoggerFactory.getLogger(WorkingHoursResource.class);

    private static final String ENTITY_NAME = "workingHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingHoursService workingHoursService;

    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursQueryService workingHoursQueryService;

    public WorkingHoursResource(
        WorkingHoursService workingHoursService,
        WorkingHoursRepository workingHoursRepository,
        WorkingHoursQueryService workingHoursQueryService
    ) {
        this.workingHoursService = workingHoursService;
        this.workingHoursRepository = workingHoursRepository;
        this.workingHoursQueryService = workingHoursQueryService;
    }

    /**
     * {@code POST  /working-hours} : Create a new workingHours.
     *
     * @param workingHoursDTO the workingHoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingHoursDTO, or with status {@code 400 (Bad Request)} if the workingHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-hours")
    public ResponseEntity<WorkingHoursDTO> createWorkingHours(@RequestBody WorkingHoursDTO workingHoursDTO) throws URISyntaxException {
        log.debug("REST request to save WorkingHours : {}", workingHoursDTO);
        if (workingHoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingHoursDTO result = workingHoursService.save(workingHoursDTO);
        return ResponseEntity
            .created(new URI("/api/working-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /working-hours/:id} : Updates an existing workingHours.
     *
     * @param id the id of the workingHoursDTO to save.
     * @param workingHoursDTO the workingHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingHoursDTO,
     * or with status {@code 400 (Bad Request)} if the workingHoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-hours/{id}")
    public ResponseEntity<WorkingHoursDTO> updateWorkingHours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkingHoursDTO workingHoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkingHours : {}, {}", id, workingHoursDTO);
        if (workingHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workingHoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingHoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkingHoursDTO result = workingHoursService.update(workingHoursDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingHoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /working-hours/:id} : Partial updates given fields of an existing workingHours, field will ignore if it is null
     *
     * @param id the id of the workingHoursDTO to save.
     * @param workingHoursDTO the workingHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingHoursDTO,
     * or with status {@code 400 (Bad Request)} if the workingHoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workingHoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workingHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/working-hours/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkingHoursDTO> partialUpdateWorkingHours(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkingHoursDTO workingHoursDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkingHours partially : {}, {}", id, workingHoursDTO);
        if (workingHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workingHoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingHoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkingHoursDTO> result = workingHoursService.partialUpdate(workingHoursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingHoursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /working-hours} : get all the workingHours.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingHours in body.
     */
    @GetMapping("/working-hours")
    public ResponseEntity<List<WorkingHoursDTO>> getAllWorkingHours(
        WorkingHoursCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkingHours by criteria: {}", criteria);
        Page<WorkingHoursDTO> page = workingHoursQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-hours/count} : count all the workingHours.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/working-hours/count")
    public ResponseEntity<Long> countWorkingHours(WorkingHoursCriteria criteria) {
        log.debug("REST request to count WorkingHours by criteria: {}", criteria);
        return ResponseEntity.ok().body(workingHoursQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /working-hours/:id} : get the "id" workingHours.
     *
     * @param id the id of the workingHoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingHoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-hours/{id}")
    public ResponseEntity<WorkingHoursDTO> getWorkingHours(@PathVariable Long id) {
        log.debug("REST request to get WorkingHours : {}", id);
        Optional<WorkingHoursDTO> workingHoursDTO = workingHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingHoursDTO);
    }

    /**
     * {@code DELETE  /working-hours/:id} : delete the "id" workingHours.
     *
     * @param id the id of the workingHoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-hours/{id}")
    public ResponseEntity<Void> deleteWorkingHours(@PathVariable Long id) {
        log.debug("REST request to delete WorkingHours : {}", id);
        workingHoursService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
