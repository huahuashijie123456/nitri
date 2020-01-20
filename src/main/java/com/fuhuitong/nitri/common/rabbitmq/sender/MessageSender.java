package com.fuhuitong.nitri.common.rabbitmq.sender;

/**
 * @Author Wang
 * @Date 2019/5/6 0006 12:21
 **/

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//RabbitTemplate.ConfirmCallback
@Slf4j
@Component
public class MessageSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String content) {
         log.info("有盾发送消息----------》");
        this.rabbitTemplate.convertAndSend("udyhy", content);
    }


    public void sendObj() {
        Object obj = null;
        System.out.println("发送 : " + obj);
        this.rabbitTemplate.convertAndSend("helloObj", obj);
    }

    //易宝支付异步通知
    public void YeePaySend(Map<String, String> result){
        String  param= JSON.toJSONString(result);
        log.info("易宝支付异步通知发送：{}"+param);
        this.rabbitTemplate.convertAndSend("yeepay", param);
    }



    //易宝提现异步通知
    public void YeePayWithSend(String jsonStr){
        log.info("易宝提现异步通知发送：{}"+jsonStr);
        this.rabbitTemplate.convertAndSend("yeepaywith", jsonStr);
    }

    //畅捷支付异步通知
    public void chanjiePaySend(String jsonStr){
        log.info("畅捷支付异步通知发送：{}"+jsonStr);
        this.rabbitTemplate.convertAndSend("chanjiePay", jsonStr);
    }
}
