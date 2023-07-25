package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A MasterPerformanceIndicator.
 */
@Entity
@Table(name = "master_performance_indicator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE master_performance_indicator SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class MasterPerformanceIndicator extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "weightage")
    private Long weightage;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MasterPerformanceIndicator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public MasterPerformanceIndicator category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return this.type;
    }

    public MasterPerformanceIndicator type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public MasterPerformanceIndicator name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public MasterPerformanceIndicator description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getWeightage() {
        return this.weightage;
    }

    public MasterPerformanceIndicator weightage(Long weightage) {
        this.setWeightage(weightage);
        return this;
    }

    public void setWeightage(Long weightage) {
        this.weightage = weightage;
    }

    public String getStatus() {
        return this.status;
    }

    public MasterPerformanceIndicator status(String status) {
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
//    public MasterPerformanceIndicator companyId(Long companyId) {
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
//    public MasterPerformanceIndicator lastModified(Instant lastModified) {
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
//    public MasterPerformanceIndicator lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof MasterPerformanceIndicator)) {
            return false;
        }
        return id != null && id.equals(((MasterPerformanceIndicator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterPerformanceIndicator{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", weightage=" + getWeightage() +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
