package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A TimeSheet.
 */
@Entity
@Table(name = "time_sheet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE time_sheet SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class TimeSheet extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name = "date")
    private Instant date;

    @Column(name = "has_checked_in")
    private Boolean hasCheckedIn;

    //    @Column(name = "company_id")
    //    private Long companyId;

    @Column(name = "status")
    private String status;

    //    @Column(name = "last_modified")
    //    private Instant lastModified;
    //
    //    @Column(name = "last_modified_by")
    //    private String lastModifiedBy;

    @ManyToOne
    private Attendance attendance;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TimeSheet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return this.time;
    }

    public TimeSheet time(String time) {
        this.setTime(time);
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Instant getDate() {
        return this.date;
    }

    public TimeSheet date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getHasCheckedIn() {
        return this.hasCheckedIn;
    }

    public TimeSheet hasCheckedIn(Boolean hasCheckedIn) {
        this.setHasCheckedIn(hasCheckedIn);
        return this;
    }

    public void setHasCheckedIn(Boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public TimeSheet companyId(Long companyId) {
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

    public TimeSheet status(String status) {
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
    //    public TimeSheet lastModified(Instant lastModified) {
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
    //    public TimeSheet lastModifiedBy(String lastModifiedBy) {
    //        this.setLastModifiedBy(lastModifiedBy);
    //        return this;
    //    }
    //
    //    public void setLastModifiedBy(String lastModifiedBy) {
    //        this.lastModifiedBy = lastModifiedBy;
    //    }

    public Attendance getAttendance() {
        return this.attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public TimeSheet attendance(Attendance attendance) {
        this.setAttendance(attendance);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheet)) {
            return false;
        }
        return id != null && id.equals(((TimeSheet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheet{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", date='" + getDate() + "'" +
            ", hasCheckedIn='" + getHasCheckedIn() + "'" +
            ", status='" + getStatus() + "'" +
            ", companyId=" + getCompanyId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
