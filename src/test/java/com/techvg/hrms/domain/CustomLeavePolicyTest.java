package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomLeavePolicyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomLeavePolicy.class);
        CustomLeavePolicy customLeavePolicy1 = new CustomLeavePolicy();
        customLeavePolicy1.setId(1L);
        CustomLeavePolicy customLeavePolicy2 = new CustomLeavePolicy();
        customLeavePolicy2.setId(customLeavePolicy1.getId());
        assertThat(customLeavePolicy1).isEqualTo(customLeavePolicy2);
        customLeavePolicy2.setId(2L);
        assertThat(customLeavePolicy1).isNotEqualTo(customLeavePolicy2);
        customLeavePolicy1.setId(null);
        assertThat(customLeavePolicy1).isNotEqualTo(customLeavePolicy2);
    }
}
