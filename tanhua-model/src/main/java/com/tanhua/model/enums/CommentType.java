package com.tanhua.model.enums;

/**
 * 评论类型 1-点赞，2-评论，3-喜欢
 * @author sxs
 * @create 2022-09-18 14:46
 */
public enum CommentType {
    LIKE(1),COMMENT(2),LOVE(3);
    private int type;
    CommentType(int type) {
        this.type=type;
    }
    public int getType() {
        return type;
    }
}
