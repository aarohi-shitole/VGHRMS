package com.techvg.hrms.scheduler;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.techvg.hrms.service.DesignationQueryService;
import com.techvg.hrms.service.EmployeeService;
import com.techvg.hrms.service.PromotionQueryService;
import com.techvg.hrms.service.criteria.DesignationCriteria;
import com.techvg.hrms.service.criteria.PromotionCriteria;
import com.techvg.hrms.service.dto.DesignationDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.PromotionDTO;

import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.StringFilter;
@Component
public class PromotionScheduler {
	
	 @Autowired
	 private static final Logger log = LoggerFactory.getLogger(PromotionScheduler.class);
	 
	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	 @Autowired
	 private PromotionQueryService promotionQueryService;
	 
	 @Autowired
	 private EmployeeService employeeService;
	 
	 @Autowired
	 private DesignationQueryService designationQueryService;
	 
	 @Scheduled(cron = "${cron.everyday}")
	 public void reportCurrentTime() {
	     log.info("HRMS Schedular getup time is now {}", dateFormat.format(new Date()));
	     System.out.println("sheduler on");
	        try {
	        	checkPromotedEmployee();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 private void checkPromotedEmployee() {
		 InstantFilter instantFilter = new InstantFilter();
		 PromotionCriteria criteria = new PromotionCriteria();

			// Create a formatter for the date string
		 	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		 	
		 	// find current date and time
		 	Calendar calendar = Calendar.getInstance();
	        Date currentDate = calendar.getTime();

	        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = dateFormat1.format(currentDate);

	        try {
	            Date parsedDate = dateFormat1.parse(formattedDate);
	            Instant currentDateTime = parsedDate.toInstant();

	            System.out.println("Current Date and Time: " + currentDateTime);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
			LocalDate localDate = cal.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDate();

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
			criteria.setPromotiedDate(instantFilter);
			List<PromotionDTO> list = promotionQueryService.findByCriteria(criteria);
			log.debug("searched list: {}", list);
			try {
				if (!list.isEmpty()) {
					this.checkPromotionList(list);
				}
			} catch (NullPointerException e) {
				System.out.print("NullPointerException Caught");
			}
	 	}
			//check promotion list
			private List<PromotionDTO> checkPromotionList(List<PromotionDTO> list) {
				int numberOfEmployeePromoted = 0;
				for (PromotionDTO promotion : list) {
				//get employee id
		          Long empid = promotion.getEmployeeId();
		          log.debug("employee id: {}", empid);
		          Optional<EmployeeDTO> employeeDTO = employeeService.findOne(empid);
		        //check designation 
		          if(employeeDTO.get().getDesignation().getName().equals(promotion.getPromotedFrom())) {
		        	 numberOfEmployeePromoted ++;
		        	 String newDesignation = promotion.getPromotedTo();
		        	 log.debug("designation list: {}", newDesignation);
		        	 DesignationCriteria desCriteria = new DesignationCriteria();
		        	 StringFilter designationName = new StringFilter();
		        	 designationName.setEquals(newDesignation);
		        	 log.debug("desCriteria list: {}", desCriteria);
		        	 desCriteria.setName(designationName);
		        	 List<DesignationDTO> designationList = designationQueryService.findByCriteria(desCriteria);
		        	 log.debug("designationList list: {}", designationList);
		        	 employeeDTO.get().setDesignation(designationList.get(0));
		        	 //update employee designation
		        	 employeeService.update(employeeDTO.get());		        	  
		          }
		          		          	          		            
		        }
				if(numberOfEmployeePromoted == 0) {
					return null;
				}
		        return list;		
		}
	 }
	 
