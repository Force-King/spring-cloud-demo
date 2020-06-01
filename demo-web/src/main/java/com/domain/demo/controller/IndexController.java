package com.domain.demo.controller;

import com.domain.demo.remote.client.UserClient;
import com.domain.demo.dto.UserDTO;
import com.domain.demo.params.UserQueryParam;
import com.domain.demo.util.RestApiResult;
import com.domain.demo.vo.IndexVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CleverApe
 * @Classname IndexController
 * @Description 首页
 * @Date 2019-07-10 17:56
 * @Version V1.0
 */
@RestController
@Slf4j
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private UserClient userClient;

    @PostMapping("/user/info")
    public RestApiResult<IndexVo> index(@RequestParam Integer uid) {
        RestApiResult<IndexVo> ret = new RestApiResult();
        UserQueryParam userQueryParam = new UserQueryParam().setUid(uid);
        RestApiResult<UserDTO> userDto = userClient.getByUid(userQueryParam);
        IndexVo indexVo = new IndexVo().setStr("首页").setProductList(null).setUserInfo(userDto.getData());
        log.info("进入index方法，uid={}",uid);
        return ret.success(indexVo);
    }

}
