package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.PaySlip} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.PaySlipResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pay-slips?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaySlipCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter month;

    private DoubleFilter salary;

    private LongFilter branchId;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;
    
    private StringFilter year;
    
    private StringFilter salaryStatus;
    
    private InstantFilter salaryDate;

    private StringFilter description;
    
    private DoubleFilter totalEarning;
    
    private DoubleFilter totalDeduction;
    
    private StringFilter salaryInWords;
    
    private DoubleFilter lossOfPay;
    
   // private LongFilter presentDays;
    private DoubleFilter presentDays;
    
    private DoubleFilter actualGrossPay;
    
    public PaySlipCriteria() {}

    public PaySlipCriteria(PaySlipCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.salaryStatus = other.salaryStatus == null ? null : other.salaryStatus.copy();
        this.salaryDate = other.salaryDate == null ? null : other.salaryDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.totalEarning = other.totalEarning == null ? null : other.totalEarning.copy();
        this.totalDeduction = other.totalDeduction == null ? null : other.totalDeduction.copy();
        this.salaryInWords = other.salaryInWords == null ? null : other.salaryInWords.copy();
        this.lossOfPay = other.lossOfPay == null ? null : other.lossOfPay.copy();
        this.presentDays = other.presentDays == null ? null : other.presentDays.copy();
        this.actualGrossPay = other.actualGrossPay == null ? null : other.actualGrossPay.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaySlipCriteria copy() {
        return new PaySlipCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }
    
    public StringFilter getMonth() {
        return month;
    }

    public StringFilter month() {
        if (month == null) {
        	month = new StringFilter();
        }
        return month;
    }

    public void setMonth(StringFilter month) {
        this.month = month;
    }

    

    public DoubleFilter getLossOfPay() {
		return lossOfPay;
	}

	public void setLossOfPay(DoubleFilter lossOfPay) {
		this.lossOfPay = lossOfPay;
	}

	
	
//	public LongFilter getPresentDays() {
//		return presentDays;
//	}
//
//	public void setPresentDays(LongFilter presentDays) {
//		this.presentDays = presentDays;
//	}

	public DoubleFilter getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(DoubleFilter presentDays) {
		this.presentDays = presentDays;
	}

	public StringFilter getSalaryInWords() {
		return salaryInWords;
	}

	public void setSalaryInWords(StringFilter salaryInWords) {
		this.salaryInWords = salaryInWords;
	}

	public DoubleFilter getTotalEarning() {
		return totalEarning;
	}

	public void setTotalEarning(DoubleFilter totalEarning) {
		this.totalEarning = totalEarning;
	}

	public DoubleFilter getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(DoubleFilter totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public DoubleFilter getSalary() {
        return salary;
    }

    public DoubleFilter salary() {
        if (salary == null) {
            salary = new DoubleFilter();
        }
        return salary;
    }

    public void setSalary(DoubleFilter salary) {
        this.salary = salary;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
    
    public StringFilter getYear() {
        return year;
    }

    public StringFilter year() {
        if (year == null) {
        	year = new StringFilter();
        }
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }
    
    

    public StringFilter getSalaryStatus() {
		return salaryStatus;
	}
    
    public StringFilter salaryStatus() {
        if (salaryStatus == null) {
        	salaryStatus = new StringFilter();
        }
        return salaryStatus;
    }

	public void setSalaryStatus(StringFilter salaryStatus) {
		this.salaryStatus = salaryStatus;
	}
	

	public InstantFilter getSalaryDate() {
		return salaryDate;
	}
	   
    public InstantFilter salaryDate() {
        if (salaryDate == null) {
        	salaryDate = new InstantFilter();
        }
        return salaryDate;
    }

	public void setSalaryDate(InstantFilter salaryDate) {
		this.salaryDate = salaryDate;
	}

	public StringFilter getDescription() {
		return description;
	}
	
    public StringFilter description() {
        if (description == null) {
        	description = new StringFilter();
        }
        return description;
    }

	public void setDescription(StringFilter description) {
		this.description = description;
	}

	
	
	public DoubleFilter getActualGrossPay() {
		return actualGrossPay;
	}

	public void setActualGrossPay(DoubleFilter actualGrossPay) {
		this.actualGrossPay = actualGrossPay;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaySlipCriteria that = (PaySlipCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(month, that.month) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(year, that.year) &&
            Objects.equals(salaryStatus, that.salaryStatus) &&
            Objects.equals(salaryDate, that.salaryDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(totalEarning, that.totalEarning) &&
            Objects.equals(totalDeduction, that.totalDeduction) &&
            Objects.equals(salaryInWords, that.salaryInWords) &&
            Objects.equals(lossOfPay, that.lossOfPay) &&
            Objects.equals(presentDays, that.presentDays) &&
            Objects.equals(actualGrossPay, that.actualGrossPay) &&
            Objects.equals(distinct, that.distinct)
 
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, month, salary, branchId, status, employeeId, companyId, 
        		lastModified, lastModifiedBy, year,salaryStatus,salaryDate,description,totalEarning,totalDeduction,salaryInWords,lossOfPay,presentDays,actualGrossPay, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaySlipCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (month != null ? "month=" + month + ", " : "") +
            (salary != null ? "salary=" + salary + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (salaryStatus != null ? "salaryStatus=" + salaryStatus + ", " : "") +
            (salaryDate != null ? "salaryDate=" + salaryDate + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (totalEarning != null ? "totalEarning=" + totalEarning + ", " : "") +
            (totalDeduction != null ? "totalDeduction=" + totalDeduction + ", " : "") +
            (salaryInWords != null ? "salaryInWords=" + salaryInWords + ", " : "") +
            (lossOfPay != null ? "lossOfPay=" + lossOfPay + ", " : "") +
            (presentDays != null ? "preentDays=" + presentDays + ", " : "") +
            (actualGrossPay != null ? "actualGrossPay=" + actualGrossPay + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
