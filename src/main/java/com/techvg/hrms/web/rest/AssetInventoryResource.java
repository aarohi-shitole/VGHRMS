package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AssetInventoryRepository;
import com.techvg.hrms.service.AssetInventoryQueryService;
import com.techvg.hrms.service.AssetInventoryService;
import com.techvg.hrms.service.criteria.AssetInventoryCriteria;
import com.techvg.hrms.service.dto.AssetInventoryDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.AssetInventory}.
 */
@RestController
@RequestMapping("/api")
public class AssetInventoryResource {

    private final Logger log = LoggerFactory.getLogger(AssetInventoryResource.class);

    private static final String ENTITY_NAME = "assetInventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetInventoryService assetInventoryService;

    private final AssetInventoryRepository assetInventoryRepository;

    private final AssetInventoryQueryService assetInventoryQueryService;

    public AssetInventoryResource(
        AssetInventoryService assetInventoryService,
        AssetInventoryRepository assetInventoryRepository,
        AssetInventoryQueryService assetInventoryQueryService
    ) {
        this.assetInventoryService = assetInventoryService;
        this.assetInventoryRepository = assetInventoryRepository;
        this.assetInventoryQueryService = assetInventoryQueryService;
    }

    /**
     * {@code POST  /asset-inventories} : Create a new assetInventory.
     *
     * @param assetInventoryDTO the assetInventoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetInventoryDTO, or with status {@code 400 (Bad Request)} if the assetInventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-inventories")
    public ResponseEntity<AssetInventoryDTO> createAssetInventory(@RequestBody AssetInventoryDTO assetInventoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssetInventory : {}", assetInventoryDTO);
        if (assetInventoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetInventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetInventoryDTO result = assetInventoryService.save(assetInventoryDTO);
        return ResponseEntity
            .created(new URI("/api/asset-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-inventories/:id} : Updates an existing assetInventory.
     *
     * @param id the id of the assetInventoryDTO to save.
     * @param assetInventoryDTO the assetInventoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetInventoryDTO,
     * or with status {@code 400 (Bad Request)} if the assetInventoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetInventoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-inventories/{id}")
    public ResponseEntity<AssetInventoryDTO> updateAssetInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetInventoryDTO assetInventoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssetInventory : {}, {}", id, assetInventoryDTO);
        if (assetInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetInventoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetInventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AssetInventoryDTO result = assetInventoryService.update(assetInventoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetInventoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asset-inventories/:id} : Partial updates given fields of an existing assetInventory, field will ignore if it is null
     *
     * @param id the id of the assetInventoryDTO to save.
     * @param assetInventoryDTO the assetInventoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetInventoryDTO,
     * or with status {@code 400 (Bad Request)} if the assetInventoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetInventoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetInventoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asset-inventories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetInventoryDTO> partialUpdateAssetInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AssetInventoryDTO assetInventoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetInventory partially : {}, {}", id, assetInventoryDTO);
        if (assetInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetInventoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetInventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetInventoryDTO> result = assetInventoryService.partialUpdate(assetInventoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetInventoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-inventories} : get all the assetInventories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetInventories in body.
     */
    @GetMapping("/asset-inventories")
    public ResponseEntity<List<AssetInventoryDTO>> getAllAssetInventories(
        AssetInventoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AssetInventories by criteria: {}", criteria);
        Page<AssetInventoryDTO> page = assetInventoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-inventories/count} : count all the assetInventories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/asset-inventories/count")
    public ResponseEntity<Long> countAssetInventories(AssetInventoryCriteria criteria) {
        log.debug("REST request to count AssetInventories by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetInventoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-inventories/:id} : get the "id" assetInventory.
     *
     * @param id the id of the assetInventoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetInventoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-inventories/{id}")
    public ResponseEntity<AssetInventoryDTO> getAssetInventory(@PathVariable Long id) {
        log.debug("REST request to get AssetInventory : {}", id);
        Optional<AssetInventoryDTO> assetInventoryDTO = assetInventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetInventoryDTO);
    }

    /**
     * {@code DELETE  /asset-inventories/:id} : delete the "id" assetInventory.
     *
     * @param id the id of the assetInventoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-inventories/{id}")
    public ResponseEntity<Void> deleteAssetInventory(@PathVariable Long id) {
        log.debug("REST request to delete AssetInventory : {}", id);
        assetInventoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
