package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.PayrollAdditionsRepository;
import com.techvg.hrms.service.PayrollAdditionsQueryService;
import com.techvg.hrms.service.PayrollAdditionsService;
import com.techvg.hrms.service.criteria.PayrollAdditionsCriteria;
import com.techvg.hrms.service.dto.PayrollAdditionsDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.PayrollAdditions}.
 */
@RestController
@RequestMapping("/api")
public class PayrollAdditionsResource {

    private final Logger log = LoggerFactory.getLogger(PayrollAdditionsResource.class);

    private static final String ENTITY_NAME = "payrollAdditions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollAdditionsService payrollAdditionsService;

    private final PayrollAdditionsRepository payrollAdditionsRepository;

    private final PayrollAdditionsQueryService payrollAdditionsQueryService;

    public PayrollAdditionsResource(
        PayrollAdditionsService payrollAdditionsService,
        PayrollAdditionsRepository payrollAdditionsRepository,
        PayrollAdditionsQueryService payrollAdditionsQueryService
    ) {
        this.payrollAdditionsService = payrollAdditionsService;
        this.payrollAdditionsRepository = payrollAdditionsRepository;
        this.payrollAdditionsQueryService = payrollAdditionsQueryService;
    }

    /**
     * {@code POST  /payroll-additions} : Create a new payrollAdditions.
     *
     * @param payrollAdditionsDTO the payrollAdditionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollAdditionsDTO, or with status {@code 400 (Bad Request)} if the payrollAdditions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payroll-additions")
    public ResponseEntity<PayrollAdditionsDTO> createPayrollAdditions(@RequestBody PayrollAdditionsDTO payrollAdditionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save PayrollAdditions : {}", payrollAdditionsDTO);
        if (payrollAdditionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new payrollAdditions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollAdditionsDTO result = payrollAdditionsService.save(payrollAdditionsDTO);
        return ResponseEntity
            .created(new URI("/api/payroll-additions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payroll-additions/:id} : Updates an existing payrollAdditions.
     *
     * @param id the id of the payrollAdditionsDTO to save.
     * @param payrollAdditionsDTO the payrollAdditionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollAdditionsDTO,
     * or with status {@code 400 (Bad Request)} if the payrollAdditionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollAdditionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payroll-additions/{id}")
    public ResponseEntity<PayrollAdditionsDTO> updatePayrollAdditions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollAdditionsDTO payrollAdditionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PayrollAdditions : {}, {}", id, payrollAdditionsDTO);
        if (payrollAdditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollAdditionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollAdditionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayrollAdditionsDTO result = payrollAdditionsService.update(payrollAdditionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollAdditionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payroll-additions/:id} : Partial updates given fields of an existing payrollAdditions, field will ignore if it is null
     *
     * @param id the id of the payrollAdditionsDTO to save.
     * @param payrollAdditionsDTO the payrollAdditionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollAdditionsDTO,
     * or with status {@code 400 (Bad Request)} if the payrollAdditionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the payrollAdditionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the payrollAdditionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payroll-additions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PayrollAdditionsDTO> partialUpdatePayrollAdditions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollAdditionsDTO payrollAdditionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayrollAdditions partially : {}, {}", id, payrollAdditionsDTO);
        if (payrollAdditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollAdditionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollAdditionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayrollAdditionsDTO> result = payrollAdditionsService.partialUpdate(payrollAdditionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollAdditionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payroll-additions} : get all the payrollAdditions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrollAdditions in body.
     */
    @GetMapping("/payroll-additions")
    public ResponseEntity<List<PayrollAdditionsDTO>> getAllPayrollAdditions(
        PayrollAdditionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PayrollAdditions by criteria: {}", criteria);
        Page<PayrollAdditionsDTO> page = payrollAdditionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payroll-additions/count} : count all the payrollAdditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payroll-additions/count")
    public ResponseEntity<Long> countPayrollAdditions(PayrollAdditionsCriteria criteria) {
        log.debug("REST request to count PayrollAdditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(payrollAdditionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payroll-additions/:id} : get the "id" payrollAdditions.
     *
     * @param id the id of the payrollAdditionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollAdditionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payroll-additions/{id}")
    public ResponseEntity<PayrollAdditionsDTO> getPayrollAdditions(@PathVariable Long id) {
        log.debug("REST request to get PayrollAdditions : {}", id);
        Optional<PayrollAdditionsDTO> payrollAdditionsDTO = payrollAdditionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payrollAdditionsDTO);
    }

    /**
     * {@code DELETE  /payroll-additions/:id} : delete the "id" payrollAdditions.
     *
     * @param id the id of the payrollAdditionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payroll-additions/{id}")
    public ResponseEntity<Void> deletePayrollAdditions(@PathVariable Long id) {
        log.debug("REST request to delete PayrollAdditions : {}", id);
        payrollAdditionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
