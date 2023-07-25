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
 * A Branch.
 */
@Entity
@Table(name = "branch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE branch SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Branch extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "description")
    private String description;

    @Column(name = "branchcode")
    private String branchcode;

    @Column(name = "branch_type")
    private String branchType;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "branch_id")
    private Long branchId;

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

    public Branch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public Branch branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDescription() {
        return this.description;
    }

    public Branch description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchcode() {
        return this.branchcode;
    }

    public Branch branchcode(String branchcode) {
        this.setBranchcode(branchcode);
        return this;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBranchType() {
        return this.branchType;
    }

    public Branch branchType(String branchType) {
        this.setBranchType(branchType);
        return this;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public Branch webSite(String webSite) {
        this.setWebSite(webSite);
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Long getBranchId() {
        return this.branchId;
    }

    public Branch branchId(Long branchId) {
        this.setBranchId(branchId);
        return this;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getRegionId() {
        return this.regionId;
    }

    public Branch regionId(Long regionId) {
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
    //    public Branch companyId(Long companyId) {
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

    public Branch status(String status) {
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
    //    public Branch lastModified(Instant lastModified) {
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
    //    public Branch lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Branch)) {
            return false;
        }
        return id != null && id.equals(((Branch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Branch{" +
            "id=" + getId() +
            ", branchName='" + getBranchName() + "'" +
            ", description='" + getDescription() + "'" +
            ", branchcode='" + getBranchcode() + "'" +
            ", branchType='" + getBranchType() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", branchId=" + getBranchId() +
            ", regionId=" + getRegionId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
