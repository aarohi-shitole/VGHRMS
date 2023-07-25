package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.PerformanceReview} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceReviewDTO implements Serializable {

    private Long id;

    private Long appraisalReviewId;

    private String employeeRemark;

    private String appraiserRemark;

    private String reviewerRemark;

    private String selfScored;

    private String scoredByAppraiser;

    private String scoredByReviewer;

    private String appraisalStatus;

    private Instant submissionDate;

    private Instant appraisalDate;

    private Instant reviewDate;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private Long target;

    private Long targetAchived;

    private PerformanceIndicatorDTO performanceIndicator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppraisalReviewId() {
        return appraisalReviewId;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public String getEmployeeRemark() {
        return employeeRemark;
    }

    public void setEmployeeRemark(String employeeRemark) {
        this.employeeRemark = employeeRemark;
    }

    public String getAppraiserRemark() {
        return appraiserRemark;
    }

    public void setAppraiserRemark(String appraiserRemark) {
        this.appraiserRemark = appraiserRemark;
    }

    public String getReviewerRemark() {
        return reviewerRemark;
    }

    public void setReviewerRemark(String reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public String getSelfScored() {
        return selfScored;
    }

    public void setSelfScored(String selfScored) {
        this.selfScored = selfScored;
    }

    public String getScoredByAppraiser() {
        return scoredByAppraiser;
    }

    public void setScoredByAppraiser(String scoredByAppraiser) {
        this.scoredByAppraiser = scoredByAppraiser;
    }

    public String getScoredByReviewer() {
        return scoredByReviewer;
    }

    public void setScoredByReviewer(String scoredByReviewer) {
        this.scoredByReviewer = scoredByReviewer;
    }

    public String getAppraisalStatus() {
        return appraisalStatus;
    }

    public void setAppraisalStatus(String appraisalStatus) {
        this.appraisalStatus = appraisalStatus;
    }

    public Instant getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Instant getAppraisalDate() {
        return appraisalDate;
    }

    public void setAppraisalDate(Instant appraisalDate) {
        this.appraisalDate = appraisalDate;
    }

    public Instant getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
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

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Long getTargetAchived() {
        return targetAchived;
    }

    public void setTargetAchived(Long targetAchived) {
        this.targetAchived = targetAchived;
    }

    public PerformanceIndicatorDTO getPerformanceIndicator() {
        return performanceIndicator;
    }

    public void setPerformanceIndicator(PerformanceIndicatorDTO performanceIndicator) {
        this.performanceIndicator = performanceIndicator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceReviewDTO)) {
            return false;
        }

        PerformanceReviewDTO performanceReviewDTO = (PerformanceReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, performanceReviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceReviewDTO{" +
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
            ", performanceIndicator=" + getPerformanceIndicator() +
            "}";
    }
}
