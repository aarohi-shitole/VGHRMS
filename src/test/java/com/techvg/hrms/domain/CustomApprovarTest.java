package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomApprovarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomApprovar.class);
        CustomApprovar customApprovar1 = new CustomApprovar();
        customApprovar1.setId(1L);
        CustomApprovar customApprovar2 = new CustomApprovar();
        customApprovar2.setId(customApprovar1.getId());
        assertThat(customApprovar1).isEqualTo(customApprovar2);
        customApprovar2.setId(2L);
        assertThat(customApprovar1).isNotEqualTo(customApprovar2);
        customApprovar1.setId(null);
        assertThat(customApprovar1).isNotEqualTo(customApprovar2);
    }
}
