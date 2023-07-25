package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.CustomApprovar} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.CustomApprovarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /custom-approvars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomApprovarCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter employeId;

    private StringFilter approvalSettingId;

    private StringFilter squence;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public CustomApprovarCriteria() {}

    public CustomApprovarCriteria(CustomApprovarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
        this.approvalSettingId = other.approvalSettingId == null ? null : other.approvalSettingId.copy();
        this.squence = other.squence == null ? null : other.squence.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CustomApprovarCriteria copy() {
        return new CustomApprovarCriteria(this);
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

    public StringFilter getApprovalSettingId() {
        return approvalSettingId;
    }

    public StringFilter approvalSettingId() {
        if (approvalSettingId == null) {
            approvalSettingId = new StringFilter();
        }
        return approvalSettingId;
    }

    public void setApprovalSettingId(StringFilter approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    public StringFilter getSquence() {
        return squence;
    }

    public StringFilter squence() {
        if (squence == null) {
            squence = new StringFilter();
        }
        return squence;
    }

    public void setSquence(StringFilter squence) {
        this.squence = squence;
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
        final CustomApprovarCriteria that = (CustomApprovarCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(employeId, that.employeId) &&
            Objects.equals(approvalSettingId, that.approvalSettingId) &&
            Objects.equals(squence, that.squence) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeId, approvalSettingId, squence, status, companyId, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomApprovarCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (employeId != null ? "employeId=" + employeId + ", " : "") +
            (approvalSettingId != null ? "approvalSettingId=" + approvalSettingId + ", " : "") +
            (squence != null ? "squence=" + squence + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
