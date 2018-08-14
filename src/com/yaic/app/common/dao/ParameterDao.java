/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.ComboDto;
import com.yaic.app.common.dto.domain.ParameterDto;
import com.yaic.fa.dao.BaseDao;

public interface ParameterDao extends BaseDao<ParameterDto> {

    /**
     * 获取下拉列表数据
     * @param paraMap[parameterType 、 localLanguage]
     * @return
     */
    List<ComboDto> getComboList(Map<String, String> paraMap);

    /**
     * 获取ac数据
     * @param paraMap
     * @return
     */
    List<AutoCompleteDto> getAcDataList(Map<String, String> paraMap);
    

}