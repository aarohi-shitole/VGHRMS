package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Deduction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeductionDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean hasUnitCal;

    private Double unitAmount;

    private String status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasUnitCal() {
        return hasUnitCal;
    }

    public void setHasUnitCal(Boolean hasUnitCal) {
        this.hasUnitCal = hasUnitCal;
    }

    public Double getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Double unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof DeductionDTO)) {
            return false;
        }

        DeductionDTO deductionDTO = (DeductionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deductionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeductionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", hasUnitCal='" + getHasUnitCal() + "'" +
            ", unitAmount=" + getUnitAmount() +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
