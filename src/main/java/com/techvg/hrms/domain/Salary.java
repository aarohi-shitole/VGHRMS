package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Salary.
 */
@Entity
@Table(name = "salary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE salary SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Salary extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "isdeduction")
	private Boolean isdeduction;

	@Column(name = "month")
	private String month;

	@Column(name = "year")
	private String year;

	@Column(name = "status")
	private String status;

	@Column(name = "employee_id")
	private Long employeeId;
	
	@Column(name = "earning_amount")
	private Double earningAmount;

	

	// jhipster-needle-entity-add-field - JHipster will add fields here

	public Long getId() {
		return this.id;
	}

	public Salary id(Long id) {
		this.setId(id);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public Salary type(String type) {
		this.setType(type);
		return this;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return this.amount;
	}

	public Salary amount(Double amount) {
		this.setAmount(amount);
		return this;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getIsdeduction() {
		return this.isdeduction;
	}

	public Salary isdeduction(Boolean isdeduction) {
		this.setIsdeduction(isdeduction);
		return this;
	}

	public void setIsdeduction(Boolean isdeduction) {
		this.isdeduction = isdeduction;
	}

	public String getMonth() {
		return this.month;
	}

	public Salary month(String month) {
		this.setMonth(month);
		return this;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return this.year;
	}

	public Salary year(String year) {
		this.setYear(year);
		return this;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStatus() {
		return this.status;
	}

	public Salary status(String status) {
		this.setStatus(status);
		return this;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public Salary employeeId(Long employeeId) {
		this.setEmployeeId(employeeId);
		return this;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Double getEarningAmount() {
		return earningAmount;
	}

	public void setEarningAmount(Double earningAmount) {
		this.earningAmount = earningAmount;
	}

	
	
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Salary)) {
			return false;
		}
		return id != null && id.equals(((Salary) o).id);
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
		return "Salary{" + "id=" + getId() + ", type='" + getType() + "'" + ", amount=" + getAmount()
				+ ", isdeduction='" + getIsdeduction() + "'" + ", month='" + getMonth()+"'" + ", earningamount='" + getEarningAmount()+  "'" + ", year='" + getYear()
				+ "'" + ", status='" + getStatus() + "'" + "}";
	}
}
