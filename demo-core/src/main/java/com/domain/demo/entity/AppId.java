package com.domain.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description AppId
 * @Classname AppId
 * @Author CleverApe
 * @Date 2020-05-27 16:47
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppId {

    private Integer id;
    private Integer innerId;
    private String outerId;
    private String description;


}
