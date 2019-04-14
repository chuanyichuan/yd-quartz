package cc.kevinlu.quartz.core.task;

import java.util.Date;

import javax.annotation.Resource;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import cc.kevinlu.quartz.bean.dto.TaskCreateDTO;
import cc.kevinlu.quartz.job.BaseJob;
import cc.kevinlu.quartz.job.JobTaskAutowire;
import cc.kevinlu.quartz.mapper.TaskErrorRecordMapper;
import cc.kevinlu.quartz.mapper.TaskRecordMapper;
import cc.kevinlu.quartz.model.TaskErrorRecordDO;
import cc.kevinlu.quartz.model.TaskRecordDO;
import lombok.extern.slf4j.Slf4j;

/**
 * 具体调度执行器
 * 
 * @author cc
 */
@Slf4j
//@DisallowConcurrentExecution
public class QuartzTaskService extends JobTaskAutowire implements BaseJob {

    @Resource
    private RestTemplate  restTemplate;
    @Resource
    TaskErrorRecordMapper taskErrorRecordMapper;
    @Resource
    TaskRecordMapper      taskRecordMapper;

    public QuartzTaskService() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 调度执行开始时间
        Date startDate = new Date();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String mapString = jobDataMap.getString("coreData");
        TaskCreateDTO coreData = null;
        try {
            coreData = JSONObject.parseObject(mapString, TaskCreateDTO.class);
            String url = coreData.getUrl();
            log.info("id:[{}], 任务:[{}] 准备请求:[{}]", coreData.getId(), coreData.getName(), url);
            restTemplate.getForObject(url, String.class);
            // 记录执行成功信息
            long duration = System.currentTimeMillis() - startDate.getTime();
            TaskRecordDO taskRecordDO = new TaskRecordDO();
            taskRecordDO.setTaskId(coreData.getId());
            taskRecordDO.setDuration(duration);
            taskRecordDO.setGmtCreated(startDate);
            taskRecordMapper.insertSelective(taskRecordDO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (coreData != null) {
                // 记录调度运行异常信息
                TaskErrorRecordDO taskErrorRecordDO = new TaskErrorRecordDO();
                taskErrorRecordDO.setTaskId(coreData.getId());
                taskErrorRecordDO.setErrorMessage(e.getMessage());
                taskErrorRecordDO.setGmtCreated(startDate);
                taskErrorRecordMapper.insertSelective(taskErrorRecordDO);
            }
        }
    }
}
