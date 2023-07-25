package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE promotion SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Promotion extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "promotion_for")
	private String promotionFor;

	@Column(name = "promoted_from")
	private String promotedFrom;

	@Column(name = "promoted_to")
	private String promotedTo;

	@Column(name = "promotied_date")
	private Instant promotiedDate;

	@Column(name = "branch_id")
	private Long branchId;

	@Column(name = "department_id")
	private Long departmentId;

	@Column(name = "status")
	private String status;

	@Column(name = "employee_id")
	private Long employeeId;

	// jhipster-needle-entity-add-field - JHipster will add fields here

	public Long getId() {
		return this.id;
	}

	public Promotion id(Long id) {
		this.setId(id);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionFor() {
		return this.promotionFor;
	}

	public Promotion promotionFor(String promotionFor) {
		this.setPromotionFor(promotionFor);
		return this;
	}

	public void setPromotionFor(String promotionFor) {
		this.promotionFor = promotionFor;
	}

	public String getPromotedFrom() {
		return this.promotedFrom;
	}

	public Promotion promotedFrom(String promotedFrom) {
		this.setPromotedFrom(promotedFrom);
		return this;
	}

	public void setPromotedFrom(String promotedFrom) {
		this.promotedFrom = promotedFrom;
	}

	public String getPromotedTo() {
		return this.promotedTo;
	}

	public Promotion promotedTo(String promotedTo) {
		this.setPromotedTo(promotedTo);
		return this;
	}

	public void setPromotedTo(String promotedTo) {
		this.promotedTo = promotedTo;
	}

	public Instant getPromotiedDate() {
		return this.promotiedDate;
	}

	public Promotion promotiedDate(Instant promotiedDate) {
		this.setPromotiedDate(promotiedDate);
		return this;
	}

	public void setPromotiedDate(Instant promotiedDate) {
		this.promotiedDate = promotiedDate;
	}

	public Long getBranchId() {
		return this.branchId;
	}

	public Promotion branchId(Long branchId) {
		this.setBranchId(branchId);
		return this;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public Promotion departmentId(Long departmentId) {
		this.setDepartmentId(departmentId);
		return this;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getStatus() {
		return this.status;
	}

	public Promotion status(String status) {
		this.setStatus(status);
		return this;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public Promotion employeeId(Long employeeId) {
		this.setEmployeeId(employeeId);
		return this;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Promotion)) {
			return false;
		}
		return id != null && id.equals(((Promotion) o).id);
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
		return "Promotion{" + "id=" + getId() + ", promotionFor='" + getPromotionFor() + "'" + ", promotedFrom='"
				+ getPromotedFrom() + "'" + ", promotedTo='" + getPromotedTo() + "'" + ", promotiedDate='"
				+ getPromotiedDate() + "'" + ", branchId=" + getBranchId() + ", departmentId=" + getDepartmentId()
				+ ", status='" + getStatus() + "'" + ", employeeId=" + getEmployeeId() + "}";
	}
}
