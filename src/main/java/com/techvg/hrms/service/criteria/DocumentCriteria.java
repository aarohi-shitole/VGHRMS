package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.hrms.domain.Document} entity. This class is used
 * in {@link com.techvg.hrms.web.rest.DocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter docType;

    private StringFilter docCategory;

    private StringFilter fileName;

    private StringFilter contentType;

    private StringFilter fileUuid;

    private StringFilter folderUuid;

    private StringFilter refTable;

    private LongFilter refTableId;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public DocumentCriteria() {}

    public DocumentCriteria(DocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.docType = other.docType == null ? null : other.docType.copy();
        this.docCategory = other.docCategory == null ? null : other.docCategory.copy();
        this.fileName = other.fileName == null ? null : other.fileName.copy();
        this.contentType = other.contentType == null ? null : other.contentType.copy();
        this.fileUuid = other.fileUuid == null ? null : other.fileUuid.copy();
        this.folderUuid = other.folderUuid == null ? null : other.folderUuid.copy();
        this.refTable = other.refTable == null ? null : other.refTable.copy();
        this.refTableId = other.refTableId == null ? null : other.refTableId.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DocumentCriteria copy() {
        return new DocumentCriteria(this);
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

    public StringFilter getDocType() {
        return docType;
    }

    public StringFilter docType() {
        if (docType == null) {
            docType = new StringFilter();
        }
        return docType;
    }

    public void setDocType(StringFilter docType) {
        this.docType = docType;
    }

    public StringFilter getDocCategory() {
        return docCategory;
    }

    public StringFilter docCategory() {
        if (docCategory == null) {
            docCategory = new StringFilter();
        }
        return docCategory;
    }

    public void setDocCategory(StringFilter docCategory) {
        this.docCategory = docCategory;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public StringFilter fileName() {
        if (fileName == null) {
            fileName = new StringFilter();
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getContentType() {
        return contentType;
    }

    public StringFilter contentType() {
        if (contentType == null) {
            contentType = new StringFilter();
        }
        return contentType;
    }

    public void setContentType(StringFilter contentType) {
        this.contentType = contentType;
    }

    public StringFilter getFileUuid() {
        return fileUuid;
    }

    public StringFilter fileUuid() {
        if (fileUuid == null) {
            fileUuid = new StringFilter();
        }
        return fileUuid;
    }

    public void setFileUuid(StringFilter fileUuid) {
        this.fileUuid = fileUuid;
    }

    public StringFilter getFolderUuid() {
        return folderUuid;
    }

    public StringFilter folderUuid() {
        if (folderUuid == null) {
            folderUuid = new StringFilter();
        }
        return folderUuid;
    }

    public void setFolderUuid(StringFilter folderUuid) {
        this.folderUuid = folderUuid;
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
        final DocumentCriteria that = (DocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(docType, that.docType) &&
            Objects.equals(docCategory, that.docCategory) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(contentType, that.contentType) &&
            Objects.equals(fileUuid, that.fileUuid) &&
            Objects.equals(folderUuid, that.folderUuid) &&
            Objects.equals(refTable, that.refTable) &&
            Objects.equals(refTableId, that.refTableId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            docType,
            docCategory,
            fileName,
            contentType,
            fileUuid,
            folderUuid,
            refTable,
            refTableId,
            status,
            companyId,
            lastModified,
            lastModifiedBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (docType != null ? "docType=" + docType + ", " : "") +
            (docCategory != null ? "docCategory=" + docCategory + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (contentType != null ? "contentType=" + contentType + ", " : "") +
            (fileUuid != null ? "fileUuid=" + fileUuid + ", " : "") +
            (folderUuid != null ? "folderUuid=" + folderUuid + ", " : "") +
            (refTable != null ? "refTable=" + refTable + ", " : "") +
            (refTableId != null ? "refTableId=" + refTableId + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
