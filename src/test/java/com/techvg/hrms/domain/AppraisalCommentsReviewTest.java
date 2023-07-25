package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppraisalCommentsReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppraisalCommentsReview.class);
        AppraisalCommentsReview appraisalCommentsReview1 = new AppraisalCommentsReview();
        appraisalCommentsReview1.setId(1L);
        AppraisalCommentsReview appraisalCommentsReview2 = new AppraisalCommentsReview();
        appraisalCommentsReview2.setId(appraisalCommentsReview1.getId());
        assertThat(appraisalCommentsReview1).isEqualTo(appraisalCommentsReview2);
        appraisalCommentsReview2.setId(2L);
        assertThat(appraisalCommentsReview1).isNotEqualTo(appraisalCommentsReview2);
        appraisalCommentsReview1.setId(null);
        assertThat(appraisalCommentsReview1).isNotEqualTo(appraisalCommentsReview2);
    }
}
