package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Branch;
import com.techvg.hrms.domain.Department;
import com.techvg.hrms.domain.Designation;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.Region;
import com.techvg.hrms.service.dto.BranchDTO;
import com.techvg.hrms.service.dto.DepartmentDTO;
import com.techvg.hrms.service.dto.DesignationDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "designation", source = "designation", qualifiedByName = "designationName")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentName")
    @Mapping(target = "branch", source = "branch", qualifiedByName = "branchBranchName")
    @Mapping(target = "region", source = "region", qualifiedByName = "regionRegionName")
    EmployeeDTO toDto(Employee s);

    @Named("designationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DesignationDTO toDtoDesignationName(Designation designation);

    @Named("departmentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DepartmentDTO toDtoDepartmentName(Department department);

    @Named("branchBranchName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "branchName", source = "branchName")
    BranchDTO toDtoBranchBranchName(Branch branch);

    @Named("regionRegionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "regionName", source = "regionName")
    RegionDTO toDtoRegionRegionName(Region region);
}
