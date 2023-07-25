package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.hrms.domain.TechvgRole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechvgRoleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private Set<TechvgPermissionDTO> techvgPermissions = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<TechvgPermissionDTO> getTechvgPermissions() {
        return techvgPermissions;
    }

    public void setTechvgPermissions(Set<TechvgPermissionDTO> techvgPermissions) {
        this.techvgPermissions = techvgPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechvgRoleDTO)) {
            return false;
        }

        TechvgRoleDTO techvgRoleDTO = (TechvgRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, techvgRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechvgRoleDTO{" +
            "id=" + getId() +
            ", roleName='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", techvgPermissions=" + getTechvgPermissions() +
            "}";
    }
}
