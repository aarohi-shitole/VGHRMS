package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.AssetApplication} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetApplicationDTO implements Serializable {

    private Long id;

    private Long assetId;

    private String assetype;

    private Long quantity;

    private String description;

    private String reqStatus;

    private Instant applyDate;

    private Instant assginDate;

    private String status;

    private Long employeeId;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetype() {
        return assetype;
    }

    public void setAssetype(String assetype) {
        this.assetype = assetype;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public Instant getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

    public Instant getAssginDate() {
        return assginDate;
    }

    public void setAssginDate(Instant assginDate) {
        this.assginDate = assginDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetApplicationDTO)) {
            return false;
        }

        AssetApplicationDTO assetApplicationDTO = (AssetApplicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetApplicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetApplicationDTO{" +
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
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
