package com.tanhua.dubbo.api;

import com.tanhua.model.enums.CommentType;
import com.tanhua.model.mongo.Comment;
import com.tanhua.model.mongo.Movement;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author sxs
 * @create 2022-09-18 14:16
 */
@DubboService
public class CommentApiImpl implements CommentApi {
    @Autowired
    private MongoTemplate mongoTemplate;

    //TODO 需要修改
    @Override
    public Integer save(Comment comment) {
        //1.获取动态
        Movement movement = mongoTemplate.findById(comment.getPublishId(), Movement.class);
        //2.向comment设置被评论人id
        if (movement != null) {
            comment.setPublishUserId(movement.getUserId());
        }
        //3.保存评论
        mongoTemplate.save(comment);
        //4.修改动态的评论数
        Query query = Query.query(Criteria.where("id").is(comment.getPublishId()));
        Update update = new Update();
        if (comment.getCommentType()== CommentType.COMMENT.getType()){
            update.inc("commentCount",1);
        }
        if (comment.getCommentType()== CommentType.LIKE.getType()){
            update.inc("likeCount",1);
        }
        if (comment.getCommentType()== CommentType.LOVE.getType()){
            update.inc("loveCount",1);
        }
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        Movement andModify = mongoTemplate.findAndModify(query, update, options, Movement.class);
        //返回评论数
        return andModify.statisCount(comment.getCommentType());
    }
}
