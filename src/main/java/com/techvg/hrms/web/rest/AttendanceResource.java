package com.techvg.hrms.web.rest;

import com.techvg.hrms.repository.AttendanceRepository;
import com.techvg.hrms.service.AttendanceQueryService;
import com.techvg.hrms.service.AttendanceRule;
import com.techvg.hrms.service.AttendanceService;
import com.techvg.hrms.service.TimeSheetService;
import com.techvg.hrms.service.criteria.AttendanceCriteria;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.techvg.hrms.domain.Attendance}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

	private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

	private static final String ENTITY_NAME = "attendance";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AttendanceService attendanceService;

	private final TimeSheetService timeSheetService;

	private final AttendanceRepository attendanceRepository;

	private final AttendanceQueryService attendanceQueryService;

	@Autowired
	private AttendanceRule attendanceRule;

	public AttendanceResource(AttendanceService attendanceService, AttendanceRepository attendanceRepository,
			AttendanceQueryService attendanceQueryService, TimeSheetService timeSheetService) {
		this.attendanceService = attendanceService;
		this.timeSheetService = timeSheetService;
		this.attendanceRepository = attendanceRepository;
		this.attendanceQueryService = attendanceQueryService;
	}

	/**
	 * {@code POST  /attendances} : Create a new attendance.
	 *
	 * @param attendanceDTO the attendanceDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new attendanceDTO, or with status {@code 400 (Bad Request)}
	 *         if the attendance has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/attendances")
	public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO)
			throws URISyntaxException {
		log.debug("REST request to save Attendance : {}", attendanceDTO);
		if (attendanceDTO.getId() != null) {
			throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
		}

		AttendanceDTO result = attendanceService.save(attendanceDTO);
		// -----------------saving time
		// Sheet---------------------------------------------------------------------------------------
//		attendanceRule.saveTimeSheet(result);

		return ResponseEntity
				.created(new URI("/api/attendances/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /attendances/:id} : Updates an existing attendance.
	 *
	 * @param id            the id of the attendanceDTO to save.
	 * @param attendanceDTO the attendanceDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated attendanceDTO, or with status {@code 400 (Bad Request)}
	 *         if the attendanceDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the attendanceDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/attendances/{id}")
	public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable(value = "id", required = false) final Long id,
			@RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
		log.debug("REST request to update Attendance : {}, {}", id, attendanceDTO);
		if (attendanceDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, attendanceDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!attendanceRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		AttendanceDTO result = attendanceService.update(attendanceDTO);
		// -----------------saving time
		// Sheet---------------------------------------------------------------------------------------
		attendanceRule.saveTimeSheet(result);
		AttendanceDTO result1 = attendanceRule.getUpdatedAttendace();
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				attendanceDTO.getId().toString())).body(result1);
	}

	/**
	 * {@code PATCH  /attendances/:id} : Partial updates given fields of an existing
	 * attendance, field will ignore if it is null
	 *
	 * @param id            the id of the attendanceDTO to save.
	 * @param attendanceDTO the attendanceDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated attendanceDTO, or with status {@code 400 (Bad Request)}
	 *         if the attendanceDTO is not valid, or with status
	 *         {@code 404 (Not Found)} if the attendanceDTO is not found, or with
	 *         status {@code 500 (Internal Server Error)} if the attendanceDTO
	 *         couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PatchMapping(value = "/attendances/{id}", consumes = { "application/json", "application/merge-patch+json" })
	public ResponseEntity<AttendanceDTO> partialUpdateAttendance(
			@PathVariable(value = "id", required = false) final Long id, @RequestBody AttendanceDTO attendanceDTO)
			throws URISyntaxException {
		log.debug("REST request to partial update Attendance partially : {}, {}", id, attendanceDTO);
		if (attendanceDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, attendanceDTO.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!attendanceRepository.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<AttendanceDTO> result = attendanceService.partialUpdate(attendanceDTO);

		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true,
				ENTITY_NAME, attendanceDTO.getId().toString()));
	}

	/**
	 * {@code GET  /attendances} : get all the attendances.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of attendances in body.
	 */
	@GetMapping("/attendances")
	public ResponseEntity<List<AttendanceDTO>> getAllAttendances(AttendanceCriteria criteria,
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get Attendances by criteria: {}", criteria);
		Page<AttendanceDTO> page = attendanceQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /attendances/count} : count all the attendances.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/attendances/count")
	public ResponseEntity<Long> countAttendances(AttendanceCriteria criteria) {
		log.debug("REST request to count Attendances by criteria: {}", criteria);
		return ResponseEntity.ok().body(attendanceQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /attendances/:id} : get the "id" attendance.
	 *
	 * @param id the id of the attendanceDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the attendanceDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/attendances/{id}")
	public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable Long id) {
		log.debug("REST request to get Attendance : {}", id);
		Optional<AttendanceDTO> attendanceDTO = attendanceService.findOne(id);
		return ResponseUtil.wrapOrNotFound(attendanceDTO);
	}

	/**
	 * {@code DELETE  /attendances/:id} : delete the "id" attendance.
	 *
	 * @param id the id of the attendanceDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/attendances/{id}")
	public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
		log.debug("REST request to delete Attendance : {}", id);
		attendanceService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	@PostMapping("/attendances/punchin-punchout")
	public ResponseEntity<AttendanceDTO> postAttendance(@RequestBody AttendanceDTO attendanceDTO)
			throws URISyntaxException {
		log.debug("REST request to save Attendance : {}", attendanceDTO);

		AttendanceDTO updatedAttendanceDto = attendanceRule.checkAttendance(attendanceDTO, "TODAY");

//		AttendanceDTO updatedAttendanceDto = attendanceRule.instanciateAttendance(attendanceDTO);

		AttendanceDTO result1 = attendanceService.save(updatedAttendanceDto);
		// --------------------------------Time Sheet
		// Save---------------------------------------------
		attendanceRule.saveTimeSheet(result1);
		AttendanceDTO result2 = attendanceRule.getUpdatedAttendace();
		return ResponseEntity
				.created(new URI("/api/attendances/" + result2.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result2.getId().toString()))
				.body(result2);

	}
}
