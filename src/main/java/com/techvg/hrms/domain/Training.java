package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE training SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Training extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "training_cost")
    private Double trainingCost;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "training_status")
    private String trainingStatus;

    @Column(name = "status")
    private String status;

    @Column(name = "trainer_id")
    private Long trainerId;

    @Column(name = "training_type_id")
    private Long trainingTypeId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_id")
    private Long employeeId;

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

    public Training id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTrainingCost() {
        return this.trainingCost;
    }

    public Training trainingCost(Double trainingCost) {
        this.setTrainingCost(trainingCost);
        return this;
    }

    public void setTrainingCost(Double trainingCost) {
        this.trainingCost = trainingCost;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Training startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Training endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Training description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrainingStatus() {
        return this.trainingStatus;
    }

    public Training trainingStatus(String trainingStatus) {
        this.setTrainingStatus(trainingStatus);
        return this;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public String getStatus() {
        return this.status;
    }

    public Training status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTrainerId() {
        return this.trainerId;
    }

    public Training trainerId(Long trainerId) {
        this.setTrainerId(trainerId);
        return this;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getTrainingTypeId() {
        return this.trainingTypeId;
    }

    public Training trainingTypeId(Long trainingTypeId) {
        this.setTrainingTypeId(trainingTypeId);
        return this;
    }

    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public Training departmentId(Long departmentId) {
        this.setDepartmentId(departmentId);
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Training employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

//    public Long getCompanyId() {
//        return this.companyId;
//    }
//
//    public Training companyId(Long companyId) {
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
//    public Training lastModified(Instant lastModified) {
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
//    public Training lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", trainingCost=" + getTrainingCost() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", trainingStatus='" + getTrainingStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", trainerId=" + getTrainerId() +
            ", trainingTypeId=" + getTrainingTypeId() +
            ", departmentId=" + getDepartmentId() +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
