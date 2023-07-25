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
public class PerformanceIndicatorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter designationId;

    private StringFilter indicatorValue;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter masterPerformanceIndicatorId;

    private Boolean distinct;

    public PerformanceIndicatorCriteria() {}

    public PerformanceIndicatorCriteria(PerformanceIndicatorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.indicatorValue = other.indicatorValue == null ? null : other.indicatorValue.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.masterPerformanceIndicatorId = other.masterPerformanceIndicatorId == null ? null : other.masterPerformanceIndicatorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PerformanceIndicatorCriteria copy() {
        return new PerformanceIndicatorCriteria(this);
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

    public LongFilter getDesignationId() {
        return designationId;
    }

    public LongFilter designationId() {
        if (designationId == null) {
            designationId = new LongFilter();
        }
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }

    public StringFilter getIndicatorValue() {
        return indicatorValue;
    }

    public StringFilter indicatorValue() {
        if (indicatorValue == null) {
            indicatorValue = new StringFilter();
        }
        return indicatorValue;
    }

    public void setIndicatorValue(StringFilter indicatorValue) {
        this.indicatorValue = indicatorValue;
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

    public LongFilter getMasterPerformanceIndicatorId() {
        return masterPerformanceIndicatorId;
    }

    public LongFilter masterPerformanceIndicatorId() {
        if (masterPerformanceIndicatorId == null) {
            masterPerformanceIndicatorId = new LongFilter();
        }
        return masterPerformanceIndicatorId;
    }

    public void setMasterPerformanceIndicatorId(LongFilter masterPerformanceIndicatorId) {
        this.masterPerformanceIndicatorId = masterPerformanceIndicatorId;
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
        final PerformanceIndicatorCriteria that = (PerformanceIndicatorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(indicatorValue, that.indicatorValue) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(masterPerformanceIndicatorId, that.masterPerformanceIndicatorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            designationId,
            indicatorValue,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            masterPerformanceIndicatorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceIndicatorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (indicatorValue != null ? "indicatorValue=" + indicatorValue + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (masterPerformanceIndicatorId != null ? "masterPerformanceIndicatorId=" + masterPerformanceIndicatorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
