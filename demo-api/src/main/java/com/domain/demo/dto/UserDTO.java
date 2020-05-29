package com.domain.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description 用户Dto
 * @Classname UserDTO
 * @Author CleverApe
 * @Date 2020-05-28 18:13
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户姓名
     */
    private String userName;
}
