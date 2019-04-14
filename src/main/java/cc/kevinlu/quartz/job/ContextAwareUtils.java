package cc.kevinlu.quartz.job;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author cc
 */
@Component
public class ContextAwareUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public ContextAwareUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextAwareUtils.applicationContext = applicationContext;
    }

    public static void autowireBean(Object bean) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
    }
}
