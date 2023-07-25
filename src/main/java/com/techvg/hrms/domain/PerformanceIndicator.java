package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PerformanceIndicator.
 */
@Entity
@Table(name = "performance_indicator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE performance_indicator SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PerformanceIndicator extends BaseEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "designation_id")
    private Long designationId;

    @Column(name = "indicator_value")
    private String indicatorValue;

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
    private MasterPerformanceIndicator masterPerformanceIndicator;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerformanceIndicator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDesignationId() {
        return this.designationId;
    }

    public PerformanceIndicator designationId(Long designationId) {
        this.setDesignationId(designationId);
        return this;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getIndicatorValue() {
        return this.indicatorValue;
    }

    public PerformanceIndicator indicatorValue(String indicatorValue) {
        this.setIndicatorValue(indicatorValue);
        return this;
    }

    public void setIndicatorValue(String indicatorValue) {
        this.indicatorValue = indicatorValue;
    }

    public String getStatus() {
        return this.status;
    }

    public PerformanceIndicator status(String status) {
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
//    public PerformanceIndicator companyId(Long companyId) {
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
//    public PerformanceIndicator lastModified(Instant lastModified) {
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
//    public PerformanceIndicator lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    public MasterPerformanceIndicator getMasterPerformanceIndicator() {
        return this.masterPerformanceIndicator;
    }

    public void setMasterPerformanceIndicator(MasterPerformanceIndicator masterPerformanceIndicator) {
        this.masterPerformanceIndicator = masterPerformanceIndicator;
    }

    public PerformanceIndicator masterPerformanceIndicator(MasterPerformanceIndicator masterPerformanceIndicator) {
        this.setMasterPerformanceIndicator(masterPerformanceIndicator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerformanceIndicator)) {
            return false;
        }
        return id != null && id.equals(((PerformanceIndicator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerformanceIndicator{" +
            "id=" + getId() +
            ", designationId=" + getDesignationId() +
            ", indicatorValue='" + getIndicatorValue() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
