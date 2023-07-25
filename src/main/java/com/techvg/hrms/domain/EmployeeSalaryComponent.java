package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A EmployeeSalaryComponent.
 */
@Entity
@Table(name = "employee_salary_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE employee_salary_component SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class EmployeeSalaryComponent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Double value;

    @Column(name = "component_type_flag")
    private String componentTypeFlag;

    @Column(name = "catagory")
    private String catagory;

    @Column(name = "status")
    private String status;
    
    @Column(name = "employee_id")
    private Long employeeId;

//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "last_modified")
//    private Instant lastModified;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployeeSalaryComponent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EmployeeSalaryComponent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public EmployeeSalaryComponent type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return this.value;
    }

    public EmployeeSalaryComponent value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getComponentTypeFlag() {
        return this.componentTypeFlag;
    }

    public EmployeeSalaryComponent componentTypeFlag(String componentTypeFlag) {
        this.setComponentTypeFlag(componentTypeFlag);
        return this;
    }

    public void setComponentTypeFlag(String componentTypeFlag) {
        this.componentTypeFlag = componentTypeFlag;
    }

    public String getCatagory() {
        return this.catagory;
    }

    public EmployeeSalaryComponent catagory(String catagory) {
        this.setCatagory(catagory);
        return this;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getStatus() {
        return this.status;
    }

    public EmployeeSalaryComponent status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getEmployeeId() {
        return this.employeeId;
    }

    public EmployeeSalaryComponent employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

//    public Long getCompanyId() {
//        return this.companyId;
//    }
//
//    public SalaryComponentType companyId(Long companyId) {
//        this.setCompanyId(companyId);
//        return this;
//    }
//
//    public void setCompanyId(Long companyId) {
//        this.companyId = companyId;
//    }
//
//    public Instant getLastModified() {
//        return this.lastModified;
//    }
//
//    public SalaryComponentType lastModified(Instant lastModified) {
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
//    public SalaryComponentType lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeSalaryComponent)) {
            return false;
        }
        return id != null && id.equals(((EmployeeSalaryComponent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSalaryComponent{" +
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
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}
