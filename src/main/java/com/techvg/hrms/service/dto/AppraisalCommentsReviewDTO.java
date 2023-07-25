package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.AppraisalCommentsReview} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalCommentsReviewDTO implements Serializable {

    private Long id;

    private String comment;

    private String commentType;

    private Long appraisalReviewId;

    private String refFormName;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Long getAppraisalReviewId() {
        return appraisalReviewId;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public String getRefFormName() {
        return refFormName;
    }

    public void setRefFormName(String refFormName) {
        this.refFormName = refFormName;
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

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalCommentsReviewDTO)) {
            return false;
        }

        AppraisalCommentsReviewDTO appraisalCommentsReviewDTO = (AppraisalCommentsReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appraisalCommentsReviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalCommentsReviewDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", commentType='" + getCommentType() + "'" +
            ", appraisalReviewId=" + getAppraisalReviewId() +
            ", refFormName='" + getRefFormName() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", employee=" + getEmployee() +
            "}";
    }
}
