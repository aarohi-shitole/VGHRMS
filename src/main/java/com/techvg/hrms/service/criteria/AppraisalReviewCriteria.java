package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.AppraisalReview} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.AppraisalReviewResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appraisal-reviews?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppraisalReviewCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reportingOfficer;

    private StringFilter appraisalStatus;
    
    private StringFilter roDesignation;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter employeId;

    private Boolean distinct;

    public AppraisalReviewCriteria() {}

    public AppraisalReviewCriteria(AppraisalReviewCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reportingOfficer = other.reportingOfficer == null ? null : other.reportingOfficer.copy();
        this.appraisalStatus = other.appraisalStatus == null ? null : other.appraisalStatus.copy();
        this.roDesignation = other.roDesignation == null ? null : other.roDesignation.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AppraisalReviewCriteria copy() {
        return new AppraisalReviewCriteria(this);
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

    public StringFilter getReportingOfficer() {
        return reportingOfficer;
    }

    public StringFilter reportingOfficer() {
        if (reportingOfficer == null) {
            reportingOfficer = new StringFilter();
        }
        return reportingOfficer;
    }

    public void setReportingOfficer(StringFilter reportingOfficer) {
        this.reportingOfficer = reportingOfficer;
    }
    
    public StringFilter getAppraisalStatus() {
        return appraisalStatus;
    }

    public StringFilter appraisalStatus() {
        if (appraisalStatus == null) {
            appraisalStatus = new StringFilter();
        }
        return appraisalStatus;
    }

    public void setAppraisalStatus(StringFilter appraisalStatus) {
        this.appraisalStatus = appraisalStatus;
    }

    public StringFilter getRoDesignation() {
        return roDesignation;
    }

    public StringFilter roDesignation() {
        if (roDesignation == null) {
            roDesignation = new StringFilter();
        }
        return roDesignation;
    }

    public void setRoDesignation(StringFilter roDesignation) {
        this.roDesignation = roDesignation;
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

    public LongFilter getEmployeId() {
        return employeId;
    }

    public LongFilter employeId() {
        if (employeId == null) {
            employeId = new LongFilter();
        }
        return employeId;
    }

    public void setEmployeId(LongFilter employeId) {
        this.employeId = employeId;
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
        final AppraisalReviewCriteria that = (AppraisalReviewCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(reportingOfficer, that.reportingOfficer) &&
            Objects.equals(appraisalStatus, that.appraisalStatus) &&
            Objects.equals(roDesignation, that.roDesignation) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(employeId, that.employeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportingOfficer, roDesignation, status,  employeId, companyId, appraisalStatus, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalReviewCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (reportingOfficer != null ? "reportingOfficer=" + reportingOfficer + ", " : "") +
            (appraisalStatus != null ? "appraisalStatus=" + appraisalStatus + ", " : "") +
            (roDesignation != null ? "roDesignation=" + roDesignation + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (employeId != null ? "employeId=" + employeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
