package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.*;
import com.geek.guiyu.domain.exception.AlreadyRegisterException;
import com.geek.guiyu.domain.exception.NoPhoneException;
import com.geek.guiyu.domain.exception.ShortMessageException;
import com.geek.guiyu.domain.model.UserFile;
import com.geek.guiyu.domain.model.UserFollow;
import com.geek.guiyu.domain.model.UserInfo;
import com.geek.guiyu.domain.model.UserInfoExample;
import com.geek.guiyu.infrastructure.dao.UserFileMapper;
import com.geek.guiyu.infrastructure.dao.UserFollowDao;
import com.geek.guiyu.infrastructure.dao.UserInfoMapper;
import com.geek.guiyu.service.UserService;
import com.geek.guiyu.service.util.ShortMessageUtil;
import com.geek.guiyu.service.util.TimeUtils;
import com.geek.guiyu.service.util.TokenUtils;
import com.geek.guiyu.service.util.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ShortMessageUtil shortMessageUtil;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserFileMapper userFileMapper;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserFollowDao userFollowDao;


    /**
     * 用来保存短信
     */
    private static ConcurrentHashMap<String, String> shortMessageMap = new ConcurrentHashMap<>();
    /**
     * 发送短信
     * @param phoneDTO 手机号
     * @return
     * @throws AlreadyRegisterException
     */
    @Override
    public MessageDTO shortMessage(PhoneDTO phoneDTO) throws AlreadyRegisterException, NoPhoneException {
        String phone = phoneDTO.getPhone();
        String type = phoneDTO.getType();
        String shortMessage;
        if("register".equals(type)){
            // 如果存在此账号，抛出AlreadyRegisterException异常
            if(userInfoMapper.selectByPhone(phone) != null){
                throw new AlreadyRegisterException();
            }
        }
        if("login".equals(type)){
            if(userInfoMapper.selectByPhone(phone) == null){
                throw new NoPhoneException();
            }
        }
        shortMessage = shortMessageUtil.sendShortMessage(phone);
        shortMessageMap.put(phone, shortMessage);
        return new MessageDTO(shortMessage);
    }

    /**
     * 注册
     * @param registerDTO
     * @return
     */
    @Override
    public TokenDTO register(RegisterDTO registerDTO) throws ShortMessageException, ParseException {
        // 获得短信
        String shortMessage = registerDTO.getShortMessage();
        // 获得手机号
        String phone = registerDTO.getPhone();

        if(!shortMessage.equals(shortMessageMap.get(phone))){
            throw new ShortMessageException();
        }
        UserInfo userInfo = dozerMapper.map(registerDTO, UserInfo.class);
        userInfo.setDeleted((byte) 0);
        userInfo.setFans(0);
        userInfo.setNickName("小萌新");
        //时间：
        Date date = TimeUtils.getTime("ss");
        userInfo.setCreateTime(date);
        userInfo.setUpdateTime(date);

        userInfoMapper.insert(userInfo);
        shortMessageMap.remove(phone);
        return tokenUtils.createToken(userInfo);
    }

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws ShortMessageException, NoPhoneException {
        String phone = loginDTO.getPhone();
        String message = loginDTO.getShortMessage();

        // 如果与发送的短信不一致
//        if(!message.equals(shortMessageMap.get(phone))){
//            throw new ShortMessageException();
//        }
        //测试
        if(!message.equals("test")){
            throw new ShortMessageException();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        //再查询用户
        userInfo = userInfoMapper.selectByPhone(phone);

        log.info("用户的ID是:"+userInfo.getId());

        shortMessageMap.remove(phone);
        return tokenUtils.createToken(userInfo);
    }

    @Override
    public boolean editUserInfo(String token, UserEditInfoDTO editInfoDTO) throws ParseException {
        // 如果没有修改
        if(editInfoDTO == null){
            return true;
        }
        // 获得用户信息
        UserInfo userInfo = tokenUtils.getUserInfo(token);

        // 将UserEditInfoDTO转换为UserInfo
        UserInfo userInfo2 = dozerMapper.map(editInfoDTO, UserInfo.class);
        //设置id
        userInfo2.setId(userInfo.getId());

        if (userInfo2.getId() == null){
            log.warn("用户的id为空");
        }

        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        //设置修改时间
        userInfo.setUpdateTime(TimeUtils.getTime("ss"));
        // 根据id来进行修改
        criteria.andIdEqualTo(userInfo2.getId());
        userInfoMapper.updateByExampleSelective(userInfo2, userInfoExample);
        return true;
    }

    @Override
    public UserInfoDTO queryUserInfo(String token) {
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        return dozerMapper.map(userInfo, UserInfoDTO.class);
    }

    /**
     * 查询用户关注
     * @param token 用户token
     * @return
     */
    @Override
    public List<FollowDTO> queryFollows(String token) {
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        // 指定查询条件
        UserFollow userFollow = new UserFollow();
        userFollow.setFansId(userInfo.getId());
        // 从数据库中查询到关注的id
        List<UserFollow> userFollows = userFollowDao.queryAll(userFollow);
        List<FollowDTO> followDTOS = new LinkedList<>();
        for(UserFollow followId: userFollows){
            UserInfo follow = userInfoMapper.selectByPrimaryKey(followId.getFollowId());
            followDTOS.add(dozerMapper.map(follow, FollowDTO.class));
        }
        return followDTOS;
    }

    /**
     * 查看粉丝
     * @param token
     * @return
     */
    @Override
    public List<FansDTO> queryFans(String token) {
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        // 指定查询条件
        UserFollow userFollow = new UserFollow();
        userFollow.setFollowId(userInfo.getId());
        // 从数据库中查询到粉丝的id
        List<UserFollow> userFollows = userFollowDao.queryAll(userFollow);
        List<FansDTO> fansDTO = new LinkedList<>();
        for(UserFollow followId: userFollows){
            UserInfo follow = userInfoMapper.selectByPrimaryKey(followId.getFollowId());
            fansDTO.add(dozerMapper.map(follow, FansDTO.class));
        }
        return fansDTO;
    }

    /**
     * 修改用户照片
     * @param request
     * @param multipartFile
     * @param type
     * @param model
     * @return
     */
    @Override
    public Boolean modPhoto(HttpServletRequest request, MultipartFile multipartFile,String type, Model model) throws IOException, FileUploadException, ParseException {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        String path = UploadUtils.imageUpload(request,multipartFile,model);
        //b用来表示头像和背景
        byte b;
        //判断头像还是背景
        if ("avatar".equals(type)){
            userInfo.setAvatarUrl(path);
            b = 0;
        } else {
            userInfo.setBackgroundImage(path);
            b = 1;
        }
        //判断用户是否从未上穿过头像或者背景,先不写了，麻烦
//        if (userInfo.getAvatarUrl() == null || userInfo.getBackgroundImage() == null){
        //更新用户文件
        setUserFile(userInfo, path,b);
        UserEditInfoDTO userEditInfoDTO = dozerMapper.map(userInfo,UserEditInfoDTO.class);
        return this.editUserInfo(token,userEditInfoDTO);
    }

    private void setUserFile(UserInfo userInfo, String path,byte b) throws ParseException {
        UserFile userFile = new UserFile();
        userFile.setCreateTime(TimeUtils.getTime("ss"));
        userFile.setUpdateTime(TimeUtils.getTime("ss"));
        userFile.setDeleted((byte)0);
        userFile.setFileUrl(path);
        //如果是头像，设置topic和describe
        if (b == 0){
            userFile.setTopic("头像");
            userFile.setdescribes("头像");
        } else {
            userFile.setTopic("背景头像");
            userFile.setdescribes("背景头像");
        }
        userFile.setUserId(userInfo.getId());
        log.warn(userFile.toString());
        userFileMapper.insert(userFile);
    }
}
