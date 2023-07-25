package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.BanksDetails} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.BanksDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /banks-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BanksDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter accountNumber;

    private StringFilter bankName;

    private StringFilter branchTransCode;

    private StringFilter taxNumber;

    private StringFilter gstin;

    private StringFilter tan;

    private StringFilter branchName;

    private StringFilter refTable;

    private LongFilter refTableId;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public BanksDetailsCriteria() {}

    public BanksDetailsCriteria(BanksDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.branchTransCode = other.branchTransCode == null ? null : other.branchTransCode.copy();
        this.taxNumber = other.taxNumber == null ? null : other.taxNumber.copy();
        this.gstin = other.gstin == null ? null : other.gstin.copy();
        this.tan = other.tan == null ? null : other.tan.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.refTable = other.refTable == null ? null : other.refTable.copy();
        this.refTableId = other.refTableId == null ? null : other.refTableId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BanksDetailsCriteria copy() {
        return new BanksDetailsCriteria(this);
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

    public LongFilter getAccountNumber() {
        return accountNumber;
    }

    public LongFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new LongFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(LongFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getBranchTransCode() {
        return branchTransCode;
    }

    public StringFilter branchTransCode() {
        if (branchTransCode == null) {
            branchTransCode = new StringFilter();
        }
        return branchTransCode;
    }

    public void setBranchTransCode(StringFilter branchTransCode) {
        this.branchTransCode = branchTransCode;
    }

    public StringFilter getTaxNumber() {
        return taxNumber;
    }

    public StringFilter taxNumber() {
        if (taxNumber == null) {
            taxNumber = new StringFilter();
        }
        return taxNumber;
    }

    public void setTaxNumber(StringFilter taxNumber) {
        this.taxNumber = taxNumber;
    }

    public StringFilter getGstin() {
        return gstin;
    }

    public StringFilter gstin() {
        if (gstin == null) {
            gstin = new StringFilter();
        }
        return gstin;
    }

    public void setGstin(StringFilter gstin) {
        this.gstin = gstin;
    }

    public StringFilter getTan() {
        return tan;
    }

    public StringFilter tan() {
        if (tan == null) {
            tan = new StringFilter();
        }
        return tan;
    }

    public void setTan(StringFilter tan) {
        this.tan = tan;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getRefTable() {
        return refTable;
    }

    public StringFilter refTable() {
        if (refTable == null) {
            refTable = new StringFilter();
        }
        return refTable;
    }

    public void setRefTable(StringFilter refTable) {
        this.refTable = refTable;
    }

    public LongFilter getRefTableId() {
        return refTableId;
    }

    public LongFilter refTableId() {
        if (refTableId == null) {
            refTableId = new LongFilter();
        }
        return refTableId;
    }

    public void setRefTableId(LongFilter refTableId) {
        this.refTableId = refTableId;
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
        final BanksDetailsCriteria that = (BanksDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchTransCode, that.branchTransCode) &&
            Objects.equals(taxNumber, that.taxNumber) &&
            Objects.equals(gstin, that.gstin) &&
            Objects.equals(tan, that.tan) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(refTable, that.refTable) &&
            Objects.equals(refTableId, that.refTableId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            accountNumber,
            bankName,
            branchTransCode,
            taxNumber,
            gstin,
            tan,
            branchName,
            refTable,
            refTableId,
            companyId,
            status,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanksDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (branchTransCode != null ? "branchTransCode=" + branchTransCode + ", " : "") +
            (taxNumber != null ? "taxNumber=" + taxNumber + ", " : "") +
            (gstin != null ? "gstin=" + gstin + ", " : "") +
            (tan != null ? "tan=" + tan + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (refTable != null ? "refTable=" + refTable + ", " : "") +
            (refTableId != null ? "refTableId=" + refTableId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
