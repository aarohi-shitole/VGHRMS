package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A AppraisalReview.
 */
@Entity
@Table(name = "appraisal_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE appraisal_review SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class AppraisalReview extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reporting_officer")
    private String reportingOfficer;

    @Column(name = "ro_designation")
    private String roDesignation;
    
    @Column(name = "appraisal_status")
    private String appraisalStatus;

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

    @Column(name = "employe_id")
    private Long employeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppraisalReview id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportingOfficer() {
        return this.reportingOfficer;
    }

    public AppraisalReview reportingOfficer(String reportingOfficer) {
        this.setReportingOfficer(reportingOfficer);
        return this;
    }

    public void setReportingOfficer(String reportingOfficer) {
        this.reportingOfficer = reportingOfficer;
    }
    
    public String getAppraisalStatus() {
        return this.appraisalStatus;
    }

    public AppraisalReview appraisalStatus(String appraisalStatus) {
        this.setAppraisalStatus(appraisalStatus);
        return this;
    }

    public void setAppraisalStatus(String appraisalStatus) {
        this.appraisalStatus = appraisalStatus;
    }

    public String getRoDesignation() {
        return this.roDesignation;
    }

    public AppraisalReview roDesignation(String roDesignation) {
        this.setRoDesignation(roDesignation);
        return this;
    }

    public void setRoDesignation(String roDesignation) {
        this.roDesignation = roDesignation;
    }

    public String getStatus() {
        return this.status;
    }

    public AppraisalReview status(String status) {
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
//    public AppraisalReview companyId(Long companyId) {
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
//    public AppraisalReview lastModified(Instant lastModified) {
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
//    public AppraisalReview lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    public Long getEmployeId() {
        return this.employeId;
    }

    public AppraisalReview employeId(Long employeId) {
        this.setEmployeId(employeId);
        return this;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalReview)) {
            return false;
        }
        return id != null && id.equals(((AppraisalReview) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalReview{" +
            "id=" + getId() +
            ", reportingOfficer='" + getReportingOfficer() + "'" +
            ", appraisalStatus='" + getAppraisalStatus() + "'" +
            ", roDesignation='" + getRoDesignation() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeId=" + getEmployeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
