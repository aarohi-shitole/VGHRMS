package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A TaxSlab.
 */
@Entity
@Table(name = "tax_slab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE tax_slab SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class TaxSlab extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "slab")
    private String slab;

    @Column(name = "amt_from")
    private Double amtFrom;

    @Column(name = "amt_to")
    private Double amtTo;

    @Column(name = "tax_percentage")
    private Double taxPercentage;

    @Column(name = "tax_regime_id")
    private Long taxRegimeId;

    @Column(name = "tax_slab_id")
    private Long taxSlabId;

    @Column(name = "status")
    private String status;

//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "last_modified")
//    private Instant lastModified;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TaxSlab id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlab() {
        return this.slab;
    }

    public TaxSlab slab(String slab) {
        this.setSlab(slab);
        return this;
    }

    public void setSlab(String slab) {
        this.slab = slab;
    }

    public Double getAmtFrom() {
        return this.amtFrom;
    }

    public TaxSlab amtFrom(Double amtFrom) {
        this.setAmtFrom(amtFrom);
        return this;
    }

    public void setAmtFrom(Double amtFrom) {
        this.amtFrom = amtFrom;
    }

    public Double getAmtTo() {
        return this.amtTo;
    }

    public TaxSlab amtTo(Double amtTo) {
        this.setAmtTo(amtTo);
        return this;
    }

    public void setAmtTo(Double amtTo) {
        this.amtTo = amtTo;
    }

    public Double getTaxPercentage() {
        return this.taxPercentage;
    }

    public TaxSlab taxPercentage(Double taxPercentage) {
        this.setTaxPercentage(taxPercentage);
        return this;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Long getTaxRegimeId() {
        return this.taxRegimeId;
    }

    public TaxSlab taxRegimeId(Long taxRegimeId) {
        this.setTaxRegimeId(taxRegimeId);
        return this;
    }

    public void setTaxRegimeId(Long taxRegimeId) {
        this.taxRegimeId = taxRegimeId;
    }

    public Long getTaxSlabId() {
        return this.taxSlabId;
    }

    public TaxSlab taxSlabId(Long taxSlabId) {
        this.setTaxSlabId(taxSlabId);
        return this;
    }

    public void setTaxSlabId(Long taxSlabId) {
        this.taxSlabId = taxSlabId;
    }

    public String getStatus() {
        return this.status;
    }

    public TaxSlab status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Long getCompanyId() {
//        return this.companyId;
//    }
//
//    public TaxSlab companyId(Long companyId) {
//        this.setCompanyId(companyId);
//        return this;
//    }
//
//    public void setCompanyId(Long companyId) {
//        this.companyId = companyId;
//    }
//
//    public Instant getLastModified() {
//        return this.lastModified;
//    }
//
//    public TaxSlab lastModified(Instant lastModified) {
//        this.setLastModified(lastModified);
//        return this;
//    }
//
//    public void setLastModified(Instant lastModified) {
//        this.lastModified = lastModified;
//    }
//
//    public String getLastModifiedBy() {
//        return this.lastModifiedBy;
//    }
//
//    public TaxSlab lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaxSlab)) {
            return false;
        }
        return id != null && id.equals(((TaxSlab) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxSlab{" +
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
