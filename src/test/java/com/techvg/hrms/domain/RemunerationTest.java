package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemunerationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remuneration.class);
        Remuneration remuneration1 = new Remuneration();
        remuneration1.setId(1L);
        Remuneration remuneration2 = new Remuneration();
        remuneration2.setId(remuneration1.getId());
        assertThat(remuneration1).isEqualTo(remuneration2);
        remuneration2.setId(2L);
        assertThat(remuneration1).isNotEqualTo(remuneration2);
        remuneration1.setId(null);
        assertThat(remuneration1).isNotEqualTo(remuneration2);
    }
}
