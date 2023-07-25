package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.LeaveApplication} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApplicationDTO implements Serializable {

    private Long id;

    private String leaveType;

    private Long noOfDays;

    private String reason;

    private Instant formDate;

    private Instant toDate;

    private String leaveStatus;

    private String status;

    private Long employeId;

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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getFormDate() {
        return formDate;
    }

    public void setFormDate(Instant formDate) {
        this.formDate = formDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
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
        if (!(o instanceof LeaveApplicationDTO)) {
            return false;
        }

        LeaveApplicationDTO leaveApplicationDTO = (LeaveApplicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leaveApplicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationDTO{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", noOfDays=" + getNoOfDays() +
            ", reason='" + getReason() + "'" +
            ", formDate='" + getFormDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", leaveStatus='" + getLeaveStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeId=" + getEmployeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
