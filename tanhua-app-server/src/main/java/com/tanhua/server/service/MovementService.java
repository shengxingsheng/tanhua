package com.tanhua.server.service;

import com.tanhua.autoconfig.template.OssTemplate;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sxs
 * @create 2022-09-17 14:41
 */
@Service
public class MovementService {
    @Autowired
    private OssTemplate ossTemplate;
    @DubboReference
    private MovementApi movementApi;
    @DubboReference
    private UserInfoApi userInfoApi;
    /**
     * 发布动态
     * @param movementDto
     * @param imageContent
     */
    public void movements(MovementDto movementDto, MultipartFile[] imageContent) throws IOException {
        //1.判断动态是否有内容
        String textContent = movementDto.getTextContent();
        if (StringUtils.isEmpty(textContent)){
            throw new BusinessException(ErrorResult.contentError());
        }
        //2.向oss保存图片
        ArrayList<String> medias = new ArrayList<>();
        for (MultipartFile file : imageContent) {
            String s = ossTemplate.upload(file.getOriginalFilename(),  file.getInputStream());
            medias.add(s);
        }
        //3.构建movement
        Movement movement = new Movement();
        BeanUtils.copyProperties(movementDto, movement);
        movement.setMedias(medias);
        movement.setLocationName(movementDto.getLocation());
        movement.setUserId(UserHolder.getId());
//        movement.setCreated(System.currentTimeMillis());
        movement.setLikeCount(0);
        movement.setLoveCount(0);
        movement.setCommentCount(0);
        //4.保存
        movementApi.publish(movement);
    }

    /**
     * 我的动态
     */
    public PageResult all(Long userId, Integer page, Integer pageSize) {
        //1.去Movement中根据userId查询
        PageResult pageResult = movementApi.findByUserId(userId, page, pageSize);
        //2.构建MovementVo列表
        ArrayList<MovementVo> items = new ArrayList<>();
        List<Movement> movementList = pageResult.getItems();
        for (Movement movement : movementList) {
            UserInfo userInfo = userInfoApi.findById(userId);
            items.add(MovementVo.init(movement,userInfo));
        }
        pageResult.setItems(items);
        return pageResult;
    }
}
