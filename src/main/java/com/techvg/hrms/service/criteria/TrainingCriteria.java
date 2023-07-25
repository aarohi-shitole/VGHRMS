package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Training} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter trainingCost;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter description;

    private StringFilter trainingStatus;

    private StringFilter status;

    private LongFilter trainerId;

    private LongFilter trainingTypeId;

    private LongFilter departmentId;

    private LongFilter employeeId;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public TrainingCriteria() {}

    public TrainingCriteria(TrainingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.trainingCost = other.trainingCost == null ? null : other.trainingCost.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.trainingStatus = other.trainingStatus == null ? null : other.trainingStatus.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.trainerId = other.trainerId == null ? null : other.trainerId.copy();
        this.trainingTypeId = other.trainingTypeId == null ? null : other.trainingTypeId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TrainingCriteria copy() {
        return new TrainingCriteria(this);
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

    public DoubleFilter getTrainingCost() {
        return trainingCost;
    }

    public DoubleFilter trainingCost() {
        if (trainingCost == null) {
            trainingCost = new DoubleFilter();
        }
        return trainingCost;
    }

    public void setTrainingCost(DoubleFilter trainingCost) {
        this.trainingCost = trainingCost;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
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

    public StringFilter getTrainingStatus() {
        return trainingStatus;
    }

    public StringFilter trainingStatus() {
        if (trainingStatus == null) {
            trainingStatus = new StringFilter();
        }
        return trainingStatus;
    }

    public void setTrainingStatus(StringFilter trainingStatus) {
        this.trainingStatus = trainingStatus;
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

    public LongFilter getTrainerId() {
        return trainerId;
    }

    public LongFilter trainerId() {
        if (trainerId == null) {
            trainerId = new LongFilter();
        }
        return trainerId;
    }

    public void setTrainerId(LongFilter trainerId) {
        this.trainerId = trainerId;
    }

    public LongFilter getTrainingTypeId() {
        return trainingTypeId;
    }

    public LongFilter trainingTypeId() {
        if (trainingTypeId == null) {
            trainingTypeId = new LongFilter();
        }
        return trainingTypeId;
    }

    public void setTrainingTypeId(LongFilter trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
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
        final TrainingCriteria that = (TrainingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(trainingCost, that.trainingCost) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(description, that.description) &&
            Objects.equals(trainingStatus, that.trainingStatus) &&
            Objects.equals(status, that.status) &&
            Objects.equals(trainerId, that.trainerId) &&
            Objects.equals(trainingTypeId, that.trainingTypeId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(employeeId, that.employeeId) &&
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
            trainingCost,
            startDate,
            endDate,
            description,
            trainingStatus,
            status,
            trainerId,
            trainingTypeId,
            departmentId,
            employeeId,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (trainingCost != null ? "trainingCost=" + trainingCost + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (trainingStatus != null ? "trainingStatus=" + trainingStatus + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (trainerId != null ? "trainerId=" + trainerId + ", " : "") +
            (trainingTypeId != null ? "trainingTypeId=" + trainingTypeId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
