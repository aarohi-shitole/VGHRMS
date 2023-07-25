package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE region SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Region extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "region_name", nullable = false)
    private String regionName;

    @Column(name = "description")
    private String description;

    @Column(name = "region_id")
    private Long regionId;

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

    public Region id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public Region regionName(String regionName) {
        this.setRegionName(regionName);
        return this;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDescription() {
        return this.description;
    }

    public Region description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public Region regionId(Long regionId) {
        this.setRegionId(regionId);
        return this;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public Region companyId(Long companyId) {
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

    public Region status(String status) {
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
    //    public Region lastModified(Instant lastModified) {
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
    //    public Region lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", regionName='" + getRegionName() + "'" +
            ", description='" + getDescription() + "'" +
            ", regionId=" + getRegionId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
