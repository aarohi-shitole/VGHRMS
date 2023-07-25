package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OverTimeMapperTest {

    private OverTimeMapper overTimeMapper;

    @BeforeEach
    public void setUp() {
        overTimeMapper = new OverTimeMapperImpl();
    }
}
