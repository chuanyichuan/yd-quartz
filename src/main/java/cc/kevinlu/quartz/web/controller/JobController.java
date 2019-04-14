package cc.kevinlu.quartz.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;

import cc.kevinlu.quartz.bean.base.BaseResult;
import cc.kevinlu.quartz.bean.base.IdBaseRequest;
import cc.kevinlu.quartz.bean.dto.TaskCreateDTO;
import cc.kevinlu.quartz.bean.dto.TaskInfoDTO;
import cc.kevinlu.quartz.bean.query.TaskQuery;
import cc.kevinlu.quartz.config.TaskManageClient;
import cc.kevinlu.quartz.service.JobTaskService;

/**
 * @author cc
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Resource
    private JobTaskService   jobTaskService;
    @Resource
    private TaskManageClient taskManageClient;

    /**
     * 创建调度任务信息
     * 
     * @param dto
     * @return
     */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Void> addTask(@RequestBody @Valid TaskCreateDTO dto) {
        jobTaskService.addTask(dto);
        return BaseResult.success();
    }

    /**
     * 更新调度任务信息
     * 
     * @param dto
     * @return
     */
    @PostMapping(value = "/modify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Void> modifyTask(@RequestBody @Valid TaskCreateDTO dto) {
        jobTaskService.modifyTask(dto);
        return BaseResult.success();
    }

    /**
     * 查询调度任务详细信息
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<TaskInfoDTO> taskInfo(@Valid IdBaseRequest request) {
        TaskInfoDTO info = jobTaskService.taskInfo(request);
        return BaseResult.success(info);
    }

    /**
     * 查询调度任务列表
     *
     * @param query
     * @return
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<PageInfo<TaskInfoDTO>> taskList(TaskQuery query) {
        PageInfo<TaskInfoDTO> page = jobTaskService.taskList(query);
        return BaseResult.success(page);
    }

    /**
     * 暂停执行调度任务
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/pause", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Void> pauseTask(@RequestBody @Valid IdBaseRequest request) {
        jobTaskService.pauseTask(request);
        return BaseResult.success();
    }

    /**
     * 恢复调度任务继续执行
     * @param request
     * @return
     */
    @PostMapping(value = "/resume", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Void> resumeTask(@RequestBody @Valid IdBaseRequest request) {
        jobTaskService.resumeTask(request);
        return BaseResult.success();
    }

    /**
     * 停止调度任务执行
     * 
     * @param request
     * @return
     */
    @PostMapping(value = "/stop", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Void> stopTask(@RequestBody @Valid IdBaseRequest request) {
        jobTaskService.stopTask(request);
        return BaseResult.success();
    }

    /**
     * 开启调度任务，使调度任务开始执行
     * 
     * @param request
     * @return
     */
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResult<Void> startTask(@RequestBody @Valid IdBaseRequest request) {
        jobTaskService.startTask(request);
        return BaseResult.success();
    }

}
