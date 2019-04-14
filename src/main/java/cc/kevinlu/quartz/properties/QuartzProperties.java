package cc.kevinlu.quartz.properties;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

/**
 * @author cc
 */
@Data
@ToString
@Component
public class QuartzProperties {

    private String             datasourceDriver;
    private String             datasourceUrl;
    private String             datasourceUser;
    private String             datasourcePassword;
    private String             datasourceMaxConnections;
    private String             schedulerName;
    private String             clustered;

    public static final String FILENAME        = "quartz.properties";
    public static final String DRIVER          = "org.quartz.dataSource.qzDS.driver";
    public static final String URL             = "org.quartz.dataSource.qzDS.URL";
    public static final String USER            = "org.quartz.dataSource.qzDS.user";
    public static final String PASSWORD        = "org.quartz.dataSource.qzDS.password";
    public static final String MAX_CONNECTIONS = "org.quartz.dataSource.qzDS.maxConnections";
    public static final String SCHEDULER_NAME  = "org.quartz.scheduler.instanceName";
    public static final String IS_CLUSTERED    = "org.quartz.jobStore.isClustered";
}
