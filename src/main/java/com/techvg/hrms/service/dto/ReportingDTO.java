package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Reporting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportingDTO implements Serializable {

    private Long id;

    private Long employeeId;

    private Long reportingEmpId;

    private Long reportingId;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getReportingEmpId() {
        return reportingEmpId;
    }

    public void setReportingEmpId(Long reportingEmpId) {
        this.reportingEmpId = reportingEmpId;
    }

    public Long getReportingId() {
        return reportingId;
    }

    public void setReportingId(Long reportingId) {
        this.reportingId = reportingId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportingDTO)) {
            return false;
        }

        ReportingDTO reportingDTO = (ReportingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportingDTO{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", reportingEmpId=" + getReportingEmpId() +
            ", reportingId=" + getReportingId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
