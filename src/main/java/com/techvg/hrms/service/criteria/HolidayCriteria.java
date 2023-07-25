package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Holiday} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.HolidayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /holidays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HolidayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter holidayName;

    private InstantFilter holidayDate;

    private StringFilter day;

    private InstantFilter year;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public HolidayCriteria() {}

    public HolidayCriteria(HolidayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.holidayName = other.holidayName == null ? null : other.holidayName.copy();
        this.holidayDate = other.holidayDate == null ? null : other.holidayDate.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HolidayCriteria copy() {
        return new HolidayCriteria(this);
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

    public StringFilter getHolidayName() {
        return holidayName;
    }

    public StringFilter holidayName() {
        if (holidayName == null) {
            holidayName = new StringFilter();
        }
        return holidayName;
    }

    public void setHolidayName(StringFilter holidayName) {
        this.holidayName = holidayName;
    }

    public InstantFilter getHolidayDate() {
        return holidayDate;
    }

    public InstantFilter holidayDate() {
        if (holidayDate == null) {
            holidayDate = new InstantFilter();
        }
        return holidayDate;
    }

    public void setHolidayDate(InstantFilter holidayDate) {
        this.holidayDate = holidayDate;
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

    public InstantFilter getYear() {
        return year;
    }

    public InstantFilter year() {
        if (year == null) {
            year = new InstantFilter();
        }
        return year;
    }

    public void setYear(InstantFilter year) {
        this.year = year;
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
        final HolidayCriteria that = (HolidayCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(holidayName, that.holidayName) &&
            Objects.equals(holidayDate, that.holidayDate) &&
            Objects.equals(day, that.day) &&
            Objects.equals(year, that.year) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holidayName, holidayDate, day, year, companyId, status, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HolidayCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (holidayName != null ? "holidayName=" + holidayName + ", " : "") +
            (holidayDate != null ? "holidayDate=" + holidayDate + ", " : "") +
            (day != null ? "day=" + day + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
