package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.LeavePolicy} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.LeavePolicyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-policies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeavePolicyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isCarryForword;

    private LongFilter genderLeave;

    private LongFilter totalLeave;

    private LongFilter maxLeave;

    private BooleanFilter hasproRataLeave;

    private StringFilter description;

    private StringFilter refTable;

    private LongFilter refTableId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter leaveTypeId;

    private LongFilter employmentTypeId;

    private Boolean distinct;

    public LeavePolicyCriteria() {}

    public LeavePolicyCriteria(LeavePolicyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isCarryForword = other.isCarryForword == null ? null : other.isCarryForword.copy();
        this.genderLeave = other.genderLeave == null ? null : other.genderLeave.copy();
        this.totalLeave = other.totalLeave == null ? null : other.totalLeave.copy();
        this.maxLeave = other.maxLeave == null ? null : other.maxLeave.copy();
        this.hasproRataLeave = other.hasproRataLeave == null ? null : other.hasproRataLeave.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.refTable = other.refTable == null ? null : other.refTable.copy();
        this.refTableId = other.refTableId == null ? null : other.refTableId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.employmentTypeId = other.employmentTypeId == null ? null : other.employmentTypeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeavePolicyCriteria copy() {
        return new LeavePolicyCriteria(this);
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

    public BooleanFilter getIsCarryForword() {
        return isCarryForword;
    }

    public BooleanFilter isCarryForword() {
        if (isCarryForword == null) {
            isCarryForword = new BooleanFilter();
        }
        return isCarryForword;
    }

    public void setIsCarryForword(BooleanFilter isCarryForword) {
        this.isCarryForword = isCarryForword;
    }

    public LongFilter getGenderLeave() {
        return genderLeave;
    }

    public LongFilter genderLeave() {
        if (genderLeave == null) {
            genderLeave = new LongFilter();
        }
        return genderLeave;
    }

    public void setGenderLeave(LongFilter genderLeave) {
        this.genderLeave = genderLeave;
    }

    public LongFilter getTotalLeave() {
        return totalLeave;
    }

    public LongFilter totalLeave() {
        if (totalLeave == null) {
            totalLeave = new LongFilter();
        }
        return totalLeave;
    }

    public void setTotalLeave(LongFilter totalLeave) {
        this.totalLeave = totalLeave;
    }

    public LongFilter getMaxLeave() {
        return maxLeave;
    }

    public LongFilter maxLeave() {
        if (maxLeave == null) {
            maxLeave = new LongFilter();
        }
        return maxLeave;
    }

    public void setMaxLeave(LongFilter maxLeave) {
        this.maxLeave = maxLeave;
    }

    public BooleanFilter getHasproRataLeave() {
        return hasproRataLeave;
    }

    public BooleanFilter hasproRataLeave() {
        if (hasproRataLeave == null) {
            hasproRataLeave = new BooleanFilter();
        }
        return hasproRataLeave;
    }

    public void setHasproRataLeave(BooleanFilter hasproRataLeave) {
        this.hasproRataLeave = hasproRataLeave;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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

    public LongFilter getLeaveTypeId() {
        return leaveTypeId;
    }

    public LongFilter leaveTypeId() {
        if (leaveTypeId == null) {
            leaveTypeId = new LongFilter();
        }
        return leaveTypeId;
    }

    public void setLeaveTypeId(LongFilter leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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
        final LeavePolicyCriteria that = (LeavePolicyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isCarryForword, that.isCarryForword) &&
            Objects.equals(genderLeave, that.genderLeave) &&
            Objects.equals(totalLeave, that.totalLeave) &&
            Objects.equals(maxLeave, that.maxLeave) &&
            Objects.equals(hasproRataLeave, that.hasproRataLeave) &&
            Objects.equals(description, that.description) &&
            Objects.equals(refTable, that.refTable) &&
            Objects.equals(refTableId, that.refTableId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(leaveTypeId, that.leaveTypeId) &&
            Objects.equals(employmentTypeId, that.employmentTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            isCarryForword,
            genderLeave,
            totalLeave,
            maxLeave,
            hasproRataLeave,
            description,
            refTable,
            refTableId,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            leaveTypeId,
            employmentTypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavePolicyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isCarryForword != null ? "isCarryForword=" + isCarryForword + ", " : "") +
            (genderLeave != null ? "genderLeave=" + genderLeave + ", " : "") +
            (totalLeave != null ? "totalLeave=" + totalLeave + ", " : "") +
            (maxLeave != null ? "maxLeave=" + maxLeave + ", " : "") +
            (hasproRataLeave != null ? "hasproRataLeave=" + hasproRataLeave + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (refTable != null ? "refTable=" + refTable + ", " : "") +
            (refTableId != null ? "refTableId=" + refTableId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (employmentTypeId != null ? "employmentTypeId=" + employmentTypeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
