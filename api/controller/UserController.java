package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.LoginDTO;
import com.geek.guiyu.domain.dataobject.PhoneDTO;
import com.geek.guiyu.domain.dataobject.RegisterDTO;
import com.geek.guiyu.domain.dataobject.UserEditInfoDTO;
import com.geek.guiyu.domain.exception.AlreadyRegisterException;
import com.geek.guiyu.domain.exception.NoPhoneException;
import com.geek.guiyu.domain.exception.ShortMessageException;
import com.geek.guiyu.service.UserService;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 短信controller层
     * @param phoneDTO
     * @return
     * @throws AlreadyRegisterException
     * @throws NoPhoneException
     */
    @GetMapping("/getShortMessage")
    public JSONObject getShortMessage(PhoneDTO phoneDTO) throws AlreadyRegisterException, NoPhoneException {
        return JSONUtils.success(userService.shortMessage(phoneDTO));
    }

    /**
     * 注册controller层
     * @param registerDTO
     * @return
     * @throws ShortMessageException
     * @throws AlreadyRegisterException
     */
    @PostMapping("/register")
    public JSONObject register(@RequestBody RegisterDTO registerDTO) throws ShortMessageException, AlreadyRegisterException {
        return JSONUtils.success(userService.register(registerDTO));
    }

    /**
     * 登录controller层
     * @param loginDTO
     * @return
     * @throws ShortMessageException
     * @throws NoPhoneException
     */
    @PostMapping("/login")
    public JSONObject login(@RequestBody LoginDTO loginDTO) throws ShortMessageException, NoPhoneException {
        return JSONUtils.success(userService.login(loginDTO));
    }

    /**
     * 修改资料controller层
     * @param request
     * @param userEditInfoDTO
     * @return
     */
    @PostMapping("/updateUserInfo")
    public JSONObject updateUserInfo(HttpServletRequest request, @RequestBody UserEditInfoDTO userEditInfoDTO){
        String token = request.getHeader("token");
        return JSONUtils.success(userService.editUserInfo(token, userEditInfoDTO));
    }

    /**
     * 查询用户信息controller层
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    public JSONObject queryUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        return JSONUtils.success(userService.queryUserInfo(token));
    }

    /**
     * 查询用户关注controller层
     * @param request
     * @return
     */
    @GetMapping("/getUserFollow")
    public JSONObject queryUserFollow(HttpServletRequest request){
        String token = request.getHeader("token");
        return JSONUtils.success(userService.queryFollows(token));
    }

    /**
     * 查询用户粉丝
     * @param request
     * @return
     */
    @GetMapping("/getUserFans")
    public JSONObject queryUserFans(HttpServletRequest request){
        String token = request.getHeader("token");
        return JSONUtils.success(userService.queryFans(token));
    }
}
