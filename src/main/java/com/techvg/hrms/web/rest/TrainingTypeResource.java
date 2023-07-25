package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.TrainingTypeRepository;
import com.techvg.hrms.service.TrainingTypeQueryService;
import com.techvg.hrms.service.TrainingTypeService;
import com.techvg.hrms.service.criteria.TrainingTypeCriteria;
import com.techvg.hrms.service.dto.TrainingTypeDTO;
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
 * REST controller for managing {@link com.techvg.hrms.domain.TrainingType}.
 */
@RestController
@RequestMapping("/api")
public class TrainingTypeResource {

    private final Logger log = LoggerFactory.getLogger(TrainingTypeResource.class);

    private static final String ENTITY_NAME = "trainingType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingTypeService trainingTypeService;

    private final TrainingTypeRepository trainingTypeRepository;

    private final TrainingTypeQueryService trainingTypeQueryService;

    public TrainingTypeResource(
        TrainingTypeService trainingTypeService,
        TrainingTypeRepository trainingTypeRepository,
        TrainingTypeQueryService trainingTypeQueryService
    ) {
        this.trainingTypeService = trainingTypeService;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeQueryService = trainingTypeQueryService;
    }

    /**
     * {@code POST  /training-types} : Create a new trainingType.
     *
     * @param trainingTypeDTO the trainingTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingTypeDTO, or with status {@code 400 (Bad Request)} if the trainingType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-types")
    public ResponseEntity<TrainingTypeDTO> createTrainingType(@RequestBody TrainingTypeDTO trainingTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TrainingType : {}", trainingTypeDTO);
        if (trainingTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingTypeDTO result = trainingTypeService.save(trainingTypeDTO);
        return ResponseEntity
            .created(new URI("/api/training-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-types/:id} : Updates an existing trainingType.
     *
     * @param id the id of the trainingTypeDTO to save.
     * @param trainingTypeDTO the trainingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the trainingTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-types/{id}")
    public ResponseEntity<TrainingTypeDTO> updateTrainingType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainingTypeDTO trainingTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainingType : {}, {}", id, trainingTypeDTO);
        if (trainingTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainingTypeDTO result = trainingTypeService.update(trainingTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /training-types/:id} : Partial updates given fields of an existing trainingType, field will ignore if it is null
     *
     * @param id the id of the trainingTypeDTO to save.
     * @param trainingTypeDTO the trainingTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingTypeDTO,
     * or with status {@code 400 (Bad Request)} if the trainingTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainingTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainingTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/training-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainingTypeDTO> partialUpdateTrainingType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainingTypeDTO trainingTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainingType partially : {}, {}", id, trainingTypeDTO);
        if (trainingTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainingTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainingTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainingTypeDTO> result = trainingTypeService.partialUpdate(trainingTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /training-types} : get all the trainingTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingTypes in body.
     */
    @GetMapping("/training-types")
    public ResponseEntity<List<TrainingTypeDTO>> getAllTrainingTypes(
        TrainingTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TrainingTypes by criteria: {}", criteria);
        Page<TrainingTypeDTO> page = trainingTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /training-types/count} : count all the trainingTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/training-types/count")
    public ResponseEntity<Long> countTrainingTypes(TrainingTypeCriteria criteria) {
        log.debug("REST request to count TrainingTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(trainingTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /training-types/:id} : get the "id" trainingType.
     *
     * @param id the id of the trainingTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-types/{id}")
    public ResponseEntity<TrainingTypeDTO> getTrainingType(@PathVariable Long id) {
        log.debug("REST request to get TrainingType : {}", id);
        Optional<TrainingTypeDTO> trainingTypeDTO = trainingTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingTypeDTO);
    }

    /**
     * {@code DELETE  /training-types/:id} : delete the "id" trainingType.
     *
     * @param id the id of the trainingTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-types/{id}")
    public ResponseEntity<Void> deleteTrainingType(@PathVariable Long id) {
        log.debug("REST request to delete TrainingType : {}", id);
        trainingTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
