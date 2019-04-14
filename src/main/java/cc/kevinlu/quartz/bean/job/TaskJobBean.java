package cc.kevinlu.quartz.bean.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskJobBean {
    private String name;
    private String jobClassName;
    private String cornExpression;
    private String description;
    private String state;

}
