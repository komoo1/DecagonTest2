package com.decagon.stock;

import com.decagon.stock.security.SecurityInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

/**
 * @author Victor.Komolafe
 */
@EnableWebMvc
@EnableAsync
@SpringBootApplication
@RequiredArgsConstructor
public class StockApplication implements WebMvcConfigurer {

    private final SecurityInterceptor securityInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean(name="asyncExec")
    public Executor asyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(10);

        executor.initialize();

        return executor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor).addPathPatterns("/**");
    }
}
