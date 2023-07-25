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
import com.techvg.hrms.domain.AssetInventory;
import com.techvg.hrms.domain.AssetInventory_;
import com.techvg.hrms.repository.AssetInventoryRepository;
import com.techvg.hrms.service.criteria.AssetInventoryCriteria;
import com.techvg.hrms.service.criteria.DefaultCriteria;
import com.techvg.hrms.service.dto.AssetInventoryDTO;
import com.techvg.hrms.service.mapper.AssetInventoryMapper;

import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AssetInventory} entities in
 * the database. The main input is a {@link AssetInventoryCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetInventoryDTO} or a {@link Page} of
 * {@link AssetInventoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetInventoryQueryService extends QueryService<AssetInventory> {

	private final Logger log = LoggerFactory.getLogger(AssetInventoryQueryService.class);

	private final AssetInventoryRepository assetInventoryRepository;

	private final AssetInventoryMapper assetInventoryMapper;

	@Autowired
	private DefaultCriteria defaultCriteria;

	public AssetInventoryQueryService(AssetInventoryRepository assetInventoryRepository,
			AssetInventoryMapper assetInventoryMapper) {
		this.assetInventoryRepository = assetInventoryRepository;
		this.assetInventoryMapper = assetInventoryMapper;
	}

	/**
	 * Return a {@link List} of {@link AssetInventoryDTO} which matches the criteria
	 * from the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<AssetInventoryDTO> findByCriteria(AssetInventoryCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
		final Specification<AssetInventory> specification = createSpecification(criteria);
		return assetInventoryMapper.toDto(assetInventoryRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link AssetInventoryDTO} which matches the criteria
	 * from the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<AssetInventoryDTO> findByCriteria(AssetInventoryCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
		final Specification<AssetInventory> specification = createSpecification(criteria);
		return assetInventoryRepository.findAll(specification, page).map(assetInventoryMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(AssetInventoryCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		criteria.setCompanyId(defaultCriteria.getDefaultCompanyId());
		final Specification<AssetInventory> specification = createSpecification(criteria);
		return assetInventoryRepository.count(specification);
	}

	/**
	 * Function to convert {@link AssetInventoryCriteria} to a {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<AssetInventory> createSpecification(AssetInventoryCriteria criteria) {
		Specification<AssetInventory> specification = Specification.where(null);
		if (criteria != null) {
			// This has to be called first, because the distinct method returns null
			if (criteria.getDistinct() != null) {
				specification = specification.and(distinct(criteria.getDistinct()));
			}
			if (criteria.getId() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getId(), AssetInventory_.id));
			}
			if (criteria.getAssetName() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getAssetName(), AssetInventory_.assetName));
			}
			if (criteria.getAssetype() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getAssetype(), AssetInventory_.assetype));
			}
			if (criteria.getAssetId() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getAssetId(), AssetInventory_.assetId));
			}
			if (criteria.getPurchaseFrom() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getPurchaseFrom(), AssetInventory_.purchaseFrom));
			}
			if (criteria.getPurchaseTo() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getPurchaseTo(), AssetInventory_.purchaseTo));
			}
			if (criteria.getManufacturer() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getManufacturer(), AssetInventory_.manufacturer));
			}
			if (criteria.getModel() != null) {
				specification = specification.and(buildStringSpecification(criteria.getModel(), AssetInventory_.model));
			}
			if (criteria.getProductNumber() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getProductNumber(), AssetInventory_.productNumber));
			}
			if (criteria.getSupplier() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getSupplier(), AssetInventory_.supplier));
			}
			if (criteria.getWarrantyInMonths() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getWarrantyInMonths(), AssetInventory_.warrantyInMonths));
			}
			if (criteria.getCondition() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCondition(), AssetInventory_.condition));
			}
			if (criteria.getValue() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getValue(), AssetInventory_.value));
			}
			if (criteria.getDescription() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getDescription(), AssetInventory_.description));
			}
			if (criteria.getAssetStatus() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getAssetStatus(), AssetInventory_.assetStatus));
			}
			if (criteria.getAssetUserId() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getAssetUserId(), AssetInventory_.assetUserId));
			}
			if (criteria.getStatus() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getStatus(), AssetInventory_.status));
			}
			if (criteria.getSubmittedAmt() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getSubmittedAmt(), AssetInventory_.submittedAmt));
			}
			if (criteria.getRefundAmt() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getRefundAmt(), AssetInventory_.refundAmt));
			}
			if (criteria.getFineAmt() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getFineAmt(), AssetInventory_.fineAmt));
			}
			if (criteria.getCompanyId() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCompanyId(), AssetInventory_.companyId));
			}
			if (criteria.getLastModified() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModified(), AssetInventory_.lastModified));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastModifiedBy(), AssetInventory_.lastModifiedBy));
			}
		}
		return specification;
	}
}
