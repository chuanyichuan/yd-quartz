package cc.kevinlu.quartz.web.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cc.kevinlu.quartz.bean.job.TaskJobParam;
import cc.kevinlu.quartz.common.Constants;
import cc.kevinlu.quartz.common.enums.TaskStatusEnums;
import cc.kevinlu.quartz.common.exception.DataNotFoundException;
import cc.kevinlu.quartz.common.exception.ParamErrorException;
import cc.kevinlu.quartz.config.TaskManageClient;
import cc.kevinlu.quartz.core.task.QuartzTaskService;
import cc.kevinlu.quartz.error.CommonError;
import cc.kevinlu.quartz.mapper.TaskJobMapper;
import cc.kevinlu.quartz.model.TaskJobDO;
import cc.kevinlu.quartz.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 调度任务Processor
 *
 * @author cc
 */
@Slf4j
@Component
public class JobTaskProcessor {
    @Resource
    private TaskJobMapper    taskJobMapper;
    @Resource
    private TaskManageClient taskManageClient;

    /**
     * 查询调度任务是否存在
     *
     * @param id 调度任务ID
     * @return
     */
    public TaskJobParam jobTask(Long id) {
        TaskJobDO taskJobDO = taskJobMapper.selectByPrimaryKey(id);
        if (taskJobDO == null) {
            throw new DataNotFoundException(CommonError.DATA_NOT_EXISTS_ERROR);
        }
        TaskJobParam taskJobParam = new TaskJobParam();
        taskJobParam.setName(taskJobDO.getName());
        taskJobParam.setGroupName(taskJobDO.getGroupName());
        return taskJobParam;
    }

    /**
     * 更新调度任务状态
     * 
     * @param id 调度任务ID
     * @param enums
     */
    public void updateTaskJobStatus(Long id, TaskStatusEnums enums) {
        TaskJobDO taskJobDO = new TaskJobDO();
        taskJobDO.setId(id);
        taskJobDO.setStatus(enums.getCode());
        taskJobDO.setGmtModified(new Date());
        taskJobMapper.updateByPrimaryKeySelective(taskJobDO);
    }

    /**
     * 创建调度
     * 
     * @param taskJobDO
     */
    public void buildTask(TaskJobDO taskJobDO) {
        TaskJobParam taskJobParam = new TaskJobParam();
        taskJobParam.setName(taskJobDO.getName());
        taskJobParam.setCornExpression(taskJobDO.getCornExpression());
        taskJobParam.setDescription(taskJobDO.getDescription());

        taskJobParam.setGroupName(Constants.DEFAULT_TASK_GROUP);
        taskJobParam.setPriority(1);
        taskJobParam.setStartTime(taskJobDO.getStartTime());
        taskJobParam.setStopTime(taskJobDO.getEndTime());
        taskJobParam.setRetry(true);
        taskJobParam.setTargetClazz(QuartzTaskService.class);

        Map<String, Object> extraData = new HashMap<>(16);
        extraData.put("coreData", JSONObject.toJSONString(taskJobDO, SerializerFeature.WRITE_MAP_NULL_FEATURES));
        taskJobParam.setExtraParams(extraData);

        if (taskManageClient.getJobTask(taskJobParam) != null) {
            taskManageClient.removeJobTask(taskJobParam);
        }
        taskManageClient.addJobTask(taskJobParam);
    }

    /**
     * 校验起止时间是否合法
     * 
     * @param startTime 起期
     * @param endTime 止期
     */
    public void validDate(String startTime, String endTime) {
        try {
            Date startDate = StringUtils.isNotBlank(startTime) ? DateUtils.strToDate(startTime) : new Date();
            if (StringUtils.isNotBlank(endTime) && DateUtils.isBefore(endTime, startDate)) {
                throw new ParamErrorException(CommonError.DATE_ERROR_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ParamErrorException(CommonError.DATE_ERROR_ERROR);
        }
    }

}
