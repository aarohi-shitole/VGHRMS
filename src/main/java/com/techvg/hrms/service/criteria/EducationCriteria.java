package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Education} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EducationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /educations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EducationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter institution;

    private StringFilter subject;

    private InstantFilter startYear;

    private InstantFilter endDate;

    private StringFilter educationType;

    private StringFilter grade;

    private StringFilter description;

    private LongFilter employeeId;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public EducationCriteria() {}

    public EducationCriteria(EducationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.institution = other.institution == null ? null : other.institution.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.startYear = other.startYear == null ? null : other.startYear.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.educationType = other.educationType == null ? null : other.educationType.copy();
        this.grade = other.grade == null ? null : other.grade.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EducationCriteria copy() {
        return new EducationCriteria(this);
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

    public StringFilter getInstitution() {
        return institution;
    }

    public StringFilter institution() {
        if (institution == null) {
            institution = new StringFilter();
        }
        return institution;
    }

    public void setInstitution(StringFilter institution) {
        this.institution = institution;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public StringFilter subject() {
        if (subject == null) {
            subject = new StringFilter();
        }
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public InstantFilter getStartYear() {
        return startYear;
    }

    public InstantFilter startYear() {
        if (startYear == null) {
            startYear = new InstantFilter();
        }
        return startYear;
    }

    public void setStartYear(InstantFilter startYear) {
        this.startYear = startYear;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getEducationType() {
        return educationType;
    }

    public StringFilter educationType() {
        if (educationType == null) {
            educationType = new StringFilter();
        }
        return educationType;
    }

    public void setEducationType(StringFilter educationType) {
        this.educationType = educationType;
    }

    public StringFilter getGrade() {
        return grade;
    }

    public StringFilter grade() {
        if (grade == null) {
            grade = new StringFilter();
        }
        return grade;
    }

    public void setGrade(StringFilter grade) {
        this.grade = grade;
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
        final EducationCriteria that = (EducationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(institution, that.institution) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(startYear, that.startYear) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(educationType, that.educationType) &&
            Objects.equals(grade, that.grade) &&
            Objects.equals(description, that.description) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            institution,
            subject,
            startYear,
            endDate,
            educationType,
            grade,
            description,
            employeeId,
            companyId,
            status,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (institution != null ? "institution=" + institution + ", " : "") +
            (subject != null ? "subject=" + subject + ", " : "") +
            (startYear != null ? "startYear=" + startYear + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (educationType != null ? "educationType=" + educationType + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
