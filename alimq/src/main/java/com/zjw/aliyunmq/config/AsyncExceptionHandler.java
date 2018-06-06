package com.zjw.aliyunmq.config;

import com.zjw.aliyunmq.exception.AsyncException;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;

/**
 * 异步异常处理类：
 * Created by 嘉炜 on 2017/9/23.
 */
@Configuration
@EnableAsync
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static Logger logger = Logger.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... objects) {
        logger.info("Async method: "+method.getName()+" has uncaught exception");

        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            logger.info("asyncException:"+asyncException.getErrorMessage());
        }

        logger.info("Exception :");
        ex.printStackTrace();
    }

}
