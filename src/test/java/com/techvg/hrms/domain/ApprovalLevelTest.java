package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApprovalLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApprovalLevel.class);
        ApprovalLevel approvalLevel1 = new ApprovalLevel();
        approvalLevel1.setId(1L);
        ApprovalLevel approvalLevel2 = new ApprovalLevel();
        approvalLevel2.setId(approvalLevel1.getId());
        assertThat(approvalLevel1).isEqualTo(approvalLevel2);
        approvalLevel2.setId(2L);
        assertThat(approvalLevel1).isNotEqualTo(approvalLevel2);
        approvalLevel1.setId(null);
        assertThat(approvalLevel1).isNotEqualTo(approvalLevel2);
    }
}
