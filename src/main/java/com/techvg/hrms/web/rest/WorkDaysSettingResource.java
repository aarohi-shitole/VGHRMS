package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.WorkDaysSettingRepository;
import com.techvg.hrms.service.WorkDaysSettingQueryService;
import com.techvg.hrms.service.WorkDaysSettingService;
import com.techvg.hrms.service.criteria.WorkDaysSettingCriteria;
import com.techvg.hrms.service.dto.WorkDaysSettingDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.WorkDaysSetting}.
 */
@RestController
@RequestMapping("/api")
public class WorkDaysSettingResource {

    private final Logger log = LoggerFactory.getLogger(WorkDaysSettingResource.class);

    private static final String ENTITY_NAME = "workDaysSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkDaysSettingService workDaysSettingService;

    private final WorkDaysSettingRepository workDaysSettingRepository;

    private final WorkDaysSettingQueryService workDaysSettingQueryService;

    public WorkDaysSettingResource(
        WorkDaysSettingService workDaysSettingService,
        WorkDaysSettingRepository workDaysSettingRepository,
        WorkDaysSettingQueryService workDaysSettingQueryService
    ) {
        this.workDaysSettingService = workDaysSettingService;
        this.workDaysSettingRepository = workDaysSettingRepository;
        this.workDaysSettingQueryService = workDaysSettingQueryService;
    }

    /**
     * {@code POST  /work-days-settings} : Create a new workDaysSetting.
     *
     * @param workDaysSettingDTO the workDaysSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workDaysSettingDTO, or with status {@code 400 (Bad Request)} if the workDaysSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-days-settings")
    public ResponseEntity<WorkDaysSettingDTO> createWorkDaysSetting(@RequestBody WorkDaysSettingDTO workDaysSettingDTO)
        throws URISyntaxException {
        log.debug("REST request to save WorkDaysSetting : {}", workDaysSettingDTO);
        if (workDaysSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new workDaysSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkDaysSettingDTO result = workDaysSettingService.save(workDaysSettingDTO);
        return ResponseEntity
            .created(new URI("/api/work-days-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-days-settings/:id} : Updates an existing workDaysSetting.
     *
     * @param id the id of the workDaysSettingDTO to save.
     * @param workDaysSettingDTO the workDaysSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workDaysSettingDTO,
     * or with status {@code 400 (Bad Request)} if the workDaysSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workDaysSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-days-settings/{id}")
    public ResponseEntity<WorkDaysSettingDTO> updateWorkDaysSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkDaysSettingDTO workDaysSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkDaysSetting : {}, {}", id, workDaysSettingDTO);
        if (workDaysSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workDaysSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workDaysSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkDaysSettingDTO result = workDaysSettingService.update(workDaysSettingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workDaysSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-days-settings/:id} : Partial updates given fields of an existing workDaysSetting, field will ignore if it is null
     *
     * @param id the id of the workDaysSettingDTO to save.
     * @param workDaysSettingDTO the workDaysSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workDaysSettingDTO,
     * or with status {@code 400 (Bad Request)} if the workDaysSettingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workDaysSettingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workDaysSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-days-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkDaysSettingDTO> partialUpdateWorkDaysSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkDaysSettingDTO workDaysSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkDaysSetting partially : {}, {}", id, workDaysSettingDTO);
        if (workDaysSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workDaysSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workDaysSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkDaysSettingDTO> result = workDaysSettingService.partialUpdate(workDaysSettingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workDaysSettingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /work-days-settings} : get all the workDaysSettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workDaysSettings in body.
     */
    @GetMapping("/work-days-settings")
    public ResponseEntity<List<WorkDaysSettingDTO>> getAllWorkDaysSettings(
        WorkDaysSettingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkDaysSettings by criteria: {}", criteria);
        Page<WorkDaysSettingDTO> page = workDaysSettingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-days-settings/count} : count all the workDaysSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-days-settings/count")
    public ResponseEntity<Long> countWorkDaysSettings(WorkDaysSettingCriteria criteria) {
        log.debug("REST request to count WorkDaysSettings by criteria: {}", criteria);
        return ResponseEntity.ok().body(workDaysSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-days-settings/:id} : get the "id" workDaysSetting.
     *
     * @param id the id of the workDaysSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workDaysSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-days-settings/{id}")
    public ResponseEntity<WorkDaysSettingDTO> getWorkDaysSetting(@PathVariable Long id) {
        log.debug("REST request to get WorkDaysSetting : {}", id);
        Optional<WorkDaysSettingDTO> workDaysSettingDTO = workDaysSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workDaysSettingDTO);
    }

    /**
     * {@code DELETE  /work-days-settings/:id} : delete the "id" workDaysSetting.
     *
     * @param id the id of the workDaysSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-days-settings/{id}")
    public ResponseEntity<Void> deleteWorkDaysSetting(@PathVariable Long id) {
        log.debug("REST request to delete WorkDaysSetting : {}", id);
        workDaysSettingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
