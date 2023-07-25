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
import com.techvg.hrms.domain.Contacts;
import com.techvg.hrms.domain.Contacts_;
import com.techvg.hrms.repository.ContactsRepository;
import com.techvg.hrms.service.criteria.ContactsCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.ContactsDTO;
import com.techvg.hrms.service.mapper.ContactsMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Contacts} entities in the database.
 * The main input is a {@link ContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactsDTO} or a {@link Page} of {@link ContactsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactsQueryService extends QueryService<Contacts> {

    private final Logger log = LoggerFactory.getLogger(ContactsQueryService.class);

    private final ContactsRepository contactsRepository;

    private final ContactsMapper contactsMapper;
    
    @Autowired
    private DefaultCriteria defaultCriteria;

    public ContactsQueryService(ContactsRepository contactsRepository, ContactsMapper contactsMapper) {
        this.contactsRepository = contactsRepository;
        this.contactsMapper = contactsMapper;
    }

    /**
     * Return a {@link List} of {@link ContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactsDTO> findByCriteria(ContactsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("find by criteria : {}", criteria);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsMapper.toDto(contactsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactsDTO> findByCriteria(ContactsCriteria criteria, Pageable page) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.findAll(specification, page).map(contactsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactsCriteria criteria) {
    	criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
        log.debug("count by criteria : {}", criteria);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contacts> createSpecification(ContactsCriteria criteria) {
        Specification<Contacts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contacts_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Contacts_.name));
            }
            if (criteria.getContactPref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPref(), Contacts_.contactPref));
            }
            if (criteria.getContactType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactType(), Contacts_.contactType));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), Contacts_.contact));
            }
            if (criteria.getRefTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefTable(), Contacts_.refTable));
            }
            if (criteria.getRefTableId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefTableId(), Contacts_.refTableId));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Contacts_.companyId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Contacts_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Contacts_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Contacts_.lastModifiedBy));
            }
            if (criteria.getContactReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactReference(), Contacts_.contactReference));
            }
        }
        return specification;
    }
}
