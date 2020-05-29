package com.domain.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description 基础实体属性
 * @Classname BaseEntity
 * @Author CleverApe
 * @Date 2020-05-27 16:47
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity {

    private Integer id;
    private Date createTime;
    private Date updateTime;
}
