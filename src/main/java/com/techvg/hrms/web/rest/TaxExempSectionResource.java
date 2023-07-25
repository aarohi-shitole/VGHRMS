package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TaxExempSectionRepository;
import com.techvg.hrms.service.TaxExempSectionQueryService;
import com.techvg.hrms.service.TaxExempSectionService;
import com.techvg.hrms.service.criteria.TaxExempSectionCriteria;
import com.techvg.hrms.service.dto.TaxExempSectionDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.TaxExempSection}.
 */
@RestController
@RequestMapping("/api")
public class TaxExempSectionResource {

    private final Logger log = LoggerFactory.getLogger(TaxExempSectionResource.class);

    private static final String ENTITY_NAME = "taxExempSection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxExempSectionService taxExempSectionService;

    private final TaxExempSectionRepository taxExempSectionRepository;

    private final TaxExempSectionQueryService taxExempSectionQueryService;

    public TaxExempSectionResource(
        TaxExempSectionService taxExempSectionService,
        TaxExempSectionRepository taxExempSectionRepository,
        TaxExempSectionQueryService taxExempSectionQueryService
    ) {
        this.taxExempSectionService = taxExempSectionService;
        this.taxExempSectionRepository = taxExempSectionRepository;
        this.taxExempSectionQueryService = taxExempSectionQueryService;
    }

    /**
     * {@code POST  /tax-exemp-sections} : Create a new taxExempSection.
     *
     * @param taxExempSectionDTO the taxExempSectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxExempSectionDTO, or with status {@code 400 (Bad Request)} if the taxExempSection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-exemp-sections")
    public ResponseEntity<TaxExempSectionDTO> createTaxExempSection(@RequestBody TaxExempSectionDTO taxExempSectionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaxExempSection : {}", taxExempSectionDTO);
        if (taxExempSectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxExempSection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxExempSectionDTO result = taxExempSectionService.save(taxExempSectionDTO);
        return ResponseEntity
            .created(new URI("/api/tax-exemp-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-exemp-sections/:id} : Updates an existing taxExempSection.
     *
     * @param id the id of the taxExempSectionDTO to save.
     * @param taxExempSectionDTO the taxExempSectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxExempSectionDTO,
     * or with status {@code 400 (Bad Request)} if the taxExempSectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxExempSectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-exemp-sections/{id}")
    public ResponseEntity<TaxExempSectionDTO> updateTaxExempSection(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxExempSectionDTO taxExempSectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaxExempSection : {}, {}", id, taxExempSectionDTO);
        if (taxExempSectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxExempSectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxExempSectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaxExempSectionDTO result = taxExempSectionService.update(taxExempSectionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxExempSectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tax-exemp-sections/:id} : Partial updates given fields of an existing taxExempSection, field will ignore if it is null
     *
     * @param id the id of the taxExempSectionDTO to save.
     * @param taxExempSectionDTO the taxExempSectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxExempSectionDTO,
     * or with status {@code 400 (Bad Request)} if the taxExempSectionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taxExempSectionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxExempSectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tax-exemp-sections/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxExempSectionDTO> partialUpdateTaxExempSection(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxExempSectionDTO taxExempSectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxExempSection partially : {}, {}", id, taxExempSectionDTO);
        if (taxExempSectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxExempSectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxExempSectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxExempSectionDTO> result = taxExempSectionService.partialUpdate(taxExempSectionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxExempSectionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tax-exemp-sections} : get all the taxExempSections.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxExempSections in body.
     */
    @GetMapping("/tax-exemp-sections")
    public ResponseEntity<List<TaxExempSectionDTO>> getAllTaxExempSections(
        TaxExempSectionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TaxExempSections by criteria: {}", criteria);
        Page<TaxExempSectionDTO> page = taxExempSectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tax-exemp-sections/count} : count all the taxExempSections.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tax-exemp-sections/count")
    public ResponseEntity<Long> countTaxExempSections(TaxExempSectionCriteria criteria) {
        log.debug("REST request to count TaxExempSections by criteria: {}", criteria);
        return ResponseEntity.ok().body(taxExempSectionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tax-exemp-sections/:id} : get the "id" taxExempSection.
     *
     * @param id the id of the taxExempSectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxExempSectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-exemp-sections/{id}")
    public ResponseEntity<TaxExempSectionDTO> getTaxExempSection(@PathVariable Long id) {
        log.debug("REST request to get TaxExempSection : {}", id);
        Optional<TaxExempSectionDTO> taxExempSectionDTO = taxExempSectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxExempSectionDTO);
    }

    /**
     * {@code DELETE  /tax-exemp-sections/:id} : delete the "id" taxExempSection.
     *
     * @param id the id of the taxExempSectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-exemp-sections/{id}")
    public ResponseEntity<Void> deleteTaxExempSection(@PathVariable Long id) {
        log.debug("REST request to delete TaxExempSection : {}", id);
        taxExempSectionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
