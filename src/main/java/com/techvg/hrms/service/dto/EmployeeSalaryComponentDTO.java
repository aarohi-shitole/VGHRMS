package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.EmployeeSalaryComponent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeSalaryComponentDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Double value;

    private String componentTypeFlag;

    private String catagory;

    private String status;

    private Long companyId;
    
    private Long employeeId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getComponentTypeFlag() {
        return componentTypeFlag;
    }

    public void setComponentTypeFlag(String componentTypeFlag) {
        this.componentTypeFlag = componentTypeFlag;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof EmployeeSalaryComponentDTO)) {
            return false;
        }

        EmployeeSalaryComponentDTO employeeSalaryComponentDTO = (EmployeeSalaryComponentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeSalaryComponentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSalaryComponentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", value=" + getValue() +
            ", componentTypeFlag='" + getComponentTypeFlag() + "'" +
            ", catagory='" + getCatagory() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            "}";
    }

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
