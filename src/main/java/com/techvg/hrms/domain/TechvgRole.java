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
 * A TechvgRole.
 */
@Entity
@Table(name = "techvg_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE techvg_role SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class TechvgRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "last_modified")
//    private Instant lastModified;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    @ManyToMany
    @JoinTable(
        name = "rel_techvg_role__techvg_permission",
        joinColumns = @JoinColumn(name = "techvg_role_id"),
        inverseJoinColumns = @JoinColumn(name = "techvg_permission_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "techvgRoles", "users" }, allowSetters = true)
    private Set<TechvgPermission> techvgPermissions = new HashSet<>();

    @ManyToMany(mappedBy = "techvgRoles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"techvgPermissions", "techvgRoles" }, allowSetters = true)
    private Set<User> users = new HashSet<>();



    //
    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TechvgRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TechvgRole name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public TechvgRole description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return this.status;
    }

    public TechvgRole status(String status) {
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
//    public TechvgRole companyId(Long companyId) {
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
//    public TechvgRole lastModified(Instant lastModified) {
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
//    public TechvgRole lastModifiedBy(String lastModifiedBy) {
//        this.setLastModifiedBy(lastModifiedBy);
//        return this;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }


    public Set<TechvgPermission> getTechvgPermissions() {
        return this.techvgPermissions;
    }

    public void setTechvgPermissions(Set<TechvgPermission> techvgPermissions) {
        this.techvgPermissions = techvgPermissions;
    }

    public TechvgRole techvgPermissions(Set<TechvgPermission> techvgPermissions) {
        this.setTechvgPermissions(techvgPermissions);
        return this;
    }

    public TechvgRole addTechvgPermission(TechvgPermission techvgPermission) {
        this.techvgPermissions.add(techvgPermission);
        techvgPermission.getTechvgRoles().add(this);
        return this;
    }

    public TechvgRole removeTechvgPermission(TechvgPermission techvgPermission) {
        this.techvgPermissions.remove(techvgPermission);
        techvgPermission.getTechvgRoles().remove(this);
        return this;
    }



    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeTechvgRole(this));
        }
        if (users != null) {
            users.forEach(i -> i.addTechvgRole(this));
        }
        this.users = users;
    }

    public TechvgRole users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public TechvgRole addUser(User user) {
        this.users.add(user);
        user.getTechvgRoles().add(this);
        return this;
    }

    public TechvgRole removeUser(User user) {
        this.users.remove(user);
        user.getTechvgRoles().remove(this);
        return this;
    }


    //
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechvgRole)) {
            return false;
        }
        return id != null && id.equals(((TechvgRole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechvgRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
