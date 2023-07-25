package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Resignation} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.ResignationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resignations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResignationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empName;

    private InstantFilter resignDate;

    private LongFilter noticePeriodIndays;

    private StringFilter reason;

    private StringFilter resignStatus;

    private InstantFilter lastWorkingDay;

    private LongFilter departmentId;

    private LongFilter employeeId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public ResignationCriteria() {}

    public ResignationCriteria(ResignationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empName = other.empName == null ? null : other.empName.copy();
        this.resignDate = other.resignDate == null ? null : other.resignDate.copy();
        this.noticePeriodIndays = other.noticePeriodIndays == null ? null : other.noticePeriodIndays.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.resignStatus = other.resignStatus == null ? null : other.resignStatus.copy();
        this.lastWorkingDay = other.lastWorkingDay == null ? null : other.lastWorkingDay.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResignationCriteria copy() {
        return new ResignationCriteria(this);
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

    public StringFilter getEmpName() {
        return empName;
    }

    public StringFilter empName() {
        if (empName == null) {
            empName = new StringFilter();
        }
        return empName;
    }

    public void setEmpName(StringFilter empName) {
        this.empName = empName;
    }

    public InstantFilter getResignDate() {
        return resignDate;
    }

    public InstantFilter resignDate() {
        if (resignDate == null) {
            resignDate = new InstantFilter();
        }
        return resignDate;
    }

    public void setResignDate(InstantFilter resignDate) {
        this.resignDate = resignDate;
    }

    public LongFilter getNoticePeriodIndays() {
        return noticePeriodIndays;
    }

    public LongFilter noticePeriodIndays() {
        if (noticePeriodIndays == null) {
            noticePeriodIndays = new LongFilter();
        }
        return noticePeriodIndays;
    }

    public void setNoticePeriodIndays(LongFilter noticePeriodIndays) {
        this.noticePeriodIndays = noticePeriodIndays;
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

    public StringFilter getResignStatus() {
        return resignStatus;
    }

    public StringFilter resignStatus() {
        if (resignStatus == null) {
            resignStatus = new StringFilter();
        }
        return resignStatus;
    }

    public void setResignStatus(StringFilter resignStatus) {
        this.resignStatus = resignStatus;
    }

    public InstantFilter getLastWorkingDay() {
        return lastWorkingDay;
    }

    public InstantFilter lastWorkingDay() {
        if (lastWorkingDay == null) {
            lastWorkingDay = new InstantFilter();
        }
        return lastWorkingDay;
    }

    public void setLastWorkingDay(InstantFilter lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
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
        final ResignationCriteria that = (ResignationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empName, that.empName) &&
            Objects.equals(resignDate, that.resignDate) &&
            Objects.equals(noticePeriodIndays, that.noticePeriodIndays) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(resignStatus, that.resignStatus) &&
            Objects.equals(lastWorkingDay, that.lastWorkingDay) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(employeeId, that.employeeId) &&
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
            empName,
            resignDate,
            noticePeriodIndays,
            reason,
            resignStatus,
            lastWorkingDay,
            departmentId,
            employeeId,
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
        return "ResignationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empName != null ? "empName=" + empName + ", " : "") +
            (resignDate != null ? "resignDate=" + resignDate + ", " : "") +
            (noticePeriodIndays != null ? "noticePeriodIndays=" + noticePeriodIndays + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (resignStatus != null ? "resignStatus=" + resignStatus + ", " : "") +
            (lastWorkingDay != null ? "lastWorkingDay=" + lastWorkingDay + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
