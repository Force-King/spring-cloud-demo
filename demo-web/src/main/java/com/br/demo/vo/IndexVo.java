package com.br.demo.vo;

import com.br.demo.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Classname IndexVo
 * @Author CleverApe
 * @Date 2020-05-29 15:46
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IndexVo implements Serializable {

    private String str;

    private List productList;

    private UserDto userInfo;
}
