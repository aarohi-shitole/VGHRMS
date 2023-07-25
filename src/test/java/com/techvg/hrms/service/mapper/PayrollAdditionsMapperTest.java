package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayrollAdditionsMapperTest {

    private PayrollAdditionsMapper payrollAdditionsMapper;

    @BeforeEach
    public void setUp() {
        payrollAdditionsMapper = new PayrollAdditionsMapperImpl();
    }
}
