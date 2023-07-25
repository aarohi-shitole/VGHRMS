package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkDaysSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDaysSettingDTO.class);
        WorkDaysSettingDTO workDaysSettingDTO1 = new WorkDaysSettingDTO();
        workDaysSettingDTO1.setId(1L);
        WorkDaysSettingDTO workDaysSettingDTO2 = new WorkDaysSettingDTO();
        assertThat(workDaysSettingDTO1).isNotEqualTo(workDaysSettingDTO2);
        workDaysSettingDTO2.setId(workDaysSettingDTO1.getId());
        assertThat(workDaysSettingDTO1).isEqualTo(workDaysSettingDTO2);
        workDaysSettingDTO2.setId(2L);
        assertThat(workDaysSettingDTO1).isNotEqualTo(workDaysSettingDTO2);
        workDaysSettingDTO1.setId(null);
        assertThat(workDaysSettingDTO1).isNotEqualTo(workDaysSettingDTO2);
    }
}
