/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.ParameterDao;
import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.ComboDto;
import com.yaic.app.common.dto.domain.ParameterDto;
import com.yaic.fa.service.BaseService;

@Service
public class ParameterService extends BaseService<ParameterDto>{

    public List<ComboDto> getComboList(Map< String, String > paraMap) {
        return getParameterDao().getComboList(paraMap);
    }
    
    private ParameterDao getParameterDao(){
        return (ParameterDao)this.getBaseDao();
    }

    public List<AutoCompleteDto> getAcDataList(Map<String, String> paraMap) {
        return getParameterDao().getAcDataList(paraMap);
    }
}
