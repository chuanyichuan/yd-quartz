package cc.kevinlu.quartz.bean.query;

import cc.kevinlu.quartz.bean.base.BaseQuery;
import lombok.Data;
import lombok.ToString;

/**
 * 调度任务查询条件
 * 
 * @author cc
 */
@Data
@ToString
public class TaskQuery extends BaseQuery {

    /**
     * 调度名称
     */
    private String name;
    /**
     * 调度负责人
     */
    private String author;
}
