package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeavePolicyMapperTest {

    private LeavePolicyMapper leavePolicyMapper;

    @BeforeEach
    public void setUp() {
        leavePolicyMapper = new LeavePolicyMapperImpl();
    }
}
