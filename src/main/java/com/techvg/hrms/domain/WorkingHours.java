package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A WorkingHours.
 */
@Entity
@Table(name = "working_hours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE working_hours SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class WorkingHours extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "no_of_hours")
    private Double noOfHours;


    @Column(name = "employment_type_id")
    private Long employmentTypeId;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    @Column(name = "ref_table")
    private String refTable;

    @Column(name = "ref_table_id")
    private Long refTableId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WorkingHours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNoOfHours() {
		return this.noOfHours;
	}

	public void setNoOfHours(Double noOfHours) {
		this.noOfHours = noOfHours;
	}

	public WorkingHours noOfHours(Double noOfHours) {
		this.setNoOfHours(noOfHours);
        return this;
	}


	public Long getEmploymentTypeId() {
        return this.employmentTypeId;
    }

    public WorkingHours employmentTypeId(Long employmentTypeId) {
        this.setEmploymentTypeId(employmentTypeId);
        return this;
    }

    public void setEmploymentTypeId(Long employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public WorkingHours companyId(Long companyId) {
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

    public WorkingHours status(String status) {
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
    //    public WorkingHours lastModified(Instant lastModified) {
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
    //    public WorkingHours lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public String getRefTable() {
        return this.refTable;
    }

    public WorkingHours refTable(String refTable) {
        this.setRefTable(refTable);
        return this;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return this.refTableId;
    }

    public WorkingHours refTableId(Long refTableId) {
        this.setRefTableId(refTableId);
        return this;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingHours)) {
            return false;
        }
        return id != null && id.equals(((WorkingHours) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingHours{" +
            "id=" + getId() +
            ", noOfHours=" + getNoOfHours() +
            ", employmentTypeId=" + getEmploymentTypeId() +
            ", status='" + getStatus() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
