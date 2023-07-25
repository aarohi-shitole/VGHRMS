package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Holiday;
import com.techvg.hrms.service.dto.HolidayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Holiday} and its DTO {@link HolidayDTO}.
 */
@Mapper(componentModel = "spring")
public interface HolidayMapper extends EntityMapper<HolidayDTO, Holiday> {}
