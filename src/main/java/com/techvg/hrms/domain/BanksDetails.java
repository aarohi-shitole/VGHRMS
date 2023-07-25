package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A BanksDetails.
 */
@Entity
@Table(name = "banks_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE banks_details SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class BanksDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_trans_code")
    private String branchTransCode;

    @Column(name = "tax_number")
    private String taxNumber;

    @Column(name = "gstin")
    private String gstin;

    @Column(name = "tan")
    private String tan;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "ref_table")
    private String refTable;

    @Column(name = "ref_table_id")
    private Long refTableId;

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

    public BanksDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return this.accountNumber;
    }

    public BanksDetails accountNumber(Long accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public BanksDetails bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchTransCode() {
        return this.branchTransCode;
    }

    public BanksDetails branchTransCode(String branchTransCode) {
        this.setBranchTransCode(branchTransCode);
        return this;
    }

    public void setBranchTransCode(String branchTransCode) {
        this.branchTransCode = branchTransCode;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public BanksDetails taxNumber(String taxNumber) {
        this.setTaxNumber(taxNumber);
        return this;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getGstin() {
        return this.gstin;
    }

    public BanksDetails gstin(String gstin) {
        this.setGstin(gstin);
        return this;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getTan() {
        return this.tan;
    }

    public BanksDetails tan(String tan) {
        this.setTan(tan);
        return this;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public BanksDetails branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRefTable() {
        return this.refTable;
    }

    public BanksDetails refTable(String refTable) {
        this.setRefTable(refTable);
        return this;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return this.refTableId;
    }

    public BanksDetails refTableId(Long refTableId) {
        this.setRefTableId(refTableId);
        return this;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public BanksDetails companyId(Long companyId) {
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

    public BanksDetails status(String status) {
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
    //    public BanksDetails lastModified(Instant lastModified) {
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
    //    public BanksDetails lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof BanksDetails)) {
            return false;
        }
        return id != null && id.equals(((BanksDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksDetails{" +
            "id=" + getId() +
            ", accountNumber=" + getAccountNumber() +
            ", bankName='" + getBankName() + "'" +
            ", branchTransCode='" + getBranchTransCode() + "'" +
            ", taxNumber='" + getTaxNumber() + "'" +
            ", gstin='" + getGstin() + "'" +
            ", tan='" + getTan() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
