package com.techvg.hrms.service;

import com.techvg.hrms.domain.TechvgPermission;
import com.techvg.hrms.repository.TechvgPermissionRepository;
import com.techvg.hrms.service.dto.TechvgPermissionDTO;
import com.techvg.hrms.service.mapper.TechvgPermissionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TechvgPermission}.
 */
@Service
@Transactional
public class TechvgPermissionService {

    private final Logger log = LoggerFactory.getLogger(TechvgPermissionService.class);

    private final TechvgPermissionRepository techvgPermissionRepository;

    private final TechvgPermissionMapper techvgPermissionMapper;

    public TechvgPermissionService(TechvgPermissionRepository techvgPermissionRepository, TechvgPermissionMapper techvgPermissionMapper) {
        this.techvgPermissionRepository = techvgPermissionRepository;
        this.techvgPermissionMapper = techvgPermissionMapper;
    }

    /**
     * Save a techvgPermission.
     *
     * @param techvgPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public TechvgPermissionDTO save(TechvgPermissionDTO techvgPermissionDTO) {
        log.debug("Request to save TechvgPermission : {}", techvgPermissionDTO);
        TechvgPermission techvgPermission = techvgPermissionMapper.toEntity(techvgPermissionDTO);
        techvgPermission = techvgPermissionRepository.save(techvgPermission);
        return techvgPermissionMapper.toDto(techvgPermission);
    }

    /**
     * Update a techvgPermission.
     *
     * @param techvgPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public TechvgPermissionDTO update(TechvgPermissionDTO techvgPermissionDTO) {
        log.debug("Request to update TechvgPermission : {}", techvgPermissionDTO);
        TechvgPermission techvgPermission = techvgPermissionMapper.toEntity(techvgPermissionDTO);
        techvgPermission = techvgPermissionRepository.save(techvgPermission);
        return techvgPermissionMapper.toDto(techvgPermission);
    }

    /**
     * Partially update a techvgPermission.
     *
     * @param techvgPermissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TechvgPermissionDTO> partialUpdate(TechvgPermissionDTO techvgPermissionDTO) {
        log.debug("Request to partially update TechvgPermission : {}", techvgPermissionDTO);

        return techvgPermissionRepository
            .findById(techvgPermissionDTO.getId())
            .map(existingTechvgPermission -> {
                techvgPermissionMapper.partialUpdate(existingTechvgPermission, techvgPermissionDTO);

                return existingTechvgPermission;
            })
            .map(techvgPermissionRepository::save)
            .map(techvgPermissionMapper::toDto);
    }

    /**
     * Get all the techvgPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TechvgPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TechvgPermissions");
        return techvgPermissionRepository.findAll(pageable).map(techvgPermissionMapper::toDto);
    }

    /**
     * Get one techvgPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechvgPermissionDTO> findOne(Long id) {
        log.debug("Request to get TechvgPermission : {}", id);
        return techvgPermissionRepository.findById(id).map(techvgPermissionMapper::toDto);
    }

    /**
     * Delete the techvgPermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechvgPermission : {}", id);
        techvgPermissionRepository.deleteById(id);
    }
}
