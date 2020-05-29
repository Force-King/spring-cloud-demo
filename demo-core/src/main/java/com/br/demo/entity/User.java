package com.br.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description 用户实体
 * @Classname User
 * @Author guifei.qin
 * @Date 2020-05-27 16:45
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends BaseEntity {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户姓名
     */
    private String userName;

}
