package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppraisalEvaluationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppraisalEvaluation.class);
        AppraisalEvaluation appraisalEvaluation1 = new AppraisalEvaluation();
        appraisalEvaluation1.setId(1L);
        AppraisalEvaluation appraisalEvaluation2 = new AppraisalEvaluation();
        appraisalEvaluation2.setId(appraisalEvaluation1.getId());
        assertThat(appraisalEvaluation1).isEqualTo(appraisalEvaluation2);
        appraisalEvaluation2.setId(2L);
        assertThat(appraisalEvaluation1).isNotEqualTo(appraisalEvaluation2);
        appraisalEvaluation1.setId(null);
        assertThat(appraisalEvaluation1).isNotEqualTo(appraisalEvaluation2);
    }
}
