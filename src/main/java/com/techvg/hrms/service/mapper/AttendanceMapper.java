package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Attendance;
import com.techvg.hrms.service.dto.AttendanceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendance} and its DTO {@link AttendanceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {}
