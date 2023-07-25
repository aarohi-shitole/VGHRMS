package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A AssetInventory.
 */
@Entity
@Table(name = "asset_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE asset_inventory SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class AssetInventory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "asset_name")
	private String assetName;

	@Column(name = "assetype")
	private String assetype;

	@Column(name = "asset_id")
	private String assetId;

	@Column(name = "purchase_from")
	private String purchaseFrom;

	@Column(name = "purchase_to")
	private String purchaseTo;

	@Column(name = "manufacturer")
	private String manufacturer;

	@Column(name = "model")
	private String model;

	@Column(name = "product_number")
	private String productNumber;

	@Column(name = "supplier")
	private String supplier;

	@Column(name = "warranty_in_months")
	private Long warrantyInMonths;

	@Column(name = "jhi_condition")
	private String condition;

	@Column(name = "value")
	private Double value;

	@Column(name = "description")
	private String description;

	@Column(name = "asset_status")
	private String assetStatus;

	@Column(name = "asset_user_id")
	private Long assetUserId;

	@Column(name = "status")
	private String status;

	@Column(name = "submitted_amt")
	private Double submittedAmt;

	@Column(name = "refund_amt")
	private Double refundAmt;

	@Column(name = "fine_amt")
	private Double fineAmt;

	// jhipster-needle-entity-add-field - JHipster will add fields here

	public Long getId() {
		return this.id;
	}

	public AssetInventory id(Long id) {
		this.setId(id);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetName() {
		return this.assetName;
	}

	public AssetInventory assetName(String assetName) {
		this.setAssetName(assetName);
		return this;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetype() {
		return this.assetype;
	}

	public AssetInventory assetype(String assetype) {
		this.setAssetype(assetype);
		return this;
	}

	public void setAssetype(String assetype) {
		this.assetype = assetype;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public AssetInventory assetId(String assetId) {
		this.setAssetId(assetId);
		return this;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getPurchaseFrom() {
		return this.purchaseFrom;
	}

	public AssetInventory purchaseFrom(String purchaseFrom) {
		this.setPurchaseFrom(purchaseFrom);
		return this;
	}

	public void setPurchaseFrom(String purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}

	public String getPurchaseTo() {
		return this.purchaseTo;
	}

	public AssetInventory purchaseTo(String purchaseTo) {
		this.setPurchaseTo(purchaseTo);
		return this;
	}

	public void setPurchaseTo(String purchaseTo) {
		this.purchaseTo = purchaseTo;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public AssetInventory manufacturer(String manufacturer) {
		this.setManufacturer(manufacturer);
		return this;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public AssetInventory model(String model) {
		this.setModel(model);
		return this;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProductNumber() {
		return this.productNumber;
	}

	public AssetInventory productNumber(String productNumber) {
		this.setProductNumber(productNumber);
		return this;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public AssetInventory supplier(String supplier) {
		this.setSupplier(supplier);
		return this;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Long getWarrantyInMonths() {
		return this.warrantyInMonths;
	}

	public AssetInventory warrantyInMonths(Long warrantyInMonths) {
		this.setWarrantyInMonths(warrantyInMonths);
		return this;
	}

	public void setWarrantyInMonths(Long warrantyInMonths) {
		this.warrantyInMonths = warrantyInMonths;
	}

	public String getCondition() {
		return this.condition;
	}

	public AssetInventory condition(String condition) {
		this.setCondition(condition);
		return this;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Double getValue() {
		return this.value;
	}

	public AssetInventory value(Double value) {
		this.setValue(value);
		return this;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDescription() {
		return this.description;
	}

	public AssetInventory description(String description) {
		this.setDescription(description);
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssetStatus() {
		return this.assetStatus;
	}

	public AssetInventory assetStatus(String assetStatus) {
		this.setAssetStatus(assetStatus);
		return this;
	}

	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}

	public Long getAssetUserId() {
		return this.assetUserId;
	}

	public AssetInventory assetUserId(Long assetUserId) {
		this.setAssetUserId(assetUserId);
		return this;
	}

	public void setAssetUserId(Long assetUserId) {
		this.assetUserId = assetUserId;
	}

	public String getStatus() {
		return this.status;
	}

	public AssetInventory status(String status) {
		this.setStatus(status);
		return this;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSubmittedAmt() {
		return this.submittedAmt;
	}

	public AssetInventory submittedAmt(Double submittedAmt) {
		this.setSubmittedAmt(submittedAmt);
		return this;
	}

	public void setSubmittedAmt(Double submittedAmt) {
		this.submittedAmt = submittedAmt;
	}

	public Double getRefundAmt() {
		return this.refundAmt;
	}

	public AssetInventory refundAmt(Double refundAmt) {
		this.setRefundAmt(refundAmt);
		return this;
	}

	public void setRefundAmt(Double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public Double getFineAmt() {
		return this.fineAmt;
	}

	public AssetInventory fineAmt(Double fineAmt) {
		this.setFineAmt(fineAmt);
		return this;
	}

	public void setFineAmt(Double fineAmt) {
		this.fineAmt = fineAmt;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AssetInventory)) {
			return false;
		}
		return id != null && id.equals(((AssetInventory) o).id);
	}

	@Override
	public int hashCode() {
		// see
		// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
		return getClass().hashCode();
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "AssetInventory{" + "id=" + getId() + ", assetName='" + getAssetName() + "'" + ", assetype='"
				+ getAssetype() + "'" + ", assetId='" + getAssetId() + "'" + ", purchaseFrom='" + getPurchaseFrom()
				+ "'" + ", purchaseTo='" + getPurchaseTo() + "'" + ", manufacturer='" + getManufacturer() + "'"
				+ ", model='" + getModel() + "'" + ", productNumber='" + getProductNumber() + "'" + ", supplier='"
				+ getSupplier() + "'" + ", warrantyInMonths=" + getWarrantyInMonths() + ", condition='" + getCondition()
				+ "'" + ", value=" + getValue() + ", description='" + getDescription() + "'" + ", assetStatus='"
				+ getAssetStatus() + "'" + ", assetUserId=" + getAssetUserId() + ", status='" + getStatus() + "'"
				+ ", submittedAmt=" + getSubmittedAmt() + ", refundAmt=" + getRefundAmt() + ", fineAmt=" + getFineAmt()
				+ "}";
	}
}
