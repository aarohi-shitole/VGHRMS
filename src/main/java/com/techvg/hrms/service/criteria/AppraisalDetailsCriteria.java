package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.PerformanceIndicator} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.PerformanceIndicatorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /performance-indicators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter performanceAppraisalId;

    private StringFilter expectedValue;

    private StringFilter setValue;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

   

    private StringFilter lastModifiedBy;

    private LongFilter performanceIndicatorId;

    private Boolean distinct;

    public AppraisalDetailsCriteria() {}

    public AppraisalDetailsCriteria(AppraisalDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.performanceAppraisalId = other.performanceAppraisalId == null ? null : other.performanceAppraisalId.copy();
        this.expectedValue = other.expectedValue == null ? null : other.expectedValue.copy();
        this.setValue = other.setValue == null ? null : other.setValue.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
      
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.performanceIndicatorId = other.performanceIndicatorId == null ? null : other.performanceIndicatorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AppraisalDetailsCriteria copy() {
        return new AppraisalDetailsCriteria(this);
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

    public LongFilter getPerformanceAppraisalId() {
        return performanceAppraisalId;
    }

    public LongFilter performanceAppraisalId() {
        if (performanceAppraisalId == null) {
            performanceAppraisalId = new LongFilter();
        }
        return performanceAppraisalId;
    }

    public void setPerformanceAppraisalId(LongFilter performanceAppraisalId) {
        this.performanceAppraisalId = performanceAppraisalId;
    }

    public StringFilter getExpectedValue() {
        return expectedValue;
    }

    public StringFilter expectedValue() {
        if (expectedValue == null) {
            expectedValue = new StringFilter();
        }
        return expectedValue;
    }

    public void setExpectedValue(StringFilter expectedValue) {
        this.expectedValue = expectedValue;
    }

    public StringFilter getSetValue() {
        return setValue;
    }

    public StringFilter setValue() {
        if (setValue == null) {
            setValue = new StringFilter();
        }
        return setValue;
    }

    public void setSetValue(StringFilter setValue) {
        this.setValue = setValue;
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

    public LongFilter getPerformanceIndicatorId() {
        return performanceIndicatorId;
    }

    public LongFilter performanceIndicatorId() {
        if (performanceIndicatorId == null) {
            performanceIndicatorId = new LongFilter();
        }
        return performanceIndicatorId;
    }

    public void setPerformanceIndicatorId(LongFilter performanceIndicatorId) {
        this.performanceIndicatorId = performanceIndicatorId;
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
        final AppraisalDetailsCriteria that = (AppraisalDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(performanceAppraisalId, that.performanceAppraisalId) &&
            Objects.equals(expectedValue, that.expectedValue) &&
            Objects.equals(setValue, that.setValue) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
           
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(performanceIndicatorId, that.performanceIndicatorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            performanceAppraisalId,
            expectedValue,
            setValue,
            status,
            companyId,
            lastModified,
           
            lastModifiedBy,
            performanceIndicatorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (performanceAppraisalId != null ? "performanceAppraisalId=" + performanceAppraisalId + ", " : "") +
            (expectedValue != null ? "expectedValue=" + expectedValue + ", " : "") +
            (setValue != null ? "setValue=" + setValue + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
           
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (performanceIndicatorId != null ? "performanceIndicatorId=" + performanceIndicatorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
