package cc.kevinlu.quartz.config.quartz;

import javax.annotation.Resource;

import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class QuartzConfiguration {

    @Resource
    private YDJobFactory ydJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactory(Trigger[] cronTriggerBean) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        log.warn("cronTriggerBean={}", cronTriggerBean);
        bean.setTriggers(cronTriggerBean);
        bean.setJobFactory(ydJobFactory);
        return bean;
    }

}
