package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.AppraisalReview} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalReviewDTO implements Serializable {

    private Long id;

    private String reportingOfficer;
    
    private String appraisalStatus;

    private String roDesignation;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private Long employeId;
    
    private List<ApprovalDTO> approvals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportingOfficer() {
        return reportingOfficer;
    }

    public void setReportingOfficer(String reportingOfficer) {
        this.reportingOfficer = reportingOfficer;
    }
    
    public String getAppraisalStatus() {
        return appraisalStatus;
    }

    public void setAppraisalStatus(String appraisalStatus) {
        this.appraisalStatus = appraisalStatus;
    }

    public String getRoDesignation() {
        return roDesignation;
    }

    public void setRoDesignation(String roDesignation) {
        this.roDesignation = roDesignation;
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

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
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
        if (!(o instanceof AppraisalReviewDTO)) {
            return false;
        }

        AppraisalReviewDTO appraisalReviewDTO = (AppraisalReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appraisalReviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalReviewDTO{" +
            "id=" + getId() +
            ", reportingOfficer='" + getReportingOfficer() + "'" +
            ", appraisalStatus='" + getAppraisalStatus() + "'" +
            ", appraisalStatus='" + getAppraisalStatus() + "'" +
            ", roDesignation='" + getRoDesignation() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", employeId=" + getEmployeId() +
            "}";
    }
}
