package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AssetApplicationRepository;
import com.techvg.hrms.service.AssetApplicationQueryService;
import com.techvg.hrms.service.AssetApplicationService;
import com.techvg.hrms.service.criteria.AssetApplicationCriteria;
import com.techvg.hrms.service.dto.AssetApplicationDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AssetApplication}.
 */
@RestController
@RequestMapping("/api")
public class AssetApplicationResource {

    private final Logger log = LoggerFactory.getLogger(AssetApplicationResource.class);

    private static final String ENTITY_NAME = "assetApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetApplicationService assetApplicationService;

    private final AssetApplicationRepository assetApplicationRepository;

    private final AssetApplicationQueryService assetApplicationQueryService;

    public AssetApplicationResource(
        AssetApplicationService assetApplicationService,
        AssetApplicationRepository assetApplicationRepository,
        AssetApplicationQueryService assetApplicationQueryService
    ) {
        this.assetApplicationService = assetApplicationService;
        this.assetApplicationRepository = assetApplicationRepository;
        this.assetApplicationQueryService = assetApplicationQueryService;
    }

    /**
     * {@code POST  /asset-applications} : Create a new assetApplication.
     *
     * @param assetApplicationDTO the assetApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetApplicationDTO, or with status {@code 400 (Bad Request)} if the assetApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-applications")
    public ResponseEntity<AssetApplicationDTO> createAssetApplication(@RequestBody AssetApplicationDTO assetApplicationDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssetApplication : {}", assetApplicationDTO);
        if (assetApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetApplicationDTO result = assetApplicationService.save(assetApplicationDTO);
        return ResponseEntity
            .created(new URI("/api/asset-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-applications/:id} : Updates an existing assetApplication.
     *
     * @param id the id of the assetApplicationDTO to save.
     * @param assetApplicationDTO the assetApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the assetApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-applications/{id}")
    public ResponseEntity<AssetApplicationDTO> updateAssetApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetApplicationDTO assetApplicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssetApplication : {}, {}", id, assetApplicationDTO);
        if (assetApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetApplicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetApplicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssetApplicationDTO result = assetApplicationService.update(assetApplicationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asset-applications/:id} : Partial updates given fields of an existing assetApplication, field will ignore if it is null
     *
     * @param id the id of the assetApplicationDTO to save.
     * @param assetApplicationDTO the assetApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the assetApplicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetApplicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asset-applications/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetApplicationDTO> partialUpdateAssetApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetApplicationDTO assetApplicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetApplication partially : {}, {}", id, assetApplicationDTO);
        if (assetApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetApplicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetApplicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetApplicationDTO> result = assetApplicationService.partialUpdate(assetApplicationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetApplicationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-applications} : get all the assetApplications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetApplications in body.
     */
    @GetMapping("/asset-applications")
    public ResponseEntity<List<AssetApplicationDTO>> getAllAssetApplications(
        AssetApplicationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AssetApplications by criteria: {}", criteria);
        Page<AssetApplicationDTO> page = assetApplicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-applications/count} : count all the assetApplications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/asset-applications/count")
    public ResponseEntity<Long> countAssetApplications(AssetApplicationCriteria criteria) {
        log.debug("REST request to count AssetApplications by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetApplicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-applications/:id} : get the "id" assetApplication.
     *
     * @param id the id of the assetApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-applications/{id}")
    public ResponseEntity<AssetApplicationDTO> getAssetApplication(@PathVariable Long id) {
        log.debug("REST request to get AssetApplication : {}", id);
        Optional<AssetApplicationDTO> assetApplicationDTO = assetApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetApplicationDTO);
    }

    /**
     * {@code DELETE  /asset-applications/:id} : delete the "id" assetApplication.
     *
     * @param id the id of the assetApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-applications/{id}")
    public ResponseEntity<Void> deleteAssetApplication(@PathVariable Long id) {
        log.debug("REST request to delete AssetApplication : {}", id);
        assetApplicationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
