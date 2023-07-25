package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.SalarySettings} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.SalarySettingsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /salary-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalarySettingsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter da;

    private DoubleFilter hra;

    private DoubleFilter employeeShare;

    private DoubleFilter companyShare;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public SalarySettingsCriteria() {}

    public SalarySettingsCriteria(SalarySettingsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.da = other.da == null ? null : other.da.copy();
        this.hra = other.hra == null ? null : other.hra.copy();
        this.employeeShare = other.employeeShare == null ? null : other.employeeShare.copy();
        this.companyShare = other.companyShare == null ? null : other.companyShare.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SalarySettingsCriteria copy() {
        return new SalarySettingsCriteria(this);
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

    public DoubleFilter getDa() {
        return da;
    }

    public DoubleFilter da() {
        if (da == null) {
            da = new DoubleFilter();
        }
        return da;
    }

    public void setDa(DoubleFilter da) {
        this.da = da;
    }

    public DoubleFilter getHra() {
        return hra;
    }

    public DoubleFilter hra() {
        if (hra == null) {
            hra = new DoubleFilter();
        }
        return hra;
    }

    public void setHra(DoubleFilter hra) {
        this.hra = hra;
    }

    public DoubleFilter getEmployeeShare() {
        return employeeShare;
    }

    public DoubleFilter employeeShare() {
        if (employeeShare == null) {
            employeeShare = new DoubleFilter();
        }
        return employeeShare;
    }

    public void setEmployeeShare(DoubleFilter employeeShare) {
        this.employeeShare = employeeShare;
    }

    public DoubleFilter getCompanyShare() {
        return companyShare;
    }

    public DoubleFilter companyShare() {
        if (companyShare == null) {
            companyShare = new DoubleFilter();
        }
        return companyShare;
    }

    public void setCompanyShare(DoubleFilter companyShare) {
        this.companyShare = companyShare;
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
        final SalarySettingsCriteria that = (SalarySettingsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(da, that.da) &&
            Objects.equals(hra, that.hra) &&
            Objects.equals(employeeShare, that.employeeShare) &&
            Objects.equals(companyShare, that.companyShare) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, da, hra, employeeShare, companyShare, status, companyId, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalarySettingsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (da != null ? "da=" + da + ", " : "") +
            (hra != null ? "hra=" + hra + ", " : "") +
            (employeeShare != null ? "employeeShare=" + employeeShare + ", " : "") +
            (companyShare != null ? "companyShare=" + companyShare + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
