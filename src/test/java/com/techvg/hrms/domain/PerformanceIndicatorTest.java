package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceIndicator.class);
        PerformanceIndicator performanceIndicator1 = new PerformanceIndicator();
        performanceIndicator1.setId(1L);
        PerformanceIndicator performanceIndicator2 = new PerformanceIndicator();
        performanceIndicator2.setId(performanceIndicator1.getId());
        assertThat(performanceIndicator1).isEqualTo(performanceIndicator2);
        performanceIndicator2.setId(2L);
        assertThat(performanceIndicator1).isNotEqualTo(performanceIndicator2);
        performanceIndicator1.setId(null);
        assertThat(performanceIndicator1).isNotEqualTo(performanceIndicator2);
    }
}
