package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeLeaveAccountMapperTest {

    private EmployeeLeaveAccountMapper employeeLeaveAccountMapper;

    @BeforeEach
    public void setUp() {
        employeeLeaveAccountMapper = new EmployeeLeaveAccountMapperImpl();
    }
}
