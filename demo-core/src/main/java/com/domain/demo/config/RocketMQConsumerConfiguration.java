package com.domain.demo.config;

import com.domain.demo.service.mq.MessageProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@SpringBootConfiguration
public class RocketMQConsumerConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LogManager.getLogger(RocketMQConsumerConfiguration.class);
  
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    
    @Value("${rocketmq.consumer.topic}")
    private String topic;
    
    @Value("${rocketmq.consumer.tag}")
    private String tag;
    
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    
    @Autowired
    private MessageProcessor messageProcessor;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
          this.init();
        } catch (MQClientException e) {
          logger.error("RocketMQ Consummer init failed, Exception:" , e);
        } 
    }
  
    private void init() throws MQClientException{
//      logger.info("------------------- Starting " + topic + ":" + tag + " Consummer Begin -------------------");
      DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName); 
      consumer.setNamesrvAddr(namesrvAddr); 
      consumer.subscribe(topic, tag); // 开启内部类实现监听 
      consumer.setConsumeThreadMax(consumeThreadMax);
      consumer.setConsumeThreadMin(consumeThreadMin);
      consumer.setMessageListener(new MessageListenerConcurrently() {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
          for(MessageExt msg: msgs){
            try {
               String msgStr = new String(msg.getBody(), "utf-8");
               logger.debug("deal mq {}" , msgStr);
               messageProcessor.dealMqMessage(msg);
            } catch (Exception e) {
                //TODO 这里应该返回RECONSUME_LATER，MQ会重新消费，不过业务要做好幂等，待改进。。。say by CleverApe
                //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                logger.warn("deal mq failed, Exception:" , e);
            }
          }
          return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        
      });
      consumer.start(); 
      logger.info("------------------- RocketMQ Consummer Start Succeed -------------------");

    }
    
}
