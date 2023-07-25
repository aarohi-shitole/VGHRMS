package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TdsRepository;
import com.techvg.hrms.service.TdsQueryService;
import com.techvg.hrms.service.TdsService;
import com.techvg.hrms.service.criteria.TdsCriteria;
import com.techvg.hrms.service.dto.TdsDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Tds}.
 */
@RestController
@RequestMapping("/api")
public class TdsResource {

    private final Logger log = LoggerFactory.getLogger(TdsResource.class);

    private static final String ENTITY_NAME = "tds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TdsService tdsService;

    private final TdsRepository tdsRepository;

    private final TdsQueryService tdsQueryService;

    public TdsResource(TdsService tdsService, TdsRepository tdsRepository, TdsQueryService tdsQueryService) {
        this.tdsService = tdsService;
        this.tdsRepository = tdsRepository;
        this.tdsQueryService = tdsQueryService;
    }

    /**
     * {@code POST  /tds} : Create a new tds.
     *
     * @param tdsDTO the tdsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tdsDTO, or with status {@code 400 (Bad Request)} if the tds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tds")
    public ResponseEntity<TdsDTO> createTds(@RequestBody TdsDTO tdsDTO) throws URISyntaxException {
        log.debug("REST request to save Tds : {}", tdsDTO);
        if (tdsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TdsDTO result = tdsService.save(tdsDTO);
        return ResponseEntity
            .created(new URI("/api/tds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tds/:id} : Updates an existing tds.
     *
     * @param id the id of the tdsDTO to save.
     * @param tdsDTO the tdsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tdsDTO,
     * or with status {@code 400 (Bad Request)} if the tdsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tdsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tds/{id}")
    public ResponseEntity<TdsDTO> updateTds(@PathVariable(value = "id", required = false) final Long id, @RequestBody TdsDTO tdsDTO)
        throws URISyntaxException {
        log.debug("REST request to update Tds : {}, {}", id, tdsDTO);
        if (tdsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tdsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tdsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TdsDTO result = tdsService.update(tdsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tdsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tds/:id} : Partial updates given fields of an existing tds, field will ignore if it is null
     *
     * @param id the id of the tdsDTO to save.
     * @param tdsDTO the tdsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tdsDTO,
     * or with status {@code 400 (Bad Request)} if the tdsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tdsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tdsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TdsDTO> partialUpdateTds(@PathVariable(value = "id", required = false) final Long id, @RequestBody TdsDTO tdsDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Tds partially : {}, {}", id, tdsDTO);
        if (tdsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tdsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tdsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TdsDTO> result = tdsService.partialUpdate(tdsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tdsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tds} : get all the tds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tds in body.
     */
    @GetMapping("/tds")
    public ResponseEntity<List<TdsDTO>> getAllTds(TdsCriteria criteria, @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get Tds by criteria: {}", criteria);
        Page<TdsDTO> page = tdsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tds/count} : count all the tds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tds/count")
    public ResponseEntity<Long> countTds(TdsCriteria criteria) {
        log.debug("REST request to count Tds by criteria: {}", criteria);
        return ResponseEntity.ok().body(tdsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tds/:id} : get the "id" tds.
     *
     * @param id the id of the tdsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tdsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tds/{id}")
    public ResponseEntity<TdsDTO> getTds(@PathVariable Long id) {
        log.debug("REST request to get Tds : {}", id);
        Optional<TdsDTO> tdsDTO = tdsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tdsDTO);
    }

    /**
     * {@code DELETE  /tds/:id} : delete the "id" tds.
     *
     * @param id the id of the tdsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tds/{id}")
    public ResponseEntity<Void> deleteTds(@PathVariable Long id) {
        log.debug("REST request to delete Tds : {}", id);
        tdsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
