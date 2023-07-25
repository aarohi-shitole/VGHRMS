package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceAppraisalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceAppraisal.class);
        PerformanceAppraisal performanceAppraisal1 = new PerformanceAppraisal();
        performanceAppraisal1.setId(1L);
        PerformanceAppraisal performanceAppraisal2 = new PerformanceAppraisal();
        performanceAppraisal2.setId(performanceAppraisal1.getId());
        assertThat(performanceAppraisal1).isEqualTo(performanceAppraisal2);
        performanceAppraisal2.setId(2L);
        assertThat(performanceAppraisal1).isNotEqualTo(performanceAppraisal2);
        performanceAppraisal1.setId(null);
        assertThat(performanceAppraisal1).isNotEqualTo(performanceAppraisal2);
    }
}
