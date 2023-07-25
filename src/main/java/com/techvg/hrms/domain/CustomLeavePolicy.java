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
 * A CustomLeavePolicy.
 */
@Entity
@Table(name = "custom_leave_policy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE custom_leave_policy SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class CustomLeavePolicy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "custom_policy_name")
    private String customPolicyName;

    @Column(name = "days")
    private Long days;

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
    @JsonIgnoreProperties(value = { "leaveType", "employmentType" }, allowSetters = true)
    private LeavePolicy leavePolicy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "designation", "department", "branch", "region" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomLeavePolicy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomPolicyName() {
        return this.customPolicyName;
    }

    public CustomLeavePolicy customPolicyName(String customPolicyName) {
        this.setCustomPolicyName(customPolicyName);
        return this;
    }

    public void setCustomPolicyName(String customPolicyName) {
        this.customPolicyName = customPolicyName;
    }

    public Long getDays() {
        return this.days;
    }

    public CustomLeavePolicy days(Long days) {
        this.setDays(days);
        return this;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public CustomLeavePolicy companyId(Long companyId) {
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

    public CustomLeavePolicy status(String status) {
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
    //    public CustomLeavePolicy lastModified(Instant lastModified) {
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
    //    public CustomLeavePolicy lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public LeavePolicy getLeavePolicy() {
        return this.leavePolicy;
    }

    public void setLeavePolicy(LeavePolicy leavePolicy) {
        this.leavePolicy = leavePolicy;
    }

    public CustomLeavePolicy leavePolicy(LeavePolicy leavePolicy) {
        this.setLeavePolicy(leavePolicy);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CustomLeavePolicy employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomLeavePolicy)) {
            return false;
        }
        return id != null && id.equals(((CustomLeavePolicy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomLeavePolicy{" +
            "id=" + getId() +
            ", customPolicyName='" + getCustomPolicyName() + "'" +
            ", days=" + getDays() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
