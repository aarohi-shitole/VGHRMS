package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A PersonalId.
 */
@Entity
@Table(name = "personal_id")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE personal_id SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class PersonalId extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "number")
    private String number;

    @Column(name = "issue_date")
    private Instant issueDate;

    @Column(name = "exp_date")
    private Instant expDate;

    @Column(name = "issuing_authority")
    private String issuingAuthority;

    @Column(name = "doc_url")
    private String docUrl;

    @Column(name = "employee_id")
    private Long employeeId;

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

    public PersonalId id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public PersonalId type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return this.number;
    }

    public PersonalId number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Instant getIssueDate() {
        return this.issueDate;
    }

    public PersonalId issueDate(Instant issueDate) {
        this.setIssueDate(issueDate);
        return this;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getExpDate() {
        return this.expDate;
    }

    public PersonalId expDate(Instant expDate) {
        this.setExpDate(expDate);
        return this;
    }

    public void setExpDate(Instant expDate) {
        this.expDate = expDate;
    }

    public String getIssuingAuthority() {
        return this.issuingAuthority;
    }

    public PersonalId issuingAuthority(String issuingAuthority) {
        this.setIssuingAuthority(issuingAuthority);
        return this;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getDocUrl() {
        return this.docUrl;
    }

    public PersonalId docUrl(String docUrl) {
        this.setDocUrl(docUrl);
        return this;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public PersonalId employeeId(Long employeeId) {
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
    //    public PersonalId companyId(Long companyId) {
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

    public PersonalId status(String status) {
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
    //    public PersonalId lastModified(Instant lastModified) {
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
    //    public PersonalId lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof PersonalId)) {
            return false;
        }
        return id != null && id.equals(((PersonalId) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalId{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", number='" + getNumber() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", expDate='" + getExpDate() + "'" +
            ", issuingAuthority='" + getIssuingAuthority() + "'" +
            ", docUrl='" + getDocUrl() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
