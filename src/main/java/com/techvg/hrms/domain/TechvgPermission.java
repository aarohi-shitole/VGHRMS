package com.techvg.hrms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A TechvgPermission.
 */
@Entity
@Table(name = "techvg_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE techvg_permission SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class TechvgPermission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;
//
//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "last_modified")
//    private Instant lastModified;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    @ManyToMany(mappedBy = "techvgPermissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "techvgPermissions", "users" }, allowSetters = true)
    private Set<TechvgRole> techvgRoles = new HashSet<>();




    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TechvgPermission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public TechvgPermission permissionName(String permissionName) {
        this.setPermissionName(permissionName);
        return this;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return this.description;
    }

    public TechvgPermission description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public TechvgPermission status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Long getCompanyId() {
//        return this.companyId;
//    }
//
//    public TechvgPermission companyId(Long companyId) {
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
//    public TechvgPermission lastModified(Instant lastModified) {
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
//    public TechvgPermission lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }

    public Set<TechvgRole> getTechvgRoles() {
        return this.techvgRoles;
    }

    public void setTechvgRoles(Set<TechvgRole> techvgRoles) {
        if (this.techvgRoles != null) {
            this.techvgRoles.forEach(i -> i.removeTechvgPermission(this));
        }
        if (techvgRoles != null) {
            techvgRoles.forEach(i -> i.addTechvgPermission(this));
        }
        this.techvgRoles = techvgRoles;
    }

    public TechvgPermission techvgRoles(Set<TechvgRole> techvgRoles) {
        this.setTechvgRoles(techvgRoles);
        return this;
    }

    public TechvgPermission addTechvgRole(TechvgRole techvgRole) {
        this.techvgRoles.add(techvgRole);
        techvgRole.getTechvgPermissions().add(this);
        return this;
    }

    public TechvgPermission removeTechvgRole(TechvgRole techvgRole) {
        this.techvgRoles.remove(techvgRole);
        techvgRole.getTechvgPermissions().remove(this);
        return this;
    }


    //

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechvgPermission)) {
            return false;
        }
        return id != null && id.equals(((TechvgPermission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechvgPermission{" +
            "id=" + getId() +
            ", permissionName='" + getPermissionName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
