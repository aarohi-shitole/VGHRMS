package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.TimeSheet} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.TimeSheetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /time-sheets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TimeSheetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter time;

    private InstantFilter date;

    private BooleanFilter hasCheckedIn;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter attendanceId;

    private Boolean distinct;

    public TimeSheetCriteria() {}

    public TimeSheetCriteria(TimeSheetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.hasCheckedIn = other.hasCheckedIn == null ? null : other.hasCheckedIn.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.attendanceId = other.attendanceId == null ? null : other.attendanceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TimeSheetCriteria copy() {
        return new TimeSheetCriteria(this);
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

    public StringFilter getTime() {
        return time;
    }

    public StringFilter time() {
        if (time == null) {
            time = new StringFilter();
        }
        return time;
    }

    public void setTime(StringFilter time) {
        this.time = time;
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

    public LongFilter getAttendanceId() {
        return attendanceId;
    }

    public LongFilter attendanceId() {
        if (attendanceId == null) {
            attendanceId = new LongFilter();
        }
        return attendanceId;
    }

    public void setAttendanceId(LongFilter attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TimeSheetCriteria that = (TimeSheetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(time, that.time) &&
            Objects.equals(date, that.date) &&
            Objects.equals(hasCheckedIn, that.hasCheckedIn) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(attendanceId, that.attendanceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, date, hasCheckedIn, status, companyId, lastModified, lastModifiedBy, attendanceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheetCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (time != null ? "time=" + time + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (hasCheckedIn != null ? "hasCheckedIn=" + hasCheckedIn + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (attendanceId != null ? "attendanceId=" + attendanceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
