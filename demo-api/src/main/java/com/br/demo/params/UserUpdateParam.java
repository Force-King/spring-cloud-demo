package com.br.demo.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Description 用户更新接口入参
 * @Classname UserUpdateParam
 * @Author CleverApe
 * @Date 2020-05-29 14:08
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserUpdateParam {

    @NotNull(message = "uid不能为空")
    private Integer uid;

    @NotNull(message = "姓名不能为空")
    private String userName;
}
