package cc.kevinlu.quartz.bean.base;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 通用查询对象
 *
 * @author cc
 */
@Data
@ToString
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 4776750142335806856L;

    /**
     * 每页条数
     */
    protected Integer         pageSize         = 10;
    /**
     * 页码
     */
    protected Integer         pageIndex        = 1;
}
