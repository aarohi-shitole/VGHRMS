package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A ApprovalLevel.
 */
@Entity
@Table(name = "approval_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE approval_level SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class ApprovalLevel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "designation_id")
    private Long designationId;

    @Column(name = "squence")
    private Long squence;

    @Column(name = "approval_setting_id")
    private Long approvalSettingId;

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

    public ApprovalLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDesignationId() {
        return this.designationId;
    }

    public ApprovalLevel designationId(Long designationId) {
        this.setDesignationId(designationId);
        return this;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public Long getSquence() {
        return this.squence;
    }

    public ApprovalLevel squence(Long squence) {
        this.setSquence(squence);
        return this;
    }

    public void setSquence(Long squence) {
        this.squence = squence;
    }

    public Long getApprovalSettingId() {
        return this.approvalSettingId;
    }

    public ApprovalLevel approvalSettingId(Long approvalSettingId) {
        this.setApprovalSettingId(approvalSettingId);
        return this;
    }

    public void setApprovalSettingId(Long approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public ApprovalLevel companyId(Long companyId) {
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

    public ApprovalLevel status(String status) {
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
    //    public ApprovalLevel lastModified(Instant lastModified) {
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
    //    public ApprovalLevel lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof ApprovalLevel)) {
            return false;
        }
        return id != null && id.equals(((ApprovalLevel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalLevel{" +
            "id=" + getId() +
            ", designationId='" + getDesignationId() + "'" +
            ", squence=" + getSquence() +
            ", approvalSettingId=" + getApprovalSettingId() +
        //    ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
        //    ", lastModified='" + getLastModified() + "'" +
        //    ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
