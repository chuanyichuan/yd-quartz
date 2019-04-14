package cc.kevinlu.quartz.bean.base;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

/**
 * ID通用请求参数
 * 
 * @author cc
 */
@Data
@ToString
public class IdBaseRequest {

    @NotNull
    private Long id;

}
