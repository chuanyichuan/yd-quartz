package cc.kevinlu.quartz.bean.job;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

/**
 * task detail info
 * 
 * @author cc
 */
@Data
@ToString
public class TaskJobParam implements Serializable {

    /**
     * task name
     */
    private String              name;

    /**
     * group name
     */
    private String              groupName;

    /**
     * target class
     */
    private Class               targetClazz;

    /**
     * corn expression
     * eg: * 1 * * * ?
     */
    private String              cornExpression;

    /**
     * task detail description
     */
    private String              description;
    /**
     * retry 
     */
    private Boolean             retry;

    /**
     * priority of task
     */
    private Integer             priority;

    /**
     * task run after startTime
     */
    private String              startTime;

    /**
     * task run before stopTime
     */
    private String              stopTime;

    /**
     * extra data
     */
    private Map<String, Object> extraParams;

}
