package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.EmployeeLeaveAccount;
import com.techvg.hrms.repository.EmployeeLeaveAccountRepository;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.criteria.EmployeeLeaveAccountCriteria;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.mapper.EmployeeLeaveAccountMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmployeeLeaveAccount}
 * entities in the database. The main input is a
 * {@link EmployeeLeaveAccountCriteria} which gets converted to
 * {@link Specification}, in a way that all the filters must apply. It returns a
 * {@link List} of {@link EmployeeLeaveAccountDTO} or a {@link Page} of
 * {@link EmployeeLeaveAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeLeaveAccountQueryService extends QueryService<EmployeeLeaveAccount> {

	private final Logger log = LoggerFactory.getLogger(EmployeeLeaveAccountQueryService.class);

	private final EmployeeLeaveAccountRepository employeeLeaveAccountRepository;

	private final EmployeeLeaveAccountMapper employeeLeaveAccountMapper;

	@Autowired
	private DefaultCriteria defaultCriteria;

	public EmployeeLeaveAccountQueryService(EmployeeLeaveAccountRepository employeeLeaveAccountRepository,
			EmployeeLeaveAccountMapper employeeLeaveAccountMapper) {
		this.employeeLeaveAccountRepository = employeeLeaveAccountRepository;
		this.employeeLeaveAccountMapper = employeeLeaveAccountMapper;
	}

	/**
	 * Return a {@link List} of {@link EmployeeLeaveAccountDTO} which matches the
	 * criteria from the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<EmployeeLeaveAccountDTO> findByCriteria(EmployeeLeaveAccountCriteria criteria) {
		criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
		log.debug("find by criteria : {}", criteria);
		final Specification<EmployeeLeaveAccount> specification = createSpecification(criteria);
		return employeeLeaveAccountMapper.toDto(employeeLeaveAccountRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link EmployeeLeaveAccountDTO} which matches the
	 * criteria from the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<EmployeeLeaveAccountDTO> findByCriteria(EmployeeLeaveAccountCriteria criteria, Pageable page) {
		criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<EmployeeLeaveAccount> specification = createSpecification(criteria);
		return employeeLeaveAccountRepository.findAll(specification, page).map(employeeLeaveAccountMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(EmployeeLeaveAccountCriteria criteria) {
		criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
		log.debug("count by criteria : {}", criteria);
		final Specification<EmployeeLeaveAccount> specification = createSpecification(criteria);
		return employeeLeaveAccountRepository.count(specification);
	}

	/**
	 * Function to convert {@link EmployeeLeaveAccountCriteria} to a
	 * {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<EmployeeLeaveAccount> createSpecification(EmployeeLeaveAccountCriteria criteria) {
		Specification<EmployeeLeaveAccount> specification = Specification.where(null);
		if (criteria != null) {
			// This has to be called first, because the distinct method returns null
			if (criteria.getDistinct() != null) {
				specification = specification.and(distinct(criteria.getDistinct()));
			}
			if (criteria.getId() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeLeaveAccount_.id));
			}
			if (criteria.getCarriedLeaves() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCarriedLeaves(), EmployeeLeaveAccount_.carriedLeaves));
			}
			if (criteria.getCreditedLeaves() != null) {
				specification = specification.and(
						buildRangeSpecification(criteria.getCreditedLeaves(), EmployeeLeaveAccount_.creditedLeaves));
			}
			if (criteria.getDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getDate(), EmployeeLeaveAccount_.date));
			}
			if (criteria.getBalance() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getBalance(), EmployeeLeaveAccount_.balance));
			}
			if (criteria.getStatus() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getStatus(), EmployeeLeaveAccount_.status));
			}
			if (criteria.getCompanyId() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCompanyId(), EmployeeLeaveAccount_.companyId));
			}
			if (criteria.getLastModified() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModified(), EmployeeLeaveAccount_.lastModified));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification.and(
						buildStringSpecification(criteria.getLastModifiedBy(), EmployeeLeaveAccount_.lastModifiedBy));
			}
			if (criteria.getLeaveTypeId() != null) {
				specification = specification.and(buildSpecification(criteria.getLeaveTypeId(),
						root -> root.join(EmployeeLeaveAccount_.leaveType, JoinType.LEFT).get(LeaveType_.id)));
			}
			if (criteria.getEmployeeId() != null) {
				specification = specification.and(buildSpecification(criteria.getEmployeeId(),
						root -> root.join(EmployeeLeaveAccount_.employee, JoinType.LEFT).get(Employee_.id)));
			}
		}
		return specification;
	}
}
