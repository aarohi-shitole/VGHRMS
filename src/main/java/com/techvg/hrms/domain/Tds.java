package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Tds.
 */
@Entity
@Table(name = "tds")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE tds SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Tds extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salary_from")
    private Instant salaryFrom;

    @Column(name = "salary_to")
    private Instant salaryTo;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "salary_setting_id")
    private Long salarySettingId;

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

    public Tds id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSalaryFrom() {
        return this.salaryFrom;
    }

    public Tds salaryFrom(Instant salaryFrom) {
        this.setSalaryFrom(salaryFrom);
        return this;
    }

    public void setSalaryFrom(Instant salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Instant getSalaryTo() {
        return this.salaryTo;
    }

    public Tds salaryTo(Instant salaryTo) {
        this.setSalaryTo(salaryTo);
        return this;
    }

    public void setSalaryTo(Instant salaryTo) {
        this.salaryTo = salaryTo;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public Tds percentage(Double percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Long getSalarySettingId() {
        return this.salarySettingId;
    }

    public Tds salarySettingId(Long salarySettingId) {
        this.setSalarySettingId(salarySettingId);
        return this;
    }

    public void setSalarySettingId(Long salarySettingId) {
        this.salarySettingId = salarySettingId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public Tds companyId(Long companyId) {
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

    public Tds status(String status) {
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
    //    public Tds lastModified(Instant lastModified) {
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
    //    public Tds lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Tds)) {
            return false;
        }
        return id != null && id.equals(((Tds) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tds{" +
            "id=" + getId() +
            ", salaryFrom='" + getSalaryFrom() + "'" +
            ", salaryTo='" + getSalaryTo() + "'" +
            ", percentage=" + getPercentage() +
            ", salarySettingId=" + getSalarySettingId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
