package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Attendance;
import com.techvg.hrms.domain.TimeSheet;
import com.techvg.hrms.service.dto.AttendanceDTO;
import com.techvg.hrms.service.dto.TimeSheetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TimeSheet} and its DTO {@link TimeSheetDTO}.
 */
@Mapper(componentModel = "spring")
public interface TimeSheetMapper extends EntityMapper<TimeSheetDTO, TimeSheet> {
    @Mapping(target = "attendance", source = "attendance", qualifiedByName = "attendanceId")
    TimeSheetDTO toDto(TimeSheet s);

    @Named("attendanceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttendanceDTO toDtoAttendanceId(Attendance attendance);
}
