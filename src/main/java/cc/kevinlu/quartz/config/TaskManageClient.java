package cc.kevinlu.quartz.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import cc.kevinlu.quartz.bean.job.TaskJobBean;
import cc.kevinlu.quartz.bean.job.TaskJobParam;
import cc.kevinlu.quartz.properties.QuartzProperties;
import cc.kevinlu.quartz.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * job task manager
 *
 * @author cc
 */
@Slf4j
@Component
public class TaskManageClient {

    private static Scheduler scheduler;
    @Resource
    private QuartzProperties properties;

    public TaskManageClient(QuartzProperties properties) {
        this.properties = properties;
        this.init();
    }

    public void addJobTask(TaskJobParam jobParam) {
        try {
            log.debug("add a task {}", jobParam.getTargetClazz().getName());
            JobDetail jobDetail = JobBuilder.newJob(jobParam.getTargetClazz())
                    .withIdentity(jobParam.getName(), jobParam.getGroupName()).requestRecovery(jobParam.getRetry())
                    .withDescription(jobParam.getDescription()).storeDurably(true).build();
            if (jobParam.getExtraParams() != null) {
                Iterator it = jobParam.getExtraParams().keySet().iterator();

                while (it.hasNext()) {
                    String key = (String) it.next();
                    jobDetail.getJobDataMap().put(key, jobParam.getExtraParams().get(key));
                }
            }

            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(jobParam.getName(), jobParam.getGroupName())
                    .startAt(StringUtils.isBlank(jobParam.getStartTime()) ? new Date()
                            : DateUtils.strToDate(jobParam.getStartTime()))
                    .endAt(StringUtils.isBlank(jobParam.getStopTime()) ? null
                            : DateUtils.strToDate(jobParam.getStopTime()))
                    .withPriority(jobParam.getPriority()).forJob(jobDetail).build();
            if (StringUtils.isBlank(jobParam.getCornExpression())) {
                triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule());
            } else {
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(jobParam.getCornExpression()));
            }

            Trigger trigger = triggerBuilder.build();
            Date startTime = scheduler.scheduleJob(jobDetail, trigger);
            log.debug("start job at {}", startTime);
        } catch (SchedulerException var6) {
            log.error("get scheduler error ", var6);
            throw new RuntimeException(var6);
        } catch (ParseException e) {
            log.error("date parse error ", e);
            throw new RuntimeException(e);
        }
    }

    public TaskJobBean getJobTask(TaskJobParam param) {
        log.debug("get a job {}", param.getName());

        try {
            JobDetail detail = scheduler.getJobDetail(new JobKey(param.getName(), param.getGroupName()));
            if (detail == null) {
                return null;
            } else {
                TaskJobBean result = new TaskJobBean();
                result.setJobClassName(detail.getJobClass().getName());
                result.setDescription(detail.getDescription());
                result.setName(detail.getKey().getName());
                Trigger.TriggerState state = scheduler
                        .getTriggerState(TriggerKey.triggerKey(param.getName(), param.getGroupName()));
                result.setState(state.name());
                return result;
            }
        } catch (SchedulerException var5) {
            log.error("get scheduler error ", var5);
            throw new RuntimeException(var5);
        }
    }

    public void interruptJobTask(TaskJobParam jobParam) {
        this.interruptJobTask(jobParam, scheduler);
    }

    public void interruptJobTask(TaskJobParam jobParam, Scheduler scheduler) {
        log.debug("interrupt a job {}", jobParam.getName());

        try {
            scheduler.interrupt(new JobKey(jobParam.getName(), jobParam.getGroupName()));
        } catch (SchedulerException var4) {
            log.error("interrupt scheduler error ", var4);
            throw new RuntimeException(var4);
        }
    }

    public void modifyJobTime(TaskJobParam param) {
        this.modifyJobTime(param, scheduler);
    }

    public void modifyJobTime(TaskJobParam param, Scheduler scheduler) {
        TriggerKey triggerKey = TriggerKey.triggerKey(param.getName(), param.getGroupName());

        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger != null) {
                String oldExpression = trigger.getCronExpression();
                if (!oldExpression.equalsIgnoreCase(param.getCornExpression())) {
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(param.getCornExpression());
                    trigger = (CronTrigger) trigger.getTriggerBuilder().withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder).build();
                    scheduler.rescheduleJob(triggerKey, trigger);
                }

            }
        } catch (Exception var7) {
            log.error("modify job cron error ", var7);
            throw new RuntimeException(var7);
        }
    }

    public void removeJobTask(TaskJobParam param) {
        this.removeJobTask(param, scheduler);
    }

    public void removeJobTask(TaskJobParam param, Scheduler scheduler) {
        TriggerKey triggerKey = TriggerKey.triggerKey(param.getName(), param.getGroupName());
        JobKey jobKey = JobKey.jobKey(param.getName(), param.getGroupName());

        try {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception var6) {
            log.error("delete job error ", var6);
            throw new RuntimeException(var6);
        }
    }

    public void pauseJobTask(TaskJobParam param) {
        this.pauseJobTask(param, scheduler);
    }

    public void pauseJobTask(TaskJobParam param, Scheduler scheduler) {
        JobKey jobKey = JobKey.jobKey(param.getName(), param.getGroupName());

        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException var5) {
            log.error("pause job error ", var5);
            throw new RuntimeException(var5);
        }
    }

    public void resumeJobTask(TaskJobParam param) {
        this.resumeJobTask(param, scheduler);
    }

    public void resumeJobTask(TaskJobParam param, Scheduler scheduler) {
        JobKey jobKey = JobKey.jobKey(param.getName(), param.getGroupName());

        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException var5) {
            log.error("resume job error ", var5);
            throw new RuntimeException(var5);
        }
    }

    private void init() {
        Properties pros = this.generateProperties();
        pros.setProperty(QuartzProperties.SCHEDULER_NAME, properties.getSchedulerName());

        try {
            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory(pros);
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException var3) {
            log.error("init SchedulerFactory error ", var3);
            throw new RuntimeException(var3);
        }
    }

    public Scheduler getScheduler(String scheduleName) {
        Properties pros = this.generateProperties();
        pros.setProperty(QuartzProperties.SCHEDULER_NAME, scheduleName);

        try {
            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory(pros);
            return schedulerFactory.getScheduler();
        } catch (SchedulerException var4) {
            log.error("init SchedulerFactory error, scheduleName is {}", scheduleName, var4);
            throw new RuntimeException(var4);
        }
    }

    private Properties generateProperties() {
        Properties pros = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(QuartzProperties.FILENAME);
        if (is == null) {
            throw new RuntimeException("can not find " + QuartzProperties.FILENAME + " in classpath");
        } else {
            try {
                pros.load(is);
            } catch (IOException var4) {
                throw new RuntimeException("load " + QuartzProperties.FILENAME + " error", var4);
            }

            properties.setDatasourceDriver(pros.getProperty(QuartzProperties.DRIVER));
            properties.setDatasourceUrl(pros.getProperty(QuartzProperties.URL));
            properties.setDatasourceUser(pros.getProperty(QuartzProperties.USER));
            properties.setDatasourcePassword(pros.getProperty(QuartzProperties.PASSWORD));
            properties.setDatasourceMaxConnections(pros.getProperty(QuartzProperties.MAX_CONNECTIONS));
            properties.setClustered(pros.getProperty(QuartzProperties.IS_CLUSTERED));
            properties.setSchedulerName(pros.getProperty(QuartzProperties.SCHEDULER_NAME));

            return pros;
        }
    }
}
