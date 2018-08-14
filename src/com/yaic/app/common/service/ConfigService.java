/************************************************************************
 * 描述 ：数据库表CMS_CONFIG对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-20 10:30:00
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.ConfigDao;
import com.yaic.app.common.dto.domain.ConfigDto;
import com.yaic.fa.service.BaseService;

@Service
public class ConfigService extends BaseService<ConfigDto>{

    private ConfigDao getConfigDao() {
        return (ConfigDao) this.getBaseDao();
    }
    
    /**
     * 获取查询列表
     * @param paraMap
     * @return
     */
    public List<ConfigDto> getRecordsByConditions(Map<String, String> paraMap) {
        return getConfigDao().getRecordsByConditions(paraMap);
    }
}
