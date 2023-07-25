package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Termination} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TerminationDTO implements Serializable {

    private Long id;

    private String empName;

    private String terminationType;

    private Instant terminationDate;

    private Instant noticeDate;

    private String reason;

    private String status;

    private Long departmentId;

    private Long employeeId;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

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

    public String getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(String terminationType) {
        this.terminationType = terminationType;
    }

    public Instant getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Instant terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Instant getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Instant noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof TerminationDTO)) {
            return false;
        }

        TerminationDTO terminationDTO = (TerminationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, terminationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerminationDTO{" +
            "id=" + getId() +
            ", empName='" + getEmpName() + "'" +
            ", terminationType='" + getTerminationType() + "'" +
            ", terminationDate='" + getTerminationDate() + "'" +
            ", noticeDate='" + getNoticeDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", status='" + getStatus() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
