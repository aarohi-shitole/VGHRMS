package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;
import tech.jhipster.service.filter.DoubleFilter;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.WorkingHours} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.WorkingHoursResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /working-hours?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingHoursCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter noOfHours;

    private LongFilter employmentTypeId;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter refTable;

    private LongFilter refTableId;

    private Boolean distinct;

    public WorkingHoursCriteria() {}

    public WorkingHoursCriteria(WorkingHoursCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.noOfHours = other.noOfHours == null ? null : other.noOfHours.copy();
        this.employmentTypeId = other.employmentTypeId == null ? null : other.employmentTypeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.refTable = other.refTable == null ? null : other.refTable.copy();
        this.refTableId = other.refTableId == null ? null : other.refTableId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public WorkingHoursCriteria copy() {
        return new WorkingHoursCriteria(this);
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

    public DoubleFilter getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(DoubleFilter noOfHours) {
		this.noOfHours = noOfHours;
	}
	
    public DoubleFilter noOfHours() {
        if (noOfHours == null) {
        	noOfHours = new DoubleFilter();
        }
        return noOfHours;
    }

	public LongFilter getEmploymentTypeId() {
        return employmentTypeId;
    }

    public LongFilter employmentTypeId() {
        if (employmentTypeId == null) {
            employmentTypeId = new LongFilter();
        }
        return employmentTypeId;
    }

    public void setEmploymentTypeId(LongFilter employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
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

    public StringFilter getRefTable() {
        return refTable;
    }

    public StringFilter refTable() {
        if (refTable == null) {
            refTable = new StringFilter();
        }
        return refTable;
    }

    public void setRefTable(StringFilter refTable) {
        this.refTable = refTable;
    }

    public LongFilter getRefTableId() {
        return refTableId;
    }

    public LongFilter refTableId() {
        if (refTableId == null) {
            refTableId = new LongFilter();
        }
        return refTableId;
    }

    public void setRefTableId(LongFilter refTableId) {
        this.refTableId = refTableId;
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
        final WorkingHoursCriteria that = (WorkingHoursCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(noOfHours, that.noOfHours) &&
            Objects.equals(employmentTypeId, that.employmentTypeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(refTable, that.refTable) &&
            Objects.equals(refTableId, that.refTableId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            noOfHours,
            employmentTypeId,
            companyId,
            status,
            lastModified,
            lastModifiedBy,
            refTable,
            refTableId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingHoursCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (noOfHours != null ? "noOfHours=" + noOfHours + ", " : "") +
            (employmentTypeId != null ? "employmentTypeId=" + employmentTypeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (refTable != null ? "refTable=" + refTable + ", " : "") +
            (refTableId != null ? "refTableId=" + refTableId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }

	
}
