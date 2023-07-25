package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.EmployeeGoalsReview} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.EmployeeGoalsReviewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-goals-reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeGoalsReviewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter goalDescription;

    private StringFilter goalCategory;

    private StringFilter goaltype;

    private StringFilter goalSetBy;

    private LongFilter employeeId;

    private LongFilter appraisalReviewId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public EmployeeGoalsReviewCriteria() {}

    public EmployeeGoalsReviewCriteria(EmployeeGoalsReviewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.goalDescription = other.goalDescription == null ? null : other.goalDescription.copy();
        this.goalCategory = other.goalCategory == null ? null : other.goalCategory.copy();
        this.goaltype = other.goaltype == null ? null : other.goaltype.copy();
        this.goalSetBy = other.goalSetBy == null ? null : other.goalSetBy.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.appraisalReviewId = other.appraisalReviewId == null ? null : other.appraisalReviewId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeGoalsReviewCriteria copy() {
        return new EmployeeGoalsReviewCriteria(this);
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

    public StringFilter getGoalDescription() {
        return goalDescription;
    }

    public StringFilter goalDescription() {
        if (goalDescription == null) {
            goalDescription = new StringFilter();
        }
        return goalDescription;
    }

    public void setGoalDescription(StringFilter goalDescription) {
        this.goalDescription = goalDescription;
    }

    public StringFilter getGoalCategory() {
        return goalCategory;
    }

    public StringFilter goalCategory() {
        if (goalCategory == null) {
            goalCategory = new StringFilter();
        }
        return goalCategory;
    }

    public void setGoalCategory(StringFilter goalCategory) {
        this.goalCategory = goalCategory;
    }

    public StringFilter getGoaltype() {
        return goaltype;
    }

    public StringFilter goaltype() {
        if (goaltype == null) {
            goaltype = new StringFilter();
        }
        return goaltype;
    }

    public void setGoaltype(StringFilter goaltype) {
        this.goaltype = goaltype;
    }

    public StringFilter getGoalSetBy() {
        return goalSetBy;
    }

    public StringFilter goalSetBy() {
        if (goalSetBy == null) {
            goalSetBy = new StringFilter();
        }
        return goalSetBy;
    }

    public void setGoalSetBy(StringFilter goalSetBy) {
        this.goalSetBy = goalSetBy;
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
        final EmployeeGoalsReviewCriteria that = (EmployeeGoalsReviewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(goalDescription, that.goalDescription) &&
            Objects.equals(goalCategory, that.goalCategory) &&
            Objects.equals(goaltype, that.goaltype) &&
            Objects.equals(goalSetBy, that.goalSetBy) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(appraisalReviewId, that.appraisalReviewId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            goalDescription,
            goalCategory,
            goaltype,
            goalSetBy,
            employeeId,
            appraisalReviewId,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeGoalsReviewCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (goalDescription != null ? "goalDescription=" + goalDescription + ", " : "") +
            (goalCategory != null ? "goalCategory=" + goalCategory + ", " : "") +
            (goaltype != null ? "goaltype=" + goaltype + ", " : "") +
            (goalSetBy != null ? "goalSetBy=" + goalSetBy + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (appraisalReviewId != null ? "appraisalReviewId=" + appraisalReviewId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
