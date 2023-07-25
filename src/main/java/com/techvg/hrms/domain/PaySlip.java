package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PaySlip.
 */
@Entity
@Table(name = "pay_slip")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE pay_slip SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PaySlip extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "month")
    private String month;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "status")
    private String status;

    @Column(name = "employee_id")
    private Long employeeId;
    

    @Column(name = "year")
    private String year;
    
    
    @Column(name = "salary_status")
    private String salaryStatus;
    
    
    @Column(name = "salary_date")
    private Instant salaryDate;

    @Column(name = "Description")
    private String description;
    
    @Column(name="total_earning")
    private Double totalEarning;
    
    @Column(name="total_deduction")
    private Double totalDeduction;
    
    @Column(name = "salary_in_words")
    private String salaryInWords;

    @Column(name="loss_of_pay")
    private Double lossOfPay;
    
   // @Column(name = "present_days")
   // private Long presentDays;

    @Column(name = "present_days")
    private Double presentDays;

    @Column(name = "actual_grosspay")
    private Double actualGrossPay;

    
    
//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "last_modified")
//    private Instant lastModified;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    
    
    public Long getId() {
        return this.id;
    }

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

	
	public String getSalaryInWords() {
		return salaryInWords;
	}

	public Double getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(Double presentDays) {
		this.presentDays = presentDays;
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

	public PaySlip id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return this.month;
    }

    public PaySlip month(String month) {
        this.setMonth(month);
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getSalary() {
        return this.salary;
    }

    public PaySlip salary(Double salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public PaySlip branchId(Long branchId) {
        this.setBranchId(branchId);
        return this;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getStatus() {
        return this.status;
    }

    public PaySlip status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public PaySlip employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getYear() {
        return this.year;
    }

    public PaySlip year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    
    public String getSalaryStatus() {
		return salaryStatus;
	}
    
    public PaySlip salaryStatus(String salaryStatus) {
        this.setSalaryStatus(salaryStatus);
        return this;
    }

	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}

	public Instant getSalaryDate() {
		return salaryDate;
	}
	
    public PaySlip salaryDate(Instant salaryDate) {
        this.setSalaryDate(salaryDate);
        return this;
    }

	public void setSalaryDate(Instant salaryDate) {
		this.salaryDate = salaryDate;
	}

	public String getDescription() {
		return description;
	}
	
	 public PaySlip description(String description) {
	        this.setDescription(description);
	        return this;
	    }

	public void setDescription(String description) {
		this.description = description;
	}

	
	
//    public Long getCompanyId() {
//        return this.companyId;
//    }
//
//    public PaySlip companyId(Long companyId) {
//        this.setCompanyId(companyId);
//        return this;
//    }
//
//    public void setCompanyId(Long companyId) {
//        this.companyId = companyId;
//    }
//
//    public Instant getLastModified() {
//        return this.lastModified;
//    }
//
//    public PaySlip lastModified(Instant lastModified) {
//        this.setLastModified(lastModified);
//        return this;
//    }
//
//    public void setLastModified(Instant lastModified) {
//        this.lastModified = lastModified;
//    }
//
//    public String getLastModifiedBy() {
//        return this.lastModifiedBy;
//    }
//
//    public PaySlip lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    

	
	public Double getActualGrossPay() {
		return actualGrossPay;
	}

	public void setActualGrossPay(Double actualGrossPay) {
		this.actualGrossPay = actualGrossPay;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaySlip)) {
            return false;
        }
        return id != null && id.equals(((PaySlip) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaySlip{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", salary=" + getSalary() +
            ", branchId=" + getBranchId() +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
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
//            ", companyId=" + getCompanyId() +
//            ", lastModified='" + getLastModified() + "'" +
//            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
