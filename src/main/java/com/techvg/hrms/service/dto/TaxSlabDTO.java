package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.TaxSlab} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxSlabDTO implements Serializable {

    private Long id;

    private String slab;

    private Double amtFrom;

    private Double amtTo;

    private Double taxPercentage;

    private Long taxRegimeId;

    private Long taxSlabId;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlab() {
        return slab;
    }

    public void setSlab(String slab) {
        this.slab = slab;
    }

    public Double getAmtFrom() {
        return amtFrom;
    }

    public void setAmtFrom(Double amtFrom) {
        this.amtFrom = amtFrom;
    }

    public Double getAmtTo() {
        return amtTo;
    }

    public void setAmtTo(Double amtTo) {
        this.amtTo = amtTo;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Long getTaxRegimeId() {
        return taxRegimeId;
    }

    public void setTaxRegimeId(Long taxRegimeId) {
        this.taxRegimeId = taxRegimeId;
    }

    public Long getTaxSlabId() {
        return taxSlabId;
    }

    public void setTaxSlabId(Long taxSlabId) {
        this.taxSlabId = taxSlabId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxSlabDTO)) {
            return false;
        }

        TaxSlabDTO taxSlabDTO = (TaxSlabDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taxSlabDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxSlabDTO{" +
            "id=" + getId() +
            ", slab='" + getSlab() + "'" +
            ", amtFrom=" + getAmtFrom() +
            ", amtTo=" + getAmtTo() +
            ", taxPercentage=" + getTaxPercentage() +
            ", taxRegimeId=" + getTaxRegimeId() +
            ", taxSlabId=" + getTaxSlabId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
