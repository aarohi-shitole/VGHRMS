package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Approval} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalDTO implements Serializable {

    private Long id;

    private Long approverEmployeeId;

    private String approvalStatus;

    private String refTable;

    private Long sequence;

    private Long refTableId;

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

    public Long getApproverEmployeeId() {
        return approverEmployeeId;
    }

    public void setApproverEmployeeId(Long approverEmployeeId) {
        this.approverEmployeeId = approverEmployeeId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
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
        if (!(o instanceof ApprovalDTO)) {
            return false;
        }

        ApprovalDTO approvalDTO = (ApprovalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalDTO{" +
            "id=" + getId() +
            ", approverEmployeeId=" + getApproverEmployeeId() +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", sequence=" + getSequence() +
            ", refTableId=" + getRefTableId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
