package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.EmployeeExemptionRepository;
import com.techvg.hrms.service.EmployeeExemptionQueryService;
import com.techvg.hrms.service.EmployeeExemptionService;
import com.techvg.hrms.service.criteria.EmployeeExemptionCriteria;
import com.techvg.hrms.service.dto.EmployeeExemptionDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.EmployeeExemption}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeExemptionResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeExemptionResource.class);

    private static final String ENTITY_NAME = "employeeExemption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeExemptionService employeeExemptionService;

    private final EmployeeExemptionRepository employeeExemptionRepository;

    private final EmployeeExemptionQueryService employeeExemptionQueryService;

    public EmployeeExemptionResource(
        EmployeeExemptionService employeeExemptionService,
        EmployeeExemptionRepository employeeExemptionRepository,
        EmployeeExemptionQueryService employeeExemptionQueryService
    ) {
        this.employeeExemptionService = employeeExemptionService;
        this.employeeExemptionRepository = employeeExemptionRepository;
        this.employeeExemptionQueryService = employeeExemptionQueryService;
    }

    /**
     * {@code POST  /employee-exemptions} : Create a new employeeExemption.
     *
     * @param employeeExemptionDTO the employeeExemptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeExemptionDTO, or with status {@code 400 (Bad Request)} if the employeeExemption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-exemptions")
    public ResponseEntity<EmployeeExemptionDTO> createEmployeeExemption(@RequestBody EmployeeExemptionDTO employeeExemptionDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeExemption : {}", employeeExemptionDTO);
        if (employeeExemptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeExemption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeExemptionDTO result = employeeExemptionService.save(employeeExemptionDTO);
        return ResponseEntity
            .created(new URI("/api/employee-exemptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-exemptions/:id} : Updates an existing employeeExemption.
     *
     * @param id the id of the employeeExemptionDTO to save.
     * @param employeeExemptionDTO the employeeExemptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeExemptionDTO,
     * or with status {@code 400 (Bad Request)} if the employeeExemptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeExemptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-exemptions/{id}")
    public ResponseEntity<EmployeeExemptionDTO> updateEmployeeExemption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeExemptionDTO employeeExemptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeExemption : {}, {}", id, employeeExemptionDTO);
        if (employeeExemptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeExemptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeExemptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeExemptionDTO result = employeeExemptionService.update(employeeExemptionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeExemptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-exemptions/:id} : Partial updates given fields of an existing employeeExemption, field will ignore if it is null
     *
     * @param id the id of the employeeExemptionDTO to save.
     * @param employeeExemptionDTO the employeeExemptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeExemptionDTO,
     * or with status {@code 400 (Bad Request)} if the employeeExemptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeExemptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeExemptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-exemptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeExemptionDTO> partialUpdateEmployeeExemption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeExemptionDTO employeeExemptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeExemption partially : {}, {}", id, employeeExemptionDTO);
        if (employeeExemptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeExemptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeExemptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeExemptionDTO> result = employeeExemptionService.partialUpdate(employeeExemptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeExemptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-exemptions} : get all the employeeExemptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeExemptions in body.
     */
    @GetMapping("/employee-exemptions")
    public ResponseEntity<List<EmployeeExemptionDTO>> getAllEmployeeExemptions(
        EmployeeExemptionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeExemptions by criteria: {}", criteria);
        Page<EmployeeExemptionDTO> page = employeeExemptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-exemptions/count} : count all the employeeExemptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-exemptions/count")
    public ResponseEntity<Long> countEmployeeExemptions(EmployeeExemptionCriteria criteria) {
        log.debug("REST request to count EmployeeExemptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeExemptionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-exemptions/:id} : get the "id" employeeExemption.
     *
     * @param id the id of the employeeExemptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeExemptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-exemptions/{id}")
    public ResponseEntity<EmployeeExemptionDTO> getEmployeeExemption(@PathVariable Long id) {
        log.debug("REST request to get EmployeeExemption : {}", id);
        Optional<EmployeeExemptionDTO> employeeExemptionDTO = employeeExemptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeExemptionDTO);
    }

    /**
     * {@code DELETE  /employee-exemptions/:id} : delete the "id" employeeExemption.
     *
     * @param id the id of the employeeExemptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-exemptions/{id}")
    public ResponseEntity<Void> deleteEmployeeExemption(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeExemption : {}", id);
        employeeExemptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
