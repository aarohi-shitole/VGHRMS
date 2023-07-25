package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TechvgPermissionRepository;
import com.techvg.hrms.service.TechvgPermissionQueryService;
import com.techvg.hrms.service.TechvgPermissionService;
import com.techvg.hrms.service.criteria.TechvgPermissionCriteria;
import com.techvg.hrms.service.dto.TechvgPermissionDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.techvg.hrms.domain.TechvgPermission}.
 */
@RestController
@RequestMapping("/api")
public class TechvgPermissionResource {

    private final Logger log = LoggerFactory.getLogger(TechvgPermissionResource.class);

    private static final String ENTITY_NAME = "techvgPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechvgPermissionService techvgPermissionService;

    private final TechvgPermissionRepository techvgPermissionRepository;

    private final TechvgPermissionQueryService techvgPermissionQueryService;

    public TechvgPermissionResource(
        TechvgPermissionService techvgPermissionService,
        TechvgPermissionRepository techvgPermissionRepository,
        TechvgPermissionQueryService techvgPermissionQueryService
    ) {
        this.techvgPermissionService = techvgPermissionService;
        this.techvgPermissionRepository = techvgPermissionRepository;
        this.techvgPermissionQueryService = techvgPermissionQueryService;
    }

    /**
     * {@code POST  /techvg-permissions} : Create a new techvgPermission.
     *
     * @param techvgPermissionDTO the techvgPermissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techvgPermissionDTO, or with status {@code 400 (Bad Request)} if the techvgPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/techvg-permissions")
    public ResponseEntity<TechvgPermissionDTO> createTechvgPermission(@Valid @RequestBody TechvgPermissionDTO techvgPermissionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TechvgPermission : {}", techvgPermissionDTO);
        if (techvgPermissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new techvgPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechvgPermissionDTO result = techvgPermissionService.save(techvgPermissionDTO);
        return ResponseEntity
            .created(new URI("/api/techvg-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /techvg-permissions/:id} : Updates an existing techvgPermission.
     *
     * @param id the id of the techvgPermissionDTO to save.
     * @param techvgPermissionDTO the techvgPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techvgPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the techvgPermissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techvgPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/techvg-permissions/{id}")
    public ResponseEntity<TechvgPermissionDTO> updateTechvgPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TechvgPermissionDTO techvgPermissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TechvgPermission : {}, {}", id, techvgPermissionDTO);
        if (techvgPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, techvgPermissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!techvgPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TechvgPermissionDTO result = techvgPermissionService.update(techvgPermissionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, techvgPermissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /techvg-permissions/:id} : Partial updates given fields of an existing techvgPermission, field will ignore if it is null
     *
     * @param id the id of the techvgPermissionDTO to save.
     * @param techvgPermissionDTO the techvgPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techvgPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the techvgPermissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the techvgPermissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the techvgPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/techvg-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TechvgPermissionDTO> partialUpdateTechvgPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TechvgPermissionDTO techvgPermissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TechvgPermission partially : {}, {}", id, techvgPermissionDTO);
        if (techvgPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, techvgPermissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!techvgPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TechvgPermissionDTO> result = techvgPermissionService.partialUpdate(techvgPermissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, techvgPermissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /techvg-permissions} : get all the techvgPermissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techvgPermissions in body.
     */
    @GetMapping("/techvg-permissions")
    public ResponseEntity<List<TechvgPermissionDTO>> getAllTechvgPermissions(
        TechvgPermissionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TechvgPermissions by criteria: {}", criteria);
        Page<TechvgPermissionDTO> page = techvgPermissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /techvg-permissions/count} : count all the techvgPermissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/techvg-permissions/count")
    public ResponseEntity<Long> countTechvgPermissions(TechvgPermissionCriteria criteria) {
        log.debug("REST request to count TechvgPermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(techvgPermissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /techvg-permissions/:id} : get the "id" techvgPermission.
     *
     * @param id the id of the techvgPermissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techvgPermissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/techvg-permissions/{id}")
    public ResponseEntity<TechvgPermissionDTO> getTechvgPermission(@PathVariable Long id) {
        log.debug("REST request to get TechvgPermission : {}", id);
        Optional<TechvgPermissionDTO> techvgPermissionDTO = techvgPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techvgPermissionDTO);
    }

    /**
     * {@code DELETE  /techvg-permissions/:id} : delete the "id" techvgPermission.
     *
     * @param id the id of the techvgPermissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/techvg-permissions/{id}")
    public ResponseEntity<Void> deleteTechvgPermission(@PathVariable Long id) {
        log.debug("REST request to delete TechvgPermission : {}", id);
        techvgPermissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
