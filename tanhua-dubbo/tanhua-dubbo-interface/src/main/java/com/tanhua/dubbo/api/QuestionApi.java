package com.tanhua.dubbo.api;

import com.tanhua.model.domain.Question;

/**
 * @author sxs
 * @create 2022-09-11 16:51
 */
public interface QuestionApi {
    /**
     * 获取问题信息
     * @param uid
     * @return
     */
    Question findByUid(Long uid);

    void save(Question question);

    void update(Question question);
}
