package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalarySettingsMapperTest {

    private SalarySettingsMapper salarySettingsMapper;

    @BeforeEach
    public void setUp() {
        salarySettingsMapper = new SalarySettingsMapperImpl();
    }
}
