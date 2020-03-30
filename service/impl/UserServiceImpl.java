package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.*;
import com.geek.guiyu.domain.exception.AlreadyRegisterException;
import com.geek.guiyu.domain.exception.NoPhoneException;
import com.geek.guiyu.domain.exception.ShortMessageException;
import com.geek.guiyu.domain.model.UserFollow;
import com.geek.guiyu.domain.model.UserInfo;
import com.geek.guiyu.domain.model.UserInfoExample;
import com.geek.guiyu.infrastructure.dao.UserFollowDao;
import com.geek.guiyu.infrastructure.dao.UserInfoMapper;
import com.geek.guiyu.service.UserService;
import com.geek.guiyu.service.util.ShortMessageUtil;
import com.geek.guiyu.service.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public TokenDTO register(RegisterDTO registerDTO) throws ShortMessageException {
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
        userInfoMapper.insert(userInfo);
        shortMessageMap.remove(phone);
        return tokenUtils.createToken(userInfo);
    }

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws ShortMessageException, NoPhoneException {
        String phone = loginDTO.getPhone();
        String message = loginDTO.getShortMessage();

        // 如果与发送的短信不一致
        if(!message.equals(shortMessageMap.get(phone))){
            throw new ShortMessageException();
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        shortMessageMap.remove(phone);
        return tokenUtils.createToken(userInfo);
    }

    @Override
    public boolean editUserInfo(String token, UserEditInfoDTO editInfoDTO) {
        // 如果没有修改
        if(editInfoDTO == null){
            return true;
        }
        // 获得用户信息
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        // 将UserEditInfoDTO转换为UserInfo
        userInfo = dozerMapper.map(editInfoDTO, UserInfo.class);
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        // 根据id来进行修改
        criteria.andIdEqualTo(userInfo.getId());
        userInfoMapper.updateByExampleSelective(userInfo, userInfoExample);
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
}
