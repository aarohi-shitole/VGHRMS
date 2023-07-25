package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Resignation.
 */
@Entity
@Table(name = "resignation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE resignation SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Resignation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "resign_date")
    private Instant resignDate;

    @Column(name = "notice_period_indays")
    private Long noticePeriodIndays;

    @Column(name = "reason")
    private String reason;

    @Column(name = "resign_status")
    private String resignStatus;

    @Column(name = "last_working_day")
    private Instant lastWorkingDay;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_id")
    private Long employeeId;

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

    public Resignation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return this.empName;
    }

    public Resignation empName(String empName) {
        this.setEmpName(empName);
        return this;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Instant getResignDate() {
        return this.resignDate;
    }

    public Resignation resignDate(Instant resignDate) {
        this.setResignDate(resignDate);
        return this;
    }

    public void setResignDate(Instant resignDate) {
        this.resignDate = resignDate;
    }

    public Long getNoticePeriodIndays() {
        return this.noticePeriodIndays;
    }

    public Resignation noticePeriodIndays(Long noticePeriodIndays) {
        this.setNoticePeriodIndays(noticePeriodIndays);
        return this;
    }

    public void setNoticePeriodIndays(Long noticePeriodIndays) {
        this.noticePeriodIndays = noticePeriodIndays;
    }

    public String getReason() {
        return this.reason;
    }

    public Resignation reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResignStatus() {
        return this.resignStatus;
    }

    public Resignation resignStatus(String resignStatus) {
        this.setResignStatus(resignStatus);
        return this;
    }

    public void setResignStatus(String resignStatus) {
        this.resignStatus = resignStatus;
    }

    public Instant getLastWorkingDay() {
        return this.lastWorkingDay;
    }

    public Resignation lastWorkingDay(Instant lastWorkingDay) {
        this.setLastWorkingDay(lastWorkingDay);
        return this;
    }

    public void setLastWorkingDay(Instant lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public Resignation departmentId(Long departmentId) {
        this.setDepartmentId(departmentId);
        return this;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Resignation employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return this.status;
    }

    public Resignation status(String status) {
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
//    public Resignation companyId(Long companyId) {
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
//    public Resignation lastModified(Instant lastModified) {
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
//    public Resignation lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Resignation)) {
            return false;
        }
        return id != null && id.equals(((Resignation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resignation{" +
            "id=" + getId() +
            ", empName='" + getEmpName() + "'" +
            ", resignDate='" + getResignDate() + "'" +
            ", noticePeriodIndays=" + getNoticePeriodIndays() +
            ", reason='" + getReason() + "'" +
            ", resignStatus='" + getResignStatus() + "'" +
            ", lastWorkingDay='" + getLastWorkingDay() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", employeeId=" + getEmployeeId() +
            ", status='" + getStatus() + "'" +
 //           ", companyId=" + getCompanyId() +
//            ", lastModified='" + getLastModified() + "'" +
//            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
