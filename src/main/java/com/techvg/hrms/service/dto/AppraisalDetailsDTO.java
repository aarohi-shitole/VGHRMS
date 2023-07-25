package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.PerformanceIndicator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalDetailsDTO implements Serializable {

    private Long id;

    private Long performanceAppraisalId;

    private String expectedValue;

    private String setValue;

    private String status;

    private Long companyId;

    private Instant lastModified;


    private String lastModifiedBy;

    private PerformanceIndicatorDTO performanceIndicator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerformanceAppraisalId() {
        return performanceAppraisalId;
    }

    public void setPerformanceAppraisalId(Long performanceAppraisalId) {
        this.performanceAppraisalId = performanceAppraisalId;
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String getSetValue() {
        return setValue;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
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

    public PerformanceIndicatorDTO getPerformanceIndicator() {
        return performanceIndicator;
    }

    public void setPerformanceIndicator(PerformanceIndicatorDTO performanceIndicator) {
        this.performanceIndicator = performanceIndicator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalDetailsDTO)) {
            return false;
        }

        AppraisalDetailsDTO appraisalDetailsDTO = (AppraisalDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appraisalDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceIndicatorDTO{" +
            "id=" + getId() +
            ", performanceAppraisalId=" + getPerformanceAppraisalId() +
            ", expectedValue='" + getExpectedValue() + "'" +
            ", setValue='" + getSetValue() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
           
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", performanceIndicator=" + getPerformanceIndicator() +
            "}";
    }
}
