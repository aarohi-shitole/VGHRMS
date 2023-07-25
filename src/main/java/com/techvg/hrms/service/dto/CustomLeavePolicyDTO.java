package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.CustomLeavePolicy} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomLeavePolicyDTO implements Serializable {

    private Long id;

    private String customPolicyName;

    private Long days;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    private LeavePolicyDTO leavePolicy;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomPolicyName() {
        return customPolicyName;
    }

    public void setCustomPolicyName(String customPolicyName) {
        this.customPolicyName = customPolicyName;
    }
    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public LeavePolicyDTO getLeavePolicy() {
        return leavePolicy;
    }

    public void setLeavePolicy(LeavePolicyDTO leavePolicy) {
        this.leavePolicy = leavePolicy;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomLeavePolicyDTO)) {
            return false;
        }

        CustomLeavePolicyDTO customLeavePolicyDTO = (CustomLeavePolicyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customLeavePolicyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomLeavePolicyDTO{" +
            "id=" + getId() +
            ", customPolicyName='" + getCustomPolicyName() + "'" +
            ", days=" + getDays() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", leavePolicy=" + getLeavePolicy() +
            ", employee=" + getEmployee() +
            "}";
    }
}
