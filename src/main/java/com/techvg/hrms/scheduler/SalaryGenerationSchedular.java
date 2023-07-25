package com.techvg.hrms.scheduler;

import com.techvg.hrms.service.CompanyQueryService;
import com.techvg.hrms.service.EmployeeQueryService;
import com.techvg.hrms.service.EmployeeSalaryComponentQueryService;
import com.techvg.hrms.service.SalaryHelper;
import com.techvg.hrms.service.criteria.CompanyCriteria;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.criteria.EmployeeSalaryComponentCriteria;
import com.techvg.hrms.service.dto.CompanyDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeSalaryComponentDTO;
import com.techvg.hrms.web.rest.errors.BadRequestAlertException;
import com.techvg.hrms.web.rest.errors.BusinessRuleException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Component
public class SalaryGenerationSchedular {

    private static final String ENTITY_NAME = "salaryHelper";

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(SalaryGenerationSchedular.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CompanyQueryService companyQueryService;

    @Autowired
    private SalaryHelper salaryHelper;

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private EmployeeSalaryComponentQueryService employeeSalaryComponentQueryService;

    @Scheduled(cron = "${cron.monthLastDaySalary}")
    public void reportCurrentTime() {
        log.info("HRMS Salary Generation Schedular getup time is now {}", dateFormat.format(new Date()));
        System.out.println("sheduler on");
        try {
            checkCompanies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCompanies() {
        // Create a formatter for the date string
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        LocalDate localDate = cal.toInstant().atZone(ZoneId.of("Asia/Calcutta")).toLocalDate();

        log.debug("Current Date and Time after add time zone to localDate:" + localDate);
        int daysInMonth = localDate.getDayOfMonth();
        //        long payslipDate = 0;
        //        if(daysInMonth==payslipDate) {
        //
        //        }

        // set startDateTime and endDateTime for entire day
        LocalDateTime startDateTime = localDate.atTime(LocalTime.of(0, 0, 0));
        LocalDateTime endDateTime = localDate.atTime(LocalTime.of(23, 59, 59));
        log.debug("LocalDateTime startDateTime(0, 0, 0):" + startDateTime);
        log.debug("LocalDateTime endDateTime(23, 59, 59):" + endDateTime);

        Instant startInstant = Instant.parse(startDateTime.format(dateFormat));
        Instant endInstant = Instant.parse(endDateTime.format(dateFormat));

        CompanyCriteria companyCriteria = new CompanyCriteria();
        //        InstantFilter instantFilter = new InstantFilter();
        //        instantFilter.setGreaterThanOrEqual(startInstant);
        //        instantFilter.setLessThanOrEqual(endInstant);
        //        companyCriteria.setPayslipDate(instantFilter);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        companyCriteria.setStatus(status);

        List<CompanyDTO> companyList = companyQueryService.findByCriteria(companyCriteria);
        log.debug("searched list: {}", companyList);
        if (companyList != null) {
            checkEmployee(companyList);
        }
    }

    private void checkEmployee(List<CompanyDTO> companyList) {
        for (CompanyDTO company : companyList) {
            long companyId = company.getId();

            EmployeeCriteria employeeCriteria = new EmployeeCriteria();

            LongFilter compId = new LongFilter();
            compId.setEquals(companyId);
            employeeCriteria.setCompanyId(compId);

            StringFilter status = new StringFilter();
            status.setEquals("A");
            employeeCriteria.setStatus(status);

            List<Long> basicPresentEmpList = new ArrayList<Long>();
            List<Long> basicAbsentEmpList = new ArrayList<Long>();
            List<EmployeeDTO> employeeList = employeeQueryService.findByCriteria(employeeCriteria);
            if (employeeList != null) {
                for (EmployeeDTO employee : employeeList) {
                    // SalaryHelper salaryHelper = new SalaryHelper();
                    long employeeId = employee.getId();
                    EmployeeSalaryComponentCriteria componentCriteria = new EmployeeSalaryComponentCriteria();
                    LongFilter empId = new LongFilter();
                    empId.setEquals(employeeId);
                    componentCriteria.setEmployeeId(empId);

                    List<EmployeeSalaryComponentDTO> componentList = employeeSalaryComponentQueryService.findByCriteria(componentCriteria);
                    if (componentList != null) {
                        for (EmployeeSalaryComponentDTO salaryComponent : componentList) {
                            String salaryComponentName = salaryComponent.getName();
                            //Add the employee in basicPresentEmpList if basic assigned
                            if (salaryComponentName.equalsIgnoreCase("Basic") && salaryComponent.getValue() > 0) {
                                log.debug("Basic Amt:" + salaryComponent.getValue());
                                basicPresentEmpList.add(employeeId);
                            }
                        }
                    }
                }
            }

            //Save the salary for each emp having basic
            for (Long employeeId : basicPresentEmpList) {
                salaryHelper.saveSalaryComponentsInSalary(employeeId);
                log.debug("Salary saved for employee id:" + employeeId);
            }

            //Add the employee in basicAbsentEmpList if basic not assigned
            if (employeeList != null) {
                for (EmployeeDTO employee : employeeList) {
                    long empId = employee.getId();
                    if (!basicPresentEmpList.contains(empId)) {
                        basicAbsentEmpList.add(empId);
                    }
                }
            }

            log.debug("Salary generated for the employee ID:" + basicPresentEmpList.toString());
            log.debug("Employee having basic salary less than zero:" + basicAbsentEmpList.toString());

            if (basicAbsentEmpList != null) {
                log.debug("basicAbsentEmpList not null");
                throw new BadRequestAlertException(
                    "Salary not generated for employee ID",
                    ENTITY_NAME,
                    "basicNotexists" + basicAbsentEmpList
                );
                //throw new BusinessRuleException("Salary not generated for employee", "Id: " +basicAbsentEmpList);
            }
        }
    }
}
