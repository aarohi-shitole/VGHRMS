package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.PayrollAdditions} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.PayrollAdditionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payroll-additions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PayrollAdditionsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter category;

    private BooleanFilter hasUnitCal;

    private DoubleFilter unitAmount;

    private StringFilter assignType;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public PayrollAdditionsCriteria() {}

    public PayrollAdditionsCriteria(PayrollAdditionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.hasUnitCal = other.hasUnitCal == null ? null : other.hasUnitCal.copy();
        this.unitAmount = other.unitAmount == null ? null : other.unitAmount.copy();
        this.assignType = other.assignType == null ? null : other.assignType.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PayrollAdditionsCriteria copy() {
        return new PayrollAdditionsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public BooleanFilter getHasUnitCal() {
        return hasUnitCal;
    }

    public BooleanFilter hasUnitCal() {
        if (hasUnitCal == null) {
            hasUnitCal = new BooleanFilter();
        }
        return hasUnitCal;
    }

    public void setHasUnitCal(BooleanFilter hasUnitCal) {
        this.hasUnitCal = hasUnitCal;
    }

    public DoubleFilter getUnitAmount() {
        return unitAmount;
    }

    public DoubleFilter unitAmount() {
        if (unitAmount == null) {
            unitAmount = new DoubleFilter();
        }
        return unitAmount;
    }

    public void setUnitAmount(DoubleFilter unitAmount) {
        this.unitAmount = unitAmount;
    }

    public StringFilter getAssignType() {
        return assignType;
    }

    public StringFilter assignType() {
        if (assignType == null) {
            assignType = new StringFilter();
        }
        return assignType;
    }

    public void setAssignType(StringFilter assignType) {
        this.assignType = assignType;
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
        final PayrollAdditionsCriteria that = (PayrollAdditionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(category, that.category) &&
            Objects.equals(hasUnitCal, that.hasUnitCal) &&
            Objects.equals(unitAmount, that.unitAmount) &&
            Objects.equals(assignType, that.assignType) &&
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
            name,
            category,
            hasUnitCal,
            unitAmount,
            assignType,
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
        return "PayrollAdditionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (hasUnitCal != null ? "hasUnitCal=" + hasUnitCal + ", " : "") +
            (unitAmount != null ? "unitAmount=" + unitAmount + ", " : "") +
            (assignType != null ? "assignType=" + assignType + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
