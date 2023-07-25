package com.techvg.hrms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PerformanceReview.
 */
@Entity
@Table(name = "performance_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE performance_review SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PerformanceReview extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "appraisal_review_id")
    private Long appraisalReviewId;

    @Column(name = "employee_remark")
    private String employeeRemark;

    @Column(name = "appraiser_remark")
    private String appraiserRemark;

    @Column(name = "reviewer_remark")
    private String reviewerRemark;

    @Column(name = "self_scored")
    private String selfScored;

    @Column(name = "scored_by_appraiser")
    private String scoredByAppraiser;

    @Column(name = "scored_by_reviewer")
    private String scoredByReviewer;

    @Column(name = "appraisal_status")
    private String appraisalStatus;

    @Column(name = "submission_date")
    private Instant submissionDate;

    @Column(name = "appraisal_date")
    private Instant appraisalDate;

    @Column(name = "review_date")
    private Instant reviewDate;

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

    @Column(name = "target")
    private Long target;

    @Column(name = "target_achived")
    private Long targetAchived;

    @ManyToOne
    @JsonIgnoreProperties(value = { "masterPerformanceIndicator" }, allowSetters = true)
    private PerformanceIndicator performanceIndicator;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerformanceReview id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppraisalReviewId() {
        return this.appraisalReviewId;
    }

    public PerformanceReview appraisalReviewId(Long appraisalReviewId) {
        this.setAppraisalReviewId(appraisalReviewId);
        return this;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public String getEmployeeRemark() {
        return this.employeeRemark;
    }

    public PerformanceReview employeeRemark(String employeeRemark) {
        this.setEmployeeRemark(employeeRemark);
        return this;
    }

    public void setEmployeeRemark(String employeeRemark) {
        this.employeeRemark = employeeRemark;
    }

    public String getAppraiserRemark() {
        return this.appraiserRemark;
    }

    public PerformanceReview appraiserRemark(String appraiserRemark) {
        this.setAppraiserRemark(appraiserRemark);
        return this;
    }

    public void setAppraiserRemark(String appraiserRemark) {
        this.appraiserRemark = appraiserRemark;
    }

    public String getReviewerRemark() {
        return this.reviewerRemark;
    }

    public PerformanceReview reviewerRemark(String reviewerRemark) {
        this.setReviewerRemark(reviewerRemark);
        return this;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public String getSelfScored() {
        return this.selfScored;
    }

    public PerformanceReview selfScored(String selfScored) {
        this.setSelfScored(selfScored);
        return this;
    }

    public void setSelfScored(String selfScored) {
        this.selfScored = selfScored;
    }

    public String getScoredByAppraiser() {
        return this.scoredByAppraiser;
    }

    public PerformanceReview scoredByAppraiser(String scoredByAppraiser) {
        this.setScoredByAppraiser(scoredByAppraiser);
        return this;
    }

    public void setScoredByAppraiser(String scoredByAppraiser) {
        this.scoredByAppraiser = scoredByAppraiser;
    }

    public String getScoredByReviewer() {
        return this.scoredByReviewer;
    }

    public PerformanceReview scoredByReviewer(String scoredByReviewer) {
        this.setScoredByReviewer(scoredByReviewer);
        return this;
    }

    public void setScoredByReviewer(String scoredByReviewer) {
        this.scoredByReviewer = scoredByReviewer;
    }

    public String getAppraisalStatus() {
        return this.appraisalStatus;
    }

    public PerformanceReview appraisalStatus(String appraisalStatus) {
        this.setAppraisalStatus(appraisalStatus);
        return this;
    }

    public void setAppraisalStatus(String appraisalStatus) {
        this.appraisalStatus = appraisalStatus;
    }

    public Instant getSubmissionDate() {
        return this.submissionDate;
    }

    public PerformanceReview submissionDate(Instant submissionDate) {
        this.setSubmissionDate(submissionDate);
        return this;
    }

    public void setSubmissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Instant getAppraisalDate() {
        return this.appraisalDate;
    }

    public PerformanceReview appraisalDate(Instant appraisalDate) {
        this.setAppraisalDate(appraisalDate);
        return this;
    }

    public void setAppraisalDate(Instant appraisalDate) {
        this.appraisalDate = appraisalDate;
    }

    public Instant getReviewDate() {
        return this.reviewDate;
    }

    public PerformanceReview reviewDate(Instant reviewDate) {
        this.setReviewDate(reviewDate);
        return this;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getStatus() {
        return this.status;
    }

    public PerformanceReview status(String status) {
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
//    public PerformanceReview companyId(Long companyId) {
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
//    public PerformanceReview lastModified(Instant lastModified) {
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
//    public PerformanceReview lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    public Long getTarget() {
        return this.target;
    }

    public PerformanceReview target(Long target) {
        this.setTarget(target);
        return this;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Long getTargetAchived() {
        return this.targetAchived;
    }

    public PerformanceReview targetAchived(Long targetAchived) {
        this.setTargetAchived(targetAchived);
        return this;
    }

    public void setTargetAchived(Long targetAchived) {
        this.targetAchived = targetAchived;
    }

    public PerformanceIndicator getPerformanceIndicator() {
        return this.performanceIndicator;
    }

    public void setPerformanceIndicator(PerformanceIndicator performanceIndicator) {
        this.performanceIndicator = performanceIndicator;
    }

    public PerformanceReview performanceIndicator(PerformanceIndicator performanceIndicator) {
        this.setPerformanceIndicator(performanceIndicator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceReview)) {
            return false;
        }
        return id != null && id.equals(((PerformanceReview) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceReview{" +
            "id=" + getId() +
            ", appraisalReviewId=" + getAppraisalReviewId() +
            ", employeeRemark='" + getEmployeeRemark() + "'" +
            ", appraiserRemark='" + getAppraiserRemark() + "'" +
            ", reviewerRemark='" + getReviewerRemark() + "'" +
            ", selfScored='" + getSelfScored() + "'" +
            ", scoredByAppraiser='" + getScoredByAppraiser() + "'" +
            ", scoredByReviewer='" + getScoredByReviewer() + "'" +
            ", appraisalStatus='" + getAppraisalStatus() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", appraisalDate='" + getAppraisalDate() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", target=" + getTarget() +
            ", targetAchived=" + getTargetAchived() +
            "}";
    }
}
