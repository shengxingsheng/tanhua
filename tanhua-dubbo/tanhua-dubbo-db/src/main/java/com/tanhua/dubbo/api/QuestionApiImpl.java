package com.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tanhua.dubbo.mapper.QuestionMapper;
import com.tanhua.model.domain.Question;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sxs
 * @create 2022-09-11 16:57
 */
@DubboService
public class QuestionApiImpl implements QuestionApi{
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public Question findByUid(Long uid) {
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Question::getUserId ,uid );
        Question question = questionMapper.selectOne(wrapper);
        return question;
    }

    @Override
    public void save(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public void update(Question question) {
        questionMapper.updateById(question);
    }
}
