package com.techvg.hrms.service.criteria;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.service.ApprovalHelper;
import com.techvg.hrms.service.EmployeeService;
import com.techvg.hrms.service.UserQueryService;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Component
public class DefaultCriteria {

    private final Logger log = LoggerFactory.getLogger(DefaultCriteria.class);

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private EmployeeService employeeService;

    public LongFilter getDefaultCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = null;
        if (authentication != null && authentication.isAuthenticated()) {
            id = authentication.getName();
        }

        AdminUserCriteria userCriteria = new AdminUserCriteria();
        StringFilter userId = new StringFilter();
        userId.setEquals(id);
        userCriteria.setId(userId);

        long currentUserId = 0;
        List<AdminUserDTO> list = userQueryService.findByCriteria(userCriteria);
        if (list != null) {
            for (AdminUserDTO adminUserDTO : list) {
                currentUserId = adminUserDTO.getEmployeeId();
                System.out.printf("!!!!!!!!!!!!!!!!REST request to get current logged in UserId : {}", currentUserId);
            }
        }

        LongFilter idFilter = new LongFilter();
        Optional<EmployeeDTO> employee = employeeService.findOne(currentUserId);
        if (employee.isPresent()) {
            long compId = employee.get().getCompanyId();
            log.debug("!!!!!!!!!!!!!!!!!!employee.get().getCompanyId()::" + compId);
            idFilter.setEquals(employee.get().getCompanyId());
        }
        return idFilter;
    }
}
