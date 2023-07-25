package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.PerformanceAppraisal} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.PerformanceAppraisalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /performance-appraisals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceAppraisalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter employeeId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;
    
    private InstantFilter appraisalDate;

    private StringFilter lastModifiedBy;

    private LongFilter appraisalReviewId;

    private Boolean distinct;

    public PerformanceAppraisalCriteria() {}

    public PerformanceAppraisalCriteria(PerformanceAppraisalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.appraisalDate = other.appraisalDate == null ? null : other.appraisalDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.appraisalReviewId = other.appraisalReviewId == null ? null : other.appraisalReviewId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PerformanceAppraisalCriteria copy() {
        return new PerformanceAppraisalCriteria(this);
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

    //
    public InstantFilter getAppraisalDate() {
        return appraisalDate;
    }

    public InstantFilter appraisalDate() {
        if (appraisalDate == null) {
            appraisalDate = new InstantFilter();
        }
        return appraisalDate;
    }

    public void setAppraisalDate(InstantFilter appraisalDate) {
        this.appraisalDate = appraisalDate;
    }
    //
    
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

    public LongFilter getAppraisalReviewId() {
        return appraisalReviewId;
    }

    public LongFilter appraisalReviewId() {
        if (appraisalReviewId == null) {
            appraisalReviewId = new LongFilter();
        }
        return appraisalReviewId;
    }

    public void setAppraisalReviewId(LongFilter appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
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
        final PerformanceAppraisalCriteria that = (PerformanceAppraisalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(appraisalDate, that.appraisalDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(appraisalReviewId, that.appraisalReviewId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, status, companyId, lastModified, appraisalDate, lastModifiedBy, appraisalReviewId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceAppraisalCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (appraisalDate != null ? "appraisalDate=" + appraisalDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (appraisalReviewId != null ? "appraisalReviewId=" + appraisalReviewId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
