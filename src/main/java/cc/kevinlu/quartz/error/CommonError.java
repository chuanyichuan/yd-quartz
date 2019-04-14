package cc.kevinlu.quartz.error;

/**
 * 通用错误信息
 * 
 * @author: cc
 **/
public enum CommonError implements IError {

    /**
     **/
    BIZ_ERROR("200", "业务异常"),
    /**
     **/
    NO_PERMISSION("302", "无操作权限"),
    /**
     **/
    DATA_CREATE_ERROR("305", "创建失败"),
    /**
     **/
    DATA_MODIFY_ERROR("306", "更新失败"),
    /**
     **/
    DATA_DELETE_ERROR("307", "删除失败"),
    /**
     **/
    PARAM_INPUT_ILLEGAL_ERROR("402", "参数输入不合法"),
    /**
     **/
    PARAM_ID_EMPTY_ERROR("403", "参数ID为空"),
    /**
     **/
    DATA_NOT_EXISTS_ERROR("404", "数据不存在"),
    /**
     **/
    DATE_ERROR_ERROR("405", "时间输入有误"),
    /**
     **/
    SYS_ERROR("500", "系统异常"),
    /**
     **/
    DATA_HAS_BEEN_DELETED("502", "数据已被删除"),
    /**
     **/
    DATA_EXISTS_ERROR("503", "数据已存在"),
    /**
     **/
    NAME_REPEAT_ERROR("5031", "名称已存在"),
    /**
     **/
    PARAM_LENGTH_NOT_ALLOW("505", "参数长度校验不合法");

    /**
     * */
    private String code;
    /**
     * 错误信息
     **/
    private String message;

    CommonError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
