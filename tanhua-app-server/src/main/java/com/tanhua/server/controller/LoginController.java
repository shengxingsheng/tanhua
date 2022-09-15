package com.tanhua.server.controller;

import com.tanhua.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-06 22:43
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map map){
        String phone = (String) map.get("phone");
        userService.sendSms(phone);
        return ResponseEntity.ok("验证码发送成功");
    }

    @PostMapping("/loginVerification")
    public ResponseEntity loginVerification(@RequestBody Map map){
        String phone = (String) map.get("phone");
        String verificationCode =(String) map.get("verificationCode");
        Map result = userService.loginVerification(phone, verificationCode);
        return ResponseEntity.ok(result);
    }

}
