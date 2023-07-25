package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.AppraisalEvaluation} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.AppraisalEvaluationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appraisal-evaluations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalEvaluationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter answerFlag;

    private StringFilter description;

    private LongFilter employeeId;

    private LongFilter appraisalReviewId;

    private LongFilter availablePoints;

    private LongFilter scoredPoints;

    private StringFilter remark;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter appraisalEvaluationParameterId;

    private Boolean distinct;

    public AppraisalEvaluationCriteria() {}

    public AppraisalEvaluationCriteria(AppraisalEvaluationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.answerFlag = other.answerFlag == null ? null : other.answerFlag.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.appraisalReviewId = other.appraisalReviewId == null ? null : other.appraisalReviewId.copy();
        this.availablePoints = other.availablePoints == null ? null : other.availablePoints.copy();
        this.scoredPoints = other.scoredPoints == null ? null : other.scoredPoints.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.appraisalEvaluationParameterId =
            other.appraisalEvaluationParameterId == null ? null : other.appraisalEvaluationParameterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AppraisalEvaluationCriteria copy() {
        return new AppraisalEvaluationCriteria(this);
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

    public BooleanFilter getAnswerFlag() {
        return answerFlag;
    }

    public BooleanFilter answerFlag() {
        if (answerFlag == null) {
            answerFlag = new BooleanFilter();
        }
        return answerFlag;
    }

    public void setAnswerFlag(BooleanFilter answerFlag) {
        this.answerFlag = answerFlag;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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

    public LongFilter getAvailablePoints() {
        return availablePoints;
    }

    public LongFilter availablePoints() {
        if (availablePoints == null) {
            availablePoints = new LongFilter();
        }
        return availablePoints;
    }

    public void setAvailablePoints(LongFilter availablePoints) {
        this.availablePoints = availablePoints;
    }

    public LongFilter getScoredPoints() {
        return scoredPoints;
    }

    public LongFilter scoredPoints() {
        if (scoredPoints == null) {
            scoredPoints = new LongFilter();
        }
        return scoredPoints;
    }

    public void setScoredPoints(LongFilter scoredPoints) {
        this.scoredPoints = scoredPoints;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public LongFilter getAppraisalEvaluationParameterId() {
        return appraisalEvaluationParameterId;
    }

    public LongFilter appraisalEvaluationParameterId() {
        if (appraisalEvaluationParameterId == null) {
            appraisalEvaluationParameterId = new LongFilter();
        }
        return appraisalEvaluationParameterId;
    }

    public void setAppraisalEvaluationParameterId(LongFilter appraisalEvaluationParameterId) {
        this.appraisalEvaluationParameterId = appraisalEvaluationParameterId;
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
        final AppraisalEvaluationCriteria that = (AppraisalEvaluationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(answerFlag, that.answerFlag) &&
            Objects.equals(description, that.description) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(appraisalReviewId, that.appraisalReviewId) &&
            Objects.equals(availablePoints, that.availablePoints) &&
            Objects.equals(scoredPoints, that.scoredPoints) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(appraisalEvaluationParameterId, that.appraisalEvaluationParameterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            answerFlag,
            description,
            employeeId,
            appraisalReviewId,
            availablePoints,
            scoredPoints,
            remark,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            appraisalEvaluationParameterId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalEvaluationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (answerFlag != null ? "answerFlag=" + answerFlag + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (appraisalReviewId != null ? "appraisalReviewId=" + appraisalReviewId + ", " : "") +
            (availablePoints != null ? "availablePoints=" + availablePoints + ", " : "") +
            (scoredPoints != null ? "scoredPoints=" + scoredPoints + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (appraisalEvaluationParameterId != null ? "appraisalEvaluationParameterId=" + appraisalEvaluationParameterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
