package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.UserFileDTO;
import com.geek.guiyu.domain.model.UserFile;
import com.geek.guiyu.infrastructure.dao.UserFileDao;
import com.geek.guiyu.infrastructure.dao.UserFileMapper;
import com.geek.guiyu.service.UploadService;
import com.geek.guiyu.service.util.TokenUtils;
import com.geek.guiyu.service.util.UploadUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UserFileMapper userFileMapper;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserFileDao userFileDao;
    private Date date = new Date();

    /**
     * 上传文件业务逻辑代码
     * @param userFileDTO 用户文件DTO
     * @param request HttpServletRequest
     * @param file 上传的文件
     * @param model Model
     * @return 返回是否成功
     * @throws IOException
     * @throws FileUploadException
     */
    @Override
    public boolean uploadImage(UserFileDTO userFileDTO, HttpServletRequest request, MultipartFile file, Model model) throws IOException, FileUploadException {
        // 将文件保存到服务器， 获得文件的相对路径
        String imageUrl = UploadUtils.imageUpload(request, file, model);
        // 把数据保存到相对路径
        UserFile userFile = dozerMapper.map(userFileDTO, UserFile.class);
        userFile.setCreateTime(date);
        userFile.setDeleted((byte) 0);
        userFile.setFileUrl(imageUrl);
        userFile.setUpdateTime(date);
        // 从redis中获得用户信息
        Integer userId = (tokenUtils.getUserInfo(request.getHeader("token")).getId());
        userFile.setUserId(userId);
        return userFileMapper.insert(userFile) > 1;
    }

    @Override
    public List<UserFile> queryUserFile(String token) {
        // 从redis中获得用户信息
        Integer userId = (tokenUtils.getUserInfo(token).getId());
        // 根据用户id查询
        UserFile userFile = new UserFile();
        userFile.setUserId(userId);
        userFileDao.queryAll(userFile);
        return userFileDao.queryAll(userFile);
    }
}
