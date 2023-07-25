package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Salary} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.SalaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /salaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalaryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private DoubleFilter amount;

    private BooleanFilter isdeduction;

    private StringFilter month;

    private StringFilter year;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;
    
    private DoubleFilter earningAmount;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public SalaryCriteria() {}

    public SalaryCriteria(SalaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.isdeduction = other.isdeduction == null ? null : other.isdeduction.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.earningAmount = other.earningAmount == null ? null : other.earningAmount.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    
    public DoubleFilter getEarningAmount() {
		return earningAmount;
	}

	public void setEarningAmount(DoubleFilter earningAmount) {
		this.earningAmount = earningAmount;
	}

	@Override
    public SalaryCriteria copy() {
        return new SalaryCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public BooleanFilter getIsdeduction() {
        return isdeduction;
    }

    public BooleanFilter isdeduction() {
        if (isdeduction == null) {
            isdeduction = new BooleanFilter();
        }
        return isdeduction;
    }

    public void setIsdeduction(BooleanFilter isdeduction) {
        this.isdeduction = isdeduction;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SalaryCriteria that = (SalaryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(isdeduction, that.isdeduction) &&
            Objects.equals(month, that.month) &&
            Objects.equals(year, that.year) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(earningAmount, that.earningAmount) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            amount,
            isdeduction,
            month,
            year,
            status,
            employeeId,
            companyId,
            earningAmount,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (isdeduction != null ? "isdeduction=" + isdeduction + ", " : "") +
            (month != null ? "month=" + month + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (earningAmount != null ? "earningAmount=" + earningAmount + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
