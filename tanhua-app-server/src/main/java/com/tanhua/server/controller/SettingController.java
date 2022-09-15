package com.tanhua.server.controller;

import com.tanhua.model.vo.PageResult;
import com.tanhua.model.vo.SettingVo;
import com.tanhua.server.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-11 15:24
 */
@RestController
@RequestMapping("/users")
public class SettingController {
    @Autowired
    SettingService settingService;

    /**
     * 获取设置信息
     * @return
     */
    @GetMapping("/settings")
    public ResponseEntity getSettings(){
        SettingVo vo= settingService.getSettings();
        return ResponseEntity.ok(vo);
    }

    /**
     *设置陌生人问题 - 保存
     * @return
     */
    @PostMapping("/questions")
    public ResponseEntity questions(@RequestBody Map map){
        settingService.saveQuestions((String) map.get("content"));
        return ResponseEntity.ok(null);
    }
    /**
     * 通知设置 - 保存
     */
    @PostMapping("/notifications/setting")
    public ResponseEntity notifications(@RequestBody Map map){
        settingService.saveNotifications(map);
        return ResponseEntity.ok(null);
    }
    /**
     * 黑名单 - 翻页列表
     */
    @GetMapping("/blacklist")
    public ResponseEntity blacklist(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int pageSize){

        PageResult pageResult = settingService.blacklist(page,pageSize);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 黑名单 - 移除
     */
    @DeleteMapping("/blacklist/{uid}")
    public ResponseEntity deleteBlacklist(@PathVariable Long uid){

        settingService.deleteBlacklist(uid);
        return ResponseEntity.ok(null);
    }
}
