package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.AssetApplication} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.AssetApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetApplicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter assetId;

    private StringFilter assetype;

    private LongFilter quantity;

    private StringFilter description;

    private StringFilter reqStatus;

    private InstantFilter applyDate;

    private InstantFilter assginDate;

    private StringFilter status;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public AssetApplicationCriteria() {}

    public AssetApplicationCriteria(AssetApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assetId = other.assetId == null ? null : other.assetId.copy();
        this.assetype = other.assetype == null ? null : other.assetype.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.reqStatus = other.reqStatus == null ? null : other.reqStatus.copy();
        this.applyDate = other.applyDate == null ? null : other.applyDate.copy();
        this.assginDate = other.assginDate == null ? null : other.assginDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AssetApplicationCriteria copy() {
        return new AssetApplicationCriteria(this);
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

    public LongFilter getAssetId() {
        return assetId;
    }

    public LongFilter assetId() {
        if (assetId == null) {
            assetId = new LongFilter();
        }
        return assetId;
    }

    public void setAssetId(LongFilter assetId) {
        this.assetId = assetId;
    }

    public StringFilter getAssetype() {
        return assetype;
    }

    public StringFilter assetype() {
        if (assetype == null) {
            assetype = new StringFilter();
        }
        return assetype;
    }

    public void setAssetype(StringFilter assetype) {
        this.assetype = assetype;
    }

    public LongFilter getQuantity() {
        return quantity;
    }

    public LongFilter quantity() {
        if (quantity == null) {
            quantity = new LongFilter();
        }
        return quantity;
    }

    public void setQuantity(LongFilter quantity) {
        this.quantity = quantity;
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

    public StringFilter getReqStatus() {
        return reqStatus;
    }

    public StringFilter reqStatus() {
        if (reqStatus == null) {
            reqStatus = new StringFilter();
        }
        return reqStatus;
    }

    public void setReqStatus(StringFilter reqStatus) {
        this.reqStatus = reqStatus;
    }

    public InstantFilter getApplyDate() {
        return applyDate;
    }

    public InstantFilter applyDate() {
        if (applyDate == null) {
            applyDate = new InstantFilter();
        }
        return applyDate;
    }

    public void setApplyDate(InstantFilter applyDate) {
        this.applyDate = applyDate;
    }

    public InstantFilter getAssginDate() {
        return assginDate;
    }

    public InstantFilter assginDate() {
        if (assginDate == null) {
            assginDate = new InstantFilter();
        }
        return assginDate;
    }

    public void setAssginDate(InstantFilter assginDate) {
        this.assginDate = assginDate;
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
        final AssetApplicationCriteria that = (AssetApplicationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(assetype, that.assetype) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(description, that.description) &&
            Objects.equals(reqStatus, that.reqStatus) &&
            Objects.equals(applyDate, that.applyDate) &&
            Objects.equals(assginDate, that.assginDate) &&
            Objects.equals(status, that.status) &&
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
            assetId,
            assetype,
            quantity,
            description,
            reqStatus,
            applyDate,
            assginDate,
            status,
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
        return "AssetApplicationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assetId != null ? "assetId=" + assetId + ", " : "") +
            (assetype != null ? "assetype=" + assetype + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (reqStatus != null ? "reqStatus=" + reqStatus + ", " : "") +
            (applyDate != null ? "applyDate=" + applyDate + ", " : "") +
            (assginDate != null ? "assginDate=" + assginDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
