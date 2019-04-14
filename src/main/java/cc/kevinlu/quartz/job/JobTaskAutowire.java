package cc.kevinlu.quartz.job;

/**
 * 将本Job Bean交于Spring容器进行管理
 * 
 * @author cc
 */
public class JobTaskAutowire {
    private boolean contextInit;

    public JobTaskAutowire() {
        this.initContextAware();
    }

    public void initContextAware() {
        if (!this.contextInit) {
            ContextAwareUtils.autowireBean(this);
            this.contextInit = true;
        }

    }
}
