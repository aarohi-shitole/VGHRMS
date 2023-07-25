package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TdsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tds.class);
        Tds tds1 = new Tds();
        tds1.setId(1L);
        Tds tds2 = new Tds();
        tds2.setId(tds1.getId());
        assertThat(tds1).isEqualTo(tds2);
        tds2.setId(2L);
        assertThat(tds1).isNotEqualTo(tds2);
        tds1.setId(null);
        assertThat(tds1).isNotEqualTo(tds2);
    }
}
