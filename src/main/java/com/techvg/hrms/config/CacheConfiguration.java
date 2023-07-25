package com.techvg.hrms.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.techvg.hrms.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.techvg.hrms.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.techvg.hrms.domain.User.class.getName());
            createCache(cm, com.techvg.hrms.domain.TechvgRole.class.getName());
            createCache(cm, com.techvg.hrms.domain.User.class.getName() + ".authorities");
            createCache(cm, com.techvg.hrms.domain.MasterLookup.class.getName());
            createCache(cm, com.techvg.hrms.domain.FormValidator.class.getName());
            createCache(cm, com.techvg.hrms.domain.Employee.class.getName());
            createCache(cm, com.techvg.hrms.domain.Reporting.class.getName());
            createCache(cm, com.techvg.hrms.domain.PersonalDetails.class.getName());
            createCache(cm, com.techvg.hrms.domain.PersonalId.class.getName());
            createCache(cm, com.techvg.hrms.domain.FamilyInfo.class.getName());
            createCache(cm, com.techvg.hrms.domain.Address.class.getName());
            createCache(cm, com.techvg.hrms.domain.Contacts.class.getName());
            createCache(cm, com.techvg.hrms.domain.BanksDetails.class.getName());
            createCache(cm, com.techvg.hrms.domain.Education.class.getName());
            createCache(cm, com.techvg.hrms.domain.WorkExperience.class.getName());
            createCache(cm, com.techvg.hrms.domain.Remuneration.class.getName());
            createCache(cm, com.techvg.hrms.domain.PfDetails.class.getName());
            createCache(cm, com.techvg.hrms.domain.EsiDetails.class.getName());
            createCache(cm, com.techvg.hrms.domain.LeaveApplication.class.getName());
            createCache(cm, com.techvg.hrms.domain.Company.class.getName());
            createCache(cm, com.techvg.hrms.domain.Region.class.getName());
            createCache(cm, com.techvg.hrms.domain.Branch.class.getName());
            createCache(cm, com.techvg.hrms.domain.State.class.getName());
            createCache(cm, com.techvg.hrms.domain.SalarySettings.class.getName());
            createCache(cm, com.techvg.hrms.domain.Tds.class.getName());
            createCache(cm, com.techvg.hrms.domain.Department.class.getName());
            createCache(cm, com.techvg.hrms.domain.Designation.class.getName());
            createCache(cm, com.techvg.hrms.domain.LeaveType.class.getName());
            createCache(cm, com.techvg.hrms.domain.LeavePolicy.class.getName());
            createCache(cm, com.techvg.hrms.domain.CustomLeavePolicy.class.getName());
            createCache(cm, com.techvg.hrms.domain.Holiday.class.getName());
            createCache(cm, com.techvg.hrms.domain.WorkDaysSetting.class.getName());
            createCache(cm, com.techvg.hrms.domain.ApprovalSetting.class.getName());
            createCache(cm, com.techvg.hrms.domain.ApprovalLevel.class.getName());
            createCache(cm, com.techvg.hrms.domain.CustomApprovar.class.getName());
            createCache(cm, com.techvg.hrms.domain.EmploymentType.class.getName());
            createCache(cm, com.techvg.hrms.domain.WorkingHours.class.getName());
            createCache(cm, com.techvg.hrms.domain.EmployeeLeaveAccount.class.getName());
            createCache(cm, com.techvg.hrms.domain.Attendance.class.getName());
            createCache(cm, com.techvg.hrms.domain.TimeSheet.class.getName());
            createCache(cm, com.techvg.hrms.domain.Document.class.getName());
            createCache(cm, com.techvg.hrms.domain.AssetInventory.class.getName());
            createCache(cm, com.techvg.hrms.domain.AssetApplication.class.getName());
            createCache(cm, com.techvg.hrms.domain.Salary.class.getName());
            createCache(cm, com.techvg.hrms.domain.PaySlip.class.getName());
            createCache(cm, com.techvg.hrms.domain.PayrollAdditions.class.getName());
            createCache(cm, com.techvg.hrms.domain.OverTime.class.getName());
            createCache(cm, com.techvg.hrms.domain.Deduction.class.getName());
            createCache(cm, com.techvg.hrms.domain.Promotion.class.getName());
            createCache(cm, com.techvg.hrms.domain.Resignation.class.getName());
            createCache(cm, com.techvg.hrms.domain.Termination.class.getName());
            createCache(cm, com.techvg.hrms.domain.Approval.class.getName());
            createCache(cm, com.techvg.hrms.domain.TrainingType.class.getName());
            createCache(cm, com.techvg.hrms.domain.Training.class.getName());
            createCache(cm, com.techvg.hrms.domain.Trainer.class.getName());
            createCache(cm, com.techvg.hrms.domain.MasterPerformanceIndicator.class.getName());
            createCache(cm, com.techvg.hrms.domain.PerformanceIndicator.class.getName());
            createCache(cm, com.techvg.hrms.domain.PerformanceAppraisal.class.getName());
            createCache(cm, com.techvg.hrms.domain.AppraisalReview.class.getName());
            createCache(cm, com.techvg.hrms.domain.PerformanceReview.class.getName());
            createCache(cm, com.techvg.hrms.domain.AppraisalCommentsReview.class.getName());
            createCache(cm, com.techvg.hrms.domain.EmployeeGoalsReview.class.getName());
            createCache(cm, com.techvg.hrms.domain.AppraisalEvaluationParameter.class.getName());
            createCache(cm, com.techvg.hrms.domain.AppraisalEvaluation.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
