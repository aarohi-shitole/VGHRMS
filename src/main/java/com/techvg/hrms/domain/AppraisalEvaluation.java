package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A AppraisalEvaluation.
 */
@Entity
@Table(name = "appraisal_evaluation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE appraisal_evaluation SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class AppraisalEvaluation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "answer_flag")
    private Boolean answerFlag;

    @Column(name = "description")
    private String description;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "appraisal_review_id")
    private Long appraisalReviewId;

    @Column(name = "available_points")
    private Long availablePoints;

    @Column(name = "scored_points")
    private Long scoredPoints;

    @Column(name = "remark")
    private String remark;

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
    private AppraisalEvaluationParameter appraisalEvaluationParameter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppraisalEvaluation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAnswerFlag() {
        return this.answerFlag;
    }

    public AppraisalEvaluation answerFlag(Boolean answerFlag) {
        this.setAnswerFlag(answerFlag);
        return this;
    }

    public void setAnswerFlag(Boolean answerFlag) {
        this.answerFlag = answerFlag;
    }

    public String getDescription() {
        return this.description;
    }

    public AppraisalEvaluation description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public AppraisalEvaluation employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAppraisalReviewId() {
        return this.appraisalReviewId;
    }

    public AppraisalEvaluation appraisalReviewId(Long appraisalReviewId) {
        this.setAppraisalReviewId(appraisalReviewId);
        return this;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public Long getAvailablePoints() {
        return this.availablePoints;
    }

    public AppraisalEvaluation availablePoints(Long availablePoints) {
        this.setAvailablePoints(availablePoints);
        return this;
    }

    public void setAvailablePoints(Long availablePoints) {
        this.availablePoints = availablePoints;
    }

    public Long getScoredPoints() {
        return this.scoredPoints;
    }

    public AppraisalEvaluation scoredPoints(Long scoredPoints) {
        this.setScoredPoints(scoredPoints);
        return this;
    }

    public void setScoredPoints(Long scoredPoints) {
        this.scoredPoints = scoredPoints;
    }

    public String getRemark() {
        return this.remark;
    }

    public AppraisalEvaluation remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return this.status;
    }

    public AppraisalEvaluation status(String status) {
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
//    public AppraisalEvaluation companyId(Long companyId) {
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
//    public AppraisalEvaluation lastModified(Instant lastModified) {
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
//    public AppraisalEvaluation lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    public AppraisalEvaluationParameter getAppraisalEvaluationParameter() {
        return this.appraisalEvaluationParameter;
    }

    public void setAppraisalEvaluationParameter(AppraisalEvaluationParameter appraisalEvaluationParameter) {
        this.appraisalEvaluationParameter = appraisalEvaluationParameter;
    }

    public AppraisalEvaluation appraisalEvaluationParameter(AppraisalEvaluationParameter appraisalEvaluationParameter) {
        this.setAppraisalEvaluationParameter(appraisalEvaluationParameter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalEvaluation)) {
            return false;
        }
        return id != null && id.equals(((AppraisalEvaluation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalEvaluation{" +
            "id=" + getId() +
            ", answerFlag='" + getAnswerFlag() + "'" +
            ", description='" + getDescription() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", appraisalReviewId=" + getAppraisalReviewId() +
            ", availablePoints=" + getAvailablePoints() +
            ", scoredPoints=" + getScoredPoints() +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
