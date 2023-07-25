package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.hrms.domain.State} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StateDTO implements Serializable {

    private Long id;

    @NotNull
    private String stateName;

    private Long lgdCode;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getLgdCode() {
        return lgdCode;
    }

    public void setLgdCode(Long lgdCode) {
        this.lgdCode = lgdCode;
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
        if (!(o instanceof StateDTO)) {
            return false;
        }

        StateDTO stateDTO = (StateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateDTO{" +
            "id=" + getId() +
            ", stateName='" + getStateName() + "'" +
            ", lgdCode=" + getLgdCode() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
