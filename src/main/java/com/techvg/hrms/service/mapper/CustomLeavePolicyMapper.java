package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.CustomLeavePolicy;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.LeavePolicy;
import com.techvg.hrms.service.dto.CustomLeavePolicyDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.LeavePolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomLeavePolicy} and its DTO {@link CustomLeavePolicyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomLeavePolicyMapper extends EntityMapper<CustomLeavePolicyDTO, CustomLeavePolicy> {
    @Mapping(target = "leavePolicy", source = "leavePolicy", qualifiedByName = "leavePolicyId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    CustomLeavePolicyDTO toDto(CustomLeavePolicy s);

    @Named("leavePolicyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeavePolicyDTO toDtoLeavePolicyId(LeavePolicy leavePolicy);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
