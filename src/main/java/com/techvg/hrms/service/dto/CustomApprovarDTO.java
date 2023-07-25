package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.CustomApprovar} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomApprovarDTO implements Serializable {

    private Long id;

    private Long employeId;

    private String approvalSettingId;

    private String squence;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Long employeId) {
        this.employeId = employeId;
    }

    public String getApprovalSettingId() {
        return approvalSettingId;
    }

    public void setApprovalSettingId(String approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
    }

    public String getSquence() {
        return squence;
    }

    public void setSquence(String squence) {
        this.squence = squence;
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
        if (!(o instanceof CustomApprovarDTO)) {
            return false;
        }

        CustomApprovarDTO customApprovarDTO = (CustomApprovarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customApprovarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomApprovarDTO{" +
            "id=" + getId() +
            ", employeId=" + getEmployeId() +
            ", approvalSettingId='" + getApprovalSettingId() + "'" +
            ", squence='" + getSquence() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
