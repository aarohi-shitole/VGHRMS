package com.techvg.hrms.service;

import com.techvg.hrms.domain.TechvgRole;
import com.techvg.hrms.repository.TechvgRoleRepository;
import com.techvg.hrms.service.dto.TechvgRoleDTO;
import com.techvg.hrms.service.mapper.TechvgRoleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TechvgRole}.
 */
@Service
@Transactional
public class TechvgRoleService {

    private final Logger log = LoggerFactory.getLogger(TechvgRoleService.class);

    private final TechvgRoleRepository techvgRoleRepository;

    private final TechvgRoleMapper techvgRoleMapper;

    public TechvgRoleService(TechvgRoleRepository techvgRoleRepository, TechvgRoleMapper techvgRoleMapper) {
        this.techvgRoleRepository = techvgRoleRepository;
        this.techvgRoleMapper = techvgRoleMapper;
    }

    /**
     * Save a techvgRole.
     *
     * @param techvgRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public TechvgRoleDTO save(TechvgRoleDTO techvgRoleDTO) {
        log.debug("Request to save TechvgRole : {}", techvgRoleDTO);
        TechvgRole techvgRole = techvgRoleMapper.toEntity(techvgRoleDTO);
        techvgRole = techvgRoleRepository.save(techvgRole);
        return techvgRoleMapper.toDto(techvgRole);
    }

    /**
     * Update a techvgRole.
     *
     * @param techvgRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public TechvgRoleDTO update(TechvgRoleDTO techvgRoleDTO) {
        log.debug("Request to update TechvgRole : {}", techvgRoleDTO);
        TechvgRole techvgRole = techvgRoleMapper.toEntity(techvgRoleDTO);
        techvgRole = techvgRoleRepository.save(techvgRole);
        return techvgRoleMapper.toDto(techvgRole);
    }

    /**
     * Partially update a techvgRole.
     *
     * @param techvgRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TechvgRoleDTO> partialUpdate(TechvgRoleDTO techvgRoleDTO) {
        log.debug("Request to partially update TechvgRole : {}", techvgRoleDTO);

        return techvgRoleRepository
            .findById(techvgRoleDTO.getId())
            .map(existingTechvgRole -> {
                techvgRoleMapper.partialUpdate(existingTechvgRole, techvgRoleDTO);

                return existingTechvgRole;
            })
            .map(techvgRoleRepository::save)
            .map(techvgRoleMapper::toDto);
    }

    /**
     * Get all the techvgRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TechvgRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TechvgRoles");
        return techvgRoleRepository.findAll(pageable).map(techvgRoleMapper::toDto);
    }

    /**
     * Get all the techvgRoles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TechvgRoleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return techvgRoleRepository.findAllWithEagerRelationships(pageable).map(techvgRoleMapper::toDto);
    }

    /**
     * Get one techvgRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechvgRoleDTO> findOne(Long id) {
        log.debug("Request to get TechvgRole : {}", id);
        return techvgRoleRepository.findOneWithEagerRelationships(id).map(techvgRoleMapper::toDto);
    }

    /**
     * Delete the techvgRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechvgRole : {}", id);
        techvgRoleRepository.deleteById(id);
    }
}
