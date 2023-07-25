package com.techvg.hrms.domain;

import com.techvg.hrms.service.criteria.AdminUserCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.jhipster.service.filter.StringFilter;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "company_id")
    private Long companyId;

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @PrePersist
    public void onPersist() {
        this.lastModified = Instant.now();
        this.lastModifiedBy = getLoggedInUser();
        this.companyId = getLoggedInUserCompanyId();
    }

    @PreUpdate
    public void onUpdate() {
        this.lastModified = Instant.now();
        this.lastModifiedBy = getLoggedInUser();
        this.companyId = getLoggedInUserCompanyId();
    }

    private String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    //    private String getLoggedInUser() {
    //		String currentUser = null;
    //		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //		if (authentication != null && authentication.isAuthenticated()) {
    //			AdminUserCriteria userCriteria = new AdminUserCriteria();
    //			StringFilter userId = new StringFilter();
    //			userId.setEquals(authentication.getName());
    //			userCriteria.setId(userId);
    //
    //			List<AdminUserDTO> list = userQueryService.findByCriteria(userCriteria);
    //			if (list != null) {
    //				for (AdminUserDTO adminUserDTO : list) {
    //					currentUser = adminUserDTO.getFirstName()+adminUserDTO.getLastName();
    //				}
    //			}
    //		}
    //		return currentUser;
    //	}

    private Long getLoggedInUserCompanyId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employeeObj = null;
        employeeObj = this.getEmployee();

        return employeeObj.getCompanyId();
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Admin");
        employee.setCompanyId(1L);
        return employee;
    }
}
