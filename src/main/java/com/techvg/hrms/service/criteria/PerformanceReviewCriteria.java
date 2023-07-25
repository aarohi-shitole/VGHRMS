package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.PerformanceReview} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.PerformanceReviewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /performance-reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerformanceReviewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter appraisalReviewId;

    private StringFilter employeeRemark;

    private StringFilter appraiserRemark;

    private StringFilter reviewerRemark;

    private StringFilter selfScored;

    private StringFilter scoredByAppraiser;

    private StringFilter scoredByReviewer;

    private StringFilter appraisalStatus;

    private InstantFilter submissionDate;

    private InstantFilter appraisalDate;

    private InstantFilter reviewDate;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter target;

    private LongFilter targetAchived;

    private LongFilter performanceIndicatorId;

    private Boolean distinct;

    public PerformanceReviewCriteria() {}

    public PerformanceReviewCriteria(PerformanceReviewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.appraisalReviewId = other.appraisalReviewId == null ? null : other.appraisalReviewId.copy();
        this.employeeRemark = other.employeeRemark == null ? null : other.employeeRemark.copy();
        this.appraiserRemark = other.appraiserRemark == null ? null : other.appraiserRemark.copy();
        this.reviewerRemark = other.reviewerRemark == null ? null : other.reviewerRemark.copy();
        this.selfScored = other.selfScored == null ? null : other.selfScored.copy();
        this.scoredByAppraiser = other.scoredByAppraiser == null ? null : other.scoredByAppraiser.copy();
        this.scoredByReviewer = other.scoredByReviewer == null ? null : other.scoredByReviewer.copy();
        this.appraisalStatus = other.appraisalStatus == null ? null : other.appraisalStatus.copy();
        this.submissionDate = other.submissionDate == null ? null : other.submissionDate.copy();
        this.appraisalDate = other.appraisalDate == null ? null : other.appraisalDate.copy();
        this.reviewDate = other.reviewDate == null ? null : other.reviewDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.target = other.target == null ? null : other.target.copy();
        this.targetAchived = other.targetAchived == null ? null : other.targetAchived.copy();
        this.performanceIndicatorId = other.performanceIndicatorId == null ? null : other.performanceIndicatorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PerformanceReviewCriteria copy() {
        return new PerformanceReviewCriteria(this);
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

    public LongFilter getAppraisalReviewId() {
        return appraisalReviewId;
    }

    public LongFilter appraisalReviewId() {
        if (appraisalReviewId == null) {
            appraisalReviewId = new LongFilter();
        }
        return appraisalReviewId;
    }

    public void setAppraisalReviewId(LongFilter appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public StringFilter getEmployeeRemark() {
        return employeeRemark;
    }

    public StringFilter employeeRemark() {
        if (employeeRemark == null) {
            employeeRemark = new StringFilter();
        }
        return employeeRemark;
    }

    public void setEmployeeRemark(StringFilter employeeRemark) {
        this.employeeRemark = employeeRemark;
    }

    public StringFilter getAppraiserRemark() {
        return appraiserRemark;
    }

    public StringFilter appraiserRemark() {
        if (appraiserRemark == null) {
            appraiserRemark = new StringFilter();
        }
        return appraiserRemark;
    }

    public void setAppraiserRemark(StringFilter appraiserRemark) {
        this.appraiserRemark = appraiserRemark;
    }

    public StringFilter getReviewerRemark() {
        return reviewerRemark;
    }

    public StringFilter reviewerRemark() {
        if (reviewerRemark == null) {
            reviewerRemark = new StringFilter();
        }
        return reviewerRemark;
    }

    public void setReviewerRemark(StringFilter reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public StringFilter getSelfScored() {
        return selfScored;
    }

    public StringFilter selfScored() {
        if (selfScored == null) {
            selfScored = new StringFilter();
        }
        return selfScored;
    }

    public void setSelfScored(StringFilter selfScored) {
        this.selfScored = selfScored;
    }

    public StringFilter getScoredByAppraiser() {
        return scoredByAppraiser;
    }

    public StringFilter scoredByAppraiser() {
        if (scoredByAppraiser == null) {
            scoredByAppraiser = new StringFilter();
        }
        return scoredByAppraiser;
    }

    public void setScoredByAppraiser(StringFilter scoredByAppraiser) {
        this.scoredByAppraiser = scoredByAppraiser;
    }

    public StringFilter getScoredByReviewer() {
        return scoredByReviewer;
    }

    public StringFilter scoredByReviewer() {
        if (scoredByReviewer == null) {
            scoredByReviewer = new StringFilter();
        }
        return scoredByReviewer;
    }

    public void setScoredByReviewer(StringFilter scoredByReviewer) {
        this.scoredByReviewer = scoredByReviewer;
    }

    public StringFilter getAppraisalStatus() {
        return appraisalStatus;
    }

    public StringFilter appraisalStatus() {
        if (appraisalStatus == null) {
            appraisalStatus = new StringFilter();
        }
        return appraisalStatus;
    }

    public void setAppraisalStatus(StringFilter appraisalStatus) {
        this.appraisalStatus = appraisalStatus;
    }

    public InstantFilter getSubmissionDate() {
        return submissionDate;
    }

    public InstantFilter submissionDate() {
        if (submissionDate == null) {
            submissionDate = new InstantFilter();
        }
        return submissionDate;
    }

    public void setSubmissionDate(InstantFilter submissionDate) {
        this.submissionDate = submissionDate;
    }

    public InstantFilter getAppraisalDate() {
        return appraisalDate;
    }

    public InstantFilter appraisalDate() {
        if (appraisalDate == null) {
            appraisalDate = new InstantFilter();
        }
        return appraisalDate;
    }

    public void setAppraisalDate(InstantFilter appraisalDate) {
        this.appraisalDate = appraisalDate;
    }

    public InstantFilter getReviewDate() {
        return reviewDate;
    }

    public InstantFilter reviewDate() {
        if (reviewDate == null) {
            reviewDate = new InstantFilter();
        }
        return reviewDate;
    }

    public void setReviewDate(InstantFilter reviewDate) {
        this.reviewDate = reviewDate;
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

    public LongFilter getTarget() {
        return target;
    }

    public LongFilter target() {
        if (target == null) {
            target = new LongFilter();
        }
        return target;
    }

    public void setTarget(LongFilter target) {
        this.target = target;
    }

    public LongFilter getTargetAchived() {
        return targetAchived;
    }

    public LongFilter targetAchived() {
        if (targetAchived == null) {
            targetAchived = new LongFilter();
        }
        return targetAchived;
    }

    public void setTargetAchived(LongFilter targetAchived) {
        this.targetAchived = targetAchived;
    }

    public LongFilter getPerformanceIndicatorId() {
        return performanceIndicatorId;
    }

    public LongFilter performanceIndicatorId() {
        if (performanceIndicatorId == null) {
            performanceIndicatorId = new LongFilter();
        }
        return performanceIndicatorId;
    }

    public void setPerformanceIndicatorId(LongFilter performanceIndicatorId) {
        this.performanceIndicatorId = performanceIndicatorId;
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
        final PerformanceReviewCriteria that = (PerformanceReviewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(appraisalReviewId, that.appraisalReviewId) &&
            Objects.equals(employeeRemark, that.employeeRemark) &&
            Objects.equals(appraiserRemark, that.appraiserRemark) &&
            Objects.equals(reviewerRemark, that.reviewerRemark) &&
            Objects.equals(selfScored, that.selfScored) &&
            Objects.equals(scoredByAppraiser, that.scoredByAppraiser) &&
            Objects.equals(scoredByReviewer, that.scoredByReviewer) &&
            Objects.equals(appraisalStatus, that.appraisalStatus) &&
            Objects.equals(submissionDate, that.submissionDate) &&
            Objects.equals(appraisalDate, that.appraisalDate) &&
            Objects.equals(reviewDate, that.reviewDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(target, that.target) &&
            Objects.equals(targetAchived, that.targetAchived) &&
            Objects.equals(performanceIndicatorId, that.performanceIndicatorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            appraisalReviewId,
            employeeRemark,
            appraiserRemark,
            reviewerRemark,
            selfScored,
            scoredByAppraiser,
            scoredByReviewer,
            appraisalStatus,
            submissionDate,
            appraisalDate,
            reviewDate,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            target,
            targetAchived,
            performanceIndicatorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceReviewCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (appraisalReviewId != null ? "appraisalReviewId=" + appraisalReviewId + ", " : "") +
            (employeeRemark != null ? "employeeRemark=" + employeeRemark + ", " : "") +
            (appraiserRemark != null ? "appraiserRemark=" + appraiserRemark + ", " : "") +
            (reviewerRemark != null ? "reviewerRemark=" + reviewerRemark + ", " : "") +
            (selfScored != null ? "selfScored=" + selfScored + ", " : "") +
            (scoredByAppraiser != null ? "scoredByAppraiser=" + scoredByAppraiser + ", " : "") +
            (scoredByReviewer != null ? "scoredByReviewer=" + scoredByReviewer + ", " : "") +
            (appraisalStatus != null ? "appraisalStatus=" + appraisalStatus + ", " : "") +
            (submissionDate != null ? "submissionDate=" + submissionDate + ", " : "") +
            (appraisalDate != null ? "appraisalDate=" + appraisalDate + ", " : "") +
            (reviewDate != null ? "reviewDate=" + reviewDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (target != null ? "target=" + target + ", " : "") +
            (targetAchived != null ? "targetAchived=" + targetAchived + ", " : "") +
            (performanceIndicatorId != null ? "performanceIndicatorId=" + performanceIndicatorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
