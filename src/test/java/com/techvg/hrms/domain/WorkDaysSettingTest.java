package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkDaysSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDaysSetting.class);
        WorkDaysSetting workDaysSetting1 = new WorkDaysSetting();
        workDaysSetting1.setId(1L);
        WorkDaysSetting workDaysSetting2 = new WorkDaysSetting();
        workDaysSetting2.setId(workDaysSetting1.getId());
        assertThat(workDaysSetting1).isEqualTo(workDaysSetting2);
        workDaysSetting2.setId(2L);
        assertThat(workDaysSetting1).isNotEqualTo(workDaysSetting2);
        workDaysSetting1.setId(null);
        assertThat(workDaysSetting1).isNotEqualTo(workDaysSetting2);
    }
}
