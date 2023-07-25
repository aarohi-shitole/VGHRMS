package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AppraisalDetailsRepository;
import com.techvg.hrms.service.AppraisalDetailsQueryService;
import com.techvg.hrms.service.AppraisalDetailsService;
import com.techvg.hrms.service.criteria.AppraisalDetailsCriteria;
import com.techvg.hrms.service.dto.AppraisalDetailsDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AppraisalDetails}.
 */
@RestController
@RequestMapping("/api")
public class AppraisalDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AppraisalDetailsResource.class);

    private static final String ENTITY_NAME = "appraisalDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppraisalDetailsService appraisalDetailsService;

    private final AppraisalDetailsRepository appraisalDetailsRepository;

    private final AppraisalDetailsQueryService appraisalDetailsQueryService;

    public AppraisalDetailsResource(
        AppraisalDetailsService appraisalDetailsService,
        AppraisalDetailsRepository appraisalDetailsRepository,
        AppraisalDetailsQueryService appraisalDetailsQueryService
    ) {
        this.appraisalDetailsService = appraisalDetailsService;
        this.appraisalDetailsRepository = appraisalDetailsRepository;
        this.appraisalDetailsQueryService = appraisalDetailsQueryService;
    }

    /**
     * {@code POST  /performance-indicators} : Create a new AppraisalDetails.
     *
     * @param AppraisalDetailsDTO the AppraisalDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new AppraisalDetailsDTO, or with status {@code 400 (Bad Request)} if the AppraisalDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appraisal-details")
    public ResponseEntity<AppraisalDetailsDTO> createAppraisalDetails(@RequestBody AppraisalDetailsDTO appraisalDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AppraisalDetails : {}", appraisalDetailsDTO);
        if (appraisalDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new AppraisalDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppraisalDetailsDTO result = appraisalDetailsService.save(appraisalDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/performance-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /performance-indicators/:id} : Updates an existing AppraisalDetails.
     *
     * @param id the id of the AppraisalDetailsDTO to save.
     * @param AppraisalDetailsDTO the AppraisalDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated AppraisalDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the AppraisalDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the AppraisalDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appraisal-details/{id}")
    public ResponseEntity<AppraisalDetailsDTO> updateAppraisalDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalDetailsDTO appraisalDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppraisalDetails : {}, {}", id, appraisalDetailsDTO);
        if (appraisalDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppraisalDetailsDTO result = appraisalDetailsService.update(appraisalDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /performance-indicators/:id} : Partial updates given fields of an existing appraisalDetails, field will ignore if it is null
     *
     * @param id the id of the appraisalDetailsDTO to save.
     * @param appraisalDetailsDTO the appraisalDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appraisalDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the appraisalDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appraisalDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appraisalDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/appraisal-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppraisalDetailsDTO> partialUpdateAppraisalDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalDetailsDTO appraisalDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update appraisalDetails partially : {}, {}", id, appraisalDetailsDTO);
        if (appraisalDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appraisalDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appraisalDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppraisalDetailsDTO> result = appraisalDetailsService.partialUpdate(appraisalDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appraisalDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /performance-indicators} : get all the appraisalDetailss.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appraisalDetailss in body.
     */
    @GetMapping("/appraisal-details")
    public ResponseEntity<List<AppraisalDetailsDTO>> getAllAppraisalDetailss(
        AppraisalDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AppraisalDetailss by criteria: {}", criteria);
        Page<AppraisalDetailsDTO> page = appraisalDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-indicators/count} : count all the appraisalDetailss.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/appraisal-details/count")
    public ResponseEntity<Long> countAppraisalDetailss(AppraisalDetailsCriteria criteria) {
        log.debug("REST request to count AppraisalDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(appraisalDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /performance-indicators/:id} : get the "id" appraisalDetails.
     *
     * @param id the id of the appraisalDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appraisalDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appraisal-details/{id}")
    public ResponseEntity<AppraisalDetailsDTO> getAppraisalDetails(@PathVariable Long id) {
        log.debug("REST request to get AppraisalDetails : {}", id);
        Optional<AppraisalDetailsDTO> appraisalDetailsDTO = appraisalDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appraisalDetailsDTO);
    }

    /**
     * {@code DELETE  /performance-indicators/:id} : delete the "id" appraisalDetails.
     *
     * @param id the id of the appraisalDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appraisal-details/{id}")
    public ResponseEntity<Void> deleteAppraisalDetails(@PathVariable Long id) {
        log.debug("REST request to delete appraisalDetails : {}", id);
        appraisalDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
