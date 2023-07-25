package com.techvg.hrms.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.hrms.domain.Contacts} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactsDTO implements Serializable {

    private Long id;

    private String name;

    private String contactPref;

    private String contactType;

    private String contact;

    private String refTable;

    private Long refTableId;

    private Long companyId;

    private String status;

    private Instant lastModified;

    private String lastModifiedBy;
    
    private String contactReference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPref() {
        return contactPref;
    }

    public void setContactPref(String contactPref) {
        this.contactPref = contactPref;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return refTableId;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
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

    public String getContactReference() {
        return contactReference;
    }

    public void setContactReference(String contactReference) {
        this.contactReference = contactReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactsDTO)) {
            return false;
        }

        ContactsDTO contactsDTO = (ContactsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactPref='" + getContactPref() + "'" +
            ", contactType='" + getContactType() + "'" +
            ", contact='" + getContact() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", contactReference='" + getContactReference() + "'" +
            "}";
    }
}
