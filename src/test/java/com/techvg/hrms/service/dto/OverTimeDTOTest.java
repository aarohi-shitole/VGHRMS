package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverTimeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OverTimeDTO.class);
        OverTimeDTO overTimeDTO1 = new OverTimeDTO();
        overTimeDTO1.setId(1L);
        OverTimeDTO overTimeDTO2 = new OverTimeDTO();
        assertThat(overTimeDTO1).isNotEqualTo(overTimeDTO2);
        overTimeDTO2.setId(overTimeDTO1.getId());
        assertThat(overTimeDTO1).isEqualTo(overTimeDTO2);
        overTimeDTO2.setId(2L);
        assertThat(overTimeDTO1).isNotEqualTo(overTimeDTO2);
        overTimeDTO1.setId(null);
        assertThat(overTimeDTO1).isNotEqualTo(overTimeDTO2);
    }
}
