package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.TaxSlab} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TaxSlabResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tax-slabs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxSlabCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter slab;

    private DoubleFilter amtFrom;

    private DoubleFilter amtTo;

    private DoubleFilter taxPercentage;

    private LongFilter taxRegimeId;

    private LongFilter taxSlabId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public TaxSlabCriteria() {}

    public TaxSlabCriteria(TaxSlabCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.slab = other.slab == null ? null : other.slab.copy();
        this.amtFrom = other.amtFrom == null ? null : other.amtFrom.copy();
        this.amtTo = other.amtTo == null ? null : other.amtTo.copy();
        this.taxPercentage = other.taxPercentage == null ? null : other.taxPercentage.copy();
        this.taxRegimeId = other.taxRegimeId == null ? null : other.taxRegimeId.copy();
        this.taxSlabId = other.taxSlabId == null ? null : other.taxSlabId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TaxSlabCriteria copy() {
        return new TaxSlabCriteria(this);
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

    public StringFilter getSlab() {
        return slab;
    }

    public StringFilter slab() {
        if (slab == null) {
            slab = new StringFilter();
        }
        return slab;
    }

    public void setSlab(StringFilter slab) {
        this.slab = slab;
    }

    public DoubleFilter getAmtFrom() {
        return amtFrom;
    }

    public DoubleFilter amtFrom() {
        if (amtFrom == null) {
            amtFrom = new DoubleFilter();
        }
        return amtFrom;
    }

    public void setAmtFrom(DoubleFilter amtFrom) {
        this.amtFrom = amtFrom;
    }

    public DoubleFilter getAmtTo() {
        return amtTo;
    }

    public DoubleFilter amtTo() {
        if (amtTo == null) {
            amtTo = new DoubleFilter();
        }
        return amtTo;
    }

    public void setAmtTo(DoubleFilter amtTo) {
        this.amtTo = amtTo;
    }

    public DoubleFilter getTaxPercentage() {
        return taxPercentage;
    }

    public DoubleFilter taxPercentage() {
        if (taxPercentage == null) {
            taxPercentage = new DoubleFilter();
        }
        return taxPercentage;
    }

    public void setTaxPercentage(DoubleFilter taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public LongFilter getTaxRegimeId() {
        return taxRegimeId;
    }

    public LongFilter taxRegimeId() {
        if (taxRegimeId == null) {
            taxRegimeId = new LongFilter();
        }
        return taxRegimeId;
    }

    public void setTaxRegimeId(LongFilter taxRegimeId) {
        this.taxRegimeId = taxRegimeId;
    }

    public LongFilter getTaxSlabId() {
        return taxSlabId;
    }

    public LongFilter taxSlabId() {
        if (taxSlabId == null) {
            taxSlabId = new LongFilter();
        }
        return taxSlabId;
    }

    public void setTaxSlabId(LongFilter taxSlabId) {
        this.taxSlabId = taxSlabId;
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
        final TaxSlabCriteria that = (TaxSlabCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(slab, that.slab) &&
            Objects.equals(amtFrom, that.amtFrom) &&
            Objects.equals(amtTo, that.amtTo) &&
            Objects.equals(taxPercentage, that.taxPercentage) &&
            Objects.equals(taxRegimeId, that.taxRegimeId) &&
            Objects.equals(taxSlabId, that.taxSlabId) &&
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
            slab,
            amtFrom,
            amtTo,
            taxPercentage,
            taxRegimeId,
            taxSlabId,
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
        return "TaxSlabCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (slab != null ? "slab=" + slab + ", " : "") +
            (amtFrom != null ? "amtFrom=" + amtFrom + ", " : "") +
            (amtTo != null ? "amtTo=" + amtTo + ", " : "") +
            (taxPercentage != null ? "taxPercentage=" + taxPercentage + ", " : "") +
            (taxRegimeId != null ? "taxRegimeId=" + taxRegimeId + ", " : "") +
            (taxSlabId != null ? "taxSlabId=" + taxSlabId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
