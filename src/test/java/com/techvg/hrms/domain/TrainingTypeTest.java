package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingType.class);
        TrainingType trainingType1 = new TrainingType();
        trainingType1.setId(1L);
        TrainingType trainingType2 = new TrainingType();
        trainingType2.setId(trainingType1.getId());
        assertThat(trainingType1).isEqualTo(trainingType2);
        trainingType2.setId(2L);
        assertThat(trainingType1).isNotEqualTo(trainingType2);
        trainingType1.setId(null);
        assertThat(trainingType1).isNotEqualTo(trainingType2);
    }
}
