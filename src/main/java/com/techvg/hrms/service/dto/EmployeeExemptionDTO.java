package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.EmployeeExemption} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeExemptionDTO implements Serializable {

    private Long id;

    private Double amount;

    private Long taxExempSectionId;

    private String exemptionStatus;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTaxExempSectionId() {
        return taxExempSectionId;
    }

    public void setTaxExempSectionId(Long taxExempSectionId) {
        this.taxExempSectionId = taxExempSectionId;
    }

    public String getExemptionStatus() {
        return exemptionStatus;
    }

    public void setExemptionStatus(String exemptionStatus) {
        this.exemptionStatus = exemptionStatus;
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
        if (!(o instanceof EmployeeExemptionDTO)) {
            return false;
        }

        EmployeeExemptionDTO employeeExemptionDTO = (EmployeeExemptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeExemptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeExemptionDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", taxExempSectionId=" + getTaxExempSectionId() +
            ", exemptionStatus='" + getExemptionStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
