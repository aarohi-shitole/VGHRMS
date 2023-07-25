package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApprovalSettingMapperTest {

    private ApprovalSettingMapper approvalSettingMapper;

    @BeforeEach
    public void setUp() {
        approvalSettingMapper = new ApprovalSettingMapperImpl();
    }
}
