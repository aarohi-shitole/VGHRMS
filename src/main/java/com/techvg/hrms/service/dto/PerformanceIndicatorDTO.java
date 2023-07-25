package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.PerformanceIndicator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceIndicatorDTO implements Serializable {

    private Long id;

    private Long designationId;

    private String indicatorValue;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private MasterPerformanceIndicatorDTO masterPerformanceIndicator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getIndicatorValue() {
        return indicatorValue;
    }

    public void setIndicatorValue(String indicatorValue) {
        this.indicatorValue = indicatorValue;
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

    public MasterPerformanceIndicatorDTO getMasterPerformanceIndicator() {
        return masterPerformanceIndicator;
    }

    public void setMasterPerformanceIndicator(MasterPerformanceIndicatorDTO masterPerformanceIndicator) {
        this.masterPerformanceIndicator = masterPerformanceIndicator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceIndicatorDTO)) {
            return false;
        }

        PerformanceIndicatorDTO performanceIndicatorDTO = (PerformanceIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, performanceIndicatorDTO.id);
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
            ", designationId=" + getDesignationId() +
            ", indicatorValue='" + getIndicatorValue() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", masterPerformanceIndicator=" + getMasterPerformanceIndicator() +
            "}";
    }
}
