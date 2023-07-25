package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.CustomApprovarRepository;
import com.techvg.hrms.service.CustomApprovarQueryService;
import com.techvg.hrms.service.CustomApprovarService;
import com.techvg.hrms.service.criteria.CustomApprovarCriteria;
import com.techvg.hrms.service.dto.CustomApprovarDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.CustomApprovar}.
 */
@RestController
@RequestMapping("/api")
public class CustomApprovarResource {

    private final Logger log = LoggerFactory.getLogger(CustomApprovarResource.class);

    private static final String ENTITY_NAME = "customApprovar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomApprovarService customApprovarService;

    private final CustomApprovarRepository customApprovarRepository;

    private final CustomApprovarQueryService customApprovarQueryService;

    public CustomApprovarResource(
        CustomApprovarService customApprovarService,
        CustomApprovarRepository customApprovarRepository,
        CustomApprovarQueryService customApprovarQueryService
    ) {
        this.customApprovarService = customApprovarService;
        this.customApprovarRepository = customApprovarRepository;
        this.customApprovarQueryService = customApprovarQueryService;
    }

    /**
     * {@code POST  /custom-approvars} : Create a new customApprovar.
     *
     * @param customApprovarDTO the customApprovarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customApprovarDTO, or with status {@code 400 (Bad Request)} if the customApprovar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-approvars")
    public ResponseEntity<CustomApprovarDTO> createCustomApprovar(@RequestBody CustomApprovarDTO customApprovarDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomApprovar : {}", customApprovarDTO);
        if (customApprovarDTO.getId() != null) {
            throw new BadRequestAlertException("A new customApprovar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomApprovarDTO result = customApprovarService.save(customApprovarDTO);
        return ResponseEntity
            .created(new URI("/api/custom-approvars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-approvars/:id} : Updates an existing customApprovar.
     *
     * @param id the id of the customApprovarDTO to save.
     * @param customApprovarDTO the customApprovarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customApprovarDTO,
     * or with status {@code 400 (Bad Request)} if the customApprovarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customApprovarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-approvars/{id}")
    public ResponseEntity<CustomApprovarDTO> updateCustomApprovar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomApprovarDTO customApprovarDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomApprovar : {}, {}", id, customApprovarDTO);
        if (customApprovarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customApprovarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customApprovarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomApprovarDTO result = customApprovarService.update(customApprovarDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customApprovarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custom-approvars/:id} : Partial updates given fields of an existing customApprovar, field will ignore if it is null
     *
     * @param id the id of the customApprovarDTO to save.
     * @param customApprovarDTO the customApprovarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customApprovarDTO,
     * or with status {@code 400 (Bad Request)} if the customApprovarDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customApprovarDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customApprovarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/custom-approvars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomApprovarDTO> partialUpdateCustomApprovar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomApprovarDTO customApprovarDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomApprovar partially : {}, {}", id, customApprovarDTO);
        if (customApprovarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customApprovarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customApprovarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomApprovarDTO> result = customApprovarService.partialUpdate(customApprovarDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customApprovarDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /custom-approvars} : get all the customApprovars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customApprovars in body.
     */
    @GetMapping("/custom-approvars")
    public ResponseEntity<List<CustomApprovarDTO>> getAllCustomApprovars(
        CustomApprovarCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CustomApprovars by criteria: {}", criteria);
        Page<CustomApprovarDTO> page = customApprovarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /custom-approvars/count} : count all the customApprovars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/custom-approvars/count")
    public ResponseEntity<Long> countCustomApprovars(CustomApprovarCriteria criteria) {
        log.debug("REST request to count CustomApprovars by criteria: {}", criteria);
        return ResponseEntity.ok().body(customApprovarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /custom-approvars/:id} : get the "id" customApprovar.
     *
     * @param id the id of the customApprovarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customApprovarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-approvars/{id}")
    public ResponseEntity<CustomApprovarDTO> getCustomApprovar(@PathVariable Long id) {
        log.debug("REST request to get CustomApprovar : {}", id);
        Optional<CustomApprovarDTO> customApprovarDTO = customApprovarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customApprovarDTO);
    }

    /**
     * {@code DELETE  /custom-approvars/:id} : delete the "id" customApprovar.
     *
     * @param id the id of the customApprovarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-approvars/{id}")
    public ResponseEntity<Void> deleteCustomApprovar(@PathVariable Long id) {
        log.debug("REST request to delete CustomApprovar : {}", id);
        customApprovarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
