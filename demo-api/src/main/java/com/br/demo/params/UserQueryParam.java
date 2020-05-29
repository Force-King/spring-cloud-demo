package com.br.demo.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Description 用户查询接口入参
 * @Classname UserQueryParam
 * @Author CleverApe
 * @Date 2020-05-27 18:49
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserQueryParam {

    @NotNull(message = "uid不能为空")
    private Integer uid;
}
