package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.LeavePolicy} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavePolicyDTO implements Serializable {

    private Long id;

    private Boolean isCarryForword;

    private Long genderLeave;

    private Long totalLeave;

    private Long maxLeave;

    private Boolean hasproRataLeave;

    private String description;

    private String refTable;

    private Long refTableId;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private LeaveTypeDTO leaveType;

    private EmploymentTypeDTO employmentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCarryForword() {
        return isCarryForword;
    }

    public void setIsCarryForword(Boolean isCarryForword) {
        this.isCarryForword = isCarryForword;
    }

    public Long getGenderLeave() {
        return genderLeave;
    }

    public void setGenderLeave(Long genderLeave) {
        this.genderLeave = genderLeave;
    }

    public Long getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(Long totalLeave) {
        this.totalLeave = totalLeave;
    }

    public Long getMaxLeave() {
        return maxLeave;
    }

    public void setMaxLeave(Long maxLeave) {
        this.maxLeave = maxLeave;
    }

    public Boolean getHasproRataLeave() {
        return hasproRataLeave;
    }

    public void setHasproRataLeave(Boolean hasproRataLeave) {
        this.hasproRataLeave = hasproRataLeave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
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

    public LeaveTypeDTO getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeDTO leaveType) {
        this.leaveType = leaveType;
    }

    public EmploymentTypeDTO getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentTypeDTO employmentType) {
        this.employmentType = employmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeavePolicyDTO)) {
            return false;
        }

        LeavePolicyDTO leavePolicyDTO = (LeavePolicyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, leavePolicyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavePolicyDTO{" +
            "id=" + getId() +
            ", isCarryForword='" + getIsCarryForword() + "'" +
            ", genderLeave=" + getGenderLeave() +
            ", totalLeave=" + getTotalLeave() +
            ", maxLeave=" + getMaxLeave() +
            ", hasproRataLeave='" + getHasproRataLeave() + "'" +
            ", description='" + getDescription() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", leaveType=" + getLeaveType() +
            ", employmentType=" + getEmploymentType() +
            "}";
    }
}
