package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.*;
import com.geek.guiyu.domain.exception.AllreadyFollowException;
import com.geek.guiyu.domain.exception.AlreadyRegisterException;
import com.geek.guiyu.domain.exception.NoPhoneException;
import com.geek.guiyu.domain.exception.ShortMessageException;
import com.geek.guiyu.service.UserService;
import com.geek.guiyu.service.util.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

@RestController
@Api(tags = "用户基本操作接口")
public class UserController {
    @Autowired
    private UserService userService;
    private HttpServletRequest request;
    private Integer id;

    /**
     * 短信controller层
     * @param phoneDTO
     * @return
     * @throws AlreadyRegisterException
     * @throws NoPhoneException
     */
    @PostMapping("/getShortMessage")
    @ApiOperation("发送手机短信,Type=(login,register)，无需token")
    public JSONObject getShortMessage(@RequestBody PhoneDTO phoneDTO) throws AlreadyRegisterException, NoPhoneException {
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
    @ApiOperation("注册,无需token")
    public JSONObject register(@RequestBody RegisterDTO registerDTO) throws ShortMessageException, AlreadyRegisterException, ParseException {
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
    @ApiOperation("登陆，会返回token存储[后端已设置为30天过期]，用来标识用户")
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
    @ApiOperation("更新用户信息,introduce为自我介绍,birthday格式:'yyyy-MM-dd',sex=(0,1)代表男、女")
    public JSONObject updateUserInfo(HttpServletRequest request, @RequestBody UserEditInfoDTO userEditInfoDTO) throws ParseException {
        String token = request.getHeader("token");
        return JSONUtils.success(userService.editUserInfo(token, userEditInfoDTO));
    }

    /**
     * 查询用户信息controller层
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    @ApiOperation("查询用户信息")
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
    @ApiOperation("查询用户自己关注的人")
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
    @ApiOperation("查看用户的粉丝")
    public JSONObject queryUserFans(HttpServletRequest request){
        String token = request.getHeader("token");
        return JSONUtils.success(userService.queryFans(token));
    }

    /**
     * 添加自己关注的人
     * @param request
     * @param id
     * @return
     * @throws ParseException
     */
    @GetMapping("/setUserFollow")
    @ApiOperation("添加自己关注的人")
    public JSONObject setUserFollow(HttpServletRequest request, @ApiParam("要添加人的id") @RequestParam Integer id) throws ParseException, AllreadyFollowException {
        String token = request.getHeader("token");
        return JSONUtils.success(userService.setUserFollow(token, id));
    }

    @GetMapping("/setUserNotFollow")
    @ApiOperation("设置这个人不关注")
    public JSONObject setUserNotFollow(HttpServletRequest request, @ApiParam("要取消关注的人id") @RequestParam Integer id) throws ParseException {
        String token = request.getHeader("token");
        return JSONUtils.success(userService.setUserNotFollow(token, id));
    }

    /**
     * 修改用户头像
     * @param request
     * @param multipartFile
     * @param type
     * @param model
     */
    @PostMapping("/modPhoto")
    @ApiOperation("修改用户头像,背景，Type=(avatar,back)分别为头像，背景")
    public JSONObject modPhoto(HttpServletRequest request, @RequestParam MultipartFile multipartFile,@RequestParam String type, Model model) throws IOException, FileUploadException, ParseException {
        return JSONUtils.success(userService.modPhoto(request,multipartFile,type,model));
    }


}
