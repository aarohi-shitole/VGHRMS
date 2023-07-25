package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Termination} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TerminationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /terminations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TerminationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empName;

    private StringFilter terminationType;

    private InstantFilter terminationDate;

    private InstantFilter noticeDate;

    private StringFilter reason;

    private StringFilter status;

    private LongFilter departmentId;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public TerminationCriteria() {}

    public TerminationCriteria(TerminationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empName = other.empName == null ? null : other.empName.copy();
        this.terminationType = other.terminationType == null ? null : other.terminationType.copy();
        this.terminationDate = other.terminationDate == null ? null : other.terminationDate.copy();
        this.noticeDate = other.noticeDate == null ? null : other.noticeDate.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TerminationCriteria copy() {
        return new TerminationCriteria(this);
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

    public StringFilter getTerminationType() {
        return terminationType;
    }

    public StringFilter terminationType() {
        if (terminationType == null) {
            terminationType = new StringFilter();
        }
        return terminationType;
    }

    public void setTerminationType(StringFilter terminationType) {
        this.terminationType = terminationType;
    }

    public InstantFilter getTerminationDate() {
        return terminationDate;
    }

    public InstantFilter terminationDate() {
        if (terminationDate == null) {
            terminationDate = new InstantFilter();
        }
        return terminationDate;
    }

    public void setTerminationDate(InstantFilter terminationDate) {
        this.terminationDate = terminationDate;
    }

    public InstantFilter getNoticeDate() {
        return noticeDate;
    }

    public InstantFilter noticeDate() {
        if (noticeDate == null) {
            noticeDate = new InstantFilter();
        }
        return noticeDate;
    }

    public void setNoticeDate(InstantFilter noticeDate) {
        this.noticeDate = noticeDate;
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
        final TerminationCriteria that = (TerminationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empName, that.empName) &&
            Objects.equals(terminationType, that.terminationType) &&
            Objects.equals(terminationDate, that.terminationDate) &&
            Objects.equals(noticeDate, that.noticeDate) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(status, that.status) &&
            Objects.equals(departmentId, that.departmentId) &&
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
            empName,
            terminationType,
            terminationDate,
            noticeDate,
            reason,
            status,
            departmentId,
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
        return "TerminationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empName != null ? "empName=" + empName + ", " : "") +
            (terminationType != null ? "terminationType=" + terminationType + ", " : "") +
            (terminationDate != null ? "terminationDate=" + terminationDate + ", " : "") +
            (noticeDate != null ? "noticeDate=" + noticeDate + ", " : "") +
            (reason != null ? "reason=" + reason + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
