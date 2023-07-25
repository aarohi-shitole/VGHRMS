package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApprovalMapperTest {

    private ApprovalMapper approvalMapper;

    @BeforeEach
    public void setUp() {
        approvalMapper = new ApprovalMapperImpl();
    }
}
