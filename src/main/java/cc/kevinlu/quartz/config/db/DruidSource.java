package cc.kevinlu.quartz.config.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.durid")
public class DruidSource {
    private String  dbUrl;

    private String  username;

    private String  password;

    private String  driverClassName;

    private int     initialSize;

    private int     minIdle;

    private int     maxActive;

    private int     maxWait;

    private int     timeBetweenEvictionRunsMillis;

    private int     minEvictableIdleTimeMillis;
    private String  validationQuery;

    private boolean testWhileIdle;
    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean poolPreparedStatements;

    private int     maxPoolPreparedStatementPerConnectionSize;

    private String  filters;

    private String  connectionProperties;

    @Bean //声明其为Bean实例
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        datasource.setFilters(filters);

        return datasource;
    }
}
