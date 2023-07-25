package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.ApprovalSetting} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.ApprovalSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /approval-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalSettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private IntegerFilter approvalCategory;

    private LongFilter departmentId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public ApprovalSettingCriteria() {}

    public ApprovalSettingCriteria(ApprovalSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.approvalCategory = other.approvalCategory == null ? null : other.approvalCategory.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApprovalSettingCriteria copy() {
        return new ApprovalSettingCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public IntegerFilter getApprovalCategory() {
        return approvalCategory;
    }

    public IntegerFilter approvalCategory() {
        if (approvalCategory == null) {
            approvalCategory = new IntegerFilter();
        }
        return approvalCategory;
    }

    public void setApprovalCategory(IntegerFilter approvalCategory) {
        this.approvalCategory = approvalCategory;
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
        final ApprovalSettingCriteria that = (ApprovalSettingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(approvalCategory, that.approvalCategory) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, approvalCategory, departmentId, status, companyId, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalSettingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (approvalCategory != null ? "approvalCategory=" + approvalCategory + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
