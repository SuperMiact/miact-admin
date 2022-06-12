package cn.miact.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    /**
     * 定义导出服务线程池
     * @return
     */
    @Bean("exportServiceExecutor")
    public Executor exportServiceExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 核心线程数
        executor.setCorePoolSize(
                // 获取当前机器的核心数
                Runtime.getRuntime().availableProcessors());

        // 最大线程数
        executor.setMaxPoolSize(
                Runtime.getRuntime().availableProcessors()*2);

        // 队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);

        // 线程池中的线程名前缀
        executor.setThreadNamePrefix("export-");

        // 拒绝策略
        executor.setRejectedExecutionHandler(
                // 直接拒绝
                new ThreadPoolExecutor.AbortPolicy());

        // 执行初始化
        executor.initialize();

        return executor;
    }

}
