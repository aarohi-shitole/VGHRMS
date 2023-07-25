package com.techvg.hrms.service;

import com.techvg.hrms.service.criteria.CompanyCriteria;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.dto.CompanyDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.PersonalDetailsDTO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

@Service
public class PersonalDetailHelper {

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private CompanyService CompanyService;

    @Autowired
    private EmployeeService employeeService;

    public void saveRetirementDate(PersonalDetailsDTO result) {
        long compId = result.getCompanyId();

        long retirementAge = 0;
        Optional<CompanyDTO> companyDTO = CompanyService.findOne(compId);
        if (companyDTO.isPresent() && companyDTO.get().getRetirementAge() != null) {
            retirementAge = companyDTO.get().getRetirementAge();
        }

        LocalDate dob = result.getDateOfBirth();
        LocalDate retirementDate = dob.plusYears(retirementAge);
        System.out.println("RetirementDate:" + retirementDate);

        // Define a time zone
        ZoneId zoneId = ZoneId.systemDefault();

        // Define a time of day (e.g., midnight)
        LocalTime localTime = LocalTime.MIDNIGHT;

        // Combine the LocalDate, LocalTime, and ZoneId to create a ZonedDateTime
        ZonedDateTime zonedDateTime = retirementDate.atTime(localTime).atZone(zoneId);

        // Get the Instant from the ZonedDateTime
        Instant instantRetirementDate = zonedDateTime.toInstant();

        //Update employee with instantRetirementDate
        long empId = result.getEmployeeId();

        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        LongFilter employeeId = new LongFilter();
        employeeId.setEquals(empId);
        employeeCriteria.setId(employeeId);

        LongFilter companyId = new LongFilter();
        companyId.setEquals(compId);
        employeeCriteria.setCompanyId(companyId);

        List<EmployeeDTO> employeeList = employeeQueryService.findByCriteria(employeeCriteria);
        if (employeeList != null || !employeeList.isEmpty()) {
            for (EmployeeDTO employee : employeeList) {
                employee.setRetirementDate(instantRetirementDate);
                employeeService.save(employee);
            }
        }
    }
}
