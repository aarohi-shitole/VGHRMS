package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.EmployeeGoalsReviewRepository;
import com.techvg.hrms.service.EmployeeGoalsReviewQueryService;
import com.techvg.hrms.service.EmployeeGoalsReviewService;
import com.techvg.hrms.service.criteria.EmployeeGoalsReviewCriteria;
import com.techvg.hrms.service.dto.EmployeeGoalsReviewDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.EmployeeGoalsReview}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeGoalsReviewResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeGoalsReviewResource.class);

    private static final String ENTITY_NAME = "employeeGoalsReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeGoalsReviewService employeeGoalsReviewService;

    private final EmployeeGoalsReviewRepository employeeGoalsReviewRepository;

    private final EmployeeGoalsReviewQueryService employeeGoalsReviewQueryService;

    public EmployeeGoalsReviewResource(
        EmployeeGoalsReviewService employeeGoalsReviewService,
        EmployeeGoalsReviewRepository employeeGoalsReviewRepository,
        EmployeeGoalsReviewQueryService employeeGoalsReviewQueryService
    ) {
        this.employeeGoalsReviewService = employeeGoalsReviewService;
        this.employeeGoalsReviewRepository = employeeGoalsReviewRepository;
        this.employeeGoalsReviewQueryService = employeeGoalsReviewQueryService;
    }

    /**
     * {@code POST  /employee-goals-reviews} : Create a new employeeGoalsReview.
     *
     * @param employeeGoalsReviewDTO the employeeGoalsReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeGoalsReviewDTO, or with status {@code 400 (Bad Request)} if the employeeGoalsReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-goals-reviews")
    public ResponseEntity<EmployeeGoalsReviewDTO> createEmployeeGoalsReview(@RequestBody EmployeeGoalsReviewDTO employeeGoalsReviewDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeGoalsReview : {}", employeeGoalsReviewDTO);
        if (employeeGoalsReviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeGoalsReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeGoalsReviewDTO result = employeeGoalsReviewService.save(employeeGoalsReviewDTO);
        return ResponseEntity
            .created(new URI("/api/employee-goals-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-goals-reviews/:id} : Updates an existing employeeGoalsReview.
     *
     * @param id the id of the employeeGoalsReviewDTO to save.
     * @param employeeGoalsReviewDTO the employeeGoalsReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeGoalsReviewDTO,
     * or with status {@code 400 (Bad Request)} if the employeeGoalsReviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeGoalsReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-goals-reviews/{id}")
    public ResponseEntity<EmployeeGoalsReviewDTO> updateEmployeeGoalsReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeGoalsReviewDTO employeeGoalsReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeGoalsReview : {}, {}", id, employeeGoalsReviewDTO);
        if (employeeGoalsReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeGoalsReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeGoalsReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeGoalsReviewDTO result = employeeGoalsReviewService.update(employeeGoalsReviewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeGoalsReviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-goals-reviews/:id} : Partial updates given fields of an existing employeeGoalsReview, field will ignore if it is null
     *
     * @param id the id of the employeeGoalsReviewDTO to save.
     * @param employeeGoalsReviewDTO the employeeGoalsReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeGoalsReviewDTO,
     * or with status {@code 400 (Bad Request)} if the employeeGoalsReviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeGoalsReviewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeGoalsReviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-goals-reviews/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeGoalsReviewDTO> partialUpdateEmployeeGoalsReview(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeGoalsReviewDTO employeeGoalsReviewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeGoalsReview partially : {}, {}", id, employeeGoalsReviewDTO);
        if (employeeGoalsReviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeGoalsReviewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeGoalsReviewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeGoalsReviewDTO> result = employeeGoalsReviewService.partialUpdate(employeeGoalsReviewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeGoalsReviewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-goals-reviews} : get all the employeeGoalsReviews.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeGoalsReviews in body.
     */
    @GetMapping("/employee-goals-reviews")
    public ResponseEntity<List<EmployeeGoalsReviewDTO>> getAllEmployeeGoalsReviews(
        EmployeeGoalsReviewCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeGoalsReviews by criteria: {}", criteria);
        Page<EmployeeGoalsReviewDTO> page = employeeGoalsReviewQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-goals-reviews/count} : count all the employeeGoalsReviews.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-goals-reviews/count")
    public ResponseEntity<Long> countEmployeeGoalsReviews(EmployeeGoalsReviewCriteria criteria) {
        log.debug("REST request to count EmployeeGoalsReviews by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeGoalsReviewQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-goals-reviews/:id} : get the "id" employeeGoalsReview.
     *
     * @param id the id of the employeeGoalsReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeGoalsReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-goals-reviews/{id}")
    public ResponseEntity<EmployeeGoalsReviewDTO> getEmployeeGoalsReview(@PathVariable Long id) {
        log.debug("REST request to get EmployeeGoalsReview : {}", id);
        Optional<EmployeeGoalsReviewDTO> employeeGoalsReviewDTO = employeeGoalsReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeGoalsReviewDTO);
    }

    /**
     * {@code DELETE  /employee-goals-reviews/:id} : delete the "id" employeeGoalsReview.
     *
     * @param id the id of the employeeGoalsReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-goals-reviews/{id}")
    public ResponseEntity<Void> deleteEmployeeGoalsReview(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeGoalsReview : {}", id);
        employeeGoalsReviewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
