package com.techvg.hrms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A EmployeeLeaveAccount.
 */
@Entity
@Table(name = "employee_leave_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE employee_leave_account SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class EmployeeLeaveAccount extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "carried_leaves")
    private Long carriedLeaves;

    @Column(name = "credited_leaves")
    private Long creditedLeaves;

    @Column(name = "date")
    private Instant date;

    @Column(name = "balance")
    private Long balance;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    @ManyToOne
    private LeaveType leaveType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "designation", "department", "branch", "region" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeLeaveAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarriedLeaves() {
        return this.carriedLeaves;
    }

    public EmployeeLeaveAccount carriedLeaves(Long carriedLeaves) {
        this.setCarriedLeaves(carriedLeaves);
        return this;
    }

    public void setCarriedLeaves(Long carriedLeaves) {
        this.carriedLeaves = carriedLeaves;
    }

    public Long getCreditedLeaves() {
        return this.creditedLeaves;
    }

    public EmployeeLeaveAccount creditedLeaves(Long creditedLeaves) {
        this.setCreditedLeaves(creditedLeaves);
        return this;
    }

    public void setCreditedLeaves(Long creditedLeaves) {
        this.creditedLeaves = creditedLeaves;
    }

    public Instant getDate() {
        return this.date;
    }

    public EmployeeLeaveAccount date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getBalance() {
        return this.balance;
    }

    public EmployeeLeaveAccount balance(Long balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public EmployeeLeaveAccount companyId(Long companyId) {
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

    public EmployeeLeaveAccount status(String status) {
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
    //    public EmployeeLeaveAccount lastModified(Instant lastModified) {
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
    //    public EmployeeLeaveAccount lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public LeaveType getLeaveType() {
        return this.leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public EmployeeLeaveAccount leaveType(LeaveType leaveType) {
        this.setLeaveType(leaveType);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeLeaveAccount employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeLeaveAccount)) {
            return false;
        }
        return id != null && id.equals(((EmployeeLeaveAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeLeaveAccount{" +
            "id=" + getId() +
            ", carriedLeaves=" + getCarriedLeaves() +
            ", creditedLeaves=" + getCreditedLeaves() +
            ", date='" + getDate() + "'" +
            ", balance=" + getBalance() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
