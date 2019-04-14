package cc.kevinlu.quartz;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cc
 */
@Slf4j
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = { "cc.kevinlu.quartz" })
public class YdQuartzApplication {
    @Autowired
    private RestTemplateBuilder builder;

    public static void main(String[] args) {
        SpringApplication.run(YdQuartzApplication.class, args);
        log.info("=======application start success======");
    }

    @Bean
    public RestTemplate restTemplate() {
        builder.setConnectTimeout(Duration.ofSeconds(30L));
        builder.setReadTimeout(Duration.ofSeconds(30L));
        return builder.build();
    }

}
