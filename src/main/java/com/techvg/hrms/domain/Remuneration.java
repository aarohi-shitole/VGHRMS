package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Remuneration.
 */
@Entity
@Table(name = "remuneration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE remuneration SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Remuneration extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salary_type")
    private String salaryType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "employe_id")
    private Long employeeId;
    
    

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
    
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "tax_regime")
    private TaxRegime taxRegime;

    public Long getId() {
        return this.id;
    }

    public Remuneration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalaryType() {
        return this.salaryType;
    }

    public Remuneration salaryType(String salaryType) {
        this.setSalaryType(salaryType);
        return this;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Remuneration amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public Remuneration paymentType(String paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Remuneration employeeId(Long employeeId) {
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
    //    public Remuneration companyId(Long companyId) {
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

    public Remuneration status(String status) {
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
    //    public Remuneration lastModified(Instant lastModified) {
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
    //    public Remuneration lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }
    
    
    
    public TaxRegime getTaxRegime() {
        return taxRegime;
    }

    public void setTaxRegime(TaxRegime taxRegime) {
        this.taxRegime = taxRegime;
    }

    public Remuneration taxRegime(TaxRegime taxRegime) {
        this.setTaxRegime(taxRegime);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remuneration)) {
            return false;
        }
        return id != null && id.equals(((Remuneration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Remuneration{" +
            "id=" + getId() +
            ", salaryType='" + getSalaryType() + "'" +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", taxRegime='" + getTaxRegime() + "'" +
            "}";
    }
}
