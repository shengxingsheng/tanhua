package com.tanhua.server.controller;

import com.tanhua.model.domain.UserInfo;
import com.tanhua.server.interceptor.UserHolder;
import com.tanhua.server.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author sxs
 * @create 2022-09-08 22:14
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    /**
     * 保存头像信息
     *   UserInfo
     *   请求头中携带token
     */
    @PostMapping("/loginReginfo")
    public ResponseEntity loginReginfo(@RequestBody UserInfo userInfo){

        Long id = UserHolder.getId();
        userInfo.setId(id);
        userInfoService.save(userInfo);
        return ResponseEntity.ok(null);

    }
    /**
     * 上传头像
     */
    @PostMapping("/loginReginfo/head")
    public ResponseEntity head(MultipartFile headPhoto) throws IOException {
        Long id = UserHolder.getId();
        userInfoService.updateHead(headPhoto,id);
        return ResponseEntity.ok(null);
    }
}
