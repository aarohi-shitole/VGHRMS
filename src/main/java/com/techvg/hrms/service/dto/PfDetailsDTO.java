package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.PfDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PfDetailsDTO implements Serializable {

    private Long id;

    private Boolean isPfContribution;

    private String pfNumber;

    private Double pfRate;

    private String additionalPfRate;

    private Double totalPfRate;

    private Long employeeId;

    private Long reEnumerationId;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsPfContribution() {
        return isPfContribution;
    }

    public void setIsPfContribution(Boolean isPfContribution) {
        this.isPfContribution = isPfContribution;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public Double getPfRate() {
        return pfRate;
    }

    public void setPfRate(Double pfRate) {
        this.pfRate = pfRate;
    }

    public String getAdditionalPfRate() {
        return additionalPfRate;
    }

    public void setAdditionalPfRate(String additionalPfRate) {
        this.additionalPfRate = additionalPfRate;
    }

    public Double getTotalPfRate() {
        return totalPfRate;
    }

    public void setTotalPfRate(Double totalPfRate) {
        this.totalPfRate = totalPfRate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getReEnumerationId() {
        return reEnumerationId;
    }

    public void setReEnumerationId(Long reEnumerationId) {
        this.reEnumerationId = reEnumerationId;
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
        if (!(o instanceof PfDetailsDTO)) {
            return false;
        }

        PfDetailsDTO pfDetailsDTO = (PfDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pfDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PfDetailsDTO{" +
            "id=" + getId() +
            ", isPfContribution='" + getIsPfContribution() + "'" +
            ", pfNumber='" + getPfNumber() + "'" +
            ", pfRate=" + getPfRate() +
            ", additionalPfRate='" + getAdditionalPfRate() + "'" +
            ", totalPfRate=" + getTotalPfRate() +
            ", employeeId=" + getEmployeeId() +
            ", reEnumerationId=" + getReEnumerationId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
