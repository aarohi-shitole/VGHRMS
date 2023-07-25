package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EsiDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsiDetailsDTO.class);
        EsiDetailsDTO esiDetailsDTO1 = new EsiDetailsDTO();
        esiDetailsDTO1.setId(1L);
        EsiDetailsDTO esiDetailsDTO2 = new EsiDetailsDTO();
        assertThat(esiDetailsDTO1).isNotEqualTo(esiDetailsDTO2);
        esiDetailsDTO2.setId(esiDetailsDTO1.getId());
        assertThat(esiDetailsDTO1).isEqualTo(esiDetailsDTO2);
        esiDetailsDTO2.setId(2L);
        assertThat(esiDetailsDTO1).isNotEqualTo(esiDetailsDTO2);
        esiDetailsDTO1.setId(null);
        assertThat(esiDetailsDTO1).isNotEqualTo(esiDetailsDTO2);
    }
}
