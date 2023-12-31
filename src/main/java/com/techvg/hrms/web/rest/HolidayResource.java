package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.HolidayRepository;
import com.techvg.hrms.service.HolidayQueryService;
import com.techvg.hrms.service.HolidayService;
import com.techvg.hrms.service.criteria.DesignationCriteria;
import com.techvg.hrms.service.criteria.HolidayCriteria;
import com.techvg.hrms.service.dto.DesignationDTO;
import com.techvg.hrms.service.dto.HolidayDTO;
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

import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.hrms.domain.Holiday}.
 */
@RestController
@RequestMapping("/api")
public class HolidayResource {

    private final Logger log = LoggerFactory.getLogger(HolidayResource.class);

    private static final String ENTITY_NAME = "holiday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HolidayService holidayService;

    private final HolidayRepository holidayRepository;

    private final HolidayQueryService holidayQueryService;

    public HolidayResource(HolidayService holidayService, HolidayRepository holidayRepository, HolidayQueryService holidayQueryService) {
        this.holidayService = holidayService;
        this.holidayRepository = holidayRepository;
        this.holidayQueryService = holidayQueryService;
    }

    /**
     * {@code POST  /holiday} : Create a new holiday.
     *
     * @param holidayDTO the holidayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new holidayDTO, or with status {@code 400 (Bad Request)} if the holiday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/holiday")
    public ResponseEntity<HolidayDTO> createHoliday(@RequestBody HolidayDTO holidayDTO) throws URISyntaxException {
        log.debug("REST request to save Holiday : {}", holidayDTO);
        if (holidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new holiday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HolidayDTO result = holidayService.save(holidayDTO);
        return ResponseEntity
            .created(new URI("/api/holiday/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /holiday/:id} : Updates an existing holiday.
     *
     * @param id the id of the holidayDTO to save.
     * @param holidayDTO the holidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidayDTO,
     * or with status {@code 400 (Bad Request)} if the holidayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the holidayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/holiday/{id}")
    public ResponseEntity<HolidayDTO> updateHoliday(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HolidayDTO holidayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Holiday : {}, {}", id, holidayDTO);
        if (holidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holidayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holidayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HolidayDTO result = holidayService.update(holidayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holidayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /holiday/:id} : Partial updates given fields of an existing holiday, field will ignore if it is null
     *
     * @param id the id of the holidayDTO to save.
     * @param holidayDTO the holidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated holidayDTO,
     * or with status {@code 400 (Bad Request)} if the holidayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the holidayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the holidayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/holiday/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HolidayDTO> partialUpdateHoliday(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HolidayDTO holidayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Holiday partially : {}, {}", id, holidayDTO);
        if (holidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, holidayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!holidayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HolidayDTO> result = holidayService.partialUpdate(holidayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, holidayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /holiday} : get all the holiday.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of holidays in body.
     */
    @GetMapping("/holiday")
    public ResponseEntity<List<HolidayDTO>> getAllHolidays(
        HolidayCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Holidays by criteria: {}", criteria);
        Page<HolidayDTO> page = holidayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /holidays/count} : count all the holidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/holiday/count")
    public ResponseEntity<Long> countHolidays(HolidayCriteria criteria) {
        log.debug("REST request to count Holidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(holidayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /holiday/:id} : get the "id" holiday.
     *
     * @param id the id of the holidayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the holidayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/holiday/{id}")
    public ResponseEntity<HolidayDTO> getHoliday(@PathVariable Long id) {
        log.debug("REST request to get Holiday : {}", id);
        Optional<HolidayDTO> holidayDTO = holidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(holidayDTO);
    }

    /**
     * {@code DELETE  /holiday/:id} : delete the "id" holiday.
     *
     * @param id the id of the holidayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/holiday/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        log.debug("REST request to delete Holiday : {}", id);
        holidayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
