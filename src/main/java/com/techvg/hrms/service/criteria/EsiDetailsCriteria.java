package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.EsiDetails} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EsiDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /esi-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EsiDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter isEsiContribution;

    private StringFilter esiNumber;

    private DoubleFilter esiRate;

    private StringFilter additionalEsiRate;

    private DoubleFilter totalEsiRate;

    private LongFilter employeeId;

    private LongFilter reEnumerationId;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public EsiDetailsCriteria() {}

    public EsiDetailsCriteria(EsiDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isEsiContribution = other.isEsiContribution == null ? null : other.isEsiContribution.copy();
        this.esiNumber = other.esiNumber == null ? null : other.esiNumber.copy();
        this.esiRate = other.esiRate == null ? null : other.esiRate.copy();
        this.additionalEsiRate = other.additionalEsiRate == null ? null : other.additionalEsiRate.copy();
        this.totalEsiRate = other.totalEsiRate == null ? null : other.totalEsiRate.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.reEnumerationId = other.reEnumerationId == null ? null : other.reEnumerationId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EsiDetailsCriteria copy() {
        return new EsiDetailsCriteria(this);
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

    public BooleanFilter getIsEsiContribution() {
        return isEsiContribution;
    }

    public BooleanFilter isEsiContribution() {
        if (isEsiContribution == null) {
            isEsiContribution = new BooleanFilter();
        }
        return isEsiContribution;
    }

    public void setIsEsiContribution(BooleanFilter isEsiContribution) {
        this.isEsiContribution = isEsiContribution;
    }

    public StringFilter getEsiNumber() {
        return esiNumber;
    }

    public StringFilter esiNumber() {
        if (esiNumber == null) {
            esiNumber = new StringFilter();
        }
        return esiNumber;
    }

    public void setEsiNumber(StringFilter esiNumber) {
        this.esiNumber = esiNumber;
    }

    public DoubleFilter getEsiRate() {
        return esiRate;
    }

    public DoubleFilter esiRate() {
        if (esiRate == null) {
            esiRate = new DoubleFilter();
        }
        return esiRate;
    }

    public void setEsiRate(DoubleFilter esiRate) {
        this.esiRate = esiRate;
    }

    public StringFilter getAdditionalEsiRate() {
        return additionalEsiRate;
    }

    public StringFilter additionalEsiRate() {
        if (additionalEsiRate == null) {
            additionalEsiRate = new StringFilter();
        }
        return additionalEsiRate;
    }

    public void setAdditionalEsiRate(StringFilter additionalEsiRate) {
        this.additionalEsiRate = additionalEsiRate;
    }

    public DoubleFilter getTotalEsiRate() {
        return totalEsiRate;
    }

    public DoubleFilter totalEsiRate() {
        if (totalEsiRate == null) {
            totalEsiRate = new DoubleFilter();
        }
        return totalEsiRate;
    }

    public void setTotalEsiRate(DoubleFilter totalEsiRate) {
        this.totalEsiRate = totalEsiRate;
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

    public LongFilter getReEnumerationId() {
        return reEnumerationId;
    }

    public LongFilter reEnumerationId() {
        if (reEnumerationId == null) {
            reEnumerationId = new LongFilter();
        }
        return reEnumerationId;
    }

    public void setReEnumerationId(LongFilter reEnumerationId) {
        this.reEnumerationId = reEnumerationId;
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
        final EsiDetailsCriteria that = (EsiDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isEsiContribution, that.isEsiContribution) &&
            Objects.equals(esiNumber, that.esiNumber) &&
            Objects.equals(esiRate, that.esiRate) &&
            Objects.equals(additionalEsiRate, that.additionalEsiRate) &&
            Objects.equals(totalEsiRate, that.totalEsiRate) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(reEnumerationId, that.reEnumerationId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            isEsiContribution,
            esiNumber,
            esiRate,
            additionalEsiRate,
            totalEsiRate,
            employeeId,
            reEnumerationId,
            companyId,
            status,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EsiDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isEsiContribution != null ? "isEsiContribution=" + isEsiContribution + ", " : "") +
            (esiNumber != null ? "esiNumber=" + esiNumber + ", " : "") +
            (esiRate != null ? "esiRate=" + esiRate + ", " : "") +
            (additionalEsiRate != null ? "additionalEsiRate=" + additionalEsiRate + ", " : "") +
            (totalEsiRate != null ? "totalEsiRate=" + totalEsiRate + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (reEnumerationId != null ? "reEnumerationId=" + reEnumerationId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
