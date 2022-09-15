package com.tanhua.server.controller;

import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.vo.UserInfoVo;
import com.tanhua.server.interceptor.UserHolder;
import com.tanhua.server.service.UserInfoService;
import com.tanhua.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-10 15:15
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    /**
     * 根据用户id获取用户信息
     * @param userID
     * @return
     */
    @GetMapping
    public ResponseEntity get(Long userID){
        if (userID==null){
            Long id = UserHolder.getId();
            userID=id;
        }
        UserInfoVo userInfoVo = userInfoService.getById(userID);
        return ResponseEntity.ok(userInfoVo);
    }

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    @PutMapping
    public ResponseEntity update(@RequestBody UserInfo userInfo){
        Long id = UserHolder.getId();
        userInfo.setId(id);
        userInfoService.update(userInfo);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新头像
     */
    @PostMapping("/header")
    public ResponseEntity head(MultipartFile headPhoto) throws IOException {
        userInfoService.updateHead(headPhoto,UserHolder.getId());
        return ResponseEntity.ok(null);
    }
    /**
     * TODO 修改手机号流程还存在一些问题
     * 修改手机号-1.发送验证码
     */
    @PostMapping("/phone/sendVerificationCode")
    public ResponseEntity sendVerificationCode(){
        userService.sendVerificationCode();
        return ResponseEntity.ok(null);
    }
    /**
     * 修改手机号-2.校验验证码
     */
    @PostMapping("/phone/checkVerificationCode")
    public ResponseEntity checkVerificationCode(@RequestBody Map map){
        Boolean verification = userService.checkVerificationCode(map);
        return ResponseEntity.ok(verification);
    }
    /**
     * 修改手机号-3.校验验证码
     */
    @PostMapping("/phone")
    public ResponseEntity phone(@RequestBody Map map){
        userService.phone(map);
        return ResponseEntity.ok(null);
    }

}
