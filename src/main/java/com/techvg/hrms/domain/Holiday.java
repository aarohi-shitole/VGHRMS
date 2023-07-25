package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Holiday.
 */
@Entity
@Table(name = "holiday")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE holiday SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Holiday extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "holiday_name")
    private String holidayName;

    @Column(name = "holiday_date")
    private Instant holidayDate;

    @Column(name = "day")
    private String day;

    @Column(name = "year")
    private Instant year;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Holiday id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayName() {
        return this.holidayName;
    }

    public Holiday holidayName(String holidayName) {
        this.setHolidayName(holidayName);
        return this;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Instant getHolidayDate() {
        return this.holidayDate;
    }

    public Holiday holidayDate(Instant holidayDate) {
        this.setHolidayDate(holidayDate);
        return this;
    }

    public void setHolidayDate(Instant holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getDay() {
        return this.day;
    }

    public Holiday day(String day) {
        this.setDay(day);
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Instant getYear() {
        return this.year;
    }

    public Holiday year(Instant year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Instant year) {
        this.year = year;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public Holiday companyId(Long companyId) {
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

    public Holiday status(String status) {
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
    //    public Holiday lastModified(Instant lastModified) {
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
    //    public Holiday lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Holiday)) {
            return false;
        }
        return id != null && id.equals(((Holiday) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Holiday{" +
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
