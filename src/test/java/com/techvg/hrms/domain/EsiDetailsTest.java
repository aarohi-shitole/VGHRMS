package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EsiDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EsiDetails.class);
        EsiDetails esiDetails1 = new EsiDetails();
        esiDetails1.setId(1L);
        EsiDetails esiDetails2 = new EsiDetails();
        esiDetails2.setId(esiDetails1.getId());
        assertThat(esiDetails1).isEqualTo(esiDetails2);
        esiDetails2.setId(2L);
        assertThat(esiDetails1).isNotEqualTo(esiDetails2);
        esiDetails1.setId(null);
        assertThat(esiDetails1).isNotEqualTo(esiDetails2);
    }
}
