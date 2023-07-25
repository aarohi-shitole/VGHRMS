package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PersonalDetails.
 */
@Entity
@Table(name = "personal_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE personal_details SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PersonalDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "religion")
    private String religion;

    @Column(name = "employee_id")
    private Long employeeId;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

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

    public PersonalDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephoneNo() {
        return this.telephoneNo;
    }

    public PersonalDetails telephoneNo(String telephoneNo) {
        this.setTelephoneNo(telephoneNo);
        return this;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getNationality() {
        return this.nationality;
    }

    public PersonalDetails nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public PersonalDetails maritalStatus(String maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getReligion() {
        return this.religion;
    }

    public PersonalDetails religion(String religion) {
        this.setReligion(religion);
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public PersonalDetails employeeId(Long employeeId) {
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
    //    public PersonalDetails companyId(Long companyId) {
    //        this.setCompanyId(companyId);
    //        return this;
    //    }
    //
    //    public void setCompanyId(Long companyId) {
    //        this.companyId = companyId;
    //    }

    public String getBloodGroup() {
        return this.bloodGroup;
    }

    public PersonalDetails bloodGroup(String bloodGroup) {
        this.setBloodGroup(bloodGroup);
        return this;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public PersonalDetails dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return this.status;
    }

    public PersonalDetails status(String status) {
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
    //    public PersonalDetails lastModified(Instant lastModified) {
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
    //    public PersonalDetails lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof PersonalDetails)) {
            return false;
        }
        return id != null && id.equals(((PersonalDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetails{" +
            "id=" + getId() +
            ", telephoneNo='" + getTelephoneNo() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", religion='" + getReligion() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
