package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.ContentsAllDTO;
import com.geek.guiyu.domain.dataobject.ContentsDTO;
import com.geek.guiyu.domain.dataobject.UserFileDTO;
import com.geek.guiyu.domain.model.Contents;
import com.geek.guiyu.domain.model.ContentsExample;
import com.geek.guiyu.domain.model.LoveContents;
import com.geek.guiyu.domain.model.UserInfo;
import com.geek.guiyu.infrastructure.dao.ContentsDao;
import com.geek.guiyu.infrastructure.dao.ContentsMapper;
import com.geek.guiyu.infrastructure.dao.LoveContentsDao;
import com.geek.guiyu.infrastructure.dao.LoveContentsMapper;
import com.geek.guiyu.service.ContentsService;
import com.geek.guiyu.service.UploadService;
import com.geek.guiyu.service.util.TimeUtils;
import com.geek.guiyu.service.util.TokenUtils;
import com.geek.guiyu.service.util.UploadUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * (Contents)表服务实现类
 *
 * @author makejava
 * @since 2020-03-31 13:43:35
 */
@Service
public class ContentsServiceImpl implements ContentsService {
    @Resource
    private ContentsDao contentsDao;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ContentsMapper contentsMapper;
    @Autowired
    private LoveContentsMapper loveContentsMapper;
    @Autowired
    private LoveContentsDao loveContentsDao;


    @Override
    public boolean publish(HttpServletRequest request, MultipartFile[] multipartFiles, ContentsDTO contentsDTO, Model model) throws ParseException, IOException, FileUploadException {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        Contents contents = dozerMapper.map(contentsDTO, Contents.class);
        String type = contentsDTO.getType();
        //判断类型，post,post_draft,attachment
        if ("post".equals(type)) {
            contents.setType("post");
            contents.setStatus((byte) 1);
        }
        if ("post_draft".equals(type)) {
            contents.setType("post_draft");
            contents.setStatus((byte) 0);
        }
        //1.插入
        contents.setCreateTime(TimeUtils.getTime("ss"));
        contents.setUpdateTime(TimeUtils.getTime("ss"));
        contents.setUid(userInfo.getId());
        contents.setCommentsNum(0);
        contents.setLoveCount(0);
        contents.setViews(0);
        contents.setIsHot((byte) 0);
        contents.setParent(0);
        //获取插入后返回主键值，设置其附件
        int parentid = contentsDao.insert(contents);
        int order = 1;
        for (MultipartFile multipartFile : multipartFiles
                ) {
            String path = UploadUtils.imageUpload(request, multipartFile, model);
            UserFileDTO userFileDTO = new UserFileDTO("文章附件", "文章附件");
            //2.放到user_file表
            String fileUrl = uploadService.uploadImage(userFileDTO, request, multipartFile, model);
            //3.放到contents表
            Contents contents2 = new Contents();
            //3.1设置parent为之前所设定的主键值
            contents2.setParent(parentid);
            //3.2设置type=attachment
            contents2.setType("attachment");
            //3.3设置图片地址
            contents2.setText(fileUrl);
            //3.4设置图片顺序
            contents2.setOrder(order);
            contentsDao.insert(contents);
        }
        return true;
    }

    /**
     * 得到草稿
     *
     * @param request
     * @return
     */
    @Override
    public List<ContentsAllDTO> getArticles(HttpServletRequest request, String type) {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);

        ContentsExample contentsExample = new ContentsExample();
        ContentsExample.Criteria criteria = contentsExample.createCriteria();
        //设置查询条件，1.用户id.2.type=post_draft
        criteria.andUidEqualTo(userInfo.getId());
        criteria.andTypeEqualTo(type);
        List<Contents> contents = contentsMapper.selectByExample(contentsExample);
        //将contents取出文章，附件放置到contentsALLDTOS中
        return getContentsAllDTOS(contents);
    }

    /**
     * 得到喜欢的文章
     *
     * @param request
     * @return
     */
    @Override
    public List<ContentsAllDTO> getLoveArticles(HttpServletRequest request) {
        UserInfo userInfo = tokenUtils.getUserInfo("token");
        List<Integer> integers = loveContentsDao.selectLoveCids(userInfo.getId());
        //1.创建List,存储文章
        integers.parallelStream().forEach(integer -> );
        return null;
    }

    /**
     * 添加/取消 喜欢的文章
     *
     * @param request
     * @param cid
     * @param type=(add,abolish)
     * @return
     */
    @Override
    public boolean setLoveArticle(HttpServletRequest request, Integer cid, String type) throws ParseException {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        //如果type=add则为添加喜欢的文章，abolish为取消喜欢
        LoveContents loveContents = new LoveContents();
        loveContents.setCreateTime(TimeUtils.getTime("ss"));
        //设置用户名和文章id
        loveContents.setUid(userInfo.getId());
        loveContents.setCid(cid);
        //判断是否之前收藏过，即之前收藏过又取消，又收藏，则不插入记录,更新deleted
        if (loveContentsDao.selectLoveExists(userInfo.getId(), cid) > 0) {
            if ("abolish".equals(type)) {
                loveContentsDao.updateDeletedByUidAndCid((byte) 1, userInfo.getId(), cid, TimeUtils.getTime("ss"));
                return true;
            }
            //如果存在，则更新updateTime与deleted
            if ("add".equals(type)) {
                loveContentsDao.updateDeletedByUidAndCid((byte) 0, userInfo.getId(), cid, TimeUtils.getTime("ss"));
                return true;
            }
        }
        //此种情况则为以前未love过，直接新建
        if ("add".equals(type)) {
            loveContents.setUpdateTime(TimeUtils.getTime("ss"));
            loveContents.setDeleted((byte) 0);
            loveContentsMapper.insert(loveContents);
        }
        return true;
    }

    /**
     * 传入contents得到相应带有附件的对象列表
     *
     * @param contents
     * @return
     */
    private List<ContentsAllDTO> getContentsAllDTOS(List<Contents> contents) {
        List<ContentsAllDTO> contentsAllDTOS = new LinkedList<>();
        for (Contents content : contents
                ) {
            //如果为文章本身，转化为ContentsALLDTO对象中到ContentDTO
            if (0 == content.getParent()) {
                /**
                 * 先转换成all对象，再查询该cid下的附件添加
                 */
                ContentsAllDTO map = dozerMapper.map(content, ContentsAllDTO.class);
                ContentsExample contentsExample1 = new ContentsExample();
                ContentsExample.Criteria criteria1 = contentsExample1.createCriteria();
                //1.判断为附件
                criteria1.andTypeEqualTo("attachment");
                //2.判断所属文章
                criteria1.andParentEqualTo(content.getParent());
                List<Contents> contents1 = contentsMapper.selectByExample(contentsExample1);
                //3.将contents1中的text与order设置进map
                for (Contents content2 : contents1
                        ) {
                    map.setAttachmentUrl(content2.getText());
                    map.setOrder(content2.getOrder());
                }
                //4.添加进contentsALLDTOS中
                contentsAllDTOS.add(map);
            }
        }
        return contentsAllDTOS;
    }

}