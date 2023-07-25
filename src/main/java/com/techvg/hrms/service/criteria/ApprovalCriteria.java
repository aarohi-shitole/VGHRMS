package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Approval} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.ApprovalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-application-approvals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter approverEmployeeId;

    private StringFilter approvalStatus;

    private StringFilter refTable;

    private LongFilter sequence;

    private LongFilter refTableId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public ApprovalCriteria() {}

    public ApprovalCriteria(ApprovalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.approverEmployeeId = other.approverEmployeeId == null ? null : other.approverEmployeeId.copy();
        this.approvalStatus = other.approvalStatus == null ? null : other.approvalStatus.copy();

        this.refTable = other.refTable == null ? null : other.refTable.copy();
        this.sequence = other.sequence == null ? null : other.sequence.copy();

        this.refTableId = other.refTableId == null ? null : other.refTableId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ApprovalCriteria copy() {
        return new ApprovalCriteria(this);
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

    public LongFilter getApproverEmployeeId() {
        return approverEmployeeId;
    }

    public LongFilter approverEmployeeId() {
        if (approverEmployeeId == null) {
            approverEmployeeId = new LongFilter();
        }
        return approverEmployeeId;
    }

    public void setApproverEmployeeId(LongFilter approverEmployeeId) {
        this.approverEmployeeId = approverEmployeeId;
    }

    public StringFilter getApprovalStatus() {
        return approvalStatus;
    }

    public StringFilter approvalStatus() {
        if (approvalStatus == null) {
            approvalStatus = new StringFilter();
        }
        return approvalStatus;
    }

    public void setApprovalStatus(StringFilter approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    public LongFilter getSequence() {
        return sequence;
    }

    public LongFilter sequence() {
        if (sequence == null) {
            sequence = new LongFilter();
        }
        return sequence;
    }

    public void setSequence(LongFilter sequence) {
        this.sequence = sequence;
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
        final ApprovalCriteria that = (ApprovalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(approverEmployeeId, that.approverEmployeeId) &&
            Objects.equals(approvalStatus, that.approvalStatus) &&
            Objects.equals(refTable, that.refTable) &&
            Objects.equals(sequence, that.sequence) &&
            Objects.equals(refTableId, that.refTableId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            approverEmployeeId,
            approvalStatus,
            refTable,
            sequence,
            refTableId,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
           (approverEmployeeId != null ? "approverEmployeeId=" + approverEmployeeId + ", " : "") +
            (approvalStatus != null ? "approvalStatus=" + approvalStatus + ", " : "") +
            (refTable != null ? "refTable=" + refTable + ", " : "") +
            (sequence != null ? "sequence=" + sequence + ", " : "") +
            (refTableId != null ? "refTableId=" + refTableId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
