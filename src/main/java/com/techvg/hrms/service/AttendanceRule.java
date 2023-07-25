package com.techvg.hrms.service;

import com.techvg.hrms.domain.Attendance;
import com.techvg.hrms.service.criteria.AttendanceCriteria;
import com.techvg.hrms.service.criteria.TimeSheetCriteria;
import com.techvg.hrms.service.criteria.TimeSheetCriteria;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import com.techvg.hrms.web.rest.AttendanceResource;
import com.techvg.hrms.web.rest.AttendanceResource;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.PaginationUtil;

@Service
public class AttendanceRule {

	private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

	private static final String ENTITY_NAME = "attendance";

	@Autowired
	private TimeSheetService timeSheetService;

	@Autowired
	private TimeSheetQueryService timeSheetQueryService;

	@Autowired
	private AttendanceQueryService attendanceQueryService;

	@Autowired
	private AttendanceService attendanceService;

	AttendanceDTO globalAttendance = new AttendanceDTO();

	AttendanceDTO globalAttendanceResult = new AttendanceDTO();

	public void saveTimeSheet(AttendanceDTO result) {
		globalAttendance = result;
		String formattedTime = result.getDate().toString().substring(11, 19);

		log.debug("time page: {}", formattedTime);
		TimeSheetDTO timeSheet = new TimeSheetDTO();
		timeSheet.setTime(formattedTime);
		timeSheet.setDate(result.getDate());
		timeSheet.setHasCheckedIn(result.getHasCheckedIn());
		timeSheet.setStatus(result.getStatus());
		timeSheet.setAttendance(result);

		TimeSheetDTO timesheetResult = timeSheetService.save(timeSheet);

		if (timesheetResult != null) {
			try {
				getTimeSheet(timesheetResult);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void getTimeSheet(TimeSheetDTO timesheetResult) throws ParseException {
		Pageable pageable = PageRequest.of(0, 30);

		InstantFilter instantFilter = new InstantFilter();
		TimeSheetCriteria criteria = new TimeSheetCriteria();

		LongFilter attendanceFilter = new LongFilter();
		attendanceFilter.setEquals(timesheetResult.getAttendance().getId());
		criteria.setAttendanceId(attendanceFilter);
		
		// Create a formatter for the date string
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		// Parse the original date into a LocalDate object
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Date.from(timesheetResult.getDate()));

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		LocalDate localDate = cal.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDate();
		// Subtract 1 day from the date
		// LocalDate previousDay = localDate.minusDays(1);

		// Construct startDateTime and endDateTime using the previous day
		LocalDateTime startDateTime = localDate.atTime(LocalTime.of(0, 0, 0));
		LocalDateTime endDateTime = localDate.atTime(LocalTime.of(23, 59, 59));

		// Use the new start and end dates in the search criteria
		ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
		ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
		Instant startInstant = Instant.parse(startZonedDateTime.format(dateFormat));
		Instant endInstant = Instant.parse(endZonedDateTime.format(dateFormat));
		instantFilter.setGreaterThanOrEqual(startInstant);
		instantFilter.setLessThanOrEqual(endInstant);
		criteria.setDate(instantFilter);
		Page<TimeSheetDTO> page = timeSheetQueryService.findByCriteria(criteria, pageable);
		List<TimeSheetDTO> list = page.getContent();
		log.debug("searched list: {}", list);

		try {
			if (list != null) {
				this.calculateLoginHours(list);
			}
		} catch (NullPointerException e) {
			System.out.print("NullPointerException Caught");
		}
	}

	private void calculateLoginHours(List<TimeSheetDTO> list) {
		List<LocalDateTime> inDates = new ArrayList<>();
		List<LocalDateTime> outDates = new ArrayList<>();
		for (TimeSheetDTO timeSheet : list) {
			if (timeSheet.getId() != null) {
				if (timeSheet.getHasCheckedIn()) {
					LocalDateTime inDate = LocalDateTime.ofEpochSecond(timeSheet.getDate().getEpochSecond(),
							timeSheet.getDate().getNano(), ZoneOffset.UTC);
					inDates.add(inDate);
					log.debug("inDates: {}", inDates);
				} else {
					LocalDateTime outDate = LocalDateTime.ofEpochSecond(timeSheet.getDate().getEpochSecond(),
							timeSheet.getDate().getNano(), ZoneOffset.UTC);
					outDates.add(outDate);
					log.debug("outDates: {}", outDates);
				}
			}
		}

		if (inDates.size() == outDates.size() && !inDates.isEmpty()) {
			long totalSeconds = 0;
			for (int i = 0; i < inDates.size(); i++) {
				LocalDateTime inDate = inDates.get(i);
				LocalDateTime outDate = outDates.get(i);
				String inTime = inDate.format(DateTimeFormatter.ofPattern("hh:mm a"));
				String outTime = outDate.format(DateTimeFormatter.ofPattern("hh:mm a"));
				log.debug("inTime: {}, outTime: {}", inTime, outTime);

				LocalTime punchInTime = LocalTime.parse(inTime, DateTimeFormatter.ofPattern("hh:mm a"));
				LocalTime punchOutTime = LocalTime.parse(outTime, DateTimeFormatter.ofPattern("hh:mm a"));

				Duration duration = Duration.between(punchInTime, punchOutTime);
				totalSeconds += duration.getSeconds();
			}

			Duration totalDuration = Duration.ofSeconds(totalSeconds);
			String diffString = String.format("%02d:%02d", totalDuration.toHours(), (totalDuration.toMinutes() % 60));
			log.debug("Time difference in hh:mm format: {}", diffString);

			globalAttendance.setHours(diffString);
			globalAttendanceResult = attendanceService.save(globalAttendance);
		} else {
			globalAttendanceResult = attendanceService.save(globalAttendance);
            log.debug("The number of inDates and outDates must be the same and greater than 0 to calculate the time difference.");
		}
	}

	public AttendanceDTO getUpdatedAttendace() {
		return globalAttendanceResult;
	}

	public AttendanceDTO instanciateAttendance(AttendanceDTO attendanceDTO) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

		// find current date and time

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		Date currentDate = calendar.getTime();
		String formattedDate = dateFormat.format(currentDate);
		Instant currentDateTime = Instant.parse(formattedDate);

		// find weekday form current date
		calendar.setTime(currentDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String[] weekdays = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		String day = weekdays[dayOfWeek];
		attendanceDTO.setDate(currentDateTime);
		attendanceDTO.setStatus("A");
		attendanceDTO.setDay(day);
		log.debug("attendanceDTO list: {}", attendanceDTO);
		return attendanceDTO;
	}

	public AttendanceDTO checkAttendance(AttendanceDTO attendanceDTO, String day) {
		List<AttendanceDTO> list;
		AttendanceDTO attendanceDTO2 = new AttendanceDTO();
		Pageable pageable = PageRequest.of(0, 30);

		InstantFilter instantFilter = new InstantFilter();
		LongFilter idFilter = new LongFilter();
		idFilter.setEquals(attendanceDTO.getEmployeeId());
		AttendanceCriteria criteria = new AttendanceCriteria();
		criteria.setEmployeeId(idFilter);

		// Create a formatter for the date string
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		// Parse the original date into a LocalDate object
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(Date.from(timesheetResult.getDate()));

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		LocalDate localDate = cal.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDate();

		if (day == "YESTERDAY") {
			// Subtract 1 day from the date
			LocalDate previousDay = localDate.minusDays(1);
			LocalDateTime startDateTime2 = previousDay.atTime(LocalTime.of(0, 0, 0));
			LocalDateTime endDateTime2 = previousDay.atTime(LocalTime.of(23, 59, 59));

			// Use the new start and end dates in the search criteria
			ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime2, ZoneId.systemDefault());
			ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime2, ZoneId.systemDefault());
			Instant startInstant = Instant.parse(startZonedDateTime.format(dateFormat));
			Instant endInstant = Instant.parse(endZonedDateTime.format(dateFormat));
			instantFilter.setGreaterThanOrEqual(startInstant);
			instantFilter.setLessThanOrEqual(endInstant);
			criteria.setDate(instantFilter);
			Page<AttendanceDTO> page = attendanceQueryService.findByCriteria(criteria, pageable);
			list = page.getContent();
			log.debug("Yesterday list: {}", list);
		} else {
			LocalDateTime startDateTime = localDate.atTime(LocalTime.of(0, 0, 0));
			LocalDateTime endDateTime = localDate.atTime(LocalTime.of(23, 59, 59));

			// Use the new start and end dates in the search criteria
			ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
			ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
			Instant startInstant = Instant.parse(startZonedDateTime.format(dateFormat));
			Instant endInstant = Instant.parse(endZonedDateTime.format(dateFormat));
			instantFilter.setGreaterThanOrEqual(startInstant);
			instantFilter.setLessThanOrEqual(endInstant);
			criteria.setDate(instantFilter);
			Page<AttendanceDTO> page = attendanceQueryService.findByCriteria(criteria, pageable);
			list = page.getContent();
			log.debug("today list: {}", list);
		}

		if (!list.isEmpty()) {
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
			Date currentDate = calendar.getTime();
			String formattedDate = dateFormat2.format(currentDate);
			Instant currentDateTime = Instant.parse(formattedDate);

			// attendanceDTO2 = list.get(list.size() - 1);
			AttendanceDTO lastObj = list.get(list.size() - 1);

			// AttendanceDTO lastObj = list.get(list.size() - 1);
			log.debug("you are in if part : {}", lastObj);
			if (day == "YESTERDAY") {
				if (attendanceDTO.getHasCheckedIn() == true) {
					Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
					LocalDate localDate2 = cal2.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDate();

					LocalDate previousDay2 = localDate2.minusDays(1);
					LocalDateTime date = previousDay2.atTime(LocalTime.of(23, 59, 59));
					ZonedDateTime endZonedDateTime = ZonedDateTime.of(date, ZoneId.systemDefault());
					currentDateTime = Instant.parse(endZonedDateTime.format(dateFormat));
					// log.debug("222 : {}", lastObj);
					attendanceDTO2 = attendanceDTO;
					// attendanceDTO2.setDate(punchoutDate);
					attendanceDTO2.setHasCheckedIn(false);
					log.debug("Yesterday last obj: {}", attendanceDTO2);
					// throw new BadRequestAlertException("Punch out not exist", ENTITY_NAME, "");
				}
			} else {
				if (attendanceDTO.getHasCheckedIn() == false && lastObj.getHasCheckedIn() == false) {
					// attendanceDTO2.setHasCheckedIn(true);
					// log.debug("111 : {}", attendanceDTO2);

					throw new BadRequestAlertException("Punch in not exist", ENTITY_NAME, "");
				}
				if (attendanceDTO.getHasCheckedIn() == true && lastObj.getHasCheckedIn() == true) {
					// log.debug("222 : {}", lastObj);
					// attendanceDTO2.setHasCheckedIn(false);
					throw new BadRequestAlertException("Punch out not exist", ENTITY_NAME, "");
				}
			}

			attendanceDTO2 = this.instanciateAttendance(attendanceDTO2);
			attendanceDTO2.setId(lastObj.getId());
			attendanceDTO2.setHasCheckedIn(attendanceDTO.getHasCheckedIn());
			attendanceDTO2.setEmployeeId(attendanceDTO.getEmployeeId());
			attendanceDTO2.setDeviceInfo(attendanceDTO.getDeviceInfo());
			attendanceDTO2.setLatitude(attendanceDTO.getLatitude());
			attendanceDTO2.setLongitude(attendanceDTO.getLatitude());
			attendanceDTO2.setDate(currentDateTime);
			attendanceDTO2.setHours(lastObj.getHours());
			log.debug("final object : {}", attendanceDTO2);
		} else {
			this.checkYesterdayAttendance(attendanceDTO.getEmployeeId());
			log.debug("before submit : {}", attendanceDTO2);
			attendanceDTO2 = this.instanciateAttendance(attendanceDTO);
			log.debug("you are in else part : {}", attendanceDTO2);
		}
		return attendanceDTO2;
	}

	private void checkYesterdayAttendance(Long employeeId) {
		// AttendanceDTO attendanceDTO2 = new AttendanceDTO();
		Pageable pageable = PageRequest.of(0, 10);

		InstantFilter instantFilter = new InstantFilter();
		LongFilter idFilter = new LongFilter();
		idFilter.setEquals(employeeId);
		AttendanceCriteria criteria = new AttendanceCriteria();
		criteria.setEmployeeId(idFilter);
		// Create a formatter for the date string
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		LocalDate localDate = cal.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDate();
		// Subtract 1 day from the date
		LocalDate previousDay = localDate.minusDays(1);

		// Construct startDateTime and endDateTime using the previous day
		LocalDateTime startDateTime = previousDay.atTime(LocalTime.of(0, 0, 0));
		LocalDateTime endDateTime = previousDay.atTime(LocalTime.of(23, 59, 59));

		// Use the new start and end dates in the search criteria
		ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
		ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
		Instant startInstant = Instant.parse(startZonedDateTime.format(dateFormat));
		Instant endInstant = Instant.parse(endZonedDateTime.format(dateFormat));
		instantFilter.setGreaterThanOrEqual(startInstant);
		instantFilter.setLessThanOrEqual(endInstant);
		criteria.setDate(instantFilter);
		Page<AttendanceDTO> page = attendanceQueryService.findByCriteria(criteria, pageable);
		List<AttendanceDTO> list = page.getContent();
		log.debug("attendance list: {}", list);
		if (list.size() >= 1) {
			AttendanceDTO lastObj = list.get(list.size() - 1);
			if (!list.isEmpty()) {
				if (lastObj.getHasCheckedIn() == true) {
					AttendanceDTO yesterDayLastObj = list.get(list.size() - 1);
					AttendanceDTO updateYesterdayAttendance = this.checkAttendance(yesterDayLastObj, "YESTERDAY");
					AttendanceDTO result1 = attendanceService.save(updateYesterdayAttendance);
					this.saveTimeSheet(result1);
					this.getUpdatedAttendace();
				}
			}
		}
	}
}
