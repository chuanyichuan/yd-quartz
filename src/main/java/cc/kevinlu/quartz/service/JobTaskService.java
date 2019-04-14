package cc.kevinlu.quartz.service;

import com.github.pagehelper.PageInfo;

import cc.kevinlu.quartz.bean.base.IdBaseRequest;
import cc.kevinlu.quartz.bean.dto.TaskCreateDTO;
import cc.kevinlu.quartz.bean.dto.TaskInfoDTO;
import cc.kevinlu.quartz.bean.query.TaskQuery;

/**
 * 调度任务Service
 *
 * @author cc
 */
public interface JobTaskService {

    /**
     * 新增调度任务
     * 
     * @param dto
     */
    void addTask(TaskCreateDTO dto);

    /**
     * 暂停调度任务
     * 
     * @param request
     */
    void pauseTask(IdBaseRequest request);

    /**
     * 恢复调度任务
     * 
     * @param request
     */
    void resumeTask(IdBaseRequest request);

    /**
     * 中断调度任务
     * 
     * @param request
     */
    void stopTask(IdBaseRequest request);

    /**
     * 查询调度任务详细信息
     * 
     * @param request
     * @return
     */
    TaskInfoDTO taskInfo(IdBaseRequest request);

    /**
     * 更新调度任务信息
     * 
     * @param dto
     */
    void modifyTask(TaskCreateDTO dto);

    /**
     * 分页查询调度列表
     * 
     * @param query
     * @return
     */
    PageInfo<TaskInfoDTO> taskList(TaskQuery query);

    /**
     * 开启调度任务
     * 
     * @param request
     */
    void startTask(IdBaseRequest request);
}
