package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MasterPerformanceIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterPerformanceIndicator.class);
        MasterPerformanceIndicator masterPerformanceIndicator1 = new MasterPerformanceIndicator();
        masterPerformanceIndicator1.setId(1L);
        MasterPerformanceIndicator masterPerformanceIndicator2 = new MasterPerformanceIndicator();
        masterPerformanceIndicator2.setId(masterPerformanceIndicator1.getId());
        assertThat(masterPerformanceIndicator1).isEqualTo(masterPerformanceIndicator2);
        masterPerformanceIndicator2.setId(2L);
        assertThat(masterPerformanceIndicator1).isNotEqualTo(masterPerformanceIndicator2);
        masterPerformanceIndicator1.setId(null);
        assertThat(masterPerformanceIndicator1).isNotEqualTo(masterPerformanceIndicator2);
    }
}
