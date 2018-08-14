/************************************************************************
 * 描述 ：数据库表CMS_PARAMETER_TYPE对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.domain.ParameterTypeDto;
import com.yaic.fa.dao.BaseDao;

public interface ParameterTypeDao extends BaseDao<ParameterTypeDto> {

    /**
     * 按条件查询系统参数类型
     * @param paraMap
     * @return
     */
    List<ParameterTypeDto> getRecordsByType(Map<String , String> paraMap);

    /**
     * 查询所有参数类型
     * @param paraMap
     * @return
     */
    List<AutoCompleteDto> getAcDataList(Map<String, String> paraMap);
}