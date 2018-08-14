/************************************************************************
 * 描述 ：数据库表CMS_COMPANY对应的DAO，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
 *
 ************************************************************************/

package com.yaic.app.common.dao;

import java.util.List;
import java.util.Map;

import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.ComboDto;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.CompanyDto;
import com.yaic.fa.dao.BaseDao;

public interface CompanyDao extends BaseDao<CompanyDto> {
    
	List<TreeNode> findAllCompany();
	
	List<CompanyDto> findMaxCompanyId();
	
	/**
	 * 机构下拉列表
	 * @param paraMap
	 * @return
	 */
	List<ComboDto> getCombDataList(Map<String,String> paraMap);
	
	/**
     * 自动补全机构拉取数据
	 * @param localLanguage 
     * @return
     */
    List<AutoCompleteDto> getAcDataList(String localLanguage);
    
}