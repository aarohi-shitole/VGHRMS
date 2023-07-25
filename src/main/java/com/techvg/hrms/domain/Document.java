package com.techvg.hrms.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@SQLDelete(sql = "UPDATE document SET status='D' WHERE id=?")
@Where(clause = "status != 'D'")
public class Document extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "doc_category")
    private String docCategory;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_uuid")
    private String fileUuid;

    @Column(name = "folder_uuid")
    private String folderUuid;

    @Column(name = "ref_table")
    private String refTable;

    @Column(name = "ref_table_id")
    private Long refTableId;

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

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocType() {
        return this.docType;
    }

    public Document docType(String docType) {
        this.setDocType(docType);
        return this;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocCategory() {
        return this.docCategory;
    }

    public Document docCategory(String docCategory) {
        this.setDocCategory(docCategory);
        return this;
    }

    public void setDocCategory(String docCategory) {
        this.docCategory = docCategory;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Document fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Document contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileUuid() {
        return this.fileUuid;
    }

    public Document fileUuid(String fileUuid) {
        this.setFileUuid(fileUuid);
        return this;
    }

    public void setFileUuid(String fileUuid) {
        this.fileUuid = fileUuid;
    }

    public String getFolderUuid() {
        return this.folderUuid;
    }

    public Document folderUuid(String folderUuid) {
        this.setFolderUuid(folderUuid);
        return this;
    }

    public void setFolderUuid(String folderUuid) {
        this.folderUuid = folderUuid;
    }

    public String getRefTable() {
        return this.refTable;
    }

    public Document refTable(String refTable) {
        this.setRefTable(refTable);
        return this;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public Long getRefTableId() {
        return this.refTableId;
    }

    public Document refTableId(Long refTableId) {
        this.setRefTableId(refTableId);
        return this;
    }

    public void setRefTableId(Long refTableId) {
        this.refTableId = refTableId;
    }

    //    public Long getCompanyId() {
    //        return this.companyId;
    //    }
    //
    //    public Document companyId(Long companyId) {
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

    public Document status(String status) {
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
    //    public Document lastModified(Instant lastModified) {
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
    //    public Document lastModifiedBy(String lastModifiedBy) {
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
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", docType='" + getDocType() + "'" +
            ", docCategory='" + getDocCategory() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", fileUuid='" + getFileUuid() + "'" +
            ", folderUuid='" + getFolderUuid() + "'" +
            ", refTable='" + getRefTable() + "'" +
            ", refTableId=" + getRefTableId() +
            ", companyId=" + getCompanyId() +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
