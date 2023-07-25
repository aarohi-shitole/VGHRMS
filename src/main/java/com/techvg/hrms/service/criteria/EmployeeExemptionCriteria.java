package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.EmployeeExemption} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EmployeeExemptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-exemptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeExemptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amount;

    private LongFilter taxExempSectionId;

    private StringFilter exemptionStatus;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public EmployeeExemptionCriteria() {}

    public EmployeeExemptionCriteria(EmployeeExemptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.taxExempSectionId = other.taxExempSectionId == null ? null : other.taxExempSectionId.copy();
        this.exemptionStatus = other.exemptionStatus == null ? null : other.exemptionStatus.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeExemptionCriteria copy() {
        return new EmployeeExemptionCriteria(this);
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

    public LongFilter getTaxExempSectionId() {
        return taxExempSectionId;
    }

    public LongFilter taxExempSectionId() {
        if (taxExempSectionId == null) {
        	taxExempSectionId = new LongFilter();
        }
        return taxExempSectionId;
    }

    public void setTaxExempSectionId(LongFilter taxExempSectionId) {
        this.taxExempSectionId = taxExempSectionId;
    }

    public StringFilter getExemptionStatus() {
        return exemptionStatus;
    }

    public StringFilter exemptionStatus() {
        if (exemptionStatus == null) {
            exemptionStatus = new StringFilter();
        }
        return exemptionStatus;
    }

    public void setExemptionStatus(StringFilter exemptionStatus) {
        this.exemptionStatus = exemptionStatus;
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
        final EmployeeExemptionCriteria that = (EmployeeExemptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(taxExempSectionId, that.taxExempSectionId) &&
            Objects.equals(exemptionStatus, that.exemptionStatus) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            amount,
            taxExempSectionId,
            exemptionStatus,
            status,
            employeeId,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeExemptionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (taxExempSectionId != null ? "taxExempSectionId=" + taxExempSectionId + ", " : "") +
            (exemptionStatus != null ? "exemptionStatus=" + exemptionStatus + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
