package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A AssetApplication.
 */
@Entity
@Table(name = "asset_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE asset_application SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class AssetApplication extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "assetype")
    private String assetype;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "req_status")
    private String reqStatus;

    @Column(name = "apply_date")
    private Instant applyDate;

    @Column(name = "assgin_date")
    private Instant assginDate;

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

    public AssetApplication id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetId() {
        return this.assetId;
    }

    public AssetApplication assetId(Long assetId) {
        this.setAssetId(assetId);
        return this;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetype() {
        return this.assetype;
    }

    public AssetApplication assetype(String assetype) {
        this.setAssetype(assetype);
        return this;
    }

    public void setAssetype(String assetype) {
        this.assetype = assetype;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public AssetApplication quantity(Long quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return this.description;
    }

    public AssetApplication description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReqStatus() {
        return this.reqStatus;
    }

    public AssetApplication reqStatus(String reqStatus) {
        this.setReqStatus(reqStatus);
        return this;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public Instant getApplyDate() {
        return this.applyDate;
    }

    public AssetApplication applyDate(Instant applyDate) {
        this.setApplyDate(applyDate);
        return this;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public Instant getAssginDate() {
        return this.assginDate;
    }

    public AssetApplication assginDate(Instant assginDate) {
        this.setAssginDate(assginDate);
        return this;
    }

    public void setAssginDate(Instant assginDate) {
        this.assginDate = assginDate;
    }

    public String getStatus() {
        return this.status;
    }

    public AssetApplication status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public AssetApplication employeeId(Long employeeId) {
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
//    public AssetApplication companyId(Long companyId) {
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
//    public AssetApplication lastModified(Instant lastModified) {
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
//    public AssetApplication lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof AssetApplication)) {
            return false;
        }
        return id != null && id.equals(((AssetApplication) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetApplication{" +
            "id=" + getId() +
            ", assetId=" + getAssetId() +
            ", assetype='" + getAssetype() + "'" +
            ", quantity=" + getQuantity() +
            ", description='" + getDescription() + "'" +
            ", reqStatus='" + getReqStatus() + "'" +
            ", applyDate='" + getApplyDate() + "'" +
            ", assginDate='" + getAssginDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeeId=" + getEmployeeId() +
 //           ", companyId=" + getCompanyId() +
//            ", lastModified='" + getLastModified() + "'" +
//            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
