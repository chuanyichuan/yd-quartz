package cc.kevinlu.quartz.bean.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cc.kevinlu.quartz.common.enums.TaskStatusEnums;
import lombok.Data;
import lombok.ToString;

/**
 * @author cc
 */
@Data
@ToString
public class TaskInfoDTO implements Serializable {

    private Long    id;
    /**
     * 调度任务名称
     */
    private String  name;
    /**
     * 调度任务负责人
     */
    private String  author;
    /**
     * 调度具体任务接口地址
     */
    private String  url;
    /**
     * 调度执行时间表达式，eg: 0-59 * * * * ?
     */
    private String  cornExpression;
    /**
     * 调度任务状态
     * @see TaskStatusEnums
     */
    private Integer status;
    /**
     * 调度任务运行时间区间起期,默认为创建后立即开始
     */
    private String  startTime;
    /**
     * 调度任务运行时间区间止期,默认9999-12-31 23:59:59
     */
    private String  endTime;
    /**
     * 调度任务描述
     */
    private String  description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    gmtCreated;

}
