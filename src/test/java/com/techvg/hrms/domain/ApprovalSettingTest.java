package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalSetting.class);
        ApprovalSetting approvalSetting1 = new ApprovalSetting();
        approvalSetting1.setId(1L);
        ApprovalSetting approvalSetting2 = new ApprovalSetting();
        approvalSetting2.setId(approvalSetting1.getId());
        assertThat(approvalSetting1).isEqualTo(approvalSetting2);
        approvalSetting2.setId(2L);
        assertThat(approvalSetting1).isNotEqualTo(approvalSetting2);
        approvalSetting1.setId(null);
        assertThat(approvalSetting1).isNotEqualTo(approvalSetting2);
    }
}
