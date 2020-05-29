package com.domain.demo.vo;

import com.domain.demo.dto.UserDTO;
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

    private static final long serialVersionUID = 1L;

    private String str;

    private List productList;

    private UserDTO userInfo;
}
