package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;

/**
 * A DTO for the {@link com.techvg.hrms.domain.PaySlip} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaySlipDTO implements Serializable {

    private Long id;

    private String month;

    private Double salary;

    private Long branchId;

    private String status;

    private Long employeeId;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;
    
    private String year;
    
    private String salaryStatus;
    
    private Instant salaryDate;

    private String description;
    
    private Double totalEarning;
    
    private Double totalDeduction;
    
    private String salaryInWords;
    
    private Double lossOfPay;
    
    //private Long presentDays;
    private Double presentDays;
    
    private Double actualGrossPay;

    public Double getLossOfPay() {
		return lossOfPay;
	}

	public void setLossOfPay(Double lossOfPay) {
		this.lossOfPay = lossOfPay;
	}

	
//	public Long getPresentDays() {
//		return presentDays;
//	}
//
//	public void setPresentDays(Long presentDays) {
//		this.presentDays = presentDays;
//	}

	public Double getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(Double presentDays) {
		this.presentDays = presentDays;
	}

	public String getSalaryInWords() {
		return salaryInWords;
	}

	public void setSalaryInWords(String salaryInWords) {
		this.salaryInWords = salaryInWords;
	}

	public Double getTotalEarning() {
		return totalEarning;
	}

	public void setTotalEarning(Double totalEarning) {
		this.totalEarning = totalEarning;
	}

	public Double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(Double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
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
    
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    
    public Double getActualGrossPay() {
		return actualGrossPay;
	}

	public void setActualGrossPay(Double actualGrossPay) {
		this.actualGrossPay = actualGrossPay;
	}

	public String getSalaryStatus() {
		return salaryStatus;
	}

	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}

	public Instant getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(Instant salaryDate) {
		this.salaryDate = salaryDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaySlipDTO)) {
            return false;
        }

        PaySlipDTO paySlipDTO = (PaySlipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paySlipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaySlipDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", salary=" + getSalary() +
            ", branchId=" + getBranchId() +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", year='" + getYear() + "'" +
            
            ", salaryStatus='" + getSalaryStatus() + "'" +

            ", salaryDate='" + getSalaryDate() + "'" +

            ", description='" + getDescription() + "'" +
            ", totalEarning='" + getTotalEarning() + "'" +
            ", totalDeduction='" + getTotalDeduction() + "'" +
            ", salaryInWords='" + getSalaryInWords() + "'" +
            ", lossOfPay='" + getLossOfPay() + "'" +
            ", presentDays='" + getPresentDays() + "'" +
            ", actualGrossPay='" + getActualGrossPay() + "'" +
            "}";
    }
}
