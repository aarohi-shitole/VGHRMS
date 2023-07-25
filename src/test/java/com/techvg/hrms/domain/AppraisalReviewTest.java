package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppraisalReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppraisalReview.class);
        AppraisalReview appraisalReview1 = new AppraisalReview();
        appraisalReview1.setId(1L);
        AppraisalReview appraisalReview2 = new AppraisalReview();
        appraisalReview2.setId(appraisalReview1.getId());
        assertThat(appraisalReview1).isEqualTo(appraisalReview2);
        appraisalReview2.setId(2L);
        assertThat(appraisalReview1).isNotEqualTo(appraisalReview2);
        appraisalReview1.setId(null);
        assertThat(appraisalReview1).isNotEqualTo(appraisalReview2);
    }
}
