package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.WorkingHours} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingHoursDTO implements Serializable {

    private Long id;

    private Double noOfHours;

    private Long employmentTypeId;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    private String refTable;

    private Long refTableId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Double getNoOfHours() { 
		return noOfHours;
	}

	public void setNoOfHours(Double noOfHours) {
		this.noOfHours = noOfHours;
	}

	public Long getEmploymentTypeId() {
        return employmentTypeId;
    }

    public void setEmploymentTypeId(Long employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingHoursDTO)) {
            return false;
        }

        WorkingHoursDTO workingHoursDTO = (WorkingHoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workingHoursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingHoursDTO{" +
            "id=" + getId() +
            ", noOfHours=" + getNoOfHours() +    
            ", employmentTypeId=" + getEmploymentTypeId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            "}";
    }
}
