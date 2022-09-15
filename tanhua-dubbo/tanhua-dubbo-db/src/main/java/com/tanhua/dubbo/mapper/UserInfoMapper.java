package com.tanhua.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanhua.model.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author sxs
 * @create 2022-09-08 21:45
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("SELECT * FROM tb_user_info where id IN\n" +
            "(SELECT black_user_id FROM tb_black_list where user_id = #{userId})")
    IPage<UserInfo> blackList(@Param("pages") Page pages,@Param("userId") Long userId);
}
