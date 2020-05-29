package com.br.demo.service.impl.mq;

import com.alibaba.fastjson.JSONObject;
import com.br.demo.service.mq.MessageProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CleverApe
 * @Classname DemoMQMessageProcessorImpl
 * @Description RocketMQ消息消费者 处理类实现Demo
 * @Date 2019-07-16
 * @Version V1.0
 */
@Component
public class DemoMQMessageProcessorImpl implements MessageProcessor {

    private Logger logger = LogManager.getLogger(DemoMQMessageProcessorImpl.class);

    /**
     * 消费 *** 消息
     * 1.参数验证、消费条件验证、幂等验证
     * 2.开始消费消息
     * 3.消费异常处理（事务回滚）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealMqMessage(MessageExt msg) {
        JSONObject body = this.parseBody(msg);
        if (checkBody(body)) {
            try {
                //TODO some things

                //...

            } catch (Exception e) {
                logger.error("RocketMQ dealMqMessage Exception， param = {} Exception ", body.toJSONString(), e);
                throw new RuntimeException(e.getMessage());
            }

        }
    }

    /**
     * function description
     *
     * @param msg
     * @return
     */
    private JSONObject parseBody(MessageExt msg) {
        JSONObject rs = null;
        try {
            String msgStr = new String(msg.getBody(), "utf-8");
            rs = JSONObject.parseObject(msgStr);
        } catch (Exception e) {
            logger.error("parseBody error e", e);
        }
        return rs;
    }


    /**
     * 检查IOS设备数据完整性
     *
     * @param body
     * @return
     */
    private boolean checkBody(JSONObject body) {
        boolean rs = true;
        try {
          if (body == null) {
              logger.warn("check MsgBody is empty.");
              return false;
          }
          if (body.getString("uid") == null || Integer.valueOf(body.getString("uid")) != 0) {
              logger.warn("checkMsgBody uid is empty or uid is not 0, body: {}", body.toJSONString());
              return false;
          }
          if (!body.containsKey("appId")) {
              logger.warn("checkMsgBody appId is empty, body: {}", body.toJSONString());
              return false;
          }
        } catch (Exception e) {
          logger.error("checkBody exception e" , e);
        }
        return rs;
    }

}
