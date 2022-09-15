package com.tanhua.server.service;

import com.tanhua.autoconfig.template.AipFaceTemplate;
import com.tanhua.autoconfig.template.OssTemplate;
import com.tanhua.dubbo.api.UserInfoApi;
import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.vo.ErrorResult;
import com.tanhua.model.vo.UserInfoVo;
import com.tanhua.server.exception.BusinessException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author sxs
 * @create 2022-09-08 22:33
 */
@Service
public class UserInfoService {

    @DubboReference
    private UserInfoApi userInfoApi;
    @Autowired
    private OssTemplate ossTemplate;
    @Autowired
    private AipFaceTemplate aipFaceTemplate;
    /**
     * 新增用户信息
     * @param userInfo
     */
    public void save(UserInfo userInfo) {
        userInfoApi.save(userInfo);
    }

    /**
     * 更新用户头像
     * @param headPhoto
     * @param id
     */
    public void updateHead(MultipartFile headPhoto, Long id) throws IOException {
        String url = ossTemplate.upload(headPhoto.getOriginalFilename(), headPhoto.getInputStream());
        boolean detect = aipFaceTemplate.detect(url);
        //detect=true; //TODO 检查人脸结果先设置为true
        if (!detect){
            throw new BusinessException(ErrorResult.faceError());
        }else {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setAvatar(url);
            userInfoApi.update(userInfo);
        }
    }

    /**
     * 根据用户id获取用户信息
     * @param userID
     * @return
     */
    public UserInfoVo getById(Long userID) {
        UserInfo userInfo = userInfoApi.findById(userID);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        Integer age = userInfo.getAge();
        if (age!=null){
            userInfoVo.setAge(String.valueOf(age));
        }
        return userInfoVo;
    }

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    public void update(UserInfo userInfo) {
        userInfoApi.update(userInfo);
    }

}
