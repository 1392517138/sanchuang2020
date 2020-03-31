package com.geek.guiyu.service;

import com.geek.guiyu.domain.dataobject.*;
import com.geek.guiyu.domain.exception.AlreadyRegisterException;
import com.geek.guiyu.domain.exception.NoPhoneException;
import com.geek.guiyu.domain.exception.ShortMessageException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface UserService {
    /**
     * 短信接口
     * @param phoneDTO
     * @return
     * @throws AlreadyRegisterException
     * @throws NoPhoneException
     */
    MessageDTO shortMessage(PhoneDTO phoneDTO) throws AlreadyRegisterException, NoPhoneException;

    /**
     * 注册接口
     * @param registerDTO
     * @return
     * @throws ShortMessageException
     */
    TokenDTO register(RegisterDTO registerDTO) throws ShortMessageException, ParseException;

    /**
     * 登录接口
     * @param loginDTO
     * @return
     * @throws ShortMessageException
     * @throws NoPhoneException
     */
    TokenDTO login(LoginDTO loginDTO) throws ShortMessageException, NoPhoneException;

    /**
     * 编辑用户
     * @param token token
     * @param editInfoDTO
     * @return
     */
    boolean editUserInfo(String token, UserEditInfoDTO editInfoDTO) throws ParseException;

    /**
     * 查询用户信息
     * @param token 用户token
     * @return
     */
    UserInfoDTO queryUserInfo(String token);

    /**
     * 查询用户的关注
     * @param token 用户token
     * @return
     */
    List<FollowDTO> queryFollows(String token);

    /**
     * 查询用户的粉丝
     * @param token
     * @return
     */
    List<FansDTO> queryFans(String token);


    /**
     * 修改用户头像，背景
     * @param request
     * @param multipartFile
     * @param type
     * @param model
     * @return
     */
    Boolean modPhoto(HttpServletRequest request, MultipartFile multipartFile,String type, Model model) throws IOException, FileUploadException, ParseException;
}
