package com.tanhua.server.controller;

import com.tanhua.model.dto.MovementDto;
import com.tanhua.model.vo.PageResult;
import com.tanhua.server.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author sxs
 * @create 2022-09-17 14:07
 */
@RestController
@RequestMapping("/movements")
public class MovementsController {
    @Autowired
    private MovementService movementService;

    /**
     * 发布动态
     */
    @PostMapping
    public ResponseEntity publish(MovementDto movementDto, MultipartFile[] imageContent) throws IOException {
        movementService.publishMovement(movementDto, imageContent);
        return ResponseEntity.ok(null);
    }

    /**
     * 我的动态
     */
    @GetMapping("/all")
    public ResponseEntity getMyMovements(Long userId,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult pageResult = movementService.getMyMovements(userId, page, pageSize);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 好友动态
     */
    @GetMapping
    public ResponseEntity getFriendMovements(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult pageResult = movementService.getFriendMovements(page, pageSize);
        return ResponseEntity.ok(pageResult);
    }
    /**
     * 推荐动态
     */
    @GetMapping("recommend")
    public ResponseEntity recommend(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult pageResult = movementService.getRecommendMovements(page, pageSize);
        return ResponseEntity.ok(pageResult);
    }
}
