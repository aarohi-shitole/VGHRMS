package com.techvg.hrms.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Approval.
 */
@Entity
@Table(name = "approval")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE approval SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Approval extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "approver_employee_id")
    private Long approverEmployeeId;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "ref_table")
    private String refTable;

    @Column(name = "ref_table_id")
    private Long refTableId;

    @Column(name = "sequence")
    private Long sequence;

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

    public Approval id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApproverEmployeeId() {
        return this.approverEmployeeId;
    }

    public Approval approverEmployeeId(Long approverEmployeeId) {
        this.setApproverEmployeeId(approverEmployeeId);
        return this;
    }

    public void setApproverEmployeeId(Long approverEmployeeId) {
        this.approverEmployeeId = approverEmployeeId;
    }

    public String getApprovalStatus() {
        return this.approvalStatus;
    }

    public Approval approvalStatus(String approvalStatus) {
        this.setApprovalStatus(approvalStatus);
        return this;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRefTable() {
        return refTable;
    }

    public Approval refTable(String refTable) {
        this.setRefTable(refTable);
        return this;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getSequence() {
        return this.sequence;
    }

    public Approval sequence(Long sequence) {
        this.setSequence(sequence);
        return this;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public Approval refTableId(Long refTableId) {
        this.setRefTableId(refTableId);
        return this;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    public String getStatus() {
        return this.status;
    }

    public Approval status(String status) {
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
    //    public Approval companyId(Long companyId) {
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
    //    public Approval lastModified(Instant lastModified) {
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
    //    public Approval lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Approval)) {
            return false;
        }
        return id != null && id.equals(((Approval) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Approval{" +
            "id=" + getId() +
            ", approverEmployeeId=" + getApproverEmployeeId() +
            ", approvalStatus='" + getApprovalStatus() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", sequence=" + getSequence() +
            ", status='" + getStatus() + "'" +
//            ", companyId=" + getCompanyId() +
//            ", lastModified='" + getLastModified() + "'" +
//            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
