package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PfDetails.
 */
@Entity
@Table(name = "pf_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE pf_details SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PfDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_pf_contribution")
    private Boolean isPfContribution;

    @Column(name = "pf_number")
    private String pfNumber;

    @Column(name = "pf_rate")
    private Double pfRate;

    @Column(name = "additional_pf_rate")
    private String additionalPfRate;

    @Column(name = "total_pf_rate")
    private Double totalPfRate;

    @Column(name = "employe_id")
    private Long employeeId;

    @Column(name = "re_enumeration_id")
    private Long reEnumerationId;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PfDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsPfContribution() {
        return this.isPfContribution;
    }

    public PfDetails isPfContribution(Boolean isPfContribution) {
        this.setIsPfContribution(isPfContribution);
        return this;
    }

    public void setIsPfContribution(Boolean isPfContribution) {
        this.isPfContribution = isPfContribution;
    }

    public String getPfNumber() {
        return this.pfNumber;
    }

    public PfDetails pfNumber(String pfNumber) {
        this.setPfNumber(pfNumber);
        return this;
    }

    public void setPfNumber(String pfNumber) {
        this.pfNumber = pfNumber;
    }

    public Double getPfRate() {
        return this.pfRate;
    }

    public PfDetails pfRate(Double pfRate) {
        this.setPfRate(pfRate);
        return this;
    }

    public void setPfRate(Double pfRate) {
        this.pfRate = pfRate;
    }

    public String getAdditionalPfRate() {
        return this.additionalPfRate;
    }

    public PfDetails additionalPfRate(String additionalPfRate) {
        this.setAdditionalPfRate(additionalPfRate);
        return this;
    }

    public void setAdditionalPfRate(String additionalPfRate) {
        this.additionalPfRate = additionalPfRate;
    }

    public Double getTotalPfRate() {
        return this.totalPfRate;
    }

    public PfDetails totalPfRate(Double totalPfRate) {
        this.setTotalPfRate(totalPfRate);
        return this;
    }

    public void setTotalPfRate(Double totalPfRate) {
        this.totalPfRate = totalPfRate;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public PfDetails employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getReEnumerationId() {
        return this.reEnumerationId;
    }

    public PfDetails reEnumerationId(Long reEnumerationId) {
        this.setReEnumerationId(reEnumerationId);
        return this;
    }

    public void setReEnumerationId(Long reEnumerationId) {
        this.reEnumerationId = reEnumerationId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public PfDetails companyId(Long companyId) {
    //        this.setCompanyId(companyId);
    //        return this;
    //    }
    //
    //    public void setCompanyId(Long companyId) {
    //        this.companyId = companyId;
    //    }

    public String getStatus() {
        return this.status;
    }

    public PfDetails status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    public Instant getLastModified() {
    //        return this.lastModified;
    //    }
    //
    //    public PfDetails lastModified(Instant lastModified) {
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
    //    public PfDetails lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof PfDetails)) {
            return false;
        }
        return id != null && id.equals(((PfDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PfDetails{" +
            "id=" + getId() +
            ", isPfContribution='" + getIsPfContribution() + "'" +
            ", pfNumber='" + getPfNumber() + "'" +
            ", pfRate=" + getPfRate() +
            ", additionalPfRate='" + getAdditionalPfRate() + "'" +
            ", totalPfRate=" + getTotalPfRate() +
            ", employeeId=" + getEmployeeId() +
            ", reEnumerationId=" + getReEnumerationId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
