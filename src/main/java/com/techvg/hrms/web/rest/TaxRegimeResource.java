package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TaxRegimeRepository;
import com.techvg.hrms.service.TaxRegimeQueryService;
import com.techvg.hrms.service.TaxRegimeService;
import com.techvg.hrms.service.criteria.TaxRegimeCriteria;
import com.techvg.hrms.service.dto.TaxRegimeDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.TaxRegime}.
 */
@RestController
@RequestMapping("/api")
public class TaxRegimeResource {

    private final Logger log = LoggerFactory.getLogger(TaxRegimeResource.class);

    private static final String ENTITY_NAME = "taxRegime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxRegimeService taxRegimeService;

    private final TaxRegimeRepository taxRegimeRepository;

    private final TaxRegimeQueryService taxRegimeQueryService;

    public TaxRegimeResource(
        TaxRegimeService taxRegimeService,
        TaxRegimeRepository taxRegimeRepository,
        TaxRegimeQueryService taxRegimeQueryService
    ) {
        this.taxRegimeService = taxRegimeService;
        this.taxRegimeRepository = taxRegimeRepository;
        this.taxRegimeQueryService = taxRegimeQueryService;
    }

    /**
     * {@code POST  /tax-regimes} : Create a new taxRegime.
     *
     * @param taxRegimeDTO the taxRegimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxRegimeDTO, or with status {@code 400 (Bad Request)} if the taxRegime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tax-regimes")
    public ResponseEntity<TaxRegimeDTO> createTaxRegime(@RequestBody TaxRegimeDTO taxRegimeDTO) throws URISyntaxException {
        log.debug("REST request to save TaxRegime : {}", taxRegimeDTO);
        if (taxRegimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new taxRegime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxRegimeDTO result = taxRegimeService.save(taxRegimeDTO);
        return ResponseEntity
            .created(new URI("/api/tax-regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tax-regimes/:id} : Updates an existing taxRegime.
     *
     * @param id the id of the taxRegimeDTO to save.
     * @param taxRegimeDTO the taxRegimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxRegimeDTO,
     * or with status {@code 400 (Bad Request)} if the taxRegimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxRegimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tax-regimes/{id}")
    public ResponseEntity<TaxRegimeDTO> updateTaxRegime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxRegimeDTO taxRegimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaxRegime : {}, {}", id, taxRegimeDTO);
        if (taxRegimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxRegimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxRegimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaxRegimeDTO result = taxRegimeService.update(taxRegimeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxRegimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tax-regimes/:id} : Partial updates given fields of an existing taxRegime, field will ignore if it is null
     *
     * @param id the id of the taxRegimeDTO to save.
     * @param taxRegimeDTO the taxRegimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxRegimeDTO,
     * or with status {@code 400 (Bad Request)} if the taxRegimeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taxRegimeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taxRegimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tax-regimes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TaxRegimeDTO> partialUpdateTaxRegime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaxRegimeDTO taxRegimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaxRegime partially : {}, {}", id, taxRegimeDTO);
        if (taxRegimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taxRegimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taxRegimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaxRegimeDTO> result = taxRegimeService.partialUpdate(taxRegimeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxRegimeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tax-regimes} : get all the taxRegimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxRegimes in body.
     */
    @GetMapping("/tax-regimes")
    public ResponseEntity<List<TaxRegimeDTO>> getAllTaxRegimes(
        TaxRegimeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TaxRegimes by criteria: {}", criteria);
        Page<TaxRegimeDTO> page = taxRegimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tax-regimes/count} : count all the taxRegimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tax-regimes/count")
    public ResponseEntity<Long> countTaxRegimes(TaxRegimeCriteria criteria) {
        log.debug("REST request to count TaxRegimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(taxRegimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tax-regimes/:id} : get the "id" taxRegime.
     *
     * @param id the id of the taxRegimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxRegimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tax-regimes/{id}")
    public ResponseEntity<TaxRegimeDTO> getTaxRegime(@PathVariable Long id) {
        log.debug("REST request to get TaxRegime : {}", id);
        Optional<TaxRegimeDTO> taxRegimeDTO = taxRegimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxRegimeDTO);
    }

    /**
     * {@code DELETE  /tax-regimes/:id} : delete the "id" taxRegime.
     *
     * @param id the id of the taxRegimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tax-regimes/{id}")
    public ResponseEntity<Void> deleteTaxRegime(@PathVariable Long id) {
        log.debug("REST request to delete TaxRegime : {}", id);
        taxRegimeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
