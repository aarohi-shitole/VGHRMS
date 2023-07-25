package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.User;
import com.techvg.hrms.repository.UserRepository;
import com.techvg.hrms.service.criteria.AdminUserCriteria;
import com.techvg.hrms.service.dto.AdminUserDTO;
import com.techvg.hrms.service.mapper.UserMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link User} entities in the
 * database. The main input is a {@link AdminUserCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It
 * returns a {@link List} of {@link AdminUserDTO} or a {@link Page} of
 * {@link AdminUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

	private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	public UserQueryService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	/**
	 * Return a {@link List} of {@link AdminUserDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<AdminUserDTO> findByCriteria(AdminUserCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<User> specification = createSpecification(criteria);
		return userMapper.usersToAdminUserDTOs(userRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link AdminUserDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<AdminUserDTO> findByCriteria(AdminUserCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<User> specification = createSpecification(criteria);
		Page<AdminUserDTO> userdetails = userRepository.findAll(specification, page)
				.map(userMapper::userToAdminUserDTO);
		return userdetails;
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(AdminUserCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<User> specification = createSpecification(criteria);
		return userRepository.count(specification);
	}

	/**
	 * d Function to convert {@link AdminUserCriteria} to a {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<User> createSpecification(AdminUserCriteria criteria) {
		Specification<User> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildStringSpecification(criteria.getId(), User_.id));
			}
			if (criteria.getLogin() != null) {
				specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
			}
			if (criteria.getFirstName() != null) {
				specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
			}
			if (criteria.getLastName() != null) {
				specification = specification.and(buildStringSpecification(criteria.getLastName(), User_.lastName));
			}
			if (criteria.email() != null) {
				specification = specification.and(buildStringSpecification(criteria.email(), User_.email));
			}
			if (criteria.imageUrl() != null) {
				specification = specification.and(buildStringSpecification(criteria.getImageUrl(), User_.imageUrl));
			}
			if (criteria.getLangKey() != null) {
				specification = specification.and(buildStringSpecification(criteria.getLangKey(), User_.langKey));
			}
			if (criteria.getActivated() != null) {
				specification = specification.and(buildSpecification(criteria.getActivated(), User_.activated));
			}
			if (criteria.createdBy() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), User_.createdBy));
			}
			if (criteria.getEmployeeId() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getEmployeeId(), User_.employeeId));
			}
			if (criteria.getCompanyId() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), User_.companyId));
			}
			if (criteria.getLastModified() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModified(), User_.lastModifiedDate));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastModifiedBy(), User_.lastModifiedBy));
			}
		}
		return specification;
	}

}
