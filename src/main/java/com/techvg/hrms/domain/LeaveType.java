package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A LeaveType.
 */
@Entity
@Table(name = "leave_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE leave_type SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class LeaveType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "no_of_days")
    private Long noOfDays;

    @Column(name = "has_carry_forward")
    private Boolean hasCarryForward;

    @Column(name = "has_earned")
    private Boolean hasEarned;

    @Column(name = "has_custom_policy")
    private Boolean hasCustomPolicy;

    
    @Column(name = "allowBackward")
    private Boolean allowBackward;
    
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

    public LeaveType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeaveType() {
        return this.leaveType;
    }

    public LeaveType leaveType(String leaveType) {
        this.setLeaveType(leaveType);
        return this;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Long getNoOfDays() {
        return this.noOfDays;
    }

    public LeaveType noOfDays(Long noOfDays) {
        this.setNoOfDays(noOfDays);
        return this;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Boolean getHasCarryForward() {
        return this.hasCarryForward;
    }

    public LeaveType hasCarryForward(Boolean hasCarryForward) {
        this.setHasCarryForward(hasCarryForward);
        return this;
    }

    public void setHasCarryForward(Boolean hasCarryForward) {
        this.hasCarryForward = hasCarryForward;
    }

    public Boolean getHasEarned() {
        return this.hasEarned;
    }

    public LeaveType hasEarned(Boolean hasEarned) {
        this.setHasEarned(hasEarned);
        return this;
    }

    public void setHasEarned(Boolean hasEarned) {
        this.hasEarned = hasEarned;
    }

    public Boolean getHasCustomPolicy() {
        return this.hasCustomPolicy;
    }

    public LeaveType hasCustomPolicy(Boolean hasCustomPolicy) {
        this.setHasCustomPolicy(hasCustomPolicy);
        return this;
    }

    public void setHasCustomPolicy(Boolean hasCustomPolicy) {
        this.hasCustomPolicy = hasCustomPolicy;
    }
    
    public Boolean getAllowBackward() {
        return this.allowBackward;
    }

    public LeaveType allowBackward(Boolean allowBackward) {
        this.setAllowBackward(allowBackward);
        return this;
    }

    public void setAllowBackward(Boolean allowBackward) {
        this.allowBackward = allowBackward;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public LeaveType companyId(Long companyId) {
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

    public LeaveType status(String status) {
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
    //    public LeaveType lastModified(Instant lastModified) {
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
    //    public LeaveType lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof LeaveType)) {
            return false;
        }
        return id != null && id.equals(((LeaveType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveType{" +
            "id=" + getId() +
            ", leaveType='" + getLeaveType() + "'" +
            ", noOfDays=" + getNoOfDays() +
            ", hasCarryForward='" + getHasCarryForward() + "'" +
            ", hasEarned='" + getHasEarned() + "'" +
            ", hasCustomPolicy='" + getHasCustomPolicy() + "'" +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", allowBackward='" + getAllowBackward() + "'" +
            "}";
    }
}
