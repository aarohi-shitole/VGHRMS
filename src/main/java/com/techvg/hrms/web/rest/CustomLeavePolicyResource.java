package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.CustomLeavePolicyRepository;
import com.techvg.hrms.service.CustomLeavePolicyHelper;
import com.techvg.hrms.service.CustomLeavePolicyQueryService;
import com.techvg.hrms.service.CustomLeavePolicyService;
import com.techvg.hrms.service.criteria.CustomLeavePolicyCriteria;
import com.techvg.hrms.service.dto.CustomLeavePolicyDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.CustomLeavePolicy}.
 */
@RestController
@RequestMapping("/api")
public class CustomLeavePolicyResource {

    private final Logger log = LoggerFactory.getLogger(CustomLeavePolicyResource.class);

    private static final String ENTITY_NAME = "customLeavePolicy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomLeavePolicyService customLeavePolicyService;

    private final CustomLeavePolicyRepository customLeavePolicyRepository;

    private final CustomLeavePolicyQueryService customLeavePolicyQueryService;

    @Autowired
    private CustomLeavePolicyHelper customLeavePolicyHelper;

    public CustomLeavePolicyResource(
        CustomLeavePolicyService customLeavePolicyService,
        CustomLeavePolicyRepository customLeavePolicyRepository,
        CustomLeavePolicyQueryService customLeavePolicyQueryService
    ) {
        this.customLeavePolicyService = customLeavePolicyService;
        this.customLeavePolicyRepository = customLeavePolicyRepository;
        this.customLeavePolicyQueryService = customLeavePolicyQueryService;
    }

    /**
     * {@code POST  /custom-leave-policies} : Create a new customLeavePolicy.
     *
     * @param customLeavePolicyDTO the customLeavePolicyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customLeavePolicyDTO, or with status {@code 400 (Bad Request)} if the customLeavePolicy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-leave-policies")
    public ResponseEntity<CustomLeavePolicyDTO> createCustomLeavePolicy(@RequestBody CustomLeavePolicyDTO customLeavePolicyDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomLeavePolicy : {}", customLeavePolicyDTO);
        if (customLeavePolicyDTO.getId() != null) {
            throw new BadRequestAlertException("A new customLeavePolicy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomLeavePolicyDTO result = customLeavePolicyService.save(customLeavePolicyDTO);
        customLeavePolicyHelper.updateEmployeeLeaveAccount(result);

        return ResponseEntity
            .created(new URI("/api/custom-leave-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-leave-policies/:id} : Updates an existing customLeavePolicy.
     *
     * @param id the id of the customLeavePolicyDTO to save.
     * @param customLeavePolicyDTO the customLeavePolicyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customLeavePolicyDTO,
     * or with status {@code 400 (Bad Request)} if the customLeavePolicyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customLeavePolicyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-leave-policies/{id}")
    public ResponseEntity<CustomLeavePolicyDTO> updateCustomLeavePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomLeavePolicyDTO customLeavePolicyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomLeavePolicy : {}, {}", id, customLeavePolicyDTO);
        if (customLeavePolicyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customLeavePolicyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customLeavePolicyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        customLeavePolicyHelper.updateEmployeeExistingLeaveAccount(customLeavePolicyDTO);
        log.debug("!!!!!!!!!!!!After updateEmployeeExistingLeaveAccount");

        CustomLeavePolicyDTO result = customLeavePolicyService.update(customLeavePolicyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customLeavePolicyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custom-leave-policies/:id} : Partial updates given fields of an existing customLeavePolicy, field will ignore if it is null
     *
     * @param id the id of the customLeavePolicyDTO to save.
     * @param customLeavePolicyDTO the customLeavePolicyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customLeavePolicyDTO,
     * or with status {@code 400 (Bad Request)} if the customLeavePolicyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customLeavePolicyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customLeavePolicyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/custom-leave-policies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomLeavePolicyDTO> partialUpdateCustomLeavePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomLeavePolicyDTO customLeavePolicyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomLeavePolicy partially : {}, {}", id, customLeavePolicyDTO);
        if (customLeavePolicyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customLeavePolicyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customLeavePolicyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomLeavePolicyDTO> result = customLeavePolicyService.partialUpdate(customLeavePolicyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customLeavePolicyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /custom-leave-policies} : get all the customLeavePolicies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customLeavePolicies in body.
     */
    @GetMapping("/custom-leave-policies")
    public ResponseEntity<List<CustomLeavePolicyDTO>> getAllCustomLeavePolicies(
        CustomLeavePolicyCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CustomLeavePolicies by criteria: {}", criteria);
        Page<CustomLeavePolicyDTO> page = customLeavePolicyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /custom-leave-policies/count} : count all the customLeavePolicies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/custom-leave-policies/count")
    public ResponseEntity<Long> countCustomLeavePolicies(CustomLeavePolicyCriteria criteria) {
        log.debug("REST request to count CustomLeavePolicies by criteria: {}", criteria);
        return ResponseEntity.ok().body(customLeavePolicyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /custom-leave-policies/:id} : get the "id" customLeavePolicy.
     *
     * @param id the id of the customLeavePolicyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customLeavePolicyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-leave-policies/{id}")
    public ResponseEntity<CustomLeavePolicyDTO> getCustomLeavePolicy(@PathVariable Long id) {
        log.debug("REST request to get CustomLeavePolicy : {}", id);
        Optional<CustomLeavePolicyDTO> customLeavePolicyDTO = customLeavePolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customLeavePolicyDTO);
    }

    /**
     * {@code DELETE  /custom-leave-policies/:id} : delete the "id" customLeavePolicy.
     *
     * @param id the id of the customLeavePolicyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-leave-policies/{id}")
    public ResponseEntity<Void> deleteCustomLeavePolicy(@PathVariable Long id) {
        log.debug("REST request to delete CustomLeavePolicy : {}", id);
        customLeavePolicyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
