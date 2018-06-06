package com.zjw.aliyunmq.config.mq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import lombok.extern.slf4j.Slf4j;


/**
 * 阿里云MQ消费者监听器
 * @author carway
 * @date 2018/6/5.
 */
@Slf4j
public class AliMqConsumerListener implements MessageListener {


    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        String msg = "";
        String key = message.getKey();
        // 根据业务唯一标识的 key 做幂等处理
        try {
            msg = new String(message.getBody(), "UTF-8");
            log.info("消费成功，消息为：" + msg);
            //do something..
            //TODO
            return Action.CommitMessage;
        } catch (Exception e) {
            //消费失败
            log.info("消费失败：" + msg, e);
            //返回ReconsumeLater|null|直接抛出异常都会让消息重试，如果想消费失败后，不重试的话,可以返回Action.CommitMessage;
            return Action.ReconsumeLater;
        }

    }
}
