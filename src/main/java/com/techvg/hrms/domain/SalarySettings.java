package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A SalarySettings.
 */
@Entity
@Table(name = "salary_settings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE salary_settings SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class SalarySettings extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "da")
    private Double da;

    @Column(name = "hra")
    private Double hra;

    @Column(name = "employee_share")
    private Double employeeShare;

    @Column(name = "company_share")
    private Double companyShare;

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

    public SalarySettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDa() {
        return this.da;
    }

    public SalarySettings da(Double da) {
        this.setDa(da);
        return this;
    }

    public void setDa(Double da) {
        this.da = da;
    }

    public Double getHra() {
        return this.hra;
    }

    public SalarySettings hra(Double hra) {
        this.setHra(hra);
        return this;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getEmployeeShare() {
        return this.employeeShare;
    }

    public SalarySettings employeeShare(Double employeeShare) {
        this.setEmployeeShare(employeeShare);
        return this;
    }

    public void setEmployeeShare(Double employeeShare) {
        this.employeeShare = employeeShare;
    }

    public Double getCompanyShare() {
        return this.companyShare;
    }

    public SalarySettings companyShare(Double companyShare) {
        this.setCompanyShare(companyShare);
        return this;
    }

    public void setCompanyShare(Double companyShare) {
        this.companyShare = companyShare;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public SalarySettings companyId(Long companyId) {
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

    public SalarySettings status(String status) {
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
    //    public SalarySettings lastModified(Instant lastModified) {
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
    //    public SalarySettings lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof SalarySettings)) {
            return false;
        }
        return id != null && id.equals(((SalarySettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalarySettings{" +
            "id=" + getId() +
            ", da=" + getDa() +
            ", hra=" + getHra() +
            ", employeeShare=" + getEmployeeShare() +
            ", companyShare=" + getCompanyShare() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
