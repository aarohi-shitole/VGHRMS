package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A WorkExperience.
 */
@Entity
@Table(name = "work_experience")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE work_experience SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class WorkExperience extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "employee_id")
    private Long employeeId;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "job_desc")
    private String jobDesc;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkExperience id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public WorkExperience jobTitle(String jobTitle) {
        this.setJobTitle(jobTitle);
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public WorkExperience companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public WorkExperience startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public WorkExperience endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public WorkExperience addressId(Long addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public WorkExperience employeeId(Long employeeId) {
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
    //    public WorkExperience companyId(Long companyId) {
    //        this.setCompanyId(companyId);
    //        return this;
    //    }
    //
    //    public void setCompanyId(Long companyId) {
    //        this.companyId = companyId;
    //    }

    public String getJobDesc() {
        return this.jobDesc;
    }

    public WorkExperience jobDesc(String jobDesc) {
        this.setJobDesc(jobDesc);
        return this;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getStatus() {
        return this.status;
    }

    public WorkExperience status(String status) {
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
    //    public WorkExperience lastModified(Instant lastModified) {
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
    //    public WorkExperience lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof WorkExperience)) {
            return false;
        }
        return id != null && id.equals(((WorkExperience) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkExperience{" +
            "id=" + getId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", addressId=" + getAddressId() +
            ", employeeId=" + getEmployeeId() +
            ", jobDesc='" + getJobDesc() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
