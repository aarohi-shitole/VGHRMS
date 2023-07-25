package com.techvg.hrms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.techvg.hrms.domain.Document;
import com.techvg.hrms.domain.Document_;
import com.techvg.hrms.repository.DocumentRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.DocumentCriteria;
import com.techvg.hrms.service.dto.DocumentDTO;
import com.techvg.hrms.service.mapper.DocumentMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Document} entities in the database.
 * The main input is a {@link DocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentDTO} or a {@link Page} of {@link DocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentQueryService extends QueryService<Document> {

    private final Logger log = LoggerFactory.getLogger(DocumentQueryService.class);

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public DocumentQueryService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentDTO> findByCriteria(DocumentCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("find by criteria : {}", criteria);
        final Specification<Document> specification = createSpecification(criteria);
        return documentMapper.toDto(documentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentDTO> findByCriteria(DocumentCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Document> specification = createSpecification(criteria);
        return documentRepository.findAll(specification, page).map(documentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("count by criteria : {}", criteria);
        final Specification<Document> specification = createSpecification(criteria);
        return documentRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Document> createSpecification(DocumentCriteria criteria) {
        Specification<Document> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Document_.id));
            }
            if (criteria.getDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocType(), Document_.docType));
            }
            if (criteria.getDocCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocCategory(), Document_.docCategory));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), Document_.fileName));
            }
            if (criteria.getContentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentType(), Document_.contentType));
            }
            if (criteria.getFileUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileUuid(), Document_.fileUuid));
            }
            if (criteria.getFolderUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFolderUuid(), Document_.folderUuid));
            }
            if (criteria.getRefTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefTable(), Document_.refTable));
            }
            if (criteria.getRefTableId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefTableId(), Document_.refTableId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Document_.status));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Document_.companyId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Document_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Document_.lastModifiedBy));
            }
        }
        return specification;
    }
}
