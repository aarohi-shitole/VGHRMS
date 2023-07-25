package com.techvg.hrms.web.rest;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techvg.hrms.repository.ApprovalLevelRepository;
import com.techvg.hrms.service.ApprovalLevelQueryService;
import com.techvg.hrms.service.ApprovalLevelService;
import com.techvg.hrms.service.criteria.ApprovalLevelCriteria;
import com.techvg.hrms.service.dto.ApprovalLevelDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.ApprovalLevel}.
 */
@RestController
@RequestMapping("/api")
public class ApprovalLevelResource {

    private final Logger log = LoggerFactory.getLogger(ApprovalLevelResource.class);

    private static final String ENTITY_NAME = "approvalLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApprovalLevelService approvalLevelService;

    private final ApprovalLevelRepository approvalLevelRepository;

    private final ApprovalLevelQueryService approvalLevelQueryService;

    public ApprovalLevelResource(
        ApprovalLevelService approvalLevelService,
        ApprovalLevelRepository approvalLevelRepository,
        ApprovalLevelQueryService approvalLevelQueryService
    ) {
        this.approvalLevelService = approvalLevelService;
        this.approvalLevelRepository = approvalLevelRepository;
        this.approvalLevelQueryService = approvalLevelQueryService;
    }

    /**
     * {@code POST  /approval-levels} : Create a new approvalLevel.
     *
     * @param approvalLevelDTO the approvalLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new approvalLevelDTO, or with status {@code 400 (Bad Request)} if the approvalLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/approval-levels")
    public ResponseEntity<ApprovalLevelDTO> createApprovalLevel(@RequestBody ApprovalLevelDTO approvalLevelDTO) throws URISyntaxException {
        log.debug("REST request to save ApprovalLevel : {}", approvalLevelDTO);
        if (approvalLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new approvalLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApprovalLevelDTO result = approvalLevelService.save(approvalLevelDTO);
        return ResponseEntity
            .created(new URI("/api/approval-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /approval-levels/:id} : Updates an existing approvalLevel.
     *
     * @param id the id of the approvalLevelDTO to save.
     * @param approvalLevelDTO the approvalLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalLevelDTO,
     * or with status {@code 400 (Bad Request)} if the approvalLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the approvalLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/approval-levels/{id}")
    public ResponseEntity<ApprovalLevelDTO> updateApprovalLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApprovalLevelDTO approvalLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApprovalLevel : {}, {}", id, approvalLevelDTO);
        if (approvalLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApprovalLevelDTO result = approvalLevelService.update(approvalLevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /approval-levels/:id} : Partial updates given fields of an existing approvalLevel, field will ignore if it is null
     *
     * @param id the id of the approvalLevelDTO to save.
     * @param approvalLevelDTO the approvalLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated approvalLevelDTO,
     * or with status {@code 400 (Bad Request)} if the approvalLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the approvalLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the approvalLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/approval-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApprovalLevelDTO> partialUpdateApprovalLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ApprovalLevelDTO approvalLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApprovalLevel partially : {}, {}", id, approvalLevelDTO);
        if (approvalLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, approvalLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!approvalLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApprovalLevelDTO> result = approvalLevelService.partialUpdate(approvalLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, approvalLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /approval-levels} : get all the approvalLevels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of approvalLevels in body.
     */
    @GetMapping("/approval-levels")
    public ResponseEntity<List<ApprovalLevelDTO>> getAllApprovalLevels(
        ApprovalLevelCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ApprovalLevels by criteria: {}", criteria);
        Page<ApprovalLevelDTO> page = approvalLevelQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /approval-levels/count} : count all the approvalLevels.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/approval-levels/count")
    public ResponseEntity<Long> countApprovalLevels(ApprovalLevelCriteria criteria) {
        log.debug("REST request to count ApprovalLevels by criteria: {}", criteria);
        return ResponseEntity.ok().body(approvalLevelQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /approval-levels/:id} : get the "id" approvalLevel.
     *
     * @param id the id of the approvalLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the approvalLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/approval-levels/{id}")
    public ResponseEntity<ApprovalLevelDTO> getApprovalLevel(@PathVariable Long id) {
        log.debug("REST request to get ApprovalLevel : {}", id);
        Optional<ApprovalLevelDTO> approvalLevelDTO = approvalLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(approvalLevelDTO);
    }

    /**
     * {@code DELETE  /approval-levels/:id} : delete the "id" approvalLevel.
     *
     * @param id the id of the approvalLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/approval-levels/{id}")
    public ResponseEntity<Void> deleteApprovalLevel(@PathVariable Long id) {
        log.debug("REST request to delete ApprovalLevel : {}", id);
        approvalLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
