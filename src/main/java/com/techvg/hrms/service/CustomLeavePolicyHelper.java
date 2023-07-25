package com.techvg.hrms.service;

import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.dto.CustomLeavePolicyDTO;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.dto.LeavePolicyDTO;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class CustomLeavePolicyHelper {

    private static final Logger log = LoggerFactory.getLogger(CustomLeavePolicyHelper.class);

    private static final String ENTITY_NAME = "customLeavePolicyHelper";

    @Autowired
    private EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService;

    @Autowired
    private EmployeeLeaveAccountService employeeLeaveAccountService;

    @Autowired
    private CustomLeavePolicyService customLeavePolicyService;

    @Autowired
    private LeavePolicyService leavePolicyService;

    public void updateEmployeeLeaveAccount(CustomLeavePolicyDTO result) {
        long leavePolicyId = result.getLeavePolicy().getId();

        Optional<LeavePolicyDTO> leavePolicy = leavePolicyService.findOne(leavePolicyId);
        if (leavePolicy.isPresent()) {
            LeaveTypeDTO leaveTypeDTO = leavePolicy.get().getLeaveType();

            long leaveTypeId = leavePolicy.get().getLeaveType().getId();
            System.out.println("-----------leaveTypeId: " + leaveTypeId);

            long compId = result.getCompanyId();
            long empId = result.getEmployee().getId();
            System.out.println("-----------empId: " + empId);

            EmployeeLeaveAccountCriteria criteria = new EmployeeLeaveAccountCriteria();

            LongFilter leaveType = new LongFilter();
            leaveType.setEquals(leavePolicy.get().getLeaveType().getId());
            criteria.setLeaveTypeId(leaveType);

            LongFilter companyId = new LongFilter();
            companyId.setEquals(compId);
            criteria.setCompanyId(companyId);

            LongFilter employeeId = new LongFilter();
            employeeId.setEquals(empId);
            criteria.setEmployeeId(employeeId);

            StringFilter status = new StringFilter();
            status.setEquals("A");
            criteria.setStatus(status);

            List<EmployeeLeaveAccountDTO> list = employeeLeaveAccountQueryService.findByCriteria(criteria);
            if (list == null || list.isEmpty()) {
                EmployeeLeaveAccountDTO employeeLeaveAccount = new EmployeeLeaveAccountDTO();
                employeeLeaveAccount.setCreditedLeaves(result.getDays());
                employeeLeaveAccount.setBalance(result.getDays());
                employeeLeaveAccount.setLeaveType(leaveTypeDTO);
                employeeLeaveAccount.setCompanyId(compId);
                employeeLeaveAccount.setEmployee(result.getEmployee());
                employeeLeaveAccount.setDate(Instant.now());
                employeeLeaveAccount.setStatus("A");
                employeeLeaveAccountService.save(employeeLeaveAccount);
            } else {
                for (EmployeeLeaveAccountDTO employeeLeaveAccount : list) {
                    long creditedLeaves = employeeLeaveAccount.getCreditedLeaves();
                    creditedLeaves = creditedLeaves + result.getDays();

                    long balanceLeaves = employeeLeaveAccount.getBalance();
                    balanceLeaves = balanceLeaves + result.getDays();

                    employeeLeaveAccount.setCreditedLeaves(creditedLeaves);
                    employeeLeaveAccount.setBalance(balanceLeaves);

                    employeeLeaveAccountService.save(employeeLeaveAccount);
                }
            }
        }
    }

    public void updateEmployeeExistingLeaveAccount(CustomLeavePolicyDTO customLeavePolicyDTO) {
        Optional<CustomLeavePolicyDTO> oldDTO = customLeavePolicyService.findOne(customLeavePolicyDTO.getId());
        long oldDays = oldDTO.get().getDays();

        long leavePolicyId = customLeavePolicyDTO.getLeavePolicy().getId();

        Optional<LeavePolicyDTO> leavePolicy = leavePolicyService.findOne(leavePolicyId);
        if (leavePolicy.isPresent()) {
            LeaveTypeDTO leaveTypeDTO = leavePolicy.get().getLeaveType();

            long leaveTypeId = leavePolicy.get().getLeaveType().getId();
            System.out.println("-----------leaveTypeId: " + leaveTypeId);

            long compId = customLeavePolicyDTO.getCompanyId();
            long empId = customLeavePolicyDTO.getEmployee().getId();
            System.out.println("-----------empId: " + empId);

            EmployeeLeaveAccountCriteria criteria = new EmployeeLeaveAccountCriteria();

            LongFilter leaveType = new LongFilter();
            leaveType.setEquals(leavePolicy.get().getLeaveType().getId());
            criteria.setLeaveTypeId(leaveType);

            LongFilter companyId = new LongFilter();
            companyId.setEquals(compId);
            criteria.setCompanyId(companyId);

            LongFilter employeeId = new LongFilter();
            employeeId.setEquals(empId);
            criteria.setEmployeeId(employeeId);

            StringFilter status = new StringFilter();
            status.setEquals("A");
            criteria.setStatus(status);

            List<EmployeeLeaveAccountDTO> list = employeeLeaveAccountQueryService.findByCriteria(criteria);
            if (list != null || !list.isEmpty()) {
                System.out.println("-----------EmployeeLeaveAccountDTO list is not empty: " + list);
                if (customLeavePolicyDTO.getDays() > oldDays) {
                    System.out.println("-----------customLeavePolicyDTO.getDays()>oldDays)");
                    long days = customLeavePolicyDTO.getDays() - oldDays;

                    for (EmployeeLeaveAccountDTO employeeLeaveAccount : list) {
                        long creditedLeaves = employeeLeaveAccount.getCreditedLeaves();
                        creditedLeaves = creditedLeaves + days;

                        long balanceLeaves = employeeLeaveAccount.getBalance();
                        balanceLeaves = balanceLeaves + days;

                        employeeLeaveAccount.setCreditedLeaves(creditedLeaves);
                        employeeLeaveAccount.setBalance(balanceLeaves);

                        employeeLeaveAccountService.save(employeeLeaveAccount);
                    }
                } else if (customLeavePolicyDTO.getDays() < oldDays) {
                    long days = oldDays - customLeavePolicyDTO.getDays();

                    for (EmployeeLeaveAccountDTO employeeLeaveAccount : list) {
                        long creditedLeaves = employeeLeaveAccount.getCreditedLeaves();
                        creditedLeaves = creditedLeaves - days;

                        long balanceLeaves = employeeLeaveAccount.getBalance();
                        balanceLeaves = balanceLeaves - days;

                        employeeLeaveAccount.setCreditedLeaves(creditedLeaves);
                        employeeLeaveAccount.setBalance(balanceLeaves);

                        employeeLeaveAccountService.save(employeeLeaveAccount);
                    }
                }
            }
        }
    }
}
