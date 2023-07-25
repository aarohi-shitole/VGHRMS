package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TaxSlabRepository;
import com.techvg.hrms.service.TaxSlabQueryService;
import com.techvg.hrms.service.TaxSlabService;
import com.techvg.hrms.service.criteria.TaxSlabCriteria;
import com.techvg.hrms.service.dto.TaxSlabDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.TaxSlab}.
 */
@RestController
@RequestMapping("/api")
public class TaxSlabResource {

    private final Logger log = LoggerFactory.getLogger(TaxSlabResource.class);

    private static final String ENTITY_NAME = "taxSlab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxSlabService taxSlabService;

    private final TaxSlabRepository taxSlabRepository;

    private final TaxSlabQueryService taxSlabQueryService;

    public TaxSlabResource(TaxSlabService taxSlabService, TaxSlabRepository taxSlabRepository, TaxSlabQueryService taxSlabQueryService) {
        this.taxSlabService = taxSlabService;
        this.taxSlabRepository = taxSlabRepository;
        this.taxSlabQueryService = taxSlabQueryService;
    }

    /**
     * {@code POST  /tax-slabs} : Create a new taxSlab.
     *
     * @param taxSlabDTO the taxSlabDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxSlabDTO, or with status {@code 400 (Bad Request)} if the taxSlab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-slabs")
    public ResponseEntity<TaxSlabDTO> createTaxSlab(@RequestBody TaxSlabDTO taxSlabDTO) throws URISyntaxException {
        log.debug("REST request to save TaxSlab : {}", taxSlabDTO);
        if (taxSlabDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxSlab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxSlabDTO result = taxSlabService.save(taxSlabDTO);
        return ResponseEntity
            .created(new URI("/api/tax-slabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-slabs/:id} : Updates an existing taxSlab.
     *
     * @param id the id of the taxSlabDTO to save.
     * @param taxSlabDTO the taxSlabDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxSlabDTO,
     * or with status {@code 400 (Bad Request)} if the taxSlabDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxSlabDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-slabs/{id}")
    public ResponseEntity<TaxSlabDTO> updateTaxSlab(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxSlabDTO taxSlabDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaxSlab : {}, {}", id, taxSlabDTO);
        if (taxSlabDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxSlabDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxSlabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaxSlabDTO result = taxSlabService.update(taxSlabDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxSlabDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tax-slabs/:id} : Partial updates given fields of an existing taxSlab, field will ignore if it is null
     *
     * @param id the id of the taxSlabDTO to save.
     * @param taxSlabDTO the taxSlabDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxSlabDTO,
     * or with status {@code 400 (Bad Request)} if the taxSlabDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taxSlabDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxSlabDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tax-slabs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxSlabDTO> partialUpdateTaxSlab(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxSlabDTO taxSlabDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxSlab partially : {}, {}", id, taxSlabDTO);
        if (taxSlabDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxSlabDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxSlabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxSlabDTO> result = taxSlabService.partialUpdate(taxSlabDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxSlabDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tax-slabs} : get all the taxSlabs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxSlabs in body.
     */
    @GetMapping("/tax-slabs")
    public ResponseEntity<List<TaxSlabDTO>> getAllTaxSlabs(
        TaxSlabCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TaxSlabs by criteria: {}", criteria);
        Page<TaxSlabDTO> page = taxSlabQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tax-slabs/count} : count all the taxSlabs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tax-slabs/count")
    public ResponseEntity<Long> countTaxSlabs(TaxSlabCriteria criteria) {
        log.debug("REST request to count TaxSlabs by criteria: {}", criteria);
        return ResponseEntity.ok().body(taxSlabQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tax-slabs/:id} : get the "id" taxSlab.
     *
     * @param id the id of the taxSlabDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxSlabDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-slabs/{id}")
    public ResponseEntity<TaxSlabDTO> getTaxSlab(@PathVariable Long id) {
        log.debug("REST request to get TaxSlab : {}", id);
        Optional<TaxSlabDTO> taxSlabDTO = taxSlabService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxSlabDTO);
    }

    /**
     * {@code DELETE  /tax-slabs/:id} : delete the "id" taxSlab.
     *
     * @param id the id of the taxSlabDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-slabs/{id}")
    public ResponseEntity<Void> deleteTaxSlab(@PathVariable Long id) {
        log.debug("REST request to delete TaxSlab : {}", id);
        taxSlabService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
