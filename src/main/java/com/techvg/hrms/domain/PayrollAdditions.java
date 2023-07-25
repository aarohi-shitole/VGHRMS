package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PayrollAdditions.
 */
@Entity
@Table(name = "payroll_additions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE payroll_additions SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PayrollAdditions extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "has_unit_cal")
    private Boolean hasUnitCal;

    @Column(name = "unit_amount")
    private Double unitAmount;

    @Column(name = "assign_type")
    private String assignType;

    @Column(name = "status")
    private String status;

    @Column(name = "employee_id")
    private Long employeeId;

//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "last_modified")
//    private Instant lastModified;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PayrollAdditions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PayrollAdditions name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public PayrollAdditions category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getHasUnitCal() {
        return this.hasUnitCal;
    }

    public PayrollAdditions hasUnitCal(Boolean hasUnitCal) {
        this.setHasUnitCal(hasUnitCal);
        return this;
    }

    public void setHasUnitCal(Boolean hasUnitCal) {
        this.hasUnitCal = hasUnitCal;
    }

    public Double getUnitAmount() {
        return this.unitAmount;
    }

    public PayrollAdditions unitAmount(Double unitAmount) {
        this.setUnitAmount(unitAmount);
        return this;
    }

    public void setUnitAmount(Double unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getAssignType() {
        return this.assignType;
    }

    public PayrollAdditions assignType(String assignType) {
        this.setAssignType(assignType);
        return this;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getStatus() {
        return this.status;
    }

    public PayrollAdditions status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public PayrollAdditions employeeId(Long employeeId) {
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
//    public PayrollAdditions companyId(Long companyId) {
//        this.setCompanyId(companyId);
//        return this;
//    }
//
//    public void setCompanyId(Long companyId) {
//        this.companyId = companyId;
//    }
//
//    public Instant getLastModified() {
//        return this.lastModified;
//    }
//
//    public PayrollAdditions lastModified(Instant lastModified) {
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
//    public PayrollAdditions lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof PayrollAdditions)) {
            return false;
        }
        return id != null && id.equals(((PayrollAdditions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollAdditions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", hasUnitCal='" + getHasUnitCal() + "'" +
            ", unitAmount=" + getUnitAmount() +
            ", assignType='" + getAssignType() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
//            ", companyId=" + getCompanyId() +
//            ", lastModified='" + getLastModified() + "'" +
//            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
