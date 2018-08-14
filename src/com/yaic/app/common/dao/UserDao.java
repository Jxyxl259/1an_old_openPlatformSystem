/************************************************************************
 * 描述 ：数据库表CMS_USER对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.Map;

import com.yaic.app.common.dto.domain.UserDto;
import com.yaic.fa.dao.BaseDao;

public interface UserDao extends BaseDao<UserDto> {
      void	updatePassword(Map<String,String> paraMap);
}