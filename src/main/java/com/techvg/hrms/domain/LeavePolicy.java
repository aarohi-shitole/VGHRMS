package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A LeavePolicy.
 */
@Entity
@Table(name = "leave_policy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE leave_policy SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class LeavePolicy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_carry_forword")
    private Boolean isCarryForword;

    @Column(name = "gender_leave")
    private Long genderLeave;

    @Column(name = "total_leave")
    private Long totalLeave;

    @Column(name = "max_leave")
    private Long maxLeave;

    @Column(name = "haspro_rata_leave")
    private Boolean hasproRataLeave;

    @Column(name = "description")
    private String description;

    @Column(name = "ref_table")
    private String refTable;

    @Column(name = "ref_table_id")
    private Long refTableId;

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
    private EmploymentType employmentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LeavePolicy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsCarryForword() {
        return this.isCarryForword;
    }

    public LeavePolicy isCarryForword(Boolean isCarryForword) {
        this.setIsCarryForword(isCarryForword);
        return this;
    }

    public void setIsCarryForword(Boolean isCarryForword) {
        this.isCarryForword = isCarryForword;
    }

    public Long getGenderLeave() {
        return this.genderLeave;
    }

    public LeavePolicy genderLeave(Long genderLeave) {
        this.setGenderLeave(genderLeave);
        return this;
    }

    public void setGenderLeave(Long genderLeave) {
        this.genderLeave = genderLeave;
    }

    public Long getTotalLeave() {
        return this.totalLeave;
    }

    public LeavePolicy totalLeave(Long totalLeave) {
        this.setTotalLeave(totalLeave);
        return this;
    }

    public void setTotalLeave(Long totalLeave) {
        this.totalLeave = totalLeave;
    }

    public Long getMaxLeave() {
        return this.maxLeave;
    }

    public LeavePolicy maxLeave(Long maxLeave) {
        this.setMaxLeave(maxLeave);
        return this;
    }

    public void setMaxLeave(Long maxLeave) {
        this.maxLeave = maxLeave;
    }

    public Boolean getHasproRataLeave() {
        return this.hasproRataLeave;
    }

    public LeavePolicy hasproRataLeave(Boolean hasproRataLeave) {
        this.setHasproRataLeave(hasproRataLeave);
        return this;
    }

    public void setHasproRataLeave(Boolean hasproRataLeave) {
        this.hasproRataLeave = hasproRataLeave;
    }

    public String getDescription() {
        return this.description;
    }

    public LeavePolicy description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefTable() {
        return this.refTable;
    }

    public LeavePolicy refTable(String refTable) {
        this.setRefTable(refTable);
        return this;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return this.refTableId;
    }

    public LeavePolicy refTableId(Long refTableId) {
        this.setRefTableId(refTableId);
        return this;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public LeavePolicy companyId(Long companyId) {
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

    public LeavePolicy status(String status) {
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
    //    public LeavePolicy lastModified(Instant lastModified) {
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
    //    public LeavePolicy lastModifiedBy(String lastModifiedBy) {
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

    public LeavePolicy leaveType(LeaveType leaveType) {
        this.setLeaveType(leaveType);
        return this;
    }

    public EmploymentType getEmploymentType() {
        return this.employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public LeavePolicy employmentType(EmploymentType employmentType) {
        this.setEmploymentType(employmentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeavePolicy)) {
            return false;
        }
        return id != null && id.equals(((LeavePolicy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeavePolicy{" +
            "id=" + getId() +
            ", isCarryForword='" + getIsCarryForword() + "'" +
            ", genderLeave=" + getGenderLeave() +
            ", totalLeave=" + getTotalLeave() +
            ", maxLeave=" + getMaxLeave() +
            ", hasproRataLeave='" + getHasproRataLeave() + "'" +
            ", description='" + getDescription() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
