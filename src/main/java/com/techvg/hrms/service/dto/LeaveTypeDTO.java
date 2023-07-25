package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.LeaveType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeDTO implements Serializable {

    private Long id;

    private String leaveType;

    private Long noOfDays;

    private Boolean hasCarryForward;

    private Boolean hasEarned;

    private Boolean hasCustomPolicy;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;
    
    private Boolean allowBackward;

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

    public Boolean getHasCarryForward() {
        return hasCarryForward;
    }

    public void setHasCarryForward(Boolean hasCarryForward) {
        this.hasCarryForward = hasCarryForward;
    }

    public Boolean getHasEarned() {
        return hasEarned;
    }

    public void setHasEarned(Boolean hasEarned) {
        this.hasEarned = hasEarned;
    }

    public Boolean getHasCustomPolicy() {
        return hasCustomPolicy;
    }

    public void setHasCustomPolicy(Boolean hasCustomPolicy) {
        this.hasCustomPolicy = hasCustomPolicy;
    }
    
    
    public Boolean getAllowBackward() {
        return allowBackward;
    }

    public void setAllowBackward(Boolean allowBackward) {
        this.allowBackward = allowBackward;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveTypeDTO)) {
            return false;
        }

        LeaveTypeDTO leaveTypeDTO = (LeaveTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leaveTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeDTO{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", noOfDays=" + getNoOfDays() +
            ", hasCarryForward='" + getHasCarryForward() + "'" +
            ", hasEarned='" + getHasEarned() + "'" +
            ", hasCustomPolicy='" + getHasCustomPolicy() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", allowBackward='" + getAllowBackward() + "'" +
            "}";
    }
}
