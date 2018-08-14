/************************************************************************
 * 描述 ：数据库表CMS_USER_EXCLUDE_RESOURCE对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.UserExcludeResourceDao;
import com.yaic.app.common.dto.domain.UserExcludeResourceDto;
import com.yaic.fa.service.BaseService;

@Service
public class UserExcludeResourceService extends BaseService<UserExcludeResourceDto>{
	
	private UserExcludeResourceDao getUserExcludeResourceDao() {
        return (UserExcludeResourceDao) this.getBaseDao();
    }

    /**
     * 批次保存
     * @param reqMap
     */
    public void saveExcludeResource(Map<String, Object> map){
    	getUserExcludeResourceDao().saveExcludeResource(map);
    }
}
