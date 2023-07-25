package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import com.techvg.hrms.domain.Approvar;

/**
 * A DTO for the {@link com.techvg.hrms.domain.ApprovalSetting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalSettingDTO implements Serializable {

    private Long id;

    private String type;

    private Integer approvalCategory;

    private Long companyId;

    private Long departmentId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getApprovalCategory() {
        return approvalCategory;
    }

    public void setApprovalCategory(Integer approvalCategory) {
        this.approvalCategory = approvalCategory;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalSettingDTO)) {
            return false;
        }

        ApprovalSettingDTO approvalSettingDTO = (ApprovalSettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalSettingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalSettingDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", approvalCategory=" + getApprovalCategory() +
            ", companyId=" + getCompanyId() +
            ", departmentId=" + getDepartmentId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
