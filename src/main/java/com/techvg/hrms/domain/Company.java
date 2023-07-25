package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE company SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "fax")
    private String fax;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "payout_date")
    private Instant payoutDate;

    @Column(name = "payslip_date")
    private Long payslipDate;

    @Column(name = "retirement_age")
    private Long retirementAge;

    @Column(name = "status")
    private String status;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "leave_setting_level")
    private String leaveSettingLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Company companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public Company contactPerson(String contactPerson) {
        this.setContactPerson(contactPerson);
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Company postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return this.email;
    }

    public Company email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Company phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public Company mobileNumber(String mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWebsiteUrl() {
        return this.websiteUrl;
    }

    public Company websiteUrl(String websiteUrl) {
        this.setWebsiteUrl(websiteUrl);
        return this;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getFax() {
        return this.fax;
    }

    public Company fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRegNumber() {
        return this.regNumber;
    }

    public Company regNumber(String regNumber) {
        this.setRegNumber(regNumber);
        return this;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Instant getPayoutDate() {
        return payoutDate;
    }

    public Company payoutDate(Instant payoutDate) {
        this.setPayoutDate(payoutDate);
        return this;
    }

    public void setPayoutDate(Instant payoutDate) {
        this.payoutDate = payoutDate;
    }

    public String getStatus() {
        return this.status;
    }

    public Long getPayslipDate() {
        return payslipDate;
    }

    public Company payslipDate(Long payslipDate) {
        this.setPayslipDate(payslipDate);
        return this;
    }

    public void setPayslipDate(Long payslipDate) {
        this.payslipDate = payslipDate;
    }

    public Long getRetirementAge() {
        return retirementAge;
    }

    public Company retirementAge(Long retirementAge) {
        this.setRetirementAge(retirementAge);
        return this;
    }

    public void setRetirementAge(Long retirementAge) {
        this.retirementAge = retirementAge;
    }

    public Company status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Company lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Company lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLeaveSettingLevel() {
        return this.leaveSettingLevel;
    }

    public Company leaveSettingLevel(String leaveSettingLevel) {
        this.setLeaveSettingLevel(leaveSettingLevel);
        return this;
    }

    public void setLeaveSettingLevel(String leaveSettingLevel) {
        this.leaveSettingLevel = leaveSettingLevel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", websiteUrl='" + getWebsiteUrl() + "'" +
            ", fax='" + getFax() + "'" +
            ", regNumber='" + getRegNumber() + "'" +
            ", payoutDate='" + getPayoutDate() + "'" +
            ", payslipDate='" + getPayslipDate() + "'" +
            ", retirementAge='" + getRetirementAge() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", leaveSettingLevel='" + getLeaveSettingLevel() + "'" +
            "}";
    }
}
