package com.tanhua.server.service;


import com.tanhua.autoconfig.template.SmsTemplate;
import com.tanhua.commons.utils.JwtUtils;
import com.tanhua.dubbo.api.UserApi;
import com.tanhua.dubbo.api.UserInfoApi;
import com.tanhua.model.domain.User;
import com.tanhua.model.domain.UserInfo;
import com.tanhua.model.vo.ErrorResult;
import com.tanhua.server.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-06 22:44
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private SmsTemplate smsTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @DubboReference
    private UserApi userApi;
    @DubboReference
    private UserInfoApi userInfoApi;

    public String sendSms(String phone)  {
        if (StringUtils.isEmpty(phone)){
            throw new BusinessException(ErrorResult.fail());
        }
//        String code = RandomStringUtils.randomNumeric(6);
        String code = "111111";//TODO 验证码：123456
        log.info(code);
        try {

            //smsTemplate.sendSms(phone, code);
        } catch (Exception e) {
            throw new BusinessException(ErrorResult.fail());
        }
        redisTemplate.opsForValue().set("CHECK_CODE_"+phone, code, Duration.ofMinutes(5));
        return code;
    }

    /**
     * 用户登录
     * @param phone
     * @param verificationCode
     * @return
     */
    public Map loginVerification(String phone, String verificationCode) {
        //1.查询redis中的验证码
        String code = redisTemplate.opsForValue().get("CHECK_CODE_" + phone);
        if (StringUtils.isEmpty(code)||!code.equals(verificationCode)){
            throw new BusinessException(ErrorResult.loginError());
        }
        //1.1删除redis中的验证码
        redisTemplate.delete("CHECK_CODE_" + phone);

        //2.远程调用UserApi 根据phone查询用户
        User user = userApi.findByMobile(phone);

        //3.判断是否是新用户
        boolean isNew = false;

        if (user==null){
            //3.1 是新用户
            user=new User();
            user.setMobile(phone);
            user.setPassword(DigestUtils.md5Hex("123456"));
            Long id = userApi.save(user);
            user.setId(id);
            isNew=true;
        }else {
            //3.2  有user没有userinfo 也是新用户
            UserInfo userInfo = userInfoApi.findById(user.getId());
            if (userInfo==null){
                isNew=true;
            }
        }

        //3.2 生成token
        HashMap hashMap = new HashMap();
        hashMap.put("id", user.getId());
        hashMap.put("phone", phone);
        String token = JwtUtils.getToken(hashMap);

        //3.3 返回结果
        HashMap resultMap = new HashMap();
        resultMap.put("isNew", isNew);
        resultMap.put("token", token);
        return resultMap;
    }


}
