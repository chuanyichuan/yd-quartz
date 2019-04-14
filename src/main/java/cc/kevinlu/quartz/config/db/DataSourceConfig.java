package cc.kevinlu.quartz.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *  多数据库DataSource 配置文件信息
 *
 *  @author
 *  @date 2019/03/21
 */
@Configuration
@MapperScan(basePackages = { "cc.kevinlu.quartz.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Bean(name = "dataSourceQuartzProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceQuartzProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourceQuartz")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceQuartz() {
        return dataSourceQuartzProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "sqlSessionFactory")
    @Autowired
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceQuartz") DataSource dataSourceTest)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceTest);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/**/*.xml"));
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dataSourceQuartz") DataSource dataSourceQuartz)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(dataSourceQuartz));
    }

    @Bean(name = "quartz_transaction")
    public PlatformTransactionManager prodTransactionManager(@Qualifier("dataSourceQuartz") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
