package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A MasterLookup.
 */
@Entity
@Table(name = "master_lookup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE master_lookup SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class MasterLookup extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "value_two")
    private String valueTwo;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MasterLookup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public MasterLookup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public MasterLookup value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueTwo() {
        return this.valueTwo;
    }

    public MasterLookup valueTwo(String valueTwo) {
        this.setValueTwo(valueTwo);
        return this;
    }

    public void setValueTwo(String valueTwo) {
        this.valueTwo = valueTwo;
    }

    public String getDescription() {
        return this.description;
    }

    public MasterLookup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public MasterLookup type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public MasterLookup companyId(Long companyId) {
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

    public MasterLookup status(String status) {
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
    //    public MasterLookup lastModified(Instant lastModified) {
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
    //    public MasterLookup lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof MasterLookup)) {
            return false;
        }
        return id != null && id.equals(((MasterLookup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterLookup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", valueTwo='" + getValueTwo() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
