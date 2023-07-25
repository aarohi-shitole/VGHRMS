package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.ApprovalLevel} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.ApprovalLevelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approval-levels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalLevelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter designationId;

    private LongFilter squence;

    private LongFilter approvalSettingId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public ApprovalLevelCriteria() {}

    public ApprovalLevelCriteria(ApprovalLevelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.squence = other.squence == null ? null : other.squence.copy();
        this.approvalSettingId = other.approvalSettingId == null ? null : other.approvalSettingId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApprovalLevelCriteria copy() {
        return new ApprovalLevelCriteria(this);
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

    public LongFilter getSquence() {
        return squence;
    }

    public LongFilter squence() {
        if (squence == null) {
            squence = new LongFilter();
        }
        return squence;
    }

    public void setSquence(LongFilter squence) {
        this.squence = squence;
    }

    public LongFilter getApprovalSettingId() {
        return approvalSettingId;
    }

    public LongFilter approvalSettingId() {
        if (approvalSettingId == null) {
            approvalSettingId = new LongFilter();
        }
        return approvalSettingId;
    }

    public void setApprovalSettingId(LongFilter approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
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
        final ApprovalLevelCriteria that = (ApprovalLevelCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(designationId, that.designationId) &&
            Objects.equals(squence, that.squence) &&
            Objects.equals(approvalSettingId, that.approvalSettingId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designationId, squence, approvalSettingId, companyId, status, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalLevelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (squence != null ? "squence=" + squence + ", " : "") +
            (approvalSettingId != null ? "approvalSettingId=" + approvalSettingId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
