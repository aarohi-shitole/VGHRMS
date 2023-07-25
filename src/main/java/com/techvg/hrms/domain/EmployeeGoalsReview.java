package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A EmployeeGoalsReview.
 */
@Entity
@Table(name = "employee_goals_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE employee_goals_review SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class EmployeeGoalsReview extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "goal_description")
    private String goalDescription;

    @Column(name = "goal_category")
    private String goalCategory;

    @Column(name = "goaltype")
    private String goaltype;

    @Column(name = "goal_set_by")
    private String goalSetBy;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "appraisal_review_id")
    private Long appraisalReviewId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeGoalsReview id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalDescription() {
        return this.goalDescription;
    }

    public EmployeeGoalsReview goalDescription(String goalDescription) {
        this.setGoalDescription(goalDescription);
        return this;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public String getGoalCategory() {
        return this.goalCategory;
    }

    public EmployeeGoalsReview goalCategory(String goalCategory) {
        this.setGoalCategory(goalCategory);
        return this;
    }

    public void setGoalCategory(String goalCategory) {
        this.goalCategory = goalCategory;
    }

    public String getGoaltype() {
        return this.goaltype;
    }

    public EmployeeGoalsReview goaltype(String goaltype) {
        this.setGoaltype(goaltype);
        return this;
    }

    public void setGoaltype(String goaltype) {
        this.goaltype = goaltype;
    }

    public String getGoalSetBy() {
        return this.goalSetBy;
    }

    public EmployeeGoalsReview goalSetBy(String goalSetBy) {
        this.setGoalSetBy(goalSetBy);
        return this;
    }

    public void setGoalSetBy(String goalSetBy) {
        this.goalSetBy = goalSetBy;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public EmployeeGoalsReview employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getAppraisalReviewId() {
        return this.appraisalReviewId;
    }

    public EmployeeGoalsReview appraisalReviewId(Long appraisalReviewId) {
        this.setAppraisalReviewId(appraisalReviewId);
        return this;
    }

    public void setAppraisalReviewId(Long appraisalReviewId) {
        this.appraisalReviewId = appraisalReviewId;
    }

    public String getStatus() {
        return this.status;
    }

    public EmployeeGoalsReview status(String status) {
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
//    public EmployeeGoalsReview companyId(Long companyId) {
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
//    public EmployeeGoalsReview lastModified(Instant lastModified) {
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
//    public EmployeeGoalsReview lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeGoalsReview)) {
            return false;
        }
        return id != null && id.equals(((EmployeeGoalsReview) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeGoalsReview{" +
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
