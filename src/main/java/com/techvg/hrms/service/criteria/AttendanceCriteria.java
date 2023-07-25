package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Attendance} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.AttendanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attendances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttendanceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter deviceInfo;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private InstantFilter date;

    private StringFilter day;

    private StringFilter hours;
    
    private StringFilter breakTime;

    private LongFilter employeeId;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter hasCheckedIn; 

    private Boolean distinct;

    public AttendanceCriteria() {}

    public AttendanceCriteria(AttendanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deviceInfo = other.deviceInfo == null ? null : other.deviceInfo.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.breakTime = other.breakTime == null ? null : other.breakTime.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.hasCheckedIn = other.hasCheckedIn == null ? null : other.hasCheckedIn.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AttendanceCriteria copy() {
        return new AttendanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDeviceInfo() {
        return deviceInfo;
    }

    public StringFilter deviceInfo() {
        if (deviceInfo == null) {
            deviceInfo = new StringFilter();
        }
        return deviceInfo;
    }

    public void setDeviceInfo(StringFilter deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public DoubleFilter latitude() {
        if (latitude == null) {
            latitude = new DoubleFilter();
        }
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            longitude = new DoubleFilter();
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public InstantFilter getDate() {
        return date;
    }

    public InstantFilter date() {
        if (date == null) {
            date = new InstantFilter();
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getDay() {
        return day;
    }

    public StringFilter day() {
        if (day == null) {
            day = new StringFilter();
        }
        return day;
    }

    public void setDay(StringFilter day) {
        this.day = day;
    }

    public StringFilter getHours() {
        return hours;
    }

    public StringFilter hours() {
        if (hours == null) {
            hours = new StringFilter();
        }
        return hours;
    }

    public void setHours(StringFilter hours) {
        this.hours = hours;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public BooleanFilter getHasCheckedIn() {
        return hasCheckedIn;
    }

    public BooleanFilter hasCheckedIn() {
        if (hasCheckedIn == null) {
            hasCheckedIn = new BooleanFilter();
        }
        return hasCheckedIn;
    }

    public void setHasCheckedIn(BooleanFilter hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    public StringFilter getBreakTime() {
		return breakTime;
	}
    
    public StringFilter breakTime() {
        if (breakTime == null) {
        	breakTime = new StringFilter();
        }
        return breakTime;
    }

	public void setBreakTime(StringFilter breakTime) {
		this.breakTime = breakTime;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceCriteria that = (AttendanceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(deviceInfo, that.deviceInfo) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(date, that.date) &&
            Objects.equals(day, that.day) &&
            Objects.equals(hours, that.hours) &&
            Objects.equals(breakTime, that.breakTime) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(hasCheckedIn, that.hasCheckedIn) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            deviceInfo,
            latitude,
            longitude,
            date,
            day,
            hours,
            breakTime,
            employeeId,
            companyId,
            status,
            lastModified,
            lastModifiedBy,
            hasCheckedIn,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (deviceInfo != null ? "deviceInfo=" + deviceInfo + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (hours != null ? "hours=" + hours + ", " : "") +
            (breakTime != null ? "breakTime=" + breakTime + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (hasCheckedIn != null ? "hasCheckedIn=" + hasCheckedIn + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
