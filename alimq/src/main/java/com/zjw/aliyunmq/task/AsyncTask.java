package com.zjw.aliyunmq.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;



/**
 * 异步任务--对未处理的数据进行处理
 * @author carway
 * @date 2018/5/30.
 */
@Slf4j
@Component
public class AsyncTask {

    @Async
    public void dpFile(String msg){
        System.out.println("我对"+msg+"消息进行处理");
    }


}
