package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.ApprovalLevel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApprovalLevelDTO implements Serializable {

    private Long id;

    private Long designationId;

    private Long squence;

    private Long approvalSettingId;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public Long getSquence() {
        return squence;
    }

    public void setSquence(Long squence) {
        this.squence = squence;
    }

    public Long getApprovalSettingId() {
        return approvalSettingId;
    }

    public void setApprovalSettingId(Long approvalSettingId) {
        this.approvalSettingId = approvalSettingId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApprovalLevelDTO)) {
            return false;
        }

        ApprovalLevelDTO approvalLevelDTO = (ApprovalLevelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, approvalLevelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApprovalLevelDTO{" +
            "id=" + getId() +
            ", designationId='" + getDesignationId() + "'" +
            ", squence=" + getSquence() +
            ", approvalSettingId=" + getApprovalSettingId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
