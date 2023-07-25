package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A CustomApprovar.
 */
@Entity
@Table(name = "custom_approvar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE custom_approvar SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class CustomApprovar extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employe_id")
    private Long employeId;

    @Column(name = "approval_setting_id")
    private String approvalSettingId;

    @Column(name = "squence")
    private String squence;

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

    public CustomApprovar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeId() {
        return this.employeId;
    }

    public CustomApprovar employeId(Long employeId) {
        this.setEmployeId(employeId);
        return this;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public String getApprovalSettingId() {
        return this.approvalSettingId;
    }

    public CustomApprovar approvalSettingId(String approvalSettingId) {
        this.setApprovalSettingId(approvalSettingId);
        return this;
    }

    public void setApprovalSettingId(String approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    public String getSquence() {
        return this.squence;
    }

    public CustomApprovar squence(String squence) {
        this.setSquence(squence);
        return this;
    }

    public void setSquence(String squence) {
        this.squence = squence;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public CustomApprovar companyId(Long companyId) {
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

    public CustomApprovar status(String status) {
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
    //    public CustomApprovar lastModified(Instant lastModified) {
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
    //    public CustomApprovar lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof CustomApprovar)) {
            return false;
        }
        return id != null && id.equals(((CustomApprovar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomApprovar{" +
            "id=" + getId() +
            ", employeId=" + getEmployeId() +
            ", approvalSettingId='" + getApprovalSettingId() + "'" +
            ", squence='" + getSquence() + "'" +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
