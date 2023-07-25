package com.techvg.hrms.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.performance.domain.MasterPerformanceIndicator} entity. This class is used
 * in {@link com.mycompany.performance.web.rest.MasterPerformanceIndicatorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /master-performance-indicators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MasterPerformanceIndicatorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter category;

    private StringFilter type;

    private StringFilter name;

    private StringFilter description;

    private LongFilter weightage;

    private StringFilter status;

    private LongFilter companyId;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public MasterPerformanceIndicatorCriteria() {}

    public MasterPerformanceIndicatorCriteria(MasterPerformanceIndicatorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.weightage = other.weightage == null ? null : other.weightage.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MasterPerformanceIndicatorCriteria copy() {
        return new MasterPerformanceIndicatorCriteria(this);
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

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
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

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getWeightage() {
        return weightage;
    }

    public LongFilter weightage() {
        if (weightage == null) {
            weightage = new LongFilter();
        }
        return weightage;
    }

    public void setWeightage(LongFilter weightage) {
        this.weightage = weightage;
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
        final MasterPerformanceIndicatorCriteria that = (MasterPerformanceIndicatorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(category, that.category) &&
            Objects.equals(type, that.type) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(weightage, that.weightage) &&
            Objects.equals(status, that.status) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, type, name, description, weightage, status, companyId, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterPerformanceIndicatorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (weightage != null ? "weightage=" + weightage + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
