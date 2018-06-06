package com.zjw.aliyunmq.config.mq;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 阿里云MQ配置类
 * @author carway
 * Created by dell on 2018/6/5.
 */
@Configuration
public class AliMqConfig {
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.producerId}")
    private String producerId;
    @Value("${mq.consumerId}")
    private String consumerId;
    @Value("${mq.accesskey}")
    private String accesskey;
    @Value("${mq.secretkey}")
    private String secretkey;
    @Value("${mq.onsaddr}")
    private String onsaddr;
    //订阅Tag
    @Value("${mq.subExpression}")
    private String subExpression;
    //超时时间
    @Value("${mq.sendMsgTimeoutMillis}")
    private String sendMsgTimeoutMillis;
    @Value("${mq.suspendTimeMillis}")
    private String suspendTimeMillis;
    @Value("${mq.maxReconsumeTimes}")
    private String maxReconsumeTimes;


    public String getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(String subExpression) {
        this.subExpression = subExpression;
    }

    @Bean(name = "producer",initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean producer() {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        System.out.println("执行producer初始化");
        properties.put(PropertyKeyConst.ProducerId, producerId);
        properties.put(PropertyKeyConst.AccessKey, accesskey);
        properties.put(PropertyKeyConst.SecretKey, secretkey);
        //properties.put(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
        properties.put(PropertyKeyConst.ONSAddr, onsaddr);
        producerBean.setProperties(properties);
        producerBean.setProperties(properties);
        producerBean.start();
        return producerBean;
    }


    @Bean(name = "consumer",initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean consumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, consumerId);
        properties.put(PropertyKeyConst.AccessKey, accesskey);
        properties.put(PropertyKeyConst.SecretKey, secretkey);
        properties.put(PropertyKeyConst.ONSAddr, onsaddr);
        //consumerProperties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING); //集群订阅方式设置（不设置的情况下，默认为集群订阅方式）
        //consumerProperties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING); // 广播订阅方式设置
        // 顺序消息消费失败进行重试前的等待时间 单位(毫秒)
        //properties.put(PropertyKeyConst.SuspendTimeMillis, suspendTimeMillis);
        // 消息消费失败时的最大重试次数
        properties.put(PropertyKeyConst.MaxReconsumeTimes, maxReconsumeTimes);
        //设置允许批量消费的最大值，实际批量消费的数目可能小于该值，默认是64
        //properties.put(PropertyKeyConst.ConsumeThreadNums,64);
        //设置每条消息消费的最大超时时间，超过设置时间则被视为消费失败，等下次重新投递再次消费。每个业务需要设置一个合理的值，单位（分钟）。默认：15
        //properties.put(PropertyKeyConst.ConsumeTimeout,15);
        consumerBean.setProperties(properties);
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setExpression("*");
        Map<Subscription, MessageListener> map = new HashMap();
        map.put(subscription, new AliMqConsumerListener());
        consumerBean.setSubscriptionTable(map);
        return consumerBean;
    }
}
