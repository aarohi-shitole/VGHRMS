package com.techvg.hrms.service;

import com.techvg.hrms.domain.Salary;
import com.techvg.hrms.repository.SalaryRepository;
import com.techvg.hrms.service.dto.SalaryDTO;
import com.techvg.hrms.service.mapper.SalaryMapper;

import java.lang.reflect.Field;
import java.util.Optional;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Salary}.
 */
@Service
@Transactional
public class SalaryService {

    private final Logger log = LoggerFactory.getLogger(SalaryService.class);

    private final SalaryRepository salaryRepository;

    private final SalaryMapper salaryMapper;
    
    @Autowired
    private ValidationService validationService;

    public SalaryService(SalaryRepository salaryRepository, SalaryMapper salaryMapper) {
        this.salaryRepository = salaryRepository;
        this.salaryMapper = salaryMapper;
    }

    /**
     * Save a salary.
     *
     * @param salaryDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaryDTO save(SalaryDTO salaryDTO) {
        log.debug("Request to save Salary : {}", salaryDTO);
        Salary salary = salaryMapper.toEntity(salaryDTO);
        validationService.validateMethod(salary);
        salary = salaryRepository.save(salary);
        return salaryMapper.toDto(salary);
    }

    /**
     * Update a salary.
     *
     * @param salaryDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaryDTO update(SalaryDTO salaryDTO) {
        log.debug("Request to update Salary : {}", salaryDTO);
        Salary salary = salaryMapper.toEntity(salaryDTO);
        validationService.validateMethod(salary);
        salary = salaryRepository.save(salary);
        return salaryMapper.toDto(salary);
    }

    /**
     * Partially update a salary.
     *
     * @param salaryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalaryDTO> partialUpdate(SalaryDTO salaryDTO) {
        log.debug("Request to partially update Salary : {}", salaryDTO);

        return salaryRepository
            .findById(salaryDTO.getId())
            .map(existingSalary -> {
                salaryMapper.partialUpdate(existingSalary, salaryDTO);

                return existingSalary;
            })
            .map(salaryRepository::save)
            .map(salaryMapper::toDto);
    }

    /**
     * Get all the salaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salaries");
        return salaryRepository.findAll(pageable).map(salaryMapper::toDto);
    }

    /**
     * Get one salary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalaryDTO> findOne(Long id) {
        log.debug("Request to get Salary : {}", id);
        return salaryRepository.findById(id).map(salaryMapper::toDto);
    }

    /**
     * Delete the salary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Salary : {}", id);
        salaryRepository.deleteById(id);
    }
}
