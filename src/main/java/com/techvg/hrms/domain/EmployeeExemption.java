package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A EmployeeExemption.
 */
@Entity
@Table(name = "employee_exemption")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE employee_exemption SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class EmployeeExemption extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "tax_exemp_section_id")
    private Long taxExempSectionId;

    @Column(name = "exemption_status")
    private String exemptionStatus;

    @Column(name = "status")
    private String status;

    @Column(name = "employee_id")
    private Long employeeId;

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

    public EmployeeExemption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public EmployeeExemption amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTaxExempSectionId() {
        return this.taxExempSectionId;
    }

    public EmployeeExemption taxExempSectionId(Long taxExempSectionId) {
        this.setTaxExempSectionId(taxExempSectionId);
        return this;
    }

    public void setTaxExempSectionId(Long taxExempSectionId) {
        this.taxExempSectionId = taxExempSectionId;
    }

    public String getExemptionStatus() {
        return this.exemptionStatus;
    }

    public EmployeeExemption exemptionStatus(String exemptionStatus) {
        this.setExemptionStatus(exemptionStatus);
        return this;
    }

    public void setExemptionStatus(String exemptionStatus) {
        this.exemptionStatus = exemptionStatus;
    }

    public String getStatus() {
        return this.status;
    }

    public EmployeeExemption status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public EmployeeExemption employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

//    public Long getCompanyId() {
//        return this.companyId;
//    }
//
//    public EmployeeExemption companyId(Long companyId) {
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
//    public EmployeeExemption lastModified(Instant lastModified) {
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
//    public EmployeeExemption lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof EmployeeExemption)) {
            return false;
        }
        return id != null && id.equals(((EmployeeExemption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeExemption{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", taxExempSectionId=" + getTaxExempSectionId() +
            ", exemptionStatus='" + getExemptionStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
