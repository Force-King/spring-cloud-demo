package com.domain.demo.service.impl.mq;

import com.alibaba.fastjson.JSONObject;
import com.domain.demo.service.mq.MQService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author CleverApe
 * @Classname MQServiceImpl
 * @Description RocketMQ 接口实现
 * @Date 2019-07-26 16:31
 * @Version V1.0
 */
@Service
public class MQServiceImpl implements MQService {

    private Logger logger = LogManager.getLogger(MQServiceImpl.class);

    private DefaultMQProducer producer;

    @Value("${rocketmq.namesrv.addr}")
    private String mqUrl;

    private SendCallback mqcallback;

    @Override
    public boolean sendMsg(String topic, String tag, String msgKey, String body) {
        if(StringUtils.isBlank(topic) || StringUtils.isBlank(tag) || StringUtils.isBlank(body)) {
            logger.error("send message parameter is null, topic:{}, tag:{}, body:{}",topic,tag,body);
            return false;
        }
        try {
            Message msg = new Message(topic, tag, body.getBytes());
            if(msgKey != null) {
                msg.setKeys(msgKey);
            }
            this.producer.send(msg, mqcallback);
        } catch (Exception e) {
            logger.error("RocketMQ send message Exception, topic={}, tag={}, msgKey={}, body={}, Exception: ",topic,tag,msgKey,body,e);
            return false;
        }
        return true;
    }

    @PostConstruct
    public void init(){
        producer = new DefaultMQProducer("demo_producer");
        producer.setNamesrvAddr(mqUrl);
        try {
            producer.start();
            logger.info("---------- RocketMQ Init demo_producer Succeed ----------");
        } catch (MQClientException e) {
            logger.error("RocketMQ Init demo_producer failed, Exception: " , e);
        }
        mqcallback = new MQCallbackImpl();
    }

    private class MQCallbackImpl implements SendCallback{

        @Override
        public void onSuccess(SendResult sendResult) {
            SendStatus status = sendResult.getSendStatus();
            if(status == SendStatus.SEND_OK){

            }else{
                logger.error("RocketMQ send message failed, result: {}" + JSONObject.toJSONString(sendResult));
            }
        }

        @Override
        public void onException(Throwable e) {
            logger.error("RocketMQ send message Exception: ", e);
        }
    }
}
