package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A AppraisalDetails.
 */
@Entity
@Table(name = "appraisal_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE appraisal_details SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class AppraisalDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "performance_appraisal_id")
    private Long performanceAppraisalId;

    @Column(name = "expected_value")
    private String expectedValue;

    @Column(name = "set_value")
    private String setValue;

    @Column(name = "status")
    private String status;

    //    @Column(name = "company_id")
    //    private Long companyId;
    //
    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    @ManyToOne
    private PerformanceIndicator performanceIndicator;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppraisalDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerformanceAppraisalId() {
        return this.performanceAppraisalId;
    }

    public AppraisalDetails performanceAppraisalId(Long performanceAppraisalId) {
        this.setPerformanceAppraisalId(performanceAppraisalId);
        return this;
    }

    public void setPerformanceAppraisalId(Long performanceAppraisalId) {
        this.performanceAppraisalId = performanceAppraisalId;
    }

    public String getExpectedValue() {
        return this.expectedValue;
    }

    public AppraisalDetails expectedValue(String expectedValue) {
        this.setExpectedValue(expectedValue);
        return this;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    public String getSetValue() {
        return this.setValue;
    }

    public AppraisalDetails setValue(String setValue) {
        this.setSetValue(setValue);
        return this;
    }

    public void setSetValue(String setValue) {
        this.setValue = setValue;
    }

    public String getStatus() {
        return this.status;
    }

    public AppraisalDetails status(String status) {
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
    //    public AppraisalDetails companyId(Long companyId) {
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
    //    public AppraisalDetails lastModified(Instant lastModified) {
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
    //    public AppraisalDetails lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public PerformanceIndicator getPerformanceIndicator() {
        return this.performanceIndicator;
    }

    public void setPerformanceIndicator(PerformanceIndicator performanceIndicator) {
        this.performanceIndicator = performanceIndicator;
    }

    public AppraisalDetails performanceIndicator(PerformanceIndicator performanceIndicator) {
        this.setPerformanceIndicator(performanceIndicator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppraisalDetails)) {
            return false;
        }
        return id != null && id.equals(((AppraisalDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppraisalDetails{" +
            "id=" + getId() +
            ", performanceAppraisalId=" + getPerformanceAppraisalId() +
            ", expectedValue='" + getExpectedValue() + "'" +
            ", setValue='" + getSetValue() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
