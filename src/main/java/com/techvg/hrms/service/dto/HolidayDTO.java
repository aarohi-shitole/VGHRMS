package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Holiday} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HolidayDTO implements Serializable {

    private Long id;

    private String holidayName;

    private Instant holidayDate;

    private String day;

    private Instant year;

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

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Instant getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Instant holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Instant getYear() {
        return year;
    }

    public void setYear(Instant year) {
        this.year = year;
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
        if (!(o instanceof HolidayDTO)) {
            return false;
        }

        HolidayDTO holidayDTO = (HolidayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, holidayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HolidayDTO{" +
            "id=" + getId() +
            ", holidayName='" + getHolidayName() + "'" +
            ", holidayDate='" + getHolidayDate() + "'" +
            ", day='" + getDay() + "'" +
            ", year='" + getYear() + "'" +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
