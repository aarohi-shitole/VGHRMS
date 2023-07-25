package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Attendance} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttendanceDTO implements Serializable {

    private Long id;

    private String deviceInfo;

    private Double latitude;

    private Double longitude;

    private Instant date;

    private String day;

    private String hours;
    
    private String breakTime;

    private Long employeeId;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;

    private Boolean hasCheckedIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHasCheckedIn() {
        return hasCheckedIn;
    }

    public void setHasCheckedIn(Boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public String getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceDTO)) {
            return false;
        }

        AttendanceDTO attendanceDTO = (AttendanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attendanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceDTO{" +
            "id=" + getId() +
            ", deviceInfo='" + getDeviceInfo() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", date='" + getDate() + "'" +
            ", day='" + getDay() + "'" +
            ", hours='" + getHours() + "'" +
            ", breakTime='" + getBreakTime() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            ", status='" + getStatus() + "'" +
            ", hasCheckedIn='" + getHasCheckedIn() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
