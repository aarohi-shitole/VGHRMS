package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverTimeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OverTime.class);
        OverTime overTime1 = new OverTime();
        overTime1.setId(1L);
        OverTime overTime2 = new OverTime();
        overTime2.setId(overTime1.getId());
        assertThat(overTime1).isEqualTo(overTime2);
        overTime2.setId(2L);
        assertThat(overTime1).isNotEqualTo(overTime2);
        overTime1.setId(null);
        assertThat(overTime1).isNotEqualTo(overTime2);
    }
}
