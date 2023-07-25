package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.EmployeeLeaveAccount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeLeaveAccountDTO implements Serializable {

    private Long id;

    private Long carriedLeaves;

    private Long creditedLeaves;

    private Instant date;

    private Long balance;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    private LeaveTypeDTO leaveType;

    private EmployeeDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarriedLeaves() {
        return carriedLeaves;
    }

    public void setCarriedLeaves(Long carriedLeaves) {
        this.carriedLeaves = carriedLeaves;
    }

    public Long getCreditedLeaves() {
        return creditedLeaves;
    }

    public void setCreditedLeaves(Long creditedLeaves) {
        this.creditedLeaves = creditedLeaves;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
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

    public LeaveTypeDTO getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeDTO leaveType) {
        this.leaveType = leaveType;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeLeaveAccountDTO)) {
            return false;
        }

        EmployeeLeaveAccountDTO employeeLeaveAccountDTO = (EmployeeLeaveAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeLeaveAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeLeaveAccountDTO{" +
            "id=" + getId() +
            ", carriedLeaves=" + getCarriedLeaves() +
            ", creditedLeaves=" + getCreditedLeaves() +
            ", date='" + getDate() + "'" +
            ", balance=" + getBalance() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", leaveType=" + getLeaveType() +
            ", employee=" + getEmployee() +
            "}";
    }
}
