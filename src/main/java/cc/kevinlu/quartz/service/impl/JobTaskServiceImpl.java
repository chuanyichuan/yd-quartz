package cc.kevinlu.quartz.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cc.kevinlu.quartz.bean.base.IdBaseRequest;
import cc.kevinlu.quartz.bean.dto.TaskCreateDTO;
import cc.kevinlu.quartz.bean.dto.TaskInfoDTO;
import cc.kevinlu.quartz.bean.job.TaskJobParam;
import cc.kevinlu.quartz.bean.query.TaskQuery;
import cc.kevinlu.quartz.common.Constants;
import cc.kevinlu.quartz.common.enums.TaskStatusEnums;
import cc.kevinlu.quartz.common.exception.ConflictException;
import cc.kevinlu.quartz.common.exception.DataNotFoundException;
import cc.kevinlu.quartz.common.exception.ParamErrorException;
import cc.kevinlu.quartz.config.TaskManageClient;
import cc.kevinlu.quartz.error.CommonError;
import cc.kevinlu.quartz.mapper.TaskJobMapper;
import cc.kevinlu.quartz.model.TaskJobDO;
import cc.kevinlu.quartz.model.TaskJobDOExample;
import cc.kevinlu.quartz.service.JobTaskService;
import cc.kevinlu.quartz.utils.BeanMapper;
import cc.kevinlu.quartz.web.processor.JobTaskProcessor;

/**
 * @author cc
 */
@Service
public class JobTaskServiceImpl implements JobTaskService {

    @Resource
    private TaskManageClient taskManageClient;
    @Resource
    private TaskJobMapper    taskJobMapper;
    @Resource
    private JobTaskProcessor jobTaskProcessor;

    @Override
    @Transactional(value = "quartz_transaction", rollbackFor = Exception.class)
    public void addTask(TaskCreateDTO dto) {
        TaskJobDOExample example = new TaskJobDOExample();
        example.createCriteria().andNameEqualTo(dto.getName());
        List<TaskJobDO> list = taskJobMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ConflictException(CommonError.NAME_REPEAT_ERROR);
        }

        jobTaskProcessor.validDate(dto.getStartTime(), dto.getEndTime());

        // 创建调度任务数据
        TaskJobDO record = BeanMapper.map(dto, TaskJobDO.class);
        record.setGroupName(Constants.DEFAULT_TASK_GROUP);
        record.setStatus(TaskStatusEnums.RUNNING.getCode());
        record.setGmtCreated(new Date());
        record.setGmtModified(new Date());
        taskJobMapper.insertSelective(record);

        // 创建调度
        jobTaskProcessor.buildTask(record);
    }

    @Override
    @Transactional(value = "quartz_transaction", rollbackFor = Exception.class)
    public void pauseTask(IdBaseRequest request) {
        TaskJobParam taskJobParam = jobTaskProcessor.jobTask(request.getId());
        if (taskManageClient.getJobTask(taskJobParam) != null) {
            taskManageClient.pauseJobTask(taskJobParam);
        }
        jobTaskProcessor.updateTaskJobStatus(request.getId(), TaskStatusEnums.PAUSE);
    }

    @Override
    @Transactional(value = "quartz_transaction", rollbackFor = Exception.class)
    public void resumeTask(IdBaseRequest request) {
        TaskJobParam taskJobParam = jobTaskProcessor.jobTask(request.getId());
        if (taskManageClient.getJobTask(taskJobParam) != null) {
            taskManageClient.resumeJobTask(taskJobParam);
        }
        jobTaskProcessor.updateTaskJobStatus(request.getId(), TaskStatusEnums.RUNNING);
    }

    @Override
    @Transactional(value = "quartz_transaction", rollbackFor = Exception.class)
    public void stopTask(IdBaseRequest request) {
        TaskJobParam taskJobParam = jobTaskProcessor.jobTask(request.getId());
        if (taskManageClient.getJobTask(taskJobParam) != null) {
            taskManageClient.removeJobTask(taskJobParam);
        }
        jobTaskProcessor.updateTaskJobStatus(request.getId(), TaskStatusEnums.STOP);
    }

    @Override
    public TaskInfoDTO taskInfo(IdBaseRequest request) {
        TaskJobDO taskJobDO = taskJobMapper.selectByPrimaryKey(request.getId());
        if (taskJobDO == null) {
            throw new DataNotFoundException(CommonError.DATA_NOT_EXISTS_ERROR);
        }
        TaskInfoDTO infoDTO = BeanMapper.map(taskJobDO, TaskInfoDTO.class);
        return infoDTO;
    }

    @Override
    @Transactional(value = "quartz_transaction", rollbackFor = Exception.class)
    public void modifyTask(TaskCreateDTO dto) {
        if (dto.getId() == null) {
            throw new ParamErrorException(CommonError.PARAM_ID_EMPTY_ERROR);
        }
        TaskJobDO taskJobDO = taskJobMapper.selectByPrimaryKey(dto.getId());
        if (taskJobDO == null) {
            throw new DataNotFoundException(CommonError.DATA_NOT_EXISTS_ERROR);
        }

        jobTaskProcessor.validDate(dto.getStartTime(), dto.getEndTime());

        // 校验调度名称是否已存在
        TaskJobDOExample example = new TaskJobDOExample();
        TaskJobDOExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(dto.getName());
        criteria.andIdNotEqualTo(dto.getId());
        List<TaskJobDO> list = taskJobMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ConflictException(CommonError.NAME_REPEAT_ERROR);
        }

        // 更新调度信息
        TaskJobDO record = BeanMapper.map(dto, TaskJobDO.class);
        record.setId(dto.getId());
        record.setGroupName(Constants.DEFAULT_TASK_GROUP);
        record.setStatus(TaskStatusEnums.RUNNING.getCode());
        record.setGmtModified(new Date());
        taskJobMapper.updateByPrimaryKeySelective(record);

        // 删除原调度
        TaskJobParam taskJobParam = jobTaskProcessor.jobTask(dto.getId());
        if (taskManageClient.getJobTask(taskJobParam) != null) {
            taskManageClient.removeJobTask(taskJobParam);
        }

        // 创建调度
        jobTaskProcessor.buildTask(taskJobDO);
    }

    @Override
    public PageInfo<TaskInfoDTO> taskList(TaskQuery query) {
        TaskJobDOExample example = new TaskJobDOExample();
        TaskJobDOExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(query.getName())) {
            criteria.andNameEqualTo(query.getName());
        }
        if (StringUtils.isNotBlank(query.getAuthor())) {
            criteria.andAuthorEqualTo(query.getAuthor());
        }

        Page page = PageHelper.startPage(query.getPageIndex(), query.getPageSize());
        PageHelper.orderBy("gmt_modified DESC");
        List<TaskJobDO> list = taskJobMapper.selectByExample(example);

        List<TaskInfoDTO> resultList = BeanMapper.mapList(list, TaskInfoDTO.class);
        PageInfo<TaskInfoDTO> pageInfo = new PageInfo<>(page);
        pageInfo.setList(resultList);
        return pageInfo;
    }

    @Override
    public void startTask(IdBaseRequest request) {
        TaskJobDO taskJobDO = taskJobMapper.selectByPrimaryKey(request.getId());
        if (taskJobDO == null) {
            throw new DataNotFoundException(CommonError.DATA_NOT_EXISTS_ERROR);
        }
        // 创建调度
        jobTaskProcessor.buildTask(taskJobDO);
        jobTaskProcessor.updateTaskJobStatus(request.getId(), TaskStatusEnums.RUNNING);
    }
}
