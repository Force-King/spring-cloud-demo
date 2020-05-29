package com.br.demo.service.mq;

import org.apache.rocketmq.common.message.MessageExt;

public interface MessageProcessor {
  void dealMqMessage(MessageExt msg);
}
