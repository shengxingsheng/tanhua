package com.tanhua.server.service;

import com.tanhua.autoconfig.template.OssTemplate;
import com.tanhua.commons.utils.Constants;
import com.tanhua.dubbo.api.MovementApi;
import com.tanhua.dubbo.api.UserInfoApi;
import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.dto.MovementDto;
import com.tanhua.model.mongo.Movement;
import com.tanhua.model.vo.ErrorResult;
import com.tanhua.model.vo.MovementVo;
import com.tanhua.model.vo.PageResult;
import com.tanhua.server.exception.BusinessException;
import com.tanhua.server.interceptor.UserHolder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sxs
 * @create 2022-09-17 14:41
 */
@Service
public class MovementService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private OssTemplate ossTemplate;
    @DubboReference
    private MovementApi movementApi;
    @DubboReference
    private UserInfoApi userInfoApi;

    /**
     * 发布动态
     *
     * @param movementDto
     * @param imageContent
     */
    public void publishMovement(MovementDto movementDto, MultipartFile[] imageContent) throws IOException {
        //1.判断动态是否有内容
        String textContent = movementDto.getTextContent();
        if (StringUtils.isEmpty(textContent)) {
            throw new BusinessException(ErrorResult.contentError());
        }
        //2.向oss保存图片
        ArrayList<String> medias = new ArrayList<>();
        for (MultipartFile file : imageContent) {
            String s = ossTemplate.upload(file.getOriginalFilename(), file.getInputStream());
            medias.add(s);
        }
        //3.构建movement
        Movement movement = new Movement();
        BeanUtils.copyProperties(movementDto, movement);
        movement.setMedias(medias);
        movement.setLocationName(movementDto.getLocation());
        movement.setUserId(UserHolder.getId());
        //4.保存
        movementApi.publish(movement);
    }

    /**
     * 我的动态
     */
    public PageResult getMyMovements(Long userId, Integer page, Integer pageSize) {
        //1.去Movement中根据userId查询
        PageResult pageResult = movementApi.findByUserId(userId, page, pageSize);
        List<Movement> movementList = pageResult.getItems();
        if (CollectionUtils.isEmpty(movementList)) {
            return pageResult;
        }
        //2.构建MovementVo列表
        ArrayList<MovementVo> items = new ArrayList<>();
        UserInfo userInfo = userInfoApi.findById(userId);
        for (Movement movement : movementList) {
            //TODO 修复点赞功能
            items.add(MovementVo.init(movement, userInfo));
        }
        pageResult.setItems(items);
        return pageResult;
    }

    /**
     * 获取好友动态
     *
     * @param page     页码
     * @param pageSize 页大小
     * @return
     */
    public PageResult getFriendMovements(Integer page, Integer pageSize) {
        Long userId = UserHolder.getId();
        //1.分页查询：调用api获取好友动态集合
        List<Movement> movementList = movementApi.findByFriendId(userId, page, pageSize);
        if (CollectionUtils.isEmpty(movementList)) {
            return new PageResult();
        }
        //2.获取好友动态UserId集合
        List<Long> ids = (List<Long>) CollectionUtils.collect(movementList, input -> ((Movement) input).getUserId());
        //3.获取好友详细信息Map<userId,userInfo>
        Map<Long, UserInfo> map = userInfoApi.findByIds(ids, null);
        //4.构建MovementVo集合
        List<MovementVo> movementVoList = new ArrayList<>();
        for (Movement movement : movementList) {
            UserInfo userInfo = map.get(movement.getUserId());
            MovementVo movementVo = MovementVo.init(movement, userInfo);
            movementVoList.add(movementVo);
        }
        //5.构建PageResult
        return new PageResult(page, pageSize, 0L, movementVoList);
    }

    /**
     * 推荐动态列表
     */
    public PageResult getRecommendMovements(Integer page, Integer pageSize) {
        //1.向redis获取pid字符串
        String redisKey = Constants.MOVEMENTS_RECOMMEND + UserHolder.getId();
        String pid = (String) redisTemplate.opsForValue().get(redisKey);
        //2.判断是否为空initialized = trueinitialized = true
        List<Movement> movementList = Collections.EMPTY_LIST;
        if (StringUtils.isEmpty(pid)) {
            //2.1 为空则随机推荐
            movementList = movementApi.randomMovements(pageSize);
        } else {
            //2.2 不为空则去动态表中查询  "16,17,18,19,20,21,22,23,24,25,27,28"
            String[] split = pid.split(",");
            Integer skipNum = pageSize * (page - 1);
            //页数超过总页数
            if (skipNum > split.length) {
                return new PageResult();
            }
            List<Long> pidList = Arrays.stream(split)
                    .skip(skipNum)
                    .limit(pageSize)
                    .map(s -> Long.valueOf(s.trim()))
                    .collect(Collectors.toList());
            movementList = movementApi.findByPids(pidList);
        }
        //3.获取用户id的集合
        List<Long> ids = (List) CollectionUtils.collect(movementList, input -> ((Movement) input).getUserId());
        //4，调用api查询userinfo
        Map<Long, UserInfo> map = userInfoApi.findByIds(ids, null);
        //5.构建MovementVO集合
        List<MovementVo> movementVoList = new ArrayList<>();
        for (Movement movement : movementList) {
            UserInfo userInfo = map.get(movement.getUserId());
            MovementVo init = MovementVo.init(movement, userInfo);
            movementVoList.add(init);
        }
        //6，构建PageResult
        return new PageResult(page, pageSize, 0L, movementVoList);
    }
}
