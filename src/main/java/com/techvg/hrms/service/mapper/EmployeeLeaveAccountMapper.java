package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.EmployeeLeaveAccount;
import com.techvg.hrms.domain.LeaveType;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeLeaveAccountDTO;
import com.techvg.hrms.service.dto.LeaveTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeLeaveAccount} and its DTO {@link EmployeeLeaveAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeLeaveAccountMapper extends EntityMapper<EmployeeLeaveAccountDTO, EmployeeLeaveAccount> {
    @Mapping(target = "leaveType", source = "leaveType", qualifiedByName = "leaveTypeLeaveType")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    EmployeeLeaveAccountDTO toDto(EmployeeLeaveAccount s);

    @Named("leaveTypeLeaveType")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "leaveType", source = "leaveType")
    LeaveTypeDTO toDtoLeaveTypeLeaveType(LeaveType leaveType);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
