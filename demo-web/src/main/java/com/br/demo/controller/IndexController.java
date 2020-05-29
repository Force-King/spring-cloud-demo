package com.br.demo.controller;


import com.br.demo.api.IUserRestApi;
import com.br.demo.client.UserClient;
import com.br.demo.dto.UserDto;
import com.br.demo.params.UserQueryParam;
import com.br.demo.util.RestApiResult;
import com.br.demo.vo.IndexVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(IndexController.class);

    @Autowired
    private IUserRestApi userRestApi;

    @Autowired
    private UserClient userClient;

    @PostMapping("/user/info")
    public RestApiResult<IndexVo> index(@RequestParam Integer uid) {
        RestApiResult<IndexVo> ret = new RestApiResult();
        UserQueryParam userQueryParam = new UserQueryParam().setUid(uid);
        RestApiResult<UserDto> userDto = userClient.getByUid(userQueryParam);
        IndexVo indexVo = new IndexVo().setStr("首页").setProductList(null).setUserInfo(userDto.getData());
        logger.info("进入index方法，uid={}",uid);
        return ret.success(indexVo);
    }

}
