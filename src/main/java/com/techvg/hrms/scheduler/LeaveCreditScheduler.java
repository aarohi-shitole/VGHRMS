package com.techvg.hrms.scheduler;

import com.techvg.hrms.service.CompanyQueryService;
import com.techvg.hrms.service.CompanyService;
import com.techvg.hrms.service.CustomLeavePolicyQueryService;
import com.techvg.hrms.service.EmployeeLeaveAccountQueryService;
import com.techvg.hrms.service.EmployeeLeaveAccountService;
import com.techvg.hrms.service.EmployeeQueryService;
import com.techvg.hrms.service.EmployeeService;
import com.techvg.hrms.service.EmploymentTypeQueryService;
import com.techvg.hrms.service.LeavePolicyQueryService;
import com.techvg.hrms.service.LeaveTypeService;
import com.techvg.hrms.service.MasterLookupQueryService;
import com.techvg.hrms.service.criteria.CompanyCriteria;
import com.techvg.hrms.service.criteria.CustomLeavePolicyCriteria;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.criteria.EmploymentTypeCriteria;
import com.techvg.hrms.service.criteria.LeavePolicyCriteria;
import com.techvg.hrms.service.criteria.MasterLookupCriteria;
import com.techvg.hrms.service.dto.CompanyDTO;
import com.techvg.hrms.service.dto.CustomLeavePolicyDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.dto.EmploymentTypeDTO;
import com.techvg.hrms.service.dto.LeavePolicyDTO;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import com.techvg.hrms.service.dto.MasterLookupDTO;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Component
public class LeaveCreditScheduler {

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(LeaveCreditScheduler.class);

    @Autowired
    private EmploymentTypeQueryService employmentTypeQueryService;

    @Autowired
    private LeavePolicyQueryService leavePolicyQueryService;

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private EmployeeLeaveAccountService employeeLeaveAccountService;

    @Autowired
    private EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyQueryService companyQueryService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomLeavePolicyQueryService customLeavePolicyQueryService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${shouldFetchDataFromHrms}")
    private boolean shouldFetchDataFromHrms;

    @Scheduled(cron = "${cron.expression}")
  // @Scheduled(cron = "${cron.everyday}")
    public void reportCurrentTime() {
        log.info("HRMS LeaveCredit Schedular getup time is now {}", dateFormat.format(new Date()));

        try {
            log.info("shouldFetchDataFromHrms =" + shouldFetchDataFromHrms);

            if (shouldFetchDataFromHrms) {
                log.info("Schedular is active");
                getCompanies();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCompanies() {
        log.info("In getCompanies");
        Pageable sortedByName = PageRequest.of(0, 1000, Sort.by("id")); // page,size,sort
        Page<CompanyDTO> companyList = companyService.findAll(sortedByName);

        if (!companyList.isEmpty() && companyList != null) {
            for (CompanyDTO company : companyList) {
                log.debug("CompanyList: " + company.toString());
                long companyId = company.getId();
                getEmploymentType(companyId);
            }
        }
    }

    private void getEmploymentType(long companyId1) {
        log.info("In getEmploymentType");
        EmploymentTypeCriteria criteria = new EmploymentTypeCriteria();

        LongFilter companyId = new LongFilter();
        companyId.setEquals(companyId1);
        criteria.setCompanyId(companyId);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        criteria.setStatus(status);

        List<EmploymentTypeDTO> employmentTypeList = employmentTypeQueryService.findByCriteria(criteria);
        if (employmentTypeList != null) {
            for (EmploymentTypeDTO employmentTypeDTO : employmentTypeList) {
                log.debug("Request to show employmentTypeDTO:" + employmentTypeDTO);
                getLeavePolicyOnEmpType(employmentTypeDTO, companyId);
            }
        }
    }

    private void getLeavePolicyOnEmpType(EmploymentTypeDTO employmentTypeDTO, LongFilter companyId) {
        // To get company LeaveSettingLevel
        CompanyCriteria companyCriteria = new CompanyCriteria();
        companyCriteria.setId(companyId);

        String refTable = null;
        List<CompanyDTO> company = companyQueryService.findByCriteria(companyCriteria);
        if (company != null) {
            for (CompanyDTO companyDTO : company) {
                refTable = companyDTO.getLeaveSettingLevel();
            }
        }

        // To set criteria to leave policy
        LeavePolicyCriteria leavePolicyCriteria = new LeavePolicyCriteria();
        leavePolicyCriteria.setCompanyId(companyId);

        LongFilter empTypeId = new LongFilter();
        empTypeId.setEquals(employmentTypeDTO.getId());
        leavePolicyCriteria.setEmploymentTypeId(empTypeId);

        StringFilter refTable1 = new StringFilter();
        refTable1.setEquals(refTable);
        leavePolicyCriteria.setRefTable(refTable1);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        leavePolicyCriteria.setStatus(status);

        log.debug("Request to show leavePolicyCriteria:" + leavePolicyCriteria);
        List<LeavePolicyDTO> leavePolicyList = leavePolicyQueryService.findByCriteria(leavePolicyCriteria);
        if (leavePolicyList != null) {
            for (LeavePolicyDTO leavePolicy : leavePolicyList) {
                LeaveTypeDTO leaveType = leavePolicy.getLeaveType();
                long leaveTypeId = leaveType.getId();
                long totalLeave = leavePolicy.getTotalLeave();
                long leavePolicyId = leavePolicy.getId();
                long maxLeave = 0;
                String refTableName = leavePolicy.getRefTable();
                long refTableId = leavePolicy.getRefTableId();
                boolean carryForward = leavePolicy.getIsCarryForword();

                if (carryForward) {
                    maxLeave = leavePolicy.getMaxLeave();
                }

                log.debug("maxLeave: " + maxLeave);

                String refTableColoumnName = refTableName;

                getEmployeeList(leaveTypeId, totalLeave, maxLeave, refTableColoumnName, refTableId, empTypeId, companyId, leavePolicyId);
            }
        }
    }

    private void getEmployeeList(
        long leaveTypeId,
        long totalLeave,
        long maxLeave,
        String refTableColoumnName,
        long refTableId,
        LongFilter employmentTypeId,
        LongFilter companyId,
        long leavePolicyId
    ) {
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setEmploymentTypeId(employmentTypeId);

        LongFilter tableId = new LongFilter();
        tableId.setEquals(refTableId);
        if (refTableColoumnName.contains("Department")) {
            employeeCriteria.setDepartmentId(tableId);
        } else if (refTableColoumnName.contains("Branch")) {
            employeeCriteria.setBranchId(tableId);
        } else if (refTableColoumnName.contains("Designation")) {
            employeeCriteria.setDesignationId(tableId);
        } else if (refTableColoumnName.contains("Region")) {
            employeeCriteria.setRegionId(tableId);
        } else if (refTableColoumnName.contains("Company")) {
            employeeCriteria.setCompanyId(tableId);
        }

        employeeCriteria.setCompanyId(companyId);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        employeeCriteria.setStatus(status);

        List<EmployeeDTO> employeeList = employeeQueryService.findByCriteria(employeeCriteria);
        if (employeeList != null) {
            for (EmployeeDTO employeeDTO : employeeList) {
                long empId = employeeDTO.getId();
                saveEmployeeLeaveAccount(leaveTypeId, empId, employmentTypeId, companyId, totalLeave, maxLeave, leavePolicyId);
            }
        }
    }

    private void saveEmployeeLeaveAccount(
        long leaveTypeId,
        long empId,
        LongFilter employmentTypeId,
        LongFilter companyId,
        long totalLeave,
        long maxLeave,
        long leavePolicyId
    ) {
        //Add noOfLeaveDays from CustomLeavePolicy in emp balance
        CustomLeavePolicyCriteria customLeavePolicyCriteria = new CustomLeavePolicyCriteria();
        LongFilter leavePolicyId1 = new LongFilter();
        leavePolicyId1.setEquals(leavePolicyId);
        customLeavePolicyCriteria.setLeavePolicyId(leavePolicyId1);

        LongFilter empId1 = new LongFilter();
        empId1.setEquals(empId);
        customLeavePolicyCriteria.setEmployeeId(empId1);

        customLeavePolicyCriteria.setCompanyId(companyId);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        customLeavePolicyCriteria.setStatus(status);

        List<CustomLeavePolicyDTO> customLeavePolicyList = customLeavePolicyQueryService.findByCriteria(customLeavePolicyCriteria);
        long customLeaveDays = 0;
        if (customLeavePolicyList != null || !customLeavePolicyList.isEmpty()) {
            for (CustomLeavePolicyDTO customLeavePolicy : customLeavePolicyList) {
                customLeaveDays = customLeaveDays + customLeavePolicy.getDays();
            }
        }

        Optional<LeaveTypeDTO> leavetypeDTO = leaveTypeService.findOne(leaveTypeId);

        EmployeeLeaveAccountCriteria employeeLeaveAccountCriteria = new EmployeeLeaveAccountCriteria();
        LongFilter employeeId = new LongFilter();
        employeeId.setEquals(empId);
        employeeLeaveAccountCriteria.setEmployeeId(employeeId);

        LongFilter leaveTypeId1 = new LongFilter();
        leaveTypeId1.setEquals(leaveTypeId);
        employeeLeaveAccountCriteria.setLeaveTypeId(leaveTypeId1);

        employeeLeaveAccountCriteria.setStatus(status);

        List<EmployeeLeaveAccountDTO> employeeLeaveAccount = employeeLeaveAccountQueryService.findByCriteria(employeeLeaveAccountCriteria);

        if (employeeLeaveAccount == null || employeeLeaveAccount.isEmpty()) {
            EmployeeLeaveAccountDTO employeeLeaveAccount1 = new EmployeeLeaveAccountDTO();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!! employeeLeaveAccount1 is null adding new employeeLeaveAccount for emp: " + empId);
            employeeLeaveAccount1.setCreditedLeaves(totalLeave + customLeaveDays);
            employeeLeaveAccount1.setBalance(totalLeave + customLeaveDays);
            Optional<EmployeeDTO> employee = employeeService.findOne(empId);
            employeeLeaveAccount1.setEmployee(employee.get());
            employeeLeaveAccount1.setLeaveType(leavetypeDTO.get());
            employeeLeaveAccount1.setDate(Instant.now());
            employeeLeaveAccount1.setStatus("A");

            employeeLeaveAccountService.save(employeeLeaveAccount1);
        }

        if (employeeLeaveAccount != null) {
            for (EmployeeLeaveAccountDTO employeeLeaveAccountDTO : employeeLeaveAccount) {
                if (
                    employeeLeaveAccountDTO.getEmployee().getId() != null &&
                    employeeLeaveAccountDTO.getLeaveType().getId() != null &&
                    employeeLeaveAccountDTO.getBalance() != null &&
                    employeeLeaveAccountDTO.getCreditedLeaves() != null
                ) {
                    if (employeeLeaveAccountDTO.getBalance() > maxLeave) {
                        System.out.println(
                            "!!!!!!!!!!!!!!!!!!!!!!!!! employeeLeaveAccountDTO.getBalance() >= maxLeave Balance:" +
                            employeeLeaveAccountDTO.getBalance() +
                            "maxLeave:" +
                            maxLeave
                        );
                        employeeLeaveAccountDTO.setDate(Instant.now());
                        employeeLeaveAccountDTO.setCarriedLeaves(maxLeave);
                        employeeLeaveAccountDTO.setCreditedLeaves(totalLeave + customLeaveDays);
                        employeeLeaveAccountDTO.setBalance(maxLeave + totalLeave + customLeaveDays);
                        employeeLeaveAccountService.save(employeeLeaveAccountDTO);
                    } else if (employeeLeaveAccountDTO.getBalance() <= maxLeave) {
                        System.out.println(
                            "!!!!!!!!!!!!!!!!!!!!!!!!! employeeLeaveAccountDTO.getBalance() <= maxLeave Balance:" +
                            employeeLeaveAccountDTO.getBalance() +
                            "maxLeave:" +
                            maxLeave
                        );
                        employeeLeaveAccountDTO.setDate(Instant.now());
                        employeeLeaveAccountDTO.setCarriedLeaves(employeeLeaveAccountDTO.getBalance());
                        employeeLeaveAccountDTO.setCreditedLeaves(totalLeave + customLeaveDays);
                        employeeLeaveAccountDTO.setBalance(employeeLeaveAccountDTO.getCarriedLeaves() + totalLeave + customLeaveDays);
                        employeeLeaveAccountService.save(employeeLeaveAccountDTO);
                    }
                    // not required
                    //					else if (employeeLeaveAccountDTO.getEmployee().getId() != null
                    //							&& employeeLeaveAccountDTO.getLeaveType().getId() == null) {
                    //						System.out.println(
                    //								"!!!!!!!!!!!!!!!!!!!!!!!!! employeeLeaveAccountDTO.getEmployee().getId() != null\n"
                    //										+ "							&& employeeLeaveAccountDTO.getLeaveType().getId() == null");
                    //						employeeLeaveAccountDTO.setCreditedLeaves(totalLeave);
                    //						employeeLeaveAccountDTO.setBalance(totalLeave);
                    //						employeeLeaveAccountDTO.setLeaveType(leavetypeDTO.get());
                    //						employeeLeaveAccountDTO.setDate(Instant.now());
                    //						employeeLeaveAccountService.save(employeeLeaveAccountDTO);
                    //					}
                }
            }
        }
    }
}
