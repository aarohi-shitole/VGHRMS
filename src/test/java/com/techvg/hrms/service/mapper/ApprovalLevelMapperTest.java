package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApprovalLevelMapperTest {

    private ApprovalLevelMapper approvalLevelMapper;

    @BeforeEach
    public void setUp() {
        approvalLevelMapper = new ApprovalLevelMapperImpl();
    }
}
