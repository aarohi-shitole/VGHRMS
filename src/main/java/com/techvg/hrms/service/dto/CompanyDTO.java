package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Company} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyDTO implements Serializable {

    private Long id;

    private String companyName;

    private String contactPerson;

    private String postalCode;

    private String email;

    private String phoneNumber;

    private String mobileNumber;

    private String websiteUrl;

    private String fax;

    private String regNumber;

    private Instant payoutDate;

    private Long payslipDate;

    private Long retirementAge;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    private String leaveSettingLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Instant getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(Instant payoutDate) {
        this.payoutDate = payoutDate;
    }

    //	public Instant getPayslipDate() {
    //		return payslipDate;
    //	}
    //
    //	public void setPayslipDate(Instant payslipDate) {
    //		this.payslipDate = payslipDate;
    //	}
    public Long getPayslipDate() {
        return payslipDate;
    }

    public void setPayslipDate(Long payslipDate) {
        this.payslipDate = payslipDate;
    }

    public Long getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(Long retirementAge) {
        this.retirementAge = retirementAge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLeaveSettingLevel() {
        return leaveSettingLevel;
    }

    public void setLeaveSettingLevel(String leaveSettingLevel) {
        this.leaveSettingLevel = leaveSettingLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
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
