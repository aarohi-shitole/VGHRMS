package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaySlipMapperTest {

    private PaySlipMapper paySlipMapper;

    @BeforeEach
    public void setUp() {
        paySlipMapper = new PaySlipMapperImpl();
    }
}
