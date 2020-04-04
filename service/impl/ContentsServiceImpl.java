package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.*;
import com.geek.guiyu.domain.model.*;
import com.geek.guiyu.infrastructure.dao.*;
import com.geek.guiyu.service.ContentsService;
import com.geek.guiyu.service.UploadService;
import com.geek.guiyu.service.UserService;
import com.geek.guiyu.service.util.TimeUtils;
import com.geek.guiyu.service.util.TokenUtils;
import com.geek.guiyu.service.util.UploadUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * (Contents)表服务实现类
 *
 * @author makejava
 * @since 2020-03-31 13:43:35
 */
@Service
@Slf4j
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
    @Autowired
    private BrowingHistoryMapper browingHistoryMapper;
    @Autowired
    private UserService userService;

    @Value("${pagehelper.pageSize}")
    Integer pageSize;


    /**
     * 通过文章cid获取文章
     *
     * @param cid
     * @return
     */
    @Override
    public Contents getContentByCid(Integer cid) {
        Contents content = contentsMapper.selectByPrimaryKey(cid);
        return content;
    }

    /**
     * 发布文章
     *
     * @param request
     * @param multipartFiles
     * @param contentsDTO
     * @param model
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws FileUploadException
     */
    @Override
    public boolean publish(HttpServletRequest request, MultipartFile[] multipartFiles, ContentsDTO contentsDTO, Model model) throws ParseException, IOException, FileUploadException {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        Contents contents = dozerMapper.map(contentsDTO, Contents.class);
        String type = contentsDTO.getType();
        //判断类型，post,post_draft,attachment
        if ("post".equals(type)) {
            contents.setCtype("post");
            contents.setStatus((byte) 1);
        }
        if ("post_draft".equals(type)) {
            contents.setCtype("post_draft");
            contents.setStatus((byte) 0);
        }
        //1.插入文章
        contents.setCreateTime(TimeUtils.getTime("ss"));
        contents.setUpdateTime(TimeUtils.getTime("ss"));
        contents.setUid(userInfo.getId());
        contents.setCommentsNum(0);
        contents.setLoveCount(0);
        contents.setViews(0);
        contents.setIsHot((byte) 0);
        contents.setParent(0);
        //获取插入后返回主键值，设置其附件
        contentsDao.insert(contents);
        int parentid = contents.getCid();
        //如果有附件
        if (multipartFiles.length > 0) {
            int order = 1;
            for (MultipartFile multipartFile : multipartFiles
                    ) {

                log.info(multipartFile.getOriginalFilename());

                UserFileDTO userFileDTO = new UserFileDTO("文章附件", "文章附件");
                //2.放到user_file表
                String fileUrl = uploadService.uploadImage(userFileDTO, request, multipartFile, model);
                //3.放到contents表
                Contents contents2 = new Contents();
                //3.1设置parent为之前所设定的主键值
                contents2.setParent(parentid);
                //3.2设置type=attachment
                contents2.setCtype("attachment");
                //3.3设置图片地址
                contents2.setCtext(fileUrl);
                //3.4设置图片顺序
                contents2.setCorder(order);
                //插入附件
                log.info(contents2.toString());
                contentsDao.insert(contents2);
                //设置顺序+1
                order++;
            }
        }

        return true;
    }

    /**
     * 得到 自己发布的文章/草稿
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
        //设置降序
        contentsExample.setOrderByClause("`create_time` DESC");
        //设置查询条件，1.用户id.2.type=post_draft
        criteria.andUidEqualTo(userInfo.getId());
        criteria.andCtypeEqualTo(type);

        List<Contents> contents = contentsMapper.selectByExampleWithBLOBs(contentsExample);

        //将contents取出文章，附件放置到contentsALLDTOS中
        return getContentsAllDTOS(contents);
    }

    /**
     * 得到 (所有/自己喜欢/关注的人) 的文章,type=(all,love,care)
     *
     * @param request
     * @param type
     * @return
     */
    @Override
    public PageInfo getTypeArticles(HttpServletRequest request, String type, Integer pageNum) {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        PageHelper.startPage(pageNum, pageSize);
        final List<Contents> contents = new LinkedList<>();
        //如果是喜欢的文章
        if ("love".equals(type)) {
            List<Integer> integers = loveContentsDao.selectLoveCids(userInfo.getId());
            //1.创建List,存储文章.因为会对每一个integer进行数据库查询操作，此阿勇parallerStream多线程并发遍历
            integers.parallelStream().forEach(integer -> {
                //在xml中设置按时间降序排序
                Contents content = contentsMapper.selectByPrimaryKey(integer);
                //2.每查询到一个content,添加进contents列表
                contents.add(content);
            });

            //lambda表达式中只能引用标记了 final 的外层局部变量
//            for (Integer integer : integers
//                    ) {
//                //在xml中设置按时间降序排序
//                Contents content = contentsMapper.selectByPrimaryKey(integer);
//                //2.每查询到一个content,添加进contents列表
//                contents.add(content);
//            }
        } else {
            //创建查询工具
            ContentsExample contentsExample = new ContentsExample();
            ContentsExample.Criteria criteria = contentsExample.createCriteria();
            //如果是所有文章
            if ("all".equals(type)) {
                //只查询文章
                criteria.andParentEqualTo(0);
                contents.addAll(contentsMapper.selectByExample(contentsExample));
            }
            //如果是关注的人
            if ("care".equals(type)) {
                List<FollowDTO> followDTOS = userService.queryFollows(request.getHeader("token"));
                for (FollowDTO followDTO : followDTOS) {
                    //得到关注人的id
                    Integer id = followDTO.getId();

                    criteria.andUidEqualTo(id);
                    //为已经发布的文章
                    criteria.andStatusEqualTo((byte) 1);
                    //添加进contents集合
                    contents.addAll(contentsMapper.selectByExample(contentsExample));
                }
            }
        }
        //按照时间进行排序
        Collections.sort(contents, (c1, c2) -> {
            return c2.getCreateTime().compareTo(c1.getCreateTime());
        });
        //3.拿到附件
        List<ContentsAllDTO> contentsAllDTOS = getContentsAllDTOS(contents);
        PageInfo pageInfo = new PageInfo(contentsAllDTOS);
        return pageInfo;
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
     * 添加浏览记录/与文章浏览人数
     *
     * @param request
     * @param cid
     * @return
     * @throws ParseException
     */
    @Override
    public boolean addBrowingHistory(HttpServletRequest request, Integer cid) throws ParseException {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        //添加文章views数
        Contents contents = contentsMapper.selectByPrimaryKey(cid);
        contents.setViews(contents.getViews() + 1);
        contentsDao.update(contents);
        //添加浏览记录
        BrowingHistory browingHistory = new BrowingHistory();
        browingHistory.setCreateTime(TimeUtils.getTime("ss"));
        browingHistory.setCid(cid);
        browingHistory.setUid(userInfo.getId());
        browingHistoryMapper.insert(browingHistory);
        return true;
    }

    /**
     * 查询用户的浏览记录
     *
     * @param request
     * @return
     */
    @Override
    public List<BrowingHistoryDTO> selectBrowingHistory(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        BrowingHistoryExample browingHistoryExample = new BrowingHistoryExample();
        BrowingHistoryExample.Criteria criteria = browingHistoryExample.createCriteria();
        criteria.andUidEqualTo(userInfo.getId());
        //1.查询出uid=#{uid}的浏览列表
        List<BrowingHistory> browingHistories = browingHistoryMapper.selectByExample(browingHistoryExample);
        //2.循环遍历，放入返回给用户的BrowingHistory列表中
        List<BrowingHistoryDTO> browingHistoryDTOS = new LinkedList<>();
        browingHistories.parallelStream().forEach((browingHistory -> {
            BrowingHistory item = browingHistoryMapper.selectByPrimaryKey(browingHistory.getId());
            BrowingHistoryDTO map = dozerMapper.map(item, BrowingHistoryDTO.class);
            browingHistoryDTOS.add(map);
        }));

        return browingHistoryDTOS;
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
                criteria1.andCtypeEqualTo("attachment");
                //2.判断所属文章
                log.info("map为:" + map);
                criteria1.andParentEqualTo(content.getCid());
                List<Contents> contents1 = contentsMapper.selectByExampleWithBLOBs(contentsExample1);
                log.info(contents1.toString());
                //3.将contents1中的text与order设置进map
                for (Contents content2 : contents1
                        ) {
                    log.warn(content2.getCtext());
                    map.getAttachment().put(content2.getCorder(), content2.getCtext());
                    log.info(map.getAttachment().toString());
                }
                //4.添加进contentsALLDTOS中
                contentsAllDTOS.add(map);
            }
        }
        return contentsAllDTOS;
    }

}