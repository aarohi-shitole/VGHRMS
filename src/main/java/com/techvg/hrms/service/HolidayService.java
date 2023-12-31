package com.techvg.hrms.service;

import com.techvg.hrms.domain.Holiday;
import com.techvg.hrms.repository.HolidayRepository;
import com.techvg.hrms.service.dto.HolidayDTO;
import com.techvg.hrms.service.mapper.HolidayMapper;

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
 * Service Implementation for managing {@link Holiday}.
 */
@Service
@Transactional
public class HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayService.class);

    private final HolidayRepository holidayRepository;

    private final HolidayMapper holidayMapper;
    @Autowired
    private ValidationService validationService;
    public HolidayService(HolidayRepository holidayRepository, HolidayMapper holidayMapper) {
        this.holidayRepository = holidayRepository;
        this.holidayMapper = holidayMapper;
    }

    /**
     * Save a holiday.
     *
     * @param holidayDTO the entity to save.
     * @return the persisted entity.
     */
    public HolidayDTO save(HolidayDTO holidayDTO) {
        log.debug("Request to save Holiday : {}", holidayDTO);
       
        Holiday holiday = holidayMapper.toEntity(holidayDTO);
        validationService.validateMethod(holiday);
        holiday = holidayRepository.save(holiday);
        return holidayMapper.toDto(holiday);
    }

    /**
     * Update a holiday.
     *
     * @param holidayDTO the entity to save.
     * @return the persisted entity.
     */
    public HolidayDTO update(HolidayDTO holidayDTO) {
        log.debug("Request to update Holiday : {}", holidayDTO);
       
        Holiday holiday = holidayMapper.toEntity(holidayDTO);
        validationService.validateMethod(holiday);
        holiday = holidayRepository.save(holiday);
        return holidayMapper.toDto(holiday);
    }

    /**
     * Partially update a holiday.
     *
     * @param holidayDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HolidayDTO> partialUpdate(HolidayDTO holidayDTO) {
        log.debug("Request to partially update Holiday : {}", holidayDTO);

        return holidayRepository
            .findById(holidayDTO.getId())
            .map(existingHoliday -> {
                holidayMapper.partialUpdate(existingHoliday, holidayDTO);

                return existingHoliday;
            })
            .map(holidayRepository::save)
            .map(holidayMapper::toDto);
    }

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HolidayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Holidays");
        return holidayRepository.findAll(pageable).map(holidayMapper::toDto);
    }

    /**
     * Get one holiday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HolidayDTO> findOne(Long id) {
        log.debug("Request to get Holiday : {}", id);
        return holidayRepository.findById(id).map(holidayMapper::toDto);
    }

    /**
     * Delete the holiday by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Holiday : {}", id);
        holidayRepository.deleteById(id);
    }
}
