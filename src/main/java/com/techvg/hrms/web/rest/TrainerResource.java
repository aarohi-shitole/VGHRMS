package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TrainerRepository;
import com.techvg.hrms.service.TrainerQueryService;
import com.techvg.hrms.service.TrainerService;
import com.techvg.hrms.service.criteria.TrainerCriteria;
import com.techvg.hrms.service.dto.TrainerDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Trainer}.
 */
@RestController
@RequestMapping("/api")
public class TrainerResource {

    private final Logger log = LoggerFactory.getLogger(TrainerResource.class);

    private static final String ENTITY_NAME = "trainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainerService trainerService;

    private final TrainerRepository trainerRepository;

    private final TrainerQueryService trainerQueryService;

    public TrainerResource(TrainerService trainerService, TrainerRepository trainerRepository, TrainerQueryService trainerQueryService) {
        this.trainerService = trainerService;
        this.trainerRepository = trainerRepository;
        this.trainerQueryService = trainerQueryService;
    }

    /**
     * {@code POST  /trainer} : Create a new trainer.
     *
     * @param trainerDTO the trainerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainerDTO, or with status {@code 400 (Bad Request)} if the trainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainer")
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody TrainerDTO trainerDTO) throws URISyntaxException {
        log.debug("REST request to save Trainer : {}", trainerDTO);
        if (trainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainerDTO result = trainerService.save(trainerDTO);
        return ResponseEntity
            .created(new URI("/api/trainers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainer/:id} : Updates an existing trainer.
     *
     * @param id the id of the trainerDTO to save.
     * @param trainerDTO the trainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainerDTO,
     * or with status {@code 400 (Bad Request)} if the trainerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainer/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainerDTO trainerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Trainer : {}, {}", id, trainerDTO);
        if (trainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainerDTO result = trainerService.update(trainerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trainer/:id} : Partial updates given fields of an existing trainer, field will ignore if it is null
     *
     * @param id the id of the trainerDTO to save.
     * @param trainerDTO the trainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainerDTO,
     * or with status {@code 400 (Bad Request)} if the trainerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trainer/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainerDTO> partialUpdateTrainer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainerDTO trainerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Trainer partially : {}, {}", id, trainerDTO);
        if (trainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainerDTO> result = trainerService.partialUpdate(trainerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trainer} : get all the trainers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainers in body.
     */
    @GetMapping("/trainer")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers(
        TrainerCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Trainers by criteria: {}", criteria);
        Page<TrainerDTO> page = trainerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainer/count} : count all the trainers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trainer/count")
    public ResponseEntity<Long> countTrainers(TrainerCriteria criteria) {
        log.debug("REST request to count Trainers by criteria: {}", criteria);
        return ResponseEntity.ok().body(trainerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trainer/:id} : get the "id" trainer.
     *
     * @param id the id of the trainerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainer/{id}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable Long id) {
        log.debug("REST request to get Trainer : {}", id);
        Optional<TrainerDTO> trainerDTO = trainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainerDTO);
    }

    /**
     * {@code DELETE  /trainer/:id} : delete the "id" trainer.
     *
     * @param id the id of the trainerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainer/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        log.debug("REST request to delete Trainer : {}", id);
        trainerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
