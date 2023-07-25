package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.TimeSheet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimeSheetDTO implements Serializable {

    private Long id;

    private String time;

    private Instant date;

    private Boolean hasCheckedIn;

    private String status;

    private Long companyId;

    private Instant lastModified;

    private String lastModifiedBy;

    private AttendanceDTO attendance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getHasCheckedIn() {
        return hasCheckedIn;
    }

    public void setHasCheckedIn(Boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
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

    public AttendanceDTO getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceDTO attendance) {
        this.attendance = attendance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheetDTO)) {
            return false;
        }

        TimeSheetDTO timeSheetDTO = (TimeSheetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timeSheetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheetDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", date='" + getDate() + "'" +
            ", hasCheckedIn='" + getHasCheckedIn() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", attendance=" + getAttendance() +
            "}";
    }
}
