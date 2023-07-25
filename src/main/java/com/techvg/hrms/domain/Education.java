package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Education.
 */
@Entity
@Table(name = "education")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE education SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Education extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "institution")
    private String institution;

    @Column(name = "subject")
    private String subject;

    @Column(name = "start_year")
    private Instant startYear;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "education_type")
    private String educationType;

    @Column(name = "grade")
    private String grade;

    @Column(name = "description")
    private String description;

    @Column(name = "employee_id")
    private Long employeeId;

    //    @Column(name = "company_id")
    //    private Long companyId;

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

    public Education id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitution() {
        return this.institution;
    }

    public Education institution(String institution) {
        this.setInstitution(institution);
        return this;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSubject() {
        return this.subject;
    }

    public Education subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getStartYear() {
        return this.startYear;
    }

    public Education startYear(Instant startYear) {
        this.setStartYear(startYear);
        return this;
    }

    public void setStartYear(Instant startYear) {
        this.startYear = startYear;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Education endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getEducationType() {
        return this.educationType;
    }

    public Education educationType(String educationType) {
        this.setEducationType(educationType);
        return this;
    }

    public void setEducationType(String educationType) {
        this.educationType = educationType;
    }

    public String getGrade() {
        return this.grade;
    }

    public Education grade(String grade) {
        this.setGrade(grade);
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return this.description;
    }

    public Education description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Education employeeId(Long employeeId) {
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
    //    public Education companyId(Long companyId) {
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

    public Education status(String status) {
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
    //    public Education lastModified(Instant lastModified) {
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
    //    public Education lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Education)) {
            return false;
        }
        return id != null && id.equals(((Education) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", institution='" + getInstitution() + "'" +
            ", subject='" + getSubject() + "'" +
            ", startYear='" + getStartYear() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", educationType='" + getEducationType() + "'" +
            ", grade='" + getGrade() + "'" +
            ", description='" + getDescription() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
