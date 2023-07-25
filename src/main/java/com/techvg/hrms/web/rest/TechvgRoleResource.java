package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TechvgRoleRepository;
import com.techvg.hrms.service.TechvgRoleQueryService;
import com.techvg.hrms.service.TechvgRoleService;
import com.techvg.hrms.service.criteria.TechvgRoleCriteria;
import com.techvg.hrms.service.dto.TechvgRoleDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.TechvgRole}.
 */
@RestController
@RequestMapping("/api")
public class TechvgRoleResource {

    private final Logger log = LoggerFactory.getLogger(TechvgRoleResource.class);

    private static final String ENTITY_NAME = "techvgRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechvgRoleService techvgRoleService;

    private final TechvgRoleRepository techvgRoleRepository;

    private final TechvgRoleQueryService techvgRoleQueryService;

    public TechvgRoleResource(
        TechvgRoleService techvgRoleService,
        TechvgRoleRepository techvgRoleRepository,
        TechvgRoleQueryService techvgRoleQueryService
    ) {
        this.techvgRoleService = techvgRoleService;
        this.techvgRoleRepository = techvgRoleRepository;
        this.techvgRoleQueryService = techvgRoleQueryService;
    }

    /**
     * {@code POST  /techvg-roles} : Create a new techvgRole.
     *
     * @param techvgRoleDTO the techvgRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new techvgRoleDTO, or with status {@code 400 (Bad Request)} if the techvgRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/techvg-roles")
    public ResponseEntity<TechvgRoleDTO> createTechvgRole(@Valid @RequestBody TechvgRoleDTO techvgRoleDTO) throws URISyntaxException {
        log.debug("REST request to save TechvgRole : {}", techvgRoleDTO);
        if (techvgRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new techvgRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechvgRoleDTO result = techvgRoleService.save(techvgRoleDTO);
        return ResponseEntity
            .created(new URI("/api/techvg-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /techvg-roles/:id} : Updates an existing techvgRole.
     *
     * @param id the id of the techvgRoleDTO to save.
     * @param techvgRoleDTO the techvgRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techvgRoleDTO,
     * or with status {@code 400 (Bad Request)} if the techvgRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the techvgRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/techvg-roles/{id}")
    public ResponseEntity<TechvgRoleDTO> updateTechvgRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TechvgRoleDTO techvgRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TechvgRole : {}, {}", id, techvgRoleDTO);
        if (techvgRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, techvgRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!techvgRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TechvgRoleDTO result = techvgRoleService.update(techvgRoleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, techvgRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /techvg-roles/:id} : Partial updates given fields of an existing techvgRole, field will ignore if it is null
     *
     * @param id the id of the techvgRoleDTO to save.
     * @param techvgRoleDTO the techvgRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated techvgRoleDTO,
     * or with status {@code 400 (Bad Request)} if the techvgRoleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the techvgRoleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the techvgRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/techvg-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TechvgRoleDTO> partialUpdateTechvgRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TechvgRoleDTO techvgRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TechvgRole partially : {}, {}", id, techvgRoleDTO);
        if (techvgRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, techvgRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!techvgRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TechvgRoleDTO> result = techvgRoleService.partialUpdate(techvgRoleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, techvgRoleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /techvg-roles} : get all the techvgRoles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of techvgRoles in body.
     */
    @GetMapping("/techvg-roles")
    public ResponseEntity<List<TechvgRoleDTO>> getAllTechvgRoles(
        TechvgRoleCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TechvgRoles by criteria: {}", criteria);
        Page<TechvgRoleDTO> page = techvgRoleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /techvg-roles/count} : count all the techvgRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/techvg-roles/count")
    public ResponseEntity<Long> countTechvgRoles(TechvgRoleCriteria criteria) {
        log.debug("REST request to count TechvgRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(techvgRoleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /techvg-roles/:id} : get the "id" techvgRole.
     *
     * @param id the id of the techvgRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the techvgRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/techvg-roles/{id}")
    public ResponseEntity<TechvgRoleDTO> getTechvgRole(@PathVariable Long id) {
        log.debug("REST request to get TechvgRole : {}", id);
        Optional<TechvgRoleDTO> techvgRoleDTO = techvgRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(techvgRoleDTO);
    }

    /**
     * {@code DELETE  /techvg-roles/:id} : delete the "id" techvgRole.
     *
     * @param id the id of the techvgRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/techvg-roles/{id}")
    public ResponseEntity<Void> deleteTechvgRole(@PathVariable Long id) {
        log.debug("REST request to delete TechvgRole : {}", id);
        techvgRoleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
