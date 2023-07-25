package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.TechvgRole} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TechvgRoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /techvg-roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechvgRoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roleName;

    private StringFilter description;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter techvgPermissionId;

    private Boolean distinct;

    public TechvgRoleCriteria() {}

    public TechvgRoleCriteria(TechvgRoleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roleName = other.roleName == null ? null : other.roleName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.techvgPermissionId = other.techvgPermissionId == null ? null : other.techvgPermissionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TechvgRoleCriteria copy() {
        return new TechvgRoleCriteria(this);
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

    public StringFilter getRoleName() {
        return roleName;
    }

    public StringFilter roleName() {
        if (roleName == null) {
            roleName = new StringFilter();
        }
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
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

    public LongFilter getTechvgPermissionId() {
        return techvgPermissionId;
    }

    public LongFilter techvgPermissionId() {
        if (techvgPermissionId == null) {
            techvgPermissionId = new LongFilter();
        }
        return techvgPermissionId;
    }

    public void setTechvgPermissionId(LongFilter techvgPermissionId) {
        this.techvgPermissionId = techvgPermissionId;
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
        final TechvgRoleCriteria that = (TechvgRoleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roleName, that.roleName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(techvgPermissionId, that.techvgPermissionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, description, status, companyId, lastModified, lastModifiedBy, techvgPermissionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechvgRoleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roleName != null ? "roleName=" + roleName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (techvgPermissionId != null ? "techvgPermissionId=" + techvgPermissionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
