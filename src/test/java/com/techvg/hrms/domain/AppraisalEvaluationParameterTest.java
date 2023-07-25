package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppraisalEvaluationParameterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppraisalEvaluationParameter.class);
        AppraisalEvaluationParameter appraisalEvaluationParameter1 = new AppraisalEvaluationParameter();
        appraisalEvaluationParameter1.setId(1L);
        AppraisalEvaluationParameter appraisalEvaluationParameter2 = new AppraisalEvaluationParameter();
        appraisalEvaluationParameter2.setId(appraisalEvaluationParameter1.getId());
        assertThat(appraisalEvaluationParameter1).isEqualTo(appraisalEvaluationParameter2);
        appraisalEvaluationParameter2.setId(2L);
        assertThat(appraisalEvaluationParameter1).isNotEqualTo(appraisalEvaluationParameter2);
        appraisalEvaluationParameter1.setId(null);
        assertThat(appraisalEvaluationParameter1).isNotEqualTo(appraisalEvaluationParameter2);
    }
}
