package cc.kevinlu.quartz.common.enums;

/**
 * 任务状态
 *
 * @author cc
 */

public enum TaskStatusEnums {
    /**
     *
     */
    STOP(0, "停止"),
    /**
     *
     */
    RUNNING(1, "运行中"),
    /**
     *
     */
    PAUSE(2, "暂停");

    private Integer code;
    private String  description;

    TaskStatusEnums(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
