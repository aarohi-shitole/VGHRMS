package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Resignation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResignationDTO implements Serializable {

    private Long id;

    private String empName;

    private Instant resignDate;

    private Long noticePeriodIndays;

    private String reason;

    private String resignStatus;

    private Instant lastWorkingDay;

    private Long departmentId;

    private Long employeeId;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;
    
    private List<ApprovalDTO> approvals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Instant getResignDate() {
        return resignDate;
    }

    public void setResignDate(Instant resignDate) {
        this.resignDate = resignDate;
    }

    public Long getNoticePeriodIndays() {
        return noticePeriodIndays;
    }

    public void setNoticePeriodIndays(Long noticePeriodIndays) {
        this.noticePeriodIndays = noticePeriodIndays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResignStatus() {
        return resignStatus;
    }

    public void setResignStatus(String resignStatus) {
        this.resignStatus = resignStatus;
    }

    public Instant getLastWorkingDay() {
        return lastWorkingDay;
    }

    public void setLastWorkingDay(Instant lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public List<ApprovalDTO> getApprovals() {
		return approvals;
	}

	public void setApprovals(List<ApprovalDTO> approvals) {
		this.approvals = approvals;
	}

    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResignationDTO)) {
            return false;
        }

        ResignationDTO resignationDTO = (ResignationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resignationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResignationDTO{" +
            "id=" + getId() +
            ", empName='" + getEmpName() + "'" +
            ", resignDate='" + getResignDate() + "'" +
            ", noticePeriodIndays=" + getNoticePeriodIndays() +
            ", reason='" + getReason() + "'" +
            ", resignStatus='" + getResignStatus() + "'" +
            ", lastWorkingDay='" + getLastWorkingDay() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", employeeId=" + getEmployeeId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
