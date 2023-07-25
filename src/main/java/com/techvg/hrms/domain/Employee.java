package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE employee SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Employee extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "emp_unique_id", nullable = false)
    private String empUniqueId;

    @Column(name = "joindate")
    private Instant joindate;

    @Column(name = "retirement_date")
    private Instant retirementDate;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    @Column(name = "email_id")
    private String emailId;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    @Column(name = "employment_type_id")
    private Long employmentTypeId;

    @Column(name = "reporting_emp_id")
    private Long reportingEmpId;

    @Column(name = "employment_date")
    private Instant employmentDate;

    @ManyToOne
    private Designation designation;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Employee middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public Employee gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmpUniqueId() {
        return this.empUniqueId;
    }

    public Employee empUniqueId(String empUniqueId) {
        this.setEmpUniqueId(empUniqueId);
        return this;
    }

    public void setEmpUniqueId(String empUniqueId) {
        this.empUniqueId = empUniqueId;
    }

    public Instant getJoindate() {
        return this.joindate;
    }

    public Employee joindate(Instant joindate) {
        this.setJoindate(joindate);
        return this;
    }

    public void setJoindate(Instant joindate) {
        this.joindate = joindate;
    }

    public Instant getRetirementDate() {
        return this.retirementDate;
    }

    public Employee retirementDate(Instant retirementDate) {
        this.setRetirementDate(retirementDate);
        return this;
    }

    public void setRetirementDate(Instant retirementDate) {
        this.retirementDate = retirementDate;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public Employee companyId(Long companyId) {
    //        this.setCompanyId(companyId);
    //        return this;
    //    }
    //
    //    public void setCompanyId(Long companyId) {
    //        this.companyId = companyId;
    //    }

    public String getStatus() {
        return this.status;
    }

    public Employee status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    public Instant getLastModified() {
    //        return this.lastModified;
    //    }
    //
    //    public Employee lastModified(Instant lastModified) {
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
    //    public Employee lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public Long getEmploymentTypeId() {
        return this.employmentTypeId;
    }

    public Employee employmentTypeId(Long employmentTypeId) {
        this.setEmploymentTypeId(employmentTypeId);
        return this;
    }

    public void setEmploymentTypeId(Long employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    public Long getReportingEmpId() {
        return this.reportingEmpId;
    }

    public Employee reportingEmpId(Long reportingEmpId) {
        this.setReportingEmpId(reportingEmpId);
        return this;
    }

    public void setReportingEmpId(Long reportingEmpId) {
        this.reportingEmpId = reportingEmpId;
    }

    public Designation getDesignation() {
        return this.designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Employee designation(Designation designation) {
        this.setDesignation(designation);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Employee branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Employee region(Region region) {
        this.setRegion(region);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getEmailId() {
        return emailId;
    }

    public Employee emailId(String emailId) {
        this.setEmailId(emailId);
        return this;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Instant getEmploymentDate() {
        return this.joindate;
    }

    public Employee employmentDate(Instant employmentDate) {
        this.setEmploymentDate(employmentDate);
        return this;
    }

    public void setEmploymentDate(Instant employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", empUniqueId='" + getEmpUniqueId() + "'" +
            ", joindate='" + getJoindate() + "'" +
            ", retirementDate='" + getRetirementDate() + "'" +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", status='" + getStatus() + "'" +
            ", emailId='" + getEmailId() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", employmentTypeId=" + getEmploymentTypeId() +
            ", reportingEmpId=" + getReportingEmpId() +
            ", employmentDate='" + getEmploymentDate() + "'" +
            "}";
    }
}
