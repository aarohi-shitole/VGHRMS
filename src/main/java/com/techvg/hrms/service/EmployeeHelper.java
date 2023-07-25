package com.techvg.hrms.service;

import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
public class EmployeeHelper {

    @Autowired
    private EmployeeLeaveAccountQueryService employeeLeaveAccountQueryService;

    @Autowired
    private EmployeeLeaveAccountService employeeLeaveAccountService;

    public void inactiveEmpLeaveAccount(@Valid EmployeeDTO employeeDTO) {
        EmployeeLeaveAccountCriteria criteria = new EmployeeLeaveAccountCriteria();

        LongFilter id = new LongFilter();
        id.setEquals(employeeDTO.getId());
        criteria.setEmployeeId(id);

        StringFilter status = new StringFilter();
        status.setEquals("A");
        criteria.setStatus(status);

        LongFilter companyId = new LongFilter();
        companyId.setEquals(employeeDTO.getCompanyId());
        criteria.setCompanyId(companyId);

        List<EmployeeLeaveAccountDTO> list = employeeLeaveAccountQueryService.findByCriteria(criteria);
        if (list != null || list.isEmpty()) {
            for (EmployeeLeaveAccountDTO leaveAccount : list) {
                leaveAccount.setStatus("I");
                employeeLeaveAccountService.save(leaveAccount);
            }
        }
    }
}
