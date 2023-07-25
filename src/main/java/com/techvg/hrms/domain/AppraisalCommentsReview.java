package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A AppraisalCommentsReview.
 */
@Entity
@Table(name = "appraisal_comments_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE appraisal_comments_review SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class AppraisalCommentsReview extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "comment_type")
    private String commentType;

    @Column(name = "appraisal_review_id")
    private Long appraisalReviewId;

    @Column(name = "ref_form_name")
    private String refFormName;

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

    @ManyToOne
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppraisalCommentsReview id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return this.comment;
    }

    public AppraisalCommentsReview comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentType() {
        return this.commentType;
    }

    public AppraisalCommentsReview commentType(String commentType) {
        this.setCommentType(commentType);
        return this;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Long getAppraisalReviewId() {
        return this.appraisalReviewId;
    }

    public AppraisalCommentsReview appraisalReviewId(Long appraisalReviewId) {
        this.setAppraisalReviewId(appraisalReviewId);
        return this;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public String getRefFormName() {
        return this.refFormName;
    }

    public AppraisalCommentsReview refFormName(String refFormName) {
        this.setRefFormName(refFormName);
        return this;
    }

    public void setRefFormName(String refFormName) {
        this.refFormName = refFormName;
    }

    public String getStatus() {
        return this.status;
    }

    public AppraisalCommentsReview status(String status) {
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
//    public AppraisalCommentsReview companyId(Long companyId) {
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
//    public AppraisalCommentsReview lastModified(Instant lastModified) {
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
//    public AppraisalCommentsReview lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public AppraisalCommentsReview employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalCommentsReview)) {
            return false;
        }
        return id != null && id.equals(((AppraisalCommentsReview) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalCommentsReview{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", commentType='" + getCommentType() + "'" +
            ", appraisalReviewId=" + getAppraisalReviewId() +
            ", refFormName='" + getRefFormName() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
