package cc.kevinlu.quartz.bean.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

/**
 * @author cc
 */
@Data
@ToString
public class TaskCreateDTO implements Serializable {

    private Long   id;
    /**
     * 调度任务名称
     */
    @NotEmpty
    @Length(min = 1, max = 120, message = "调度任务名称长度为1~120个字符")
    private String name;
    /**
     * 调度任务负责人
     */
    @NotEmpty
    @Length(min = 1, max = 20, message = "调度任务负责人长度为1~20个字符")
    private String author;
    /**
     * 调度具体任务接口地址
     */
    @NotEmpty
    private String url;
    /**
     * 调度执行时间表达式，eg: 0-59 * * * * ?
     */
    @NotEmpty
    private String cornExpression;
    /**
     * 调度任务运行时间区间起期,默认为创建后立即开始
     */
    private String startTime;
    /**
     * 调度任务运行时间区间止期,默认9999-12-31 23:59:59
     */
    private String endTime;
    /**
     * 调度任务描述
     */
    private String description;

}
