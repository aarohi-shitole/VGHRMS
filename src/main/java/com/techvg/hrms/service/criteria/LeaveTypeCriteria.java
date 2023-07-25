package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.LeaveType} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.LeaveTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter leaveType;

    private LongFilter noOfDays;

    private BooleanFilter hasCarryForward;

    private BooleanFilter hasEarned;

    private BooleanFilter hasCustomPolicy;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;
    
    private BooleanFilter allowBackward;

    public LeaveTypeCriteria() {}

    public LeaveTypeCriteria(LeaveTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leaveType = other.leaveType == null ? null : other.leaveType.copy();
        this.noOfDays = other.noOfDays == null ? null : other.noOfDays.copy();
        this.hasCarryForward = other.hasCarryForward == null ? null : other.hasCarryForward.copy();
        this.hasEarned = other.hasEarned == null ? null : other.hasEarned.copy();
        this.hasCustomPolicy = other.hasCustomPolicy == null ? null : other.hasCustomPolicy.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
        this.allowBackward = other.allowBackward == null ? null : other.allowBackward.copy();
    }

    @Override
    public LeaveTypeCriteria copy() {
        return new LeaveTypeCriteria(this);
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

    public StringFilter getLeaveType() {
        return leaveType;
    }

    public StringFilter leaveType() {
        if (leaveType == null) {
            leaveType = new StringFilter();
        }
        return leaveType;
    }

    public void setLeaveType(StringFilter leaveType) {
        this.leaveType = leaveType;
    }

    public LongFilter getNoOfDays() {
        return noOfDays;
    }

    public LongFilter noOfDays() {
        if (noOfDays == null) {
            noOfDays = new LongFilter();
        }
        return noOfDays;
    }

    public void setNoOfDays(LongFilter noOfDays) {
        this.noOfDays = noOfDays;
    }

    public BooleanFilter getHasCarryForward() {
        return hasCarryForward;
    }

    public BooleanFilter hasCarryForward() {
        if (hasCarryForward == null) {
            hasCarryForward = new BooleanFilter();
        }
        return hasCarryForward;
    }

    public void setHasCarryForward(BooleanFilter hasCarryForward) {
        this.hasCarryForward = hasCarryForward;
    }

    public BooleanFilter getHasEarned() {
        return hasEarned;
    }

    public BooleanFilter hasEarned() {
        if (hasEarned == null) {
            hasEarned = new BooleanFilter();
        }
        return hasEarned;
    }

    public void setHasEarned(BooleanFilter hasEarned) {
        this.hasEarned = hasEarned;
    }

    public BooleanFilter getHasCustomPolicy() {
        return hasCustomPolicy;
    }

    public BooleanFilter hasCustomPolicy() {
        if (hasCustomPolicy == null) {
            hasCustomPolicy = new BooleanFilter();
        }
        return hasCustomPolicy;
    }

    public void setHasCustomPolicy(BooleanFilter hasCustomPolicy) {
        this.hasCustomPolicy = hasCustomPolicy;
    }

    
    public BooleanFilter getAllowBackward() {
        return allowBackward;
    }

    public BooleanFilter allowBackward() {
        if (allowBackward == null) {
        	allowBackward = new BooleanFilter();
        }
        return allowBackward;
    }

    public void setAllowBackward(BooleanFilter allowBackward) {
        this.allowBackward = allowBackward;
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
        final LeaveTypeCriteria that = (LeaveTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leaveType, that.leaveType) &&
            Objects.equals(noOfDays, that.noOfDays) &&
            Objects.equals(hasCarryForward, that.hasCarryForward) &&
            Objects.equals(hasEarned, that.hasEarned) &&
            Objects.equals(hasCustomPolicy, that.hasCustomPolicy) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(allowBackward, that.allowBackward) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            leaveType,
            noOfDays,
            hasCarryForward,
            hasEarned,
            hasCustomPolicy,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct,
            allowBackward
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leaveType != null ? "leaveType=" + leaveType + ", " : "") +
            (noOfDays != null ? "noOfDays=" + noOfDays + ", " : "") +
            (hasCarryForward != null ? "hasCarryForward=" + hasCarryForward + ", " : "") +
            (hasEarned != null ? "hasEarned=" + hasEarned + ", " : "") +
            (hasCustomPolicy != null ? "hasCustomPolicy=" + hasCustomPolicy + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            (allowBackward != null ? "allowBackward=" + allowBackward + ", " : "") +
            "}";
    }
}
