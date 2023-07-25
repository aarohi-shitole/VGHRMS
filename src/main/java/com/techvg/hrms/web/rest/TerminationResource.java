package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TerminationRepository;
import com.techvg.hrms.service.TerminationQueryService;
import com.techvg.hrms.service.TerminationService;
import com.techvg.hrms.service.criteria.TerminationCriteria;
import com.techvg.hrms.service.dto.TerminationDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Termination}.
 */
@RestController
@RequestMapping("/api")
public class TerminationResource {

    private final Logger log = LoggerFactory.getLogger(TerminationResource.class);

    private static final String ENTITY_NAME = "termination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerminationService terminationService;

    private final TerminationRepository terminationRepository;

    private final TerminationQueryService terminationQueryService;

    public TerminationResource(
        TerminationService terminationService,
        TerminationRepository terminationRepository,
        TerminationQueryService terminationQueryService
    ) {
        this.terminationService = terminationService;
        this.terminationRepository = terminationRepository;
        this.terminationQueryService = terminationQueryService;
    }

    /**
     * {@code POST  /terminations} : Create a new termination.
     *
     * @param terminationDTO the terminationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terminationDTO, or with status {@code 400 (Bad Request)} if the termination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terminations")
    public ResponseEntity<TerminationDTO> createTermination(@RequestBody TerminationDTO terminationDTO) throws URISyntaxException {
        log.debug("REST request to save Termination : {}", terminationDTO);
        if (terminationDTO.getId() != null) {
            throw new BadRequestAlertException("A new termination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TerminationDTO result = terminationService.save(terminationDTO);
        return ResponseEntity
            .created(new URI("/api/terminations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terminations/:id} : Updates an existing termination.
     *
     * @param id the id of the terminationDTO to save.
     * @param terminationDTO the terminationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminationDTO,
     * or with status {@code 400 (Bad Request)} if the terminationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terminationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terminations/{id}")
    public ResponseEntity<TerminationDTO> updateTermination(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TerminationDTO terminationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Termination : {}, {}", id, terminationDTO);
        if (terminationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terminationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terminationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TerminationDTO result = terminationService.update(terminationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terminationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /terminations/:id} : Partial updates given fields of an existing termination, field will ignore if it is null
     *
     * @param id the id of the terminationDTO to save.
     * @param terminationDTO the terminationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terminationDTO,
     * or with status {@code 400 (Bad Request)} if the terminationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the terminationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the terminationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/terminations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TerminationDTO> partialUpdateTermination(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TerminationDTO terminationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Termination partially : {}, {}", id, terminationDTO);
        if (terminationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terminationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terminationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TerminationDTO> result = terminationService.partialUpdate(terminationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terminationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /terminations} : get all the terminations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terminations in body.
     */
    @GetMapping("/terminations")
    public ResponseEntity<List<TerminationDTO>> getAllTerminations(
        TerminationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Terminations by criteria: {}", criteria);
        Page<TerminationDTO> page = terminationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terminations/count} : count all the terminations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/terminations/count")
    public ResponseEntity<Long> countTerminations(TerminationCriteria criteria) {
        log.debug("REST request to count Terminations by criteria: {}", criteria);
        return ResponseEntity.ok().body(terminationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /terminations/:id} : get the "id" termination.
     *
     * @param id the id of the terminationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terminationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terminations/{id}")
    public ResponseEntity<TerminationDTO> getTermination(@PathVariable Long id) {
        log.debug("REST request to get Termination : {}", id);
        Optional<TerminationDTO> terminationDTO = terminationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(terminationDTO);
    }

    /**
     * {@code DELETE  /terminations/:id} : delete the "id" termination.
     *
     * @param id the id of the terminationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terminations/{id}")
    public ResponseEntity<Void> deleteTermination(@PathVariable Long id) {
        log.debug("REST request to delete Termination : {}", id);
        terminationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
