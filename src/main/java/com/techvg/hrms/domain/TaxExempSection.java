package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A TaxExempSection.
 */
@Entity
@Table(name = "tax_exemp_section")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE tax_exemp_section SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class TaxExempSection extends BaseEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tax_exemp_section")
    private String taxExempSection;

    @Column(name = "tax_exemp_section_id")
    private Long taxExempSectionId;

    @Column(name = "maxlimit")
    private Double maxlimit;

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

    public TaxExempSection id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxExempSection() {
        return this.taxExempSection;
    }

    public TaxExempSection taxExempSection(String taxExempSection) {
        this.setTaxExempSection(taxExempSection);
        return this;
    }

    public void setTaxExempSection(String taxExempSection) {
        this.taxExempSection = taxExempSection;
    }

    public Long getTaxExempSectionId() {
        return this.taxExempSectionId;
    }

    public TaxExempSection taxExempSectionId(Long taxExempSectionId) {
        this.setTaxExempSectionId(taxExempSectionId);
        return this;
    }

    public void setTaxExempSectionId(Long taxExempSectionId) {
        this.taxExempSectionId = taxExempSectionId;
    }

    public Double getMaxlimit() {
        return this.maxlimit;
    }

    public TaxExempSection maxlimit(Double maxlimit) {
        this.setMaxlimit(maxlimit);
        return this;
    }

    public void setMaxlimit(Double maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String getStatus() {
        return this.status;
    }

    public TaxExempSection status(String status) {
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
//    public TaxExempSection companyId(Long companyId) {
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
//    public TaxExempSection lastModified(Instant lastModified) {
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
//    public TaxExempSection lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof TaxExempSection)) {
            return false;
        }
        return id != null && id.equals(((TaxExempSection) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxExempSection{" +
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
