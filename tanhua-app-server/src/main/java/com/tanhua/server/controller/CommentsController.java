package com.tanhua.server.controller;

import com.tanhua.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author sxs
 * @create 2022-09-18 14:05
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    /**
     * 发布评论
     * @param map
     * @return
     */
    @PostMapping
    public ResponseEntity publish(@RequestBody Map map){
        String movementId = (String) map.get("movementId");
        String comment = (String) map.get("comment");
        commentService.publish(movementId,comment);
        return ResponseEntity.ok(null);
    }
}
