package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A WorkDaysSetting.
 */
@Entity
@Table(name = "work_days_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE work_days_setting SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class WorkDaysSetting extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "hours")
    private String hours;

    @Column(name = "day_off")
    private Boolean dayOff;

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

    public WorkDaysSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public WorkDaysSetting day(String day) {
        this.setDay(day);
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return this.hours;
    }

    public WorkDaysSetting hours(String hours) {
        this.setHours(hours);
        return this;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public Boolean getDayOff() {
        return this.dayOff;
    }

    public WorkDaysSetting dayOff(Boolean dayOff) {
        this.setDayOff(dayOff);
        return this;
    }

    public void setDayOff(Boolean dayOff) {
        this.dayOff = dayOff;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public WorkDaysSetting companyId(Long companyId) {
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

    public WorkDaysSetting status(String status) {
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
    //    public WorkDaysSetting lastModified(Instant lastModified) {
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
    //    public WorkDaysSetting lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof WorkDaysSetting)) {
            return false;
        }
        return id != null && id.equals(((WorkDaysSetting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkDaysSetting{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", hours='" + getHours() + "'" +
            ", dayOff='" + getDayOff() + "'" +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
