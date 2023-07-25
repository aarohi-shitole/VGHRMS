package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.EmployeeSalaryComponent} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.EmployeeSalaryComponentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /salary-component-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeSalaryComponentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter type;

    private DoubleFilter value;

    private StringFilter componentTypeFlag;

    private StringFilter catagory;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;
    
    private LongFilter employeeId;

    private Boolean distinct;

    public EmployeeSalaryComponentCriteria() {}

    public EmployeeSalaryComponentCriteria(EmployeeSalaryComponentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.componentTypeFlag = other.componentTypeFlag == null ? null : other.componentTypeFlag.copy();
        this.catagory = other.catagory == null ? null : other.catagory.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeSalaryComponentCriteria copy() {
        return new EmployeeSalaryComponentCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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

    public StringFilter getComponentTypeFlag() {
        return componentTypeFlag;
    }

    public StringFilter componentTypeFlag() {
        if (componentTypeFlag == null) {
            componentTypeFlag = new StringFilter();
        }
        return componentTypeFlag;
    }

    public void setComponentTypeFlag(StringFilter componentTypeFlag) {
        this.componentTypeFlag = componentTypeFlag;
    }

    public StringFilter getCatagory() {
        return catagory;
    }

    public StringFilter catagory() {
        if (catagory == null) {
            catagory = new StringFilter();
        }
        return catagory;
    }

    public void setCatagory(StringFilter catagory) {
        this.catagory = catagory;
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
    
    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
        	employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
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
        final EmployeeSalaryComponentCriteria that = (EmployeeSalaryComponentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(value, that.value) &&
            Objects.equals(componentTypeFlag, that.componentTypeFlag) &&
            Objects.equals(catagory, that.catagory) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, value, componentTypeFlag, catagory, status, companyId, lastModified, lastModifiedBy, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSalaryComponentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (componentTypeFlag != null ? "componentTypeFlag=" + componentTypeFlag + ", " : "") +
            (catagory != null ? "catagory=" + catagory + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
