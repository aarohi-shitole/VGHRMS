package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkDaysSettingMapperTest {

    private WorkDaysSettingMapper workDaysSettingMapper;

    @BeforeEach
    public void setUp() {
        workDaysSettingMapper = new WorkDaysSettingMapperImpl();
    }
}
