package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeGoalsReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeGoalsReview.class);
        EmployeeGoalsReview employeeGoalsReview1 = new EmployeeGoalsReview();
        employeeGoalsReview1.setId(1L);
        EmployeeGoalsReview employeeGoalsReview2 = new EmployeeGoalsReview();
        employeeGoalsReview2.setId(employeeGoalsReview1.getId());
        assertThat(employeeGoalsReview1).isEqualTo(employeeGoalsReview2);
        employeeGoalsReview2.setId(2L);
        assertThat(employeeGoalsReview1).isNotEqualTo(employeeGoalsReview2);
        employeeGoalsReview1.setId(null);
        assertThat(employeeGoalsReview1).isNotEqualTo(employeeGoalsReview2);
    }
}
