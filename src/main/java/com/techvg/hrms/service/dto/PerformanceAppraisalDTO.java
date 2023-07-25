package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.PerformanceAppraisal} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceAppraisalDTO implements Serializable {

    private Long id;

    private Long employeeId;

    private String status;

    private Long companyId;

    private Instant lastModified;
    
    private Instant appraisalDate;

    private String lastModifiedBy;

    private AppraisalReviewDTO appraisalReview;

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

    public Instant getAppraisalDate() {
        return appraisalDate;
    }

    public void setAppraisalDate(Instant appraisalDate) {
        this.appraisalDate = appraisalDate;
    }
    
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public AppraisalReviewDTO getAppraisalReview() {
        return appraisalReview;
    }

    public void setAppraisalReview(AppraisalReviewDTO appraisalReview) {
        this.appraisalReview = appraisalReview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceAppraisalDTO)) {
            return false;
        }

        PerformanceAppraisalDTO performanceAppraisalDTO = (PerformanceAppraisalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, performanceAppraisalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceAppraisalDTO{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", appraisalDate='" + getAppraisalDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", appraisalReview=" + getAppraisalReview() +
            "}";
    }
}
