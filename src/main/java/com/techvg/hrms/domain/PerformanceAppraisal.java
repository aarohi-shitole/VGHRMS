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
 * A PerformanceAppraisal.
 */
@Entity
@Table(name = "performance_appraisal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE performance_appraisal SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PerformanceAppraisal extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "company_id")
    //    private Long companyId;
    //

    @Column(name = "appraisal_date")
    private Instant appraisalDate;

    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AppraisalReview appraisalReview;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerformanceAppraisal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public PerformanceAppraisal employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return this.status;
    }

    public PerformanceAppraisal status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public PerformanceAppraisal companyId(Long companyId) {
    //        this.setCompanyId(companyId);
    //        return this;
    //    }
    //
    //    public void setCompanyId(Long companyId) {
    //        this.companyId = companyId;
    //    }

    public Instant getAppraisalDate() {
        return this.appraisalDate;
    }

    public PerformanceAppraisal appraisalDate(Instant appraisalDate) {
        this.setAppraisalDate(appraisalDate);
        return this;
    }

    public void setAppraisalDate(Instant appraisalDate) {
        this.appraisalDate = appraisalDate;
    }

    //    public String getLastModifiedBy() {
    //        return this.lastModifiedBy;
    //    }
    //
    //    public PerformanceAppraisal lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public AppraisalReview getAppraisalReview() {
        return this.appraisalReview;
    }

    public void setAppraisalReview(AppraisalReview appraisalReview) {
        this.appraisalReview = appraisalReview;
    }

    public PerformanceAppraisal appraisalReview(AppraisalReview appraisalReview) {
        this.setAppraisalReview(appraisalReview);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceAppraisal)) {
            return false;
        }
        return id != null && id.equals(((PerformanceAppraisal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceAppraisal{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", appraisalDate='" + getAppraisalDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
