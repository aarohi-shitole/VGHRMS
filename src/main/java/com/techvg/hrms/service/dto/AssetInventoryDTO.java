package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.AssetInventory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetInventoryDTO implements Serializable {

    private Long id;

    private String assetName;

    private String assetype;

    private String assetId;

    private String purchaseFrom;

    private String purchaseTo;

    private String manufacturer;

    private String model;

    private String productNumber;

    private String supplier;

    private Long warrantyInMonths;

    private String condition;

    private Double value;

    private String description;

    private String assetStatus;

    private Long assetUserId;

    private String status;

    private Double submittedAmt;

    private Double refundAmt;

    private Double fineAmt;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetype() {
        return assetype;
    }

    public void setAssetype(String assetype) {
        this.assetype = assetype;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getPurchaseFrom() {
        return purchaseFrom;
    }

    public void setPurchaseFrom(String purchaseFrom) {
        this.purchaseFrom = purchaseFrom;
    }

    public String getPurchaseTo() {
        return purchaseTo;
    }

    public void setPurchaseTo(String purchaseTo) {
        this.purchaseTo = purchaseTo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getWarrantyInMonths() {
        return warrantyInMonths;
    }

    public void setWarrantyInMonths(Long warrantyInMonths) {
        this.warrantyInMonths = warrantyInMonths;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public Long getAssetUserId() {
        return assetUserId;
    }

    public void setAssetUserId(Long assetUserId) {
        this.assetUserId = assetUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSubmittedAmt() {
        return submittedAmt;
    }

    public void setSubmittedAmt(Double submittedAmt) {
        this.submittedAmt = submittedAmt;
    }

    public Double getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(Double refundAmt) {
        this.refundAmt = refundAmt;
    }

    public Double getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(Double fineAmt) {
        this.fineAmt = fineAmt;
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
        if (!(o instanceof AssetInventoryDTO)) {
            return false;
        }

        AssetInventoryDTO assetInventoryDTO = (AssetInventoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assetInventoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetInventoryDTO{" +
            "id=" + getId() +
            ", assetName='" + getAssetName() + "'" +
            ", assetype='" + getAssetype() + "'" +
            ", assetId='" + getAssetId() + "'" +
            ", purchaseFrom='" + getPurchaseFrom() + "'" +
            ", purchaseTo='" + getPurchaseTo() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", model='" + getModel() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", supplier='" + getSupplier() + "'" +
            ", warrantyInMonths=" + getWarrantyInMonths() +
            ", condition='" + getCondition() + "'" +
            ", value=" + getValue() +
            ", description='" + getDescription() + "'" +
            ", assetStatus='" + getAssetStatus() + "'" +
            ", assetUserId=" + getAssetUserId() +
            ", status='" + getStatus() + "'" +
            ", submittedAmt=" + getSubmittedAmt() +
            ", refundAmt=" + getRefundAmt() +
            ", fineAmt=" + getFineAmt() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
