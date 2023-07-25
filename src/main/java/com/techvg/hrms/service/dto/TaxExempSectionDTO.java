package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.TaxExempSection} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxExempSectionDTO implements Serializable {

    private Long id;

    private String taxExempSection;

    private Long taxExempSectionId;

    private Double maxlimit;

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

    public String getTaxExempSection() {
        return taxExempSection;
    }

    public void setTaxExempSection(String taxExempSection) {
        this.taxExempSection = taxExempSection;
    }

    public Long getTaxExempSectionId() {
        return taxExempSectionId;
    }

    public void setTaxExempSectionId(Long taxExempSectionId) {
        this.taxExempSectionId = taxExempSectionId;
    }

    public Double getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(Double maxlimit) {
        this.maxlimit = maxlimit;
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
        if (!(o instanceof TaxExempSectionDTO)) {
            return false;
        }

        TaxExempSectionDTO taxExempSectionDTO = (TaxExempSectionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taxExempSectionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxExempSectionDTO{" +
            "id=" + getId() +
            ", taxExempSection='" + getTaxExempSection() + "'" +
            ", taxExempSectionId=" + getTaxExempSectionId() +
            ", maxlimit=" + getMaxlimit() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
