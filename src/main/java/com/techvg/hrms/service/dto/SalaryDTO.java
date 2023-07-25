package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Salary} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalaryDTO implements Serializable {

    private Long id;

    private String type;

    private Double amount;

    private Boolean isdeduction;

    private String month;

    private String year;

    private String status;

    private Long employeeId;

    private Long companyId;
    
    private Double earningAmount; 

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getIsdeduction() {
        return isdeduction;
    }

    public void setIsdeduction(Boolean isdeduction) {
        this.isdeduction = isdeduction;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
    
    

    public Double getEarningAmount() {
		return earningAmount;
	}

	public void setEarningAmount(Double earningAmount) {
		this.earningAmount = earningAmount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalaryDTO)) {
            return false;
        }

        SalaryDTO salaryDTO = (SalaryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salaryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalaryDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", isdeduction='" + getIsdeduction() + "'" +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", earningAmount=" + getEarningAmount() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
