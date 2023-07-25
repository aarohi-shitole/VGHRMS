package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.AssetInventory} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.AssetInventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssetInventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter assetName;

    private StringFilter assetype;

    private StringFilter assetId;

    private StringFilter purchaseFrom;

    private StringFilter purchaseTo;

    private StringFilter manufacturer;

    private StringFilter model;

    private StringFilter productNumber;

    private StringFilter supplier;

    private LongFilter warrantyInMonths;

    private StringFilter condition;

    private DoubleFilter value;

    private StringFilter description;

    private StringFilter assetStatus;

    private LongFilter assetUserId;

    private StringFilter status;

    private DoubleFilter submittedAmt;

    private DoubleFilter refundAmt;

    private DoubleFilter fineAmt;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public AssetInventoryCriteria() {}

    public AssetInventoryCriteria(AssetInventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.assetName = other.assetName == null ? null : other.assetName.copy();
        this.assetype = other.assetype == null ? null : other.assetype.copy();
        this.assetId = other.assetId == null ? null : other.assetId.copy();
        this.purchaseFrom = other.purchaseFrom == null ? null : other.purchaseFrom.copy();
        this.purchaseTo = other.purchaseTo == null ? null : other.purchaseTo.copy();
        this.manufacturer = other.manufacturer == null ? null : other.manufacturer.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.productNumber = other.productNumber == null ? null : other.productNumber.copy();
        this.supplier = other.supplier == null ? null : other.supplier.copy();
        this.warrantyInMonths = other.warrantyInMonths == null ? null : other.warrantyInMonths.copy();
        this.condition = other.condition == null ? null : other.condition.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.assetStatus = other.assetStatus == null ? null : other.assetStatus.copy();
        this.assetUserId = other.assetUserId == null ? null : other.assetUserId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.submittedAmt = other.submittedAmt == null ? null : other.submittedAmt.copy();
        this.refundAmt = other.refundAmt == null ? null : other.refundAmt.copy();
        this.fineAmt = other.fineAmt == null ? null : other.fineAmt.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AssetInventoryCriteria copy() {
        return new AssetInventoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAssetName() {
        return assetName;
    }

    public StringFilter assetName() {
        if (assetName == null) {
            assetName = new StringFilter();
        }
        return assetName;
    }

    public void setAssetName(StringFilter assetName) {
        this.assetName = assetName;
    }

    public StringFilter getAssetype() {
        return assetype;
    }

    public StringFilter assetype() {
        if (assetype == null) {
            assetype = new StringFilter();
        }
        return assetype;
    }

    public void setAssetype(StringFilter assetype) {
        this.assetype = assetype;
    }

    public StringFilter getAssetId() {
        return assetId;
    }

    public StringFilter assetId() {
        if (assetId == null) {
            assetId = new StringFilter();
        }
        return assetId;
    }

    public void setAssetId(StringFilter assetId) {
        this.assetId = assetId;
    }

    public StringFilter getPurchaseFrom() {
        return purchaseFrom;
    }

    public StringFilter purchaseFrom() {
        if (purchaseFrom == null) {
            purchaseFrom = new StringFilter();
        }
        return purchaseFrom;
    }

    public void setPurchaseFrom(StringFilter purchaseFrom) {
        this.purchaseFrom = purchaseFrom;
    }

    public StringFilter getPurchaseTo() {
        return purchaseTo;
    }

    public StringFilter purchaseTo() {
        if (purchaseTo == null) {
            purchaseTo = new StringFilter();
        }
        return purchaseTo;
    }

    public void setPurchaseTo(StringFilter purchaseTo) {
        this.purchaseTo = purchaseTo;
    }

    public StringFilter getManufacturer() {
        return manufacturer;
    }

    public StringFilter manufacturer() {
        if (manufacturer == null) {
            manufacturer = new StringFilter();
        }
        return manufacturer;
    }

    public void setManufacturer(StringFilter manufacturer) {
        this.manufacturer = manufacturer;
    }

    public StringFilter getModel() {
        return model;
    }

    public StringFilter model() {
        if (model == null) {
            model = new StringFilter();
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public StringFilter getProductNumber() {
        return productNumber;
    }

    public StringFilter productNumber() {
        if (productNumber == null) {
            productNumber = new StringFilter();
        }
        return productNumber;
    }

    public void setProductNumber(StringFilter productNumber) {
        this.productNumber = productNumber;
    }

    public StringFilter getSupplier() {
        return supplier;
    }

    public StringFilter supplier() {
        if (supplier == null) {
            supplier = new StringFilter();
        }
        return supplier;
    }

    public void setSupplier(StringFilter supplier) {
        this.supplier = supplier;
    }

    public LongFilter getWarrantyInMonths() {
        return warrantyInMonths;
    }

    public LongFilter warrantyInMonths() {
        if (warrantyInMonths == null) {
            warrantyInMonths = new LongFilter();
        }
        return warrantyInMonths;
    }

    public void setWarrantyInMonths(LongFilter warrantyInMonths) {
        this.warrantyInMonths = warrantyInMonths;
    }

    public StringFilter getCondition() {
        return condition;
    }

    public StringFilter condition() {
        if (condition == null) {
            condition = new StringFilter();
        }
        return condition;
    }

    public void setCondition(StringFilter condition) {
        this.condition = condition;
    }

    public DoubleFilter getValue() {
        return value;
    }

    public DoubleFilter value() {
        if (value == null) {
            value = new DoubleFilter();
        }
        return value;
    }

    public void setValue(DoubleFilter value) {
        this.value = value;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getAssetStatus() {
        return assetStatus;
    }

    public StringFilter assetStatus() {
        if (assetStatus == null) {
            assetStatus = new StringFilter();
        }
        return assetStatus;
    }

    public void setAssetStatus(StringFilter assetStatus) {
        this.assetStatus = assetStatus;
    }

    public LongFilter getAssetUserId() {
        return assetUserId;
    }

    public LongFilter assetUserId() {
        if (assetUserId == null) {
            assetUserId = new LongFilter();
        }
        return assetUserId;
    }

    public void setAssetUserId(LongFilter assetUserId) {
        this.assetUserId = assetUserId;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public DoubleFilter getSubmittedAmt() {
        return submittedAmt;
    }

    public DoubleFilter submittedAmt() {
        if (submittedAmt == null) {
            submittedAmt = new DoubleFilter();
        }
        return submittedAmt;
    }

    public void setSubmittedAmt(DoubleFilter submittedAmt) {
        this.submittedAmt = submittedAmt;
    }

    public DoubleFilter getRefundAmt() {
        return refundAmt;
    }

    public DoubleFilter refundAmt() {
        if (refundAmt == null) {
            refundAmt = new DoubleFilter();
        }
        return refundAmt;
    }

    public void setRefundAmt(DoubleFilter refundAmt) {
        this.refundAmt = refundAmt;
    }

    public DoubleFilter getFineAmt() {
        return fineAmt;
    }

    public DoubleFilter fineAmt() {
        if (fineAmt == null) {
            fineAmt = new DoubleFilter();
        }
        return fineAmt;
    }

    public void setFineAmt(DoubleFilter fineAmt) {
        this.fineAmt = fineAmt;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssetInventoryCriteria that = (AssetInventoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(assetName, that.assetName) &&
            Objects.equals(assetype, that.assetype) &&
            Objects.equals(assetId, that.assetId) &&
            Objects.equals(purchaseFrom, that.purchaseFrom) &&
            Objects.equals(purchaseTo, that.purchaseTo) &&
            Objects.equals(manufacturer, that.manufacturer) &&
            Objects.equals(model, that.model) &&
            Objects.equals(productNumber, that.productNumber) &&
            Objects.equals(supplier, that.supplier) &&
            Objects.equals(warrantyInMonths, that.warrantyInMonths) &&
            Objects.equals(condition, that.condition) &&
            Objects.equals(value, that.value) &&
            Objects.equals(description, that.description) &&
            Objects.equals(assetStatus, that.assetStatus) &&
            Objects.equals(assetUserId, that.assetUserId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(submittedAmt, that.submittedAmt) &&
            Objects.equals(refundAmt, that.refundAmt) &&
            Objects.equals(fineAmt, that.fineAmt) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            assetName,
            assetype,
            assetId,
            purchaseFrom,
            purchaseTo,
            manufacturer,
            model,
            productNumber,
            supplier,
            warrantyInMonths,
            condition,
            value,
            description,
            assetStatus,
            assetUserId,
            status,
            submittedAmt,
            refundAmt,
            fineAmt,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetInventoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (assetName != null ? "assetName=" + assetName + ", " : "") +
            (assetype != null ? "assetype=" + assetype + ", " : "") +
            (assetId != null ? "assetId=" + assetId + ", " : "") +
            (purchaseFrom != null ? "purchaseFrom=" + purchaseFrom + ", " : "") +
            (purchaseTo != null ? "purchaseTo=" + purchaseTo + ", " : "") +
            (manufacturer != null ? "manufacturer=" + manufacturer + ", " : "") +
            (model != null ? "model=" + model + ", " : "") +
            (productNumber != null ? "productNumber=" + productNumber + ", " : "") +
            (supplier != null ? "supplier=" + supplier + ", " : "") +
            (warrantyInMonths != null ? "warrantyInMonths=" + warrantyInMonths + ", " : "") +
            (condition != null ? "condition=" + condition + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (assetStatus != null ? "assetStatus=" + assetStatus + ", " : "") +
            (assetUserId != null ? "assetUserId=" + assetUserId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (submittedAmt != null ? "submittedAmt=" + submittedAmt + ", " : "") +
            (refundAmt != null ? "refundAmt=" + refundAmt + ", " : "") +
            (fineAmt != null ? "fineAmt=" + fineAmt + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
