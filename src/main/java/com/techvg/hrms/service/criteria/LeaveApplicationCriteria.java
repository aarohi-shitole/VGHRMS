package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.LeaveApplication} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.LeaveApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LeaveApplicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter leaveType;

    private LongFilter noOfDays;

    private StringFilter reason;

    private InstantFilter formDate;

    private InstantFilter toDate;

    private StringFilter leaveStatus;

    private StringFilter status;

    private LongFilter employeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public LeaveApplicationCriteria() {}

    public LeaveApplicationCriteria(LeaveApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.leaveType = other.leaveType == null ? null : other.leaveType.copy();
        this.noOfDays = other.noOfDays == null ? null : other.noOfDays.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.formDate = other.formDate == null ? null : other.formDate.copy();
        this.toDate = other.toDate == null ? null : other.toDate.copy();
        this.leaveStatus = other.leaveStatus == null ? null : other.leaveStatus.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LeaveApplicationCriteria copy() {
        return new LeaveApplicationCriteria(this);
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

    public StringFilter getReason() {
        return reason;
    }

    public StringFilter reason() {
        if (reason == null) {
            reason = new StringFilter();
        }
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public InstantFilter getFormDate() {
        return formDate;
    }

    public InstantFilter formDate() {
        if (formDate == null) {
            formDate = new InstantFilter();
        }
        return formDate;
    }

    public void setFormDate(InstantFilter formDate) {
        this.formDate = formDate;
    }

    public InstantFilter getToDate() {
        return toDate;
    }

    public InstantFilter toDate() {
        if (toDate == null) {
            toDate = new InstantFilter();
        }
        return toDate;
    }

    public void setToDate(InstantFilter toDate) {
        this.toDate = toDate;
    }

    public StringFilter getLeaveStatus() {
        return leaveStatus;
    }

    public StringFilter leaveStatus() {
        if (leaveStatus == null) {
            leaveStatus = new StringFilter();
        }
        return leaveStatus;
    }

    public void setLeaveStatus(StringFilter leaveStatus) {
        this.leaveStatus = leaveStatus;
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

    public LongFilter getEmployeId() {
        return employeId;
    }

    public LongFilter employeId() {
        if (employeId == null) {
            employeId = new LongFilter();
        }
        return employeId;
    }

    public void setEmployeId(LongFilter employeId) {
        this.employeId = employeId;
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
        final LeaveApplicationCriteria that = (LeaveApplicationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(leaveType, that.leaveType) &&
            Objects.equals(noOfDays, that.noOfDays) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(formDate, that.formDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(leaveStatus, that.leaveStatus) &&
            Objects.equals(status, that.status) &&
            Objects.equals(employeId, that.employeId) &&
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
            leaveType,
            noOfDays,
            reason,
            formDate,
            toDate,
            leaveStatus,
            status,
            employeId,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (leaveType != null ? "leaveType=" + leaveType + ", " : "") +
            (noOfDays != null ? "noOfDays=" + noOfDays + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (formDate != null ? "formDate=" + formDate + ", " : "") +
            (toDate != null ? "toDate=" + toDate + ", " : "") +
            (leaveStatus != null ? "leaveStatus=" + leaveStatus + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeId != null ? "employeId=" + employeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
