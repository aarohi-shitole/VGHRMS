package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PfDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PfDetails.class);
        PfDetails pfDetails1 = new PfDetails();
        pfDetails1.setId(1L);
        PfDetails pfDetails2 = new PfDetails();
        pfDetails2.setId(pfDetails1.getId());
        assertThat(pfDetails1).isEqualTo(pfDetails2);
        pfDetails2.setId(2L);
        assertThat(pfDetails1).isNotEqualTo(pfDetails2);
        pfDetails1.setId(null);
        assertThat(pfDetails1).isNotEqualTo(pfDetails2);
    }
}
