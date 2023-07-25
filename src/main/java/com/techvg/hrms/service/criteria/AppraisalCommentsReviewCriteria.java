package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.AppraisalCommentsReview} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.AppraisalCommentsReviewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appraisal-comments-reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalCommentsReviewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter comment;

    private StringFilter commentType;

    private LongFilter appraisalReviewId;

    private StringFilter refFormName;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter employeeId;

    private Boolean distinct;

    public AppraisalCommentsReviewCriteria() {}

    public AppraisalCommentsReviewCriteria(AppraisalCommentsReviewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.commentType = other.commentType == null ? null : other.commentType.copy();
        this.appraisalReviewId = other.appraisalReviewId == null ? null : other.appraisalReviewId.copy();
        this.refFormName = other.refFormName == null ? null : other.refFormName.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AppraisalCommentsReviewCriteria copy() {
        return new AppraisalCommentsReviewCriteria(this);
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

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public StringFilter getCommentType() {
        return commentType;
    }

    public StringFilter commentType() {
        if (commentType == null) {
            commentType = new StringFilter();
        }
        return commentType;
    }

    public void setCommentType(StringFilter commentType) {
        this.commentType = commentType;
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

    public StringFilter getRefFormName() {
        return refFormName;
    }

    public StringFilter refFormName() {
        if (refFormName == null) {
            refFormName = new StringFilter();
        }
        return refFormName;
    }

    public void setRefFormName(StringFilter refFormName) {
        this.refFormName = refFormName;
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
        final AppraisalCommentsReviewCriteria that = (AppraisalCommentsReviewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(commentType, that.commentType) &&
            Objects.equals(appraisalReviewId, that.appraisalReviewId) &&
            Objects.equals(refFormName, that.refFormName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            comment,
            commentType,
            appraisalReviewId,
            refFormName,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            employeeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalCommentsReviewCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (commentType != null ? "commentType=" + commentType + ", " : "") +
            (appraisalReviewId != null ? "appraisalReviewId=" + appraisalReviewId + ", " : "") +
            (refFormName != null ? "refFormName=" + refFormName + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
