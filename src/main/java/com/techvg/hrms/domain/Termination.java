package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Termination.
 */
@Entity
@Table(name = "termination")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE termination SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Termination extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "emp_name")
	private String empName;

	@Column(name = "termination_type")
	private String terminationType;

	@Column(name = "termination_date")
	private Instant terminationDate;

	@Column(name = "notice_date")
	private Instant noticeDate;

	@Column(name = "reason")
	private String reason;

	@Column(name = "status")
	private String status;

	@Column(name = "department_id")
	private Long departmentId;

	@Column(name = "employee_id")
	private Long employeeId;

	// jhipster-needle-entity-add-field - JHipster will add fields here

	public Long getId() {
		return this.id;
	}

	public Termination id(Long id) {
		this.setId(id);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpName() {
		return this.empName;
	}

	public Termination empName(String empName) {
		this.setEmpName(empName);
		return this;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTerminationType() {
		return this.terminationType;
	}

	public Termination terminationType(String terminationType) {
		this.setTerminationType(terminationType);
		return this;
	}

	public void setTerminationType(String terminationType) {
		this.terminationType = terminationType;
	}

	public Instant getTerminationDate() {
		return this.terminationDate;
	}

	public Termination terminationDate(Instant terminationDate) {
		this.setTerminationDate(terminationDate);
		return this;
	}

	public void setTerminationDate(Instant terminationDate) {
		this.terminationDate = terminationDate;
	}

	public Instant getNoticeDate() {
		return this.noticeDate;
	}

	public Termination noticeDate(Instant noticeDate) {
		this.setNoticeDate(noticeDate);
		return this;
	}

	public void setNoticeDate(Instant noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getReason() {
		return this.reason;
	}

	public Termination reason(String reason) {
		this.setReason(reason);
		return this;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return this.status;
	}

	public Termination status(String status) {
		this.setStatus(status);
		return this;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public Termination departmentId(Long departmentId) {
		this.setDepartmentId(departmentId);
		return this;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public Termination employeeId(Long employeeId) {
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
		if (!(o instanceof Termination)) {
			return false;
		}
		return id != null && id.equals(((Termination) o).id);
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
		return "Termination{" + "id=" + getId() + ", empName='" + getEmpName() + "'" + ", terminationType='"
				+ getTerminationType() + "'" + ", terminationDate='" + getTerminationDate() + "'" + ", noticeDate='"
				+ getNoticeDate() + "'" + ", reason='" + getReason() + "'" + ", status='" + getStatus() + "'"
				+ ", departmentId=" + getDepartmentId() + ", employeeId=" + getEmployeeId() + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + "}";
	}
}
