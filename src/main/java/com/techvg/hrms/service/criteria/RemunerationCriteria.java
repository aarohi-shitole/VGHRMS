package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Remuneration} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.RemunerationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /remunerations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemunerationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter salaryType;

    private DoubleFilter amount;

    private StringFilter paymentType;

    private LongFilter employeeId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;
    
    private LongFilter taxRegimeId;

    public RemunerationCriteria() {}

    public RemunerationCriteria(RemunerationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.salaryType = other.salaryType == null ? null : other.salaryType.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.taxRegimeId = other.taxRegimeId == null ? null : other.taxRegimeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RemunerationCriteria copy() {
        return new RemunerationCriteria(this);
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

    public StringFilter getSalaryType() {
        return salaryType;
    }

    public StringFilter salaryType() {
        if (salaryType == null) {
            salaryType = new StringFilter();
        }
        return salaryType;
    }

    public void setSalaryType(StringFilter salaryType) {
        this.salaryType = salaryType;
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

    public StringFilter getPaymentType() {
        return paymentType;
    }

    public StringFilter paymentType() {
        if (paymentType == null) {
            paymentType = new StringFilter();
        }
        return paymentType;
    }

    public void setPaymentType(StringFilter paymentType) {
        this.paymentType = paymentType;
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

    public LongFilter getTaxRegimeId() {
        return taxRegimeId;
    }

    public LongFilter taxRegimeId() {
        if (taxRegimeId == null) {
        	taxRegimeId = new LongFilter();
        }
        return taxRegimeId;
    }

    public void setTaxRegimeId(LongFilter taxRegimeId) {
        this.taxRegimeId = taxRegimeId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RemunerationCriteria that = (RemunerationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(salaryType, that.salaryType) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(taxRegimeId, that.taxRegimeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, salaryType, amount, paymentType, employeeId, status, companyId, lastModified, lastModifiedBy, distinct,taxRegimeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemunerationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (salaryType != null ? "salaryType=" + salaryType + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            (taxRegimeId != null ? "taxRegimeId=" + taxRegimeId + ", " : "") +
            "}";
    }
}
