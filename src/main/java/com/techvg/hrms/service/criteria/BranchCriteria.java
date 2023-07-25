package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Branch} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.BranchResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /branches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BranchCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter branchName;

    private StringFilter description;

    private StringFilter branchcode;

    private StringFilter branchType;

    private StringFilter webSite;

    private LongFilter branchId;

    private LongFilter regionId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public BranchCriteria() {}

    public BranchCriteria(BranchCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.branchcode = other.branchcode == null ? null : other.branchcode.copy();
        this.branchType = other.branchType == null ? null : other.branchType.copy();
        this.webSite = other.webSite == null ? null : other.webSite.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BranchCriteria copy() {
        return new BranchCriteria(this);
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

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
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

    public StringFilter getBranchcode() {
        return branchcode;
    }

    public StringFilter branchcode() {
        if (branchcode == null) {
            branchcode = new StringFilter();
        }
        return branchcode;
    }

    public void setBranchcode(StringFilter branchcode) {
        this.branchcode = branchcode;
    }

    public StringFilter getBranchType() {
        return branchType;
    }

    public StringFilter branchType() {
        if (branchType == null) {
            branchType = new StringFilter();
        }
        return branchType;
    }

    public void setBranchType(StringFilter branchType) {
        this.branchType = branchType;
    }

    public StringFilter getWebSite() {
        return webSite;
    }

    public StringFilter webSite() {
        if (webSite == null) {
            webSite = new StringFilter();
        }
        return webSite;
    }

    public void setWebSite(StringFilter webSite) {
        this.webSite = webSite;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public LongFilter regionId() {
        if (regionId == null) {
            regionId = new LongFilter();
        }
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
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
        final BranchCriteria that = (BranchCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(branchcode, that.branchcode) &&
            Objects.equals(branchType, that.branchType) &&
            Objects.equals(webSite, that.webSite) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(regionId, that.regionId) &&
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
            branchName,
            description,
            branchcode,
            branchType,
            webSite,
            branchId,
            regionId,
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
        return "BranchCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (branchcode != null ? "branchcode=" + branchcode + ", " : "") +
            (branchType != null ? "branchType=" + branchType + ", " : "") +
            (webSite != null ? "webSite=" + webSite + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (regionId != null ? "regionId=" + regionId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
