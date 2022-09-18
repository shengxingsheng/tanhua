package com.tanhua.dubbo.api;

import com.tanhua.model.mongo.Comment;

/**
 * @author sxs
 * @create 2022-09-18 14:15
 */
public interface CommentApi {
    Integer save(Comment comment);
}
