package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE attendance SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Attendance extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "date")
    private Instant date;

    @Column(name = "day")
    private String day;

    @Column(name = "hours")
    private String hours;
    
    @Column(name = "break_time")
    private String breakTime;
    
    @Column(name = "employee_id")
    private Long employeeId;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    @Column(name = "has_checked_in")
    private Boolean hasCheckedIn;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Attendance id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHasCheckedIn() {
        return this.hasCheckedIn;
    }

    public Attendance hasCheckedIn(Boolean hasCheckedIn) {
        this.setHasCheckedIn(hasCheckedIn);
        return this;
    }

    public void setHasCheckedIn(Boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public String getDeviceInfo() {
        return this.deviceInfo;
    }

    public Attendance deviceInfo(String deviceInfo) {
        this.setDeviceInfo(deviceInfo);
        return this;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Attendance latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Attendance longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Instant getDate() {
        return this.date;
    }

    public Attendance date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getDay() {
        return this.day;
    }

    public Attendance day(String day) {
        this.setDay(day);
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return this.hours;
    }

    public Attendance hours(String hours) {
        this.setHours(hours);
        return this;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Attendance employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public Attendance companyId(Long companyId) {
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

    public Attendance status(String status) {
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
    //    public Attendance lastModified(Instant lastModified) {
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
    //    public Attendance lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public String getBreakTime() {
		return breakTime;
	}
    
    public Attendance breakTime(String breakTime) {
        this.setBreakTime(breakTime);
        return this;
    }

	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendance{" +
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
