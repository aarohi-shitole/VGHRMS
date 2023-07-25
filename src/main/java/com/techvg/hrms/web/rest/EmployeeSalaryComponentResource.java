package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.EmployeeSalaryComponentRepository;
import com.techvg.hrms.service.EmployeeSalaryComponentQueryService;
import com.techvg.hrms.service.EmployeeSalaryComponentService;
import com.techvg.hrms.service.criteria.EmployeeSalaryComponentCriteria;
import com.techvg.hrms.service.dto.EmployeeSalaryComponentDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.EmployeeSalaryComponent}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeSalaryComponentResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryComponentResource.class);

    private static final String ENTITY_NAME = "employeeSalaryComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeSalaryComponentService employeeSalaryComponentService;

    private final EmployeeSalaryComponentRepository employeeSalaryComponentRepository;

    private final EmployeeSalaryComponentQueryService employeeSalaryComponentQueryService;

    public EmployeeSalaryComponentResource(
        EmployeeSalaryComponentService employeeSalaryComponentService,
        EmployeeSalaryComponentRepository employeeSalaryComponentRepository,
        EmployeeSalaryComponentQueryService employeeSalaryComponentQueryService
    ) {
        this.employeeSalaryComponentService = employeeSalaryComponentService;
        this.employeeSalaryComponentRepository = employeeSalaryComponentRepository;
        this.employeeSalaryComponentQueryService = employeeSalaryComponentQueryService;
    }

    /**
     * {@code POST  /employee-salary-component} : Create a new employeeSalaryComponent.
     *
     * @param employeeSalaryComponentDTO the employeeSalaryComponentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeSalaryComponentDTO, or with status {@code 400 (Bad Request)} if the employeeSalaryComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-salary-component")
    public ResponseEntity<EmployeeSalaryComponentDTO> createEmployeeSalaryComponent(@RequestBody EmployeeSalaryComponentDTO employeeSalaryComponentDTO)
        throws URISyntaxException {
        log.debug("REST request to save employeeSalaryComponent : {}", employeeSalaryComponentDTO);
        if (employeeSalaryComponentDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeSalaryComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSalaryComponentDTO result = employeeSalaryComponentService.save(employeeSalaryComponentDTO);
        return ResponseEntity
            .created(new URI("/api/employee-salary-component/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-salary-component/:id} : Updates an existing employeeSalaryComponent.
     *
     * @param id the id of the employeeSalaryComponentDTO to save.
     * @param employeeSalaryComponentDTO the employeeSalaryComponentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSalaryComponentDTO,
     * or with status {@code 400 (Bad Request)} if the employeeSalaryComponentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeSalaryComponentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-salary-component/{id}")
    public ResponseEntity<EmployeeSalaryComponentDTO> updateEmployeeSalaryComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeSalaryComponentDTO employeeSalaryComponentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update employeeSalaryComponent : {}, {}", id, employeeSalaryComponentDTO);
        if (employeeSalaryComponentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeSalaryComponentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeSalaryComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeSalaryComponentDTO result = employeeSalaryComponentService.update(employeeSalaryComponentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeSalaryComponentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-salary-component/:id} : Partial updates given fields of an existing employeeSalaryComponent, field will ignore if it is null
     *
     * @param id the id of the employeeSalaryComponentDTO to save.
     * @param employeeSalaryComponentDTO the employeeSalaryComponentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeSalaryComponentDTO,
     * or with status {@code 400 (Bad Request)} if the employeeSalaryComponentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeSalaryComponentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeSalaryComponentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-salary-component/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeSalaryComponentDTO> partialUpdateEmployeeSalaryComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeSalaryComponentDTO employeeSalaryComponentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update employeeSalaryComponent partially : {}, {}", id, employeeSalaryComponentDTO);
        if (employeeSalaryComponentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeSalaryComponentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeSalaryComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeSalaryComponentDTO> result = employeeSalaryComponentService.partialUpdate(employeeSalaryComponentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeSalaryComponentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-salary-component} : get all the employeeSalaryComponent.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeSalaryComponent in body.
     */
    @GetMapping("/employee-salary-component")
    public ResponseEntity<List<EmployeeSalaryComponentDTO>> getAllEmployeeSalaryComponent(
        EmployeeSalaryComponentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get employeeSalaryComponent by criteria: {}", criteria);
        Page<EmployeeSalaryComponentDTO> page = employeeSalaryComponentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-salary-component/count} : count all the employeeSalaryComponent.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-salary-component/count")
    public ResponseEntity<Long> countEmployeeSalaryComponent(EmployeeSalaryComponentCriteria criteria) {
        log.debug("REST request to count employeeSalaryComponent by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeSalaryComponentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-salary-component/:id} : get the "id" employeeSalaryComponent.
     *
     * @param id the id of the employeeSalaryComponentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeSalaryComponentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-salary-component/{id}")
    public ResponseEntity<EmployeeSalaryComponentDTO> getEmployeeSalaryComponent(@PathVariable Long id) {
        log.debug("REST request to get employeeSalaryComponent : {}", id);
        Optional<EmployeeSalaryComponentDTO> employeeSalaryComponentDTO = employeeSalaryComponentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSalaryComponentDTO);
    }

    /**
     * {@code DELETE  /employee-salary-component/:id} : delete the "id" employeeSalaryComponent.
     *
     * @param id the id of the employeeSalaryComponentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-salary-component/{id}")
    public ResponseEntity<Void> deleteEmployeeSalaryComponent(@PathVariable Long id) {
        log.debug("REST request to delete employeeSalaryComponent : {}", id);
        employeeSalaryComponentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
