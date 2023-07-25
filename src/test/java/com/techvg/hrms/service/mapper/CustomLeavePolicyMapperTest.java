package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomLeavePolicyMapperTest {

    private CustomLeavePolicyMapper customLeavePolicyMapper;

    @BeforeEach
    public void setUp() {
        customLeavePolicyMapper = new CustomLeavePolicyMapperImpl();
    }
}
