package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.EmployeeLeaveAccountRepository;
import com.techvg.hrms.scheduler.LeaveCreditScheduler;
import com.techvg.hrms.service.EmployeeLeaveAccountQueryService;
import com.techvg.hrms.service.EmployeeLeaveAccountService;
import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.techvg.hrms.domain.EmployeeLeaveAccount}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLeaveAccountResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLeaveAccountResource.class);

    private static final String ENTITY_NAME = "employeeLeaveAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeLeaveAccountService employeeLeaveAccountService;

    private final EmployeeLeaveAccountRepository employeeLeaveAccountRepository;

    private final EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService;

    @Autowired
    private LeaveCreditScheduler leaveCreditScheduler;

    public EmployeeLeaveAccountResource(
        EmployeeLeaveAccountService employeeLeaveAccountService,
        EmployeeLeaveAccountRepository employeeLeaveAccountRepository,
        EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService
    ) {
        this.employeeLeaveAccountService = employeeLeaveAccountService;
        this.employeeLeaveAccountRepository = employeeLeaveAccountRepository;
        this.employeeLeaveAccountQueryService = employeeLeaveAccountQueryService;
    }

    /**
     * {@code POST  /employee-leave-accounts} : Create a new employeeLeaveAccount.
     *
     * @param employeeLeaveAccountDTO the employeeLeaveAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeLeaveAccountDTO, or with status {@code 400 (Bad Request)} if the employeeLeaveAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-leave-accounts")
    public ResponseEntity<EmployeeLeaveAccountDTO> createEmployeeLeaveAccount(@RequestBody EmployeeLeaveAccountDTO employeeLeaveAccountDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeLeaveAccount : {}", employeeLeaveAccountDTO);
        if (employeeLeaveAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeLeaveAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeLeaveAccountDTO result = employeeLeaveAccountService.save(employeeLeaveAccountDTO);
        return ResponseEntity
            .created(new URI("/api/employee-leave-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-leave-accounts/:id} : Updates an existing employeeLeaveAccount.
     *
     * @param id the id of the employeeLeaveAccountDTO to save.
     * @param employeeLeaveAccountDTO the employeeLeaveAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLeaveAccountDTO,
     * or with status {@code 400 (Bad Request)} if the employeeLeaveAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeLeaveAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-leave-accounts/{id}")
    public ResponseEntity<EmployeeLeaveAccountDTO> updateEmployeeLeaveAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeLeaveAccountDTO employeeLeaveAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeLeaveAccount : {}, {}", id, employeeLeaveAccountDTO);
        if (employeeLeaveAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeLeaveAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeLeaveAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeLeaveAccountDTO result = employeeLeaveAccountService.update(employeeLeaveAccountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeLeaveAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-leave-accounts/:id} : Partial updates given fields of an existing employeeLeaveAccount, field will ignore if it is null
     *
     * @param id the id of the employeeLeaveAccountDTO to save.
     * @param employeeLeaveAccountDTO the employeeLeaveAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLeaveAccountDTO,
     * or with status {@code 400 (Bad Request)} if the employeeLeaveAccountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeLeaveAccountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeLeaveAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-leave-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeLeaveAccountDTO> partialUpdateEmployeeLeaveAccount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeLeaveAccountDTO employeeLeaveAccountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeLeaveAccount partially : {}, {}", id, employeeLeaveAccountDTO);
        if (employeeLeaveAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeLeaveAccountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeLeaveAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeLeaveAccountDTO> result = employeeLeaveAccountService.partialUpdate(employeeLeaveAccountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeLeaveAccountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-leave-accounts} : get all the employeeLeaveAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeLeaveAccounts in body.
     */
    @GetMapping("/employee-leave-accounts")
    public ResponseEntity<List<EmployeeLeaveAccountDTO>> getAllEmployeeLeaveAccounts(
        EmployeeLeaveAccountCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EmployeeLeaveAccounts by criteria: {}", criteria);
        Page<EmployeeLeaveAccountDTO> page = employeeLeaveAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-leave-accounts/count} : count all the employeeLeaveAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-leave-accounts/count")
    public ResponseEntity<Long> countEmployeeLeaveAccounts(EmployeeLeaveAccountCriteria criteria) {
        log.debug("REST request to count EmployeeLeaveAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeLeaveAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-leave-accounts/:id} : get the "id" employeeLeaveAccount.
     *
     * @param id the id of the employeeLeaveAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeLeaveAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-leave-accounts/{id}")
    public ResponseEntity<EmployeeLeaveAccountDTO> getEmployeeLeaveAccount(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLeaveAccount : {}", id);
        Optional<EmployeeLeaveAccountDTO> employeeLeaveAccountDTO = employeeLeaveAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeLeaveAccountDTO);
    }

    /**
     * {@code DELETE  /employee-leave-accounts/:id} : delete the "id" employeeLeaveAccount.
     *
     * @param id the id of the employeeLeaveAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-leave-accounts/{id}")
    public ResponseEntity<Void> deleteEmployeeLeaveAccount(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLeaveAccount : {}", id);
        employeeLeaveAccountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("execute-creditLeave-Schedular")
    public void executeCreditLeaveSchedular() throws URISyntaxException {
        log.debug("REST request to executeCreditLeaveSchedular: {}");

        leaveCreditScheduler.reportCurrentTime();
    }
}
