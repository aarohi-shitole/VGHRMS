package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.performance.domain.EmployeeGoalsReview} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeGoalsReviewDTO implements Serializable {

    private Long id;

    private String goalDescription;

    private String goalCategory;

    private String goaltype;

    private String goalSetBy;

    private Long employeeId;

    private Long appraisalReviewId;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public String getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(String goalCategory) {
        this.goalCategory = goalCategory;
    }

    public String getGoaltype() {
        return goaltype;
    }

    public void setGoaltype(String goaltype) {
        this.goaltype = goaltype;
    }

    public String getGoalSetBy() {
        return goalSetBy;
    }

    public void setGoalSetBy(String goalSetBy) {
        this.goalSetBy = goalSetBy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeGoalsReviewDTO)) {
            return false;
        }

        EmployeeGoalsReviewDTO employeeGoalsReviewDTO = (EmployeeGoalsReviewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeGoalsReviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeGoalsReviewDTO{" +
            "id=" + getId() +
            ", goalDescription='" + getGoalDescription() + "'" +
            ", goalCategory='" + getGoalCategory() + "'" +
            ", goaltype='" + getGoaltype() + "'" +
            ", goalSetBy='" + getGoalSetBy() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", appraisalReviewId=" + getAppraisalReviewId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
