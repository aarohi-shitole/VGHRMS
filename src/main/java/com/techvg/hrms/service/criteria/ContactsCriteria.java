package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Contacts} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.ContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter contactPref;

    private StringFilter contactType;

    private StringFilter contact;

    private StringFilter refTable;

    private LongFilter refTableId;

    private LongFilter companyId;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;
    
    private StringFilter contactReference;

    private Boolean distinct;

    public ContactsCriteria() {}

    public ContactsCriteria(ContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.contactPref = other.contactPref == null ? null : other.contactPref.copy();
        this.contactType = other.contactType == null ? null : other.contactType.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.refTable = other.refTable == null ? null : other.refTable.copy();
        this.refTableId = other.refTableId == null ? null : other.refTableId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.contactReference = other.contactReference == null ? null : other.contactReference.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContactsCriteria copy() {
        return new ContactsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getContactPref() {
        return contactPref;
    }

    public StringFilter contactPref() {
        if (contactPref == null) {
            contactPref = new StringFilter();
        }
        return contactPref;
    }

    public void setContactPref(StringFilter contactPref) {
        this.contactPref = contactPref;
    }

    public StringFilter getContactType() {
        return contactType;
    }

    public StringFilter contactType() {
        if (contactType == null) {
            contactType = new StringFilter();
        }
        return contactType;
    }

    public void setContactType(StringFilter contactType) {
        this.contactType = contactType;
    }

    public StringFilter getContact() {
        return contact;
    }

    public StringFilter contact() {
        if (contact == null) {
            contact = new StringFilter();
        }
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public StringFilter getRefTable() {
        return refTable;
    }

    public StringFilter refTable() {
        if (refTable == null) {
            refTable = new StringFilter();
        }
        return refTable;
    }

    public void setRefTable(StringFilter refTable) {
        this.refTable = refTable;
    }

    public LongFilter getRefTableId() {
        return refTableId;
    }

    public LongFilter refTableId() {
        if (refTableId == null) {
            refTableId = new LongFilter();
        }
        return refTableId;
    }

    public void setRefTableId(LongFilter refTableId) {
        this.refTableId = refTableId;
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
    
    public StringFilter getContactReference() {
        return contactReference;
    }

    public StringFilter contactReference() {
        if (contactReference == null) {
        	contactReference = new StringFilter();
        }
        return contactReference;
    }

    public void setContactReference(StringFilter contactReference) {
        this.contactReference = contactReference;
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
        final ContactsCriteria that = (ContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contactPref, that.contactPref) &&
            Objects.equals(contactType, that.contactType) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(refTable, that.refTable) &&
            Objects.equals(refTableId, that.refTableId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(contactReference, that.contactReference) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            contactPref,
            contactType,
            contact,
            refTable,
            refTableId,
            companyId,
            status,
            lastModified,
            lastModifiedBy,
            contactReference,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (contactPref != null ? "contactPref=" + contactPref + ", " : "") +
            (contactType != null ? "contactType=" + contactType + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (refTable != null ? "refTable=" + refTable + ", " : "") +
            (refTableId != null ? "refTableId=" + refTableId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (contactReference!= null ? "contactReference=" + contactReference + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
