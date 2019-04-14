package cc.kevinlu.quartz.core.init;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import cc.kevinlu.quartz.mapper.TaskJobMapper;
import cc.kevinlu.quartz.model.TaskJobDO;
import cc.kevinlu.quartz.model.TaskJobDOExample;
import cc.kevinlu.quartz.utils.DateUtils;
import cc.kevinlu.quartz.web.processor.JobTaskProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务启动时扫描数据库中的调度任务，自动加载
 *
 * @author cc
 */
@Slf4j
@Component
public class TaskInitStarter {

    @Resource
    private TaskJobMapper    taskJobMapper;
    @Resource
    private JobTaskProcessor jobTaskProcessor;

    @PostConstruct
    public void initTask() {
        TaskJobDOExample example = new TaskJobDOExample();
        example.createCriteria().andEndTimeGreaterThan(DateUtils.dateToStr(new Date()));
        List<TaskJobDO> list = taskJobMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("服务启动初始化完成，发现[0]个可执行队列!");
            return;
        }
        log.warn("服务启动初始化完成，发现[{}]个可执行队列!", list.size());
        for (TaskJobDO t : list) {
            jobTaskProcessor.buildTask(t);
        }

    }

}
