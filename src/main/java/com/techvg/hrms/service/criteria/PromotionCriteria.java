package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Promotion} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.PromotionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /promotions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PromotionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter promotionFor;

    private StringFilter promotedFrom;

    private StringFilter promotedTo;

    private InstantFilter promotiedDate;

    private LongFilter branchId;

    private LongFilter departmentId;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public PromotionCriteria() {}

    public PromotionCriteria(PromotionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.promotionFor = other.promotionFor == null ? null : other.promotionFor.copy();
        this.promotedFrom = other.promotedFrom == null ? null : other.promotedFrom.copy();
        this.promotedTo = other.promotedTo == null ? null : other.promotedTo.copy();
        this.promotiedDate = other.promotiedDate == null ? null : other.promotiedDate.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PromotionCriteria copy() {
        return new PromotionCriteria(this);
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

    public StringFilter getPromotionFor() {
        return promotionFor;
    }

    public StringFilter promotionFor() {
        if (promotionFor == null) {
            promotionFor = new StringFilter();
        }
        return promotionFor;
    }

    public void setPromotionFor(StringFilter promotionFor) {
        this.promotionFor = promotionFor;
    }

    public StringFilter getPromotedFrom() {
        return promotedFrom;
    }

    public StringFilter promotedFrom() {
        if (promotedFrom == null) {
            promotedFrom = new StringFilter();
        }
        return promotedFrom;
    }

    public void setPromotedFrom(StringFilter promotedFrom) {
        this.promotedFrom = promotedFrom;
    }

    public StringFilter getPromotedTo() {
        return promotedTo;
    }

    public StringFilter promotedTo() {
        if (promotedTo == null) {
            promotedTo = new StringFilter();
        }
        return promotedTo;
    }

    public void setPromotedTo(StringFilter promotedTo) {
        this.promotedTo = promotedTo;
    }

    public InstantFilter getPromotiedDate() {
        return promotiedDate;
    }

    public InstantFilter promotiedDate() {
        if (promotiedDate == null) {
            promotiedDate = new InstantFilter();
        }
        return promotiedDate;
    }

    public void setPromotiedDate(InstantFilter promotiedDate) {
        this.promotiedDate = promotiedDate;
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

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
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
        final PromotionCriteria that = (PromotionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(promotionFor, that.promotionFor) &&
            Objects.equals(promotedFrom, that.promotedFrom) &&
            Objects.equals(promotedTo, that.promotedTo) &&
            Objects.equals(promotiedDate, that.promotiedDate) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(departmentId, that.departmentId) &&
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
            promotionFor,
            promotedFrom,
            promotedTo,
            promotiedDate,
            branchId,
            departmentId,
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
        return "PromotionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (promotionFor != null ? "promotionFor=" + promotionFor + ", " : "") +
            (promotedFrom != null ? "promotedFrom=" + promotedFrom + ", " : "") +
            (promotedTo != null ? "promotedTo=" + promotedTo + ", " : "") +
            (promotiedDate != null ? "promotiedDate=" + promotiedDate + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
