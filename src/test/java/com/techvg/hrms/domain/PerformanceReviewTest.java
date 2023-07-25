package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerformanceReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerformanceReview.class);
        PerformanceReview performanceReview1 = new PerformanceReview();
        performanceReview1.setId(1L);
        PerformanceReview performanceReview2 = new PerformanceReview();
        performanceReview2.setId(performanceReview1.getId());
        assertThat(performanceReview1).isEqualTo(performanceReview2);
        performanceReview2.setId(2L);
        assertThat(performanceReview1).isNotEqualTo(performanceReview2);
        performanceReview1.setId(null);
        assertThat(performanceReview1).isNotEqualTo(performanceReview2);
    }
}
