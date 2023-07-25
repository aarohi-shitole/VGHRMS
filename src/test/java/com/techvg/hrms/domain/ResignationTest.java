package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResignationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resignation.class);
        Resignation resignation1 = new Resignation();
        resignation1.setId(1L);
        Resignation resignation2 = new Resignation();
        resignation2.setId(resignation1.getId());
        assertThat(resignation1).isEqualTo(resignation2);
        resignation2.setId(2L);
        assertThat(resignation1).isNotEqualTo(resignation2);
        resignation1.setId(null);
        assertThat(resignation1).isNotEqualTo(resignation2);
    }
}
