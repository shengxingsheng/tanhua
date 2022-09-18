package com.tanhua.server.service;

import com.tanhua.dubbo.api.CommentApi;
import com.tanhua.dubbo.api.MovementApi;
import com.tanhua.model.enums.CommentType;
import com.tanhua.model.mongo.Comment;
import com.tanhua.model.vo.ErrorResult;
import com.tanhua.server.exception.BusinessException;
import com.tanhua.server.interceptor.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * @author sxs
 * @create 2022-09-18 14:08
 */
@Slf4j
@Service
public class CommentService {
    @DubboReference
    private CommentApi commentApi;
    @DubboReference
    private MovementApi movementApi;

    /**
     * 发布评论
     */
    public void publish(String movementId,String content) {
        if (StringUtils.isEmpty(content)){
            throw new BusinessException(ErrorResult.contentError());
        }

        //构建Comment对象
        Comment comment = new Comment();
        comment.setCommentType(CommentType.COMMENT.getType());
        comment.setContent(content);
        comment.setPublishId(new ObjectId(movementId));
        comment.setUserId(UserHolder.getId());
        comment.setCreated(System.currentTimeMillis());
        //动态用户id
        //保存
        Integer commentCount = commentApi.save(comment);
        log.info("commentCount:{}"+commentCount);
    }
}
