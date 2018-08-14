/************************************************************************
 * 描述 ：数据库表CMS_COMPANY对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-17 09:06:38
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.CompanyDao;
import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.ComboDto;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.CompanyDto;
import com.yaic.fa.service.BaseService;

@Service
public class CompanyService extends BaseService<CompanyDto>{
	
	private CompanyDao getCompanyDao(){
		return (CompanyDao)this.getBaseDao();
	}
	
	/**
	 * 查询所有机构
	 * @return
	 */
	public List<TreeNode> findAllCompany(){
		return getCompanyDao().findAllCompany();
	}
	
	/**
	 * 保存机构
	 * @param companyDto
	 */
	public int saveCompany(CompanyDto companyDto){
		return getCompanyDao().insertNotNull(companyDto);
	}
	
	/**
	 * 查询机构最大编号
	 * @return
	 */
	public List<CompanyDto> findMaxCompanyId(){
		return getCompanyDao().findMaxCompanyId();
	}

	/**
	 * 自动补全机构拉取数据
	 * @param localLanguage 
	 * @return
	 */
    public List<AutoCompleteDto> getAcDataList(String localLanguage) {
        return getCompanyDao().getAcDataList(localLanguage);
    }
    
	/**
	 * 机构下拉列表
	 * @param localLanguage 
	 * @return
	 */
    public List<ComboDto> getCombDataList(Map<String,String> paraMap) {
        return getCompanyDao().getCombDataList(paraMap);
    }
}
