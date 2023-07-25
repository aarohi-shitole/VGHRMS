package com.techvg.hrms.scheduler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.techvg.hrms.service.ResignationService;
import com.techvg.hrms.service.UserManagment;
import com.techvg.hrms.service.UserQueryService;
import com.techvg.hrms.service.criteria.AdminUserCriteria;

import com.techvg.hrms.service.dto.AdminUserDTO;

import com.techvg.hrms.service.dto.ResignationDTO;

import tech.jhipster.service.filter.LongFilter;

@Component
public class ResignationScheduler {

	@Autowired
	private static final Logger log = LoggerFactory.getLogger(ResignationScheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ResignationService resignationService;

	@Autowired
	public UserQueryService userQueryService;

	@Autowired
	public UserManagment userManagment;

	@Scheduled(cron = "${cron.everydayresignation}")
	public void reportCurrentTime() {
		log.info("HRMS Schedular getup time is now {}", dateFormat.format(new Date()));
		System.out.println("sheduler on");
		try {
			deactiveResignationEmployee();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deactiveResignationEmployee() {
		log.info("In resignations");
		Pageable sortedByName = PageRequest.of(0, 1000, Sort.by("id")); // page,size,sort
		Page<ResignationDTO> resignationList = resignationService.findAll(sortedByName);

		if (!resignationList.isEmpty() && resignationList != null) {
			List<LocalDate> lastWorkingDays = new ArrayList<>();

			for (ResignationDTO resignationDTO1 : resignationList) {
				log.debug("ResignationList: " + resignationDTO1.toString());

				// get current date of month
				LocalDate currentDate = LocalDate.now();

				LocalDate lastWorkingDay = resignationDTO1.getLastWorkingDay().atZone(ZoneId.systemDefault())
						.toLocalDate();

				if (lastWorkingDay.isEqual(currentDate)) {
					if (resignationDTO1.getResignStatus().equals("Approved")) {
						lastWorkingDays.add(lastWorkingDay);

						for (LocalDate lastWorkingDay1 : lastWorkingDays) {
							AdminUserCriteria adminCriteria = new AdminUserCriteria();
							LongFilter empId = new LongFilter();
							empId.setEquals(resignationDTO1.getEmployeeId());
							adminCriteria.setEmployeeId(empId);

							String currentUserId1 = null;
							List<AdminUserDTO> list1 = userQueryService.findByCriteria(adminCriteria);

							if (list1 != null && !list1.isEmpty()) {
								for (AdminUserDTO adminUserDTO : list1) {

									currentUserId1 = adminUserDTO.getId();
									userManagment.deleteUser(currentUserId1);

									log.debug("!!!!!!!!!!!!!!!!REST request to get current logged in UserId : {}",
											currentUserId1);
								}
							}
						}
					}
				}
			}
		}
	}
}