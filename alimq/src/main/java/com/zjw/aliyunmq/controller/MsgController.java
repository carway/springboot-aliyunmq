package com.zjw.aliyunmq.controller;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.zjw.aliyunmq.util.AliMqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 消息controller
 * @author carway
 * @date 2018/6/5.
 */
@RestController
@RequestMapping(value = "/msg")
public class MsgController {
    @Autowired
    AliMqUtil aliMqUtil;

    @PostMapping(value = "/send")
    public SendResult msg(@RequestBody String msgbody, HttpServletRequest request){
        return aliMqUtil.sendMessage("test",msgbody);
    }
}
