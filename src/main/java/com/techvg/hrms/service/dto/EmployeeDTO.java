package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Employee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO implements Serializable {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    @NotNull
    private String empUniqueId;

    private Instant joindate;

    private Instant retirementDate;

    private Long companyId;

    private String status;

    private String emailId;

    private Instant lastModified;

    private String lastModifiedBy;

    private Long employmentTypeId;

    private Long reportingEmpId;

    private DesignationDTO designation;

    private DepartmentDTO department;

    private BranchDTO branch;

    private RegionDTO region;

    private List<AddressDTO> addressList;

    private List<ContactsDTO> contactList;

    private Instant employmentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmpUniqueId() {
        return empUniqueId;
    }

    public void setEmpUniqueId(String empUniqueId) {
        this.empUniqueId = empUniqueId;
    }

    public Instant getJoindate() {
        return joindate;
    }

    public void setJoindate(Instant joindate) {
        this.joindate = joindate;
    }

    public Instant getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(Instant retirementDate) {
        this.retirementDate = retirementDate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Long getEmploymentTypeId() {
        return employmentTypeId;
    }

    public void setEmploymentTypeId(Long employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    public Long getReportingEmpId() {
        return reportingEmpId;
    }

    public void setReportingEmpId(Long reportingEmpId) {
        this.reportingEmpId = reportingEmpId;
    }

    public DesignationDTO getDesignation() {
        return designation;
    }

    public void setDesignation(DesignationDTO designation) {
        this.designation = designation;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public RegionDTO getRegion() {
        return region;
    }

    public void setRegion(RegionDTO region) {
        this.region = region;
    }

    public Instant getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Instant employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
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
            ", emailId='" + getEmailId() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", employmentTypeId=" + getEmploymentTypeId() +
            ", reportingEmpId=" + getReportingEmpId() +
            ", designation=" + getDesignation() +
            ", department=" + getDepartment() +
            ", branch=" + getBranch() +
            ", region=" + getRegion() +
            ", employmentDate='" + getEmploymentDate() + "'" +
            "}";
    }

    public List<AddressDTO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressDTO> addressList) {
        this.addressList = addressList;
    }

    public List<ContactsDTO> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactsDTO> addressList) {
        this.contactList = addressList;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
