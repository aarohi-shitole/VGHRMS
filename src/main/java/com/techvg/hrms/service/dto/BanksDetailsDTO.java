package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.BanksDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanksDetailsDTO implements Serializable {

    private Long id;

    private Long accountNumber;

    private String bankName;

    private String branchTransCode;

    private String taxNumber;

    private String gstin;

    private String tan;

    private String branchName;

    private String refTable;

    private Long refTableId;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchTransCode() {
        return branchTransCode;
    }

    public void setBranchTransCode(String branchTransCode) {
        this.branchTransCode = branchTransCode;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof BanksDetailsDTO)) {
            return false;
        }

        BanksDetailsDTO banksDetailsDTO = (BanksDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, banksDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksDetailsDTO{" +
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
