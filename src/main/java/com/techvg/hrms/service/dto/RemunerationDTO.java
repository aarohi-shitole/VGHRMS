package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


/**
 * A DTO for the {@link com.techvg.hrms.domain.Remuneration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemunerationDTO implements Serializable {

    private Long id;

    private String salaryType;

    private Double amount;

    private String paymentType;

    private Long employeeId;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;
    
    private TaxRegimeDTO taxRegime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    
    
    public TaxRegimeDTO getTaxRegime() {
        return taxRegime;
    }

    public void setTaxRegime(TaxRegimeDTO taxRegime) {
        this.taxRegime = taxRegime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemunerationDTO)) {
            return false;
        }

        RemunerationDTO remunerationDTO = (RemunerationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, remunerationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemunerationDTO{" +
            "id=" + getId() +
            ", salaryType='" + getSalaryType() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", taxRegime='" + getTaxRegime() + "'" +

            "}";
    }
}
