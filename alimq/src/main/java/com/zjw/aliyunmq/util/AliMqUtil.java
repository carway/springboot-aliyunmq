package com.zjw.aliyunmq.util;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.zjw.aliyunmq.config.mq.AliMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里云MQ工具类
 * @author carway
 * @date 2018/6/5.
 */
@Component
@Slf4j
public class AliMqUtil {

    //MQ配置
    @Autowired
    private AliMqConfig aliMqConfig;

    //生产者，向spring注入的bean别名是producer
    @Autowired
    private ProducerBean producer;

    //消息主题
    @Value("${mq.topic}")
    public String topic;

    /**
     * 同步发送消息
     * @param tag  标签
     * @param msgbody 消息主体
     * @return
     */
    public SendResult sendMessage(String tag, String msgbody) {

        Message msg = new Message(
                // Message 所属的 Topic
                topic,
                // Message Tag,
                // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                tag,
                // Message Body
                // 任何二进制形式的数据，MQ 不做任何干预，需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                msgbody.getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_" + Math.round(Math.random() * 8999 + 1000));
        try {
            SendResult sendResult = producer.send(msg);
            if (sendResult != null) {
                log.info("消息发送成功：" + sendResult.toString());
                return sendResult;
            }
        } catch (ONSClientException e) {
            log.info("消息发送失败：", e);
            // 出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
            return null;
        }
        return null;
    }


    /**
     * 异步发送消息
     * @param tag 消息标签
     * @param msgbody 消息主体
     */
    public void sendAsyncMessage(String tag, String msgbody) {
        Message msg = new Message(
                // Message 所属的 Topic
                topic,
                // Message Tag,
                // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                tag,
                // Message Body
                // 任何二进制形式的数据，MQ 不做任何干预，需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                msgbody.getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_" + Math.round(Math.random() * 8999 + 1000));

        producer.sendAsync(msg, new SendCallback() {
            @Override
            public void onSuccess(final SendResult sendResult) {
                // 消费发送成功
                log.info("消息发送成功：topic=" + sendResult.getTopic() + ", msgId=" + sendResult.getMessageId());
            }

            @Override
            public void onException(OnExceptionContext context) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                log.info("消息发送失败：topic=" + context.getTopic() + ", msgId=" + context.getMessageId());
            }
        });
    }

    /**
     * 单向发送消息
     * @param tag 消息标签
     * @param msgbody 消息主体
     */
    public void sendOnewayMessage(String tag, String msgbody) {
        Message msg = new Message(
                // Message 所属的 Topic
                topic,
                // Message Tag,
                // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                tag,
                // Message Body
                // 任何二进制形式的数据，MQ 不做任何干预，需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                msgbody.getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_" + Math.round(Math.random() * 8999 + 1000));
        producer.sendOneway(msg);
    }

}
