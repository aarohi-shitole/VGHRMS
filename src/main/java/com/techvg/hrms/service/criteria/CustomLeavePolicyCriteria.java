package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.CustomLeavePolicy} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.CustomLeavePolicyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /custom-leave-policies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomLeavePolicyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter customPolicyName;

    private LongFilter days;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter leavePolicyId;

    private LongFilter employeeId;

    private Boolean distinct;

    public CustomLeavePolicyCriteria() {}

    public CustomLeavePolicyCriteria(CustomLeavePolicyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.customPolicyName = other.customPolicyName == null ? null : other.customPolicyName.copy();
        this.days = other.days == null ? null : other.days.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.leavePolicyId = other.leavePolicyId == null ? null : other.leavePolicyId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomLeavePolicyCriteria copy() {
        return new CustomLeavePolicyCriteria(this);
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

    public StringFilter getCustomPolicyName() {
        return customPolicyName;
    }

    public StringFilter customPolicyName() {
        if (customPolicyName == null) {
            customPolicyName = new StringFilter();
        }
        return customPolicyName;
    }

    public void setCustomPolicyName(StringFilter customPolicyName) {
        this.customPolicyName = customPolicyName;
    }

    public LongFilter getDays() {
        return days;
    }

    public LongFilter days() {
        if (days == null) {
            days = new LongFilter();
        }
        return days;
    }

    public void setDays(LongFilter days) {
        this.days = days;
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

    public LongFilter getLeavePolicyId() {
        return leavePolicyId;
    }

    public LongFilter leavePolicyId() {
        if (leavePolicyId == null) {
            leavePolicyId = new LongFilter();
        }
        return leavePolicyId;
    }

    public void setLeavePolicyId(LongFilter leavePolicyId) {
        this.leavePolicyId = leavePolicyId;
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
        final CustomLeavePolicyCriteria that = (CustomLeavePolicyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(customPolicyName, that.customPolicyName) &&
            Objects.equals(days, that.days) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(leavePolicyId, that.leavePolicyId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            customPolicyName,
            days,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            leavePolicyId,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomLeavePolicyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (customPolicyName != null ? "customPolicyName=" + customPolicyName + ", " : "") +
            (days != null ? "days=" + days + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (leavePolicyId != null ? "leavePolicyId=" + leavePolicyId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
