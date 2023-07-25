package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.TaxExempSection} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TaxExempSectionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tax-exemp-sections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxExempSectionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter taxExempSection;

    private LongFilter taxExempSectionId;

    private DoubleFilter maxlimit;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public TaxExempSectionCriteria() {}

    public TaxExempSectionCriteria(TaxExempSectionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.taxExempSection = other.taxExempSection == null ? null : other.taxExempSection.copy();
        this.taxExempSectionId = other.taxExempSectionId == null ? null : other.taxExempSectionId.copy();
        this.maxlimit = other.maxlimit == null ? null : other.maxlimit.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TaxExempSectionCriteria copy() {
        return new TaxExempSectionCriteria(this);
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

    public StringFilter getTaxExempSection() {
        return taxExempSection;
    }

    public StringFilter taxExempSection() {
        if (taxExempSection == null) {
            taxExempSection = new StringFilter();
        }
        return taxExempSection;
    }

    public void setTaxExempSection(StringFilter taxExempSection) {
        this.taxExempSection = taxExempSection;
    }

    public LongFilter getTaxExempSectionId() {
        return taxExempSectionId;
    }

    public LongFilter taxExempSectionId() {
        if (taxExempSectionId == null) {
            taxExempSectionId = new LongFilter();
        }
        return taxExempSectionId;
    }

    public void setTaxExempSectionId(LongFilter taxExempSectionId) {
        this.taxExempSectionId = taxExempSectionId;
    }

    public DoubleFilter getMaxlimit() {
        return maxlimit;
    }

    public DoubleFilter maxlimit() {
        if (maxlimit == null) {
            maxlimit = new DoubleFilter();
        }
        return maxlimit;
    }

    public void setMaxlimit(DoubleFilter maxlimit) {
        this.maxlimit = maxlimit;
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
        final TaxExempSectionCriteria that = (TaxExempSectionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(taxExempSection, that.taxExempSection) &&
            Objects.equals(taxExempSectionId, that.taxExempSectionId) &&
            Objects.equals(maxlimit, that.maxlimit) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taxExempSection, taxExempSectionId, maxlimit, status, companyId, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxExempSectionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (taxExempSection != null ? "taxExempSection=" + taxExempSection + ", " : "") +
            (taxExempSectionId != null ? "taxExempSectionId=" + taxExempSectionId + ", " : "") +
            (maxlimit != null ? "maxlimit=" + maxlimit + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
