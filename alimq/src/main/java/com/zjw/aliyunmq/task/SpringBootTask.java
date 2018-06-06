package com.zjw.aliyunmq.task;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.SendResult;
import com.zjw.aliyunmq.util.AliMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


/**
 * @author carway
 * springBoot自带的定时器
 * Created by 嘉炜 on 2017/9/30.
 */
@Slf4j
@Component
public class SpringBootTask {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static long TEN_Minute =  10*60 * 1000;

    private AliMqUtil aliMqUtil;

    @Autowired
    public SpringBootTask(AliMqUtil aliMqUtil) {
        this.aliMqUtil = aliMqUtil;
    }

    @Scheduled(fixedDelay=TEN_Minute)
    //@Scheduled(cron="0 0 23 * * ?")
    public void dataProcess(){
       for(int i=1;i<=10;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",i);
            jsonObject.put("userId",i*10);
            jsonObject.put("filePath","b"+i);
            jsonObject.put("fileClass",1);
            SendResult result =aliMqUtil.sendMessage("test", jsonObject.toJSONString());
            System.out.println("第"+i+"个的发送结果：topic="+ result.getTopic() + ", msgId=" + result.getMessageId());
        }
    }

}
