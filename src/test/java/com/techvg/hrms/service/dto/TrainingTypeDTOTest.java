package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrainingTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingTypeDTO.class);
        TrainingTypeDTO trainingTypeDTO1 = new TrainingTypeDTO();
        trainingTypeDTO1.setId(1L);
        TrainingTypeDTO trainingTypeDTO2 = new TrainingTypeDTO();
        assertThat(trainingTypeDTO1).isNotEqualTo(trainingTypeDTO2);
        trainingTypeDTO2.setId(trainingTypeDTO1.getId());
        assertThat(trainingTypeDTO1).isEqualTo(trainingTypeDTO2);
        trainingTypeDTO2.setId(2L);
        assertThat(trainingTypeDTO1).isNotEqualTo(trainingTypeDTO2);
        trainingTypeDTO1.setId(null);
        assertThat(trainingTypeDTO1).isNotEqualTo(trainingTypeDTO2);
    }
}
