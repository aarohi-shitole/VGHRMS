package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Tds} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TdsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TdsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter salaryFrom;

    private InstantFilter salaryTo;

    private DoubleFilter percentage;

    private LongFilter salarySettingId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public TdsCriteria() {}

    public TdsCriteria(TdsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.salaryFrom = other.salaryFrom == null ? null : other.salaryFrom.copy();
        this.salaryTo = other.salaryTo == null ? null : other.salaryTo.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.salarySettingId = other.salarySettingId == null ? null : other.salarySettingId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TdsCriteria copy() {
        return new TdsCriteria(this);
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

    public InstantFilter getSalaryFrom() {
        return salaryFrom;
    }

    public InstantFilter salaryFrom() {
        if (salaryFrom == null) {
            salaryFrom = new InstantFilter();
        }
        return salaryFrom;
    }

    public void setSalaryFrom(InstantFilter salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public InstantFilter getSalaryTo() {
        return salaryTo;
    }

    public InstantFilter salaryTo() {
        if (salaryTo == null) {
            salaryTo = new InstantFilter();
        }
        return salaryTo;
    }

    public void setSalaryTo(InstantFilter salaryTo) {
        this.salaryTo = salaryTo;
    }

    public DoubleFilter getPercentage() {
        return percentage;
    }

    public DoubleFilter percentage() {
        if (percentage == null) {
            percentage = new DoubleFilter();
        }
        return percentage;
    }

    public void setPercentage(DoubleFilter percentage) {
        this.percentage = percentage;
    }

    public LongFilter getSalarySettingId() {
        return salarySettingId;
    }

    public LongFilter salarySettingId() {
        if (salarySettingId == null) {
            salarySettingId = new LongFilter();
        }
        return salarySettingId;
    }

    public void setSalarySettingId(LongFilter salarySettingId) {
        this.salarySettingId = salarySettingId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TdsCriteria that = (TdsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(salaryFrom, that.salaryFrom) &&
            Objects.equals(salaryTo, that.salaryTo) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(salarySettingId, that.salarySettingId) &&
            Objects.equals(status, that.status) &&
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
            salaryFrom,
            salaryTo,
            percentage,
            salarySettingId,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TdsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (salaryFrom != null ? "salaryFrom=" + salaryFrom + ", " : "") +
            (salaryTo != null ? "salaryTo=" + salaryTo + ", " : "") +
            (percentage != null ? "percentage=" + percentage + ", " : "") +
            (salarySettingId != null ? "salarySettingId=" + salarySettingId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
