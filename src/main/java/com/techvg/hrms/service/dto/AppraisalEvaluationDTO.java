package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.AppraisalEvaluation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalEvaluationDTO implements Serializable {

    private Long id;

    private Boolean answerFlag;

    private String description;

    private Long employeeId;

    private Long appraisalReviewId;

    private Long availablePoints;

    private Long scoredPoints;

    private String remark;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private AppraisalEvaluationParameterDTO appraisalEvaluationParameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(Boolean answerFlag) {
        this.answerFlag = answerFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAppraisalReviewId() {
        return appraisalReviewId;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public Long getAvailablePoints() {
        return availablePoints;
    }

    public void setAvailablePoints(Long availablePoints) {
        this.availablePoints = availablePoints;
    }

    public Long getScoredPoints() {
        return scoredPoints;
    }

    public void setScoredPoints(Long scoredPoints) {
        this.scoredPoints = scoredPoints;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public AppraisalEvaluationParameterDTO getAppraisalEvaluationParameter() {
        return appraisalEvaluationParameter;
    }

    public void setAppraisalEvaluationParameter(AppraisalEvaluationParameterDTO appraisalEvaluationParameter) {
        this.appraisalEvaluationParameter = appraisalEvaluationParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalEvaluationDTO)) {
            return false;
        }

        AppraisalEvaluationDTO appraisalEvaluationDTO = (AppraisalEvaluationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appraisalEvaluationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalEvaluationDTO{" +
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
            ", appraisalEvaluationParameter=" + getAppraisalEvaluationParameter() +
            "}";
    }
}
