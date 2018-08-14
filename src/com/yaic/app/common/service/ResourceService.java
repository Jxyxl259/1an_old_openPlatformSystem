/************************************************************************
 * 描述 ：数据库表${tableInfo.tableOriginalName}对应的Service，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yaic.app.common.dao.ResourceDao;
import com.yaic.app.common.dto.TreeNode;
import com.yaic.app.common.dto.domain.ResourceDto;
import com.yaic.fa.service.BaseService;

@Service
public class ResourceService extends BaseService<ResourceDto> {

    private final static String RESOURCE_ROOT_ID = "0000";

    private final static String INDENT_PLACEHOLDER = "    ";

    private HashMap<String, ArrayList<ResourceDto>> menuTree = null;

    private StringBuffer sb = null;

    public String getMenu(String userId) {
        
        menuTree = new HashMap<String, ArrayList<ResourceDto>>();
        
        sb = new StringBuffer(1000);

//        ResourceDto record = new ResourceDto();
//        record.setResourceType("menu");
//        List<ResourceDto> result = this.select(record);
        
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("userId", userId);
        condition.put("resourceType", "menu");
        List<ResourceDto> result = this.getResource(condition);
        
        for (ResourceDto tmp : result) {
            String parentResourceId = tmp.getParentResourceId();
            ArrayList<ResourceDto> subTree = menuTree.get(parentResourceId);
            if (subTree == null) {
                subTree = new ArrayList<ResourceDto>();
                menuTree.put(parentResourceId, subTree);
            }
            subTree.add(tmp);
        }
        processFirstLevelMenu(INDENT_PLACEHOLDER + INDENT_PLACEHOLDER);
        return sb.toString();
    }

    private void processFirstLevelMenu(String prefix) {
        
        List<ResourceDto> subTree = menuTree.get(RESOURCE_ROOT_ID);
        if(subTree == null){
            return;
        }
        
        sb.append(prefix).append("<ul class=\"main-menu\">");
        for (ResourceDto tmp : subTree) {

            String href = "#";
            boolean isEndFlag = false;
            if ("1".equals(tmp.getEndFlag())) {
                href = tmp.getActionUrl();
                isEndFlag = true;
            }

            sb.append(prefix).append("    <li>");
            sb.append(prefix).append("    <a href=\"").append(href).append("\" class=\"js-sub-menu-toggle\">");
            sb.append(prefix).append("        <i class=\"").append(tmp.getResourceIconClass()).append("\"></i>");
            sb.append(prefix).append("        <span class=\"text\">").append(tmp.getResourceName()).append("</span>");
            sb.append(prefix).append("        <i class=\"toggle-icon fa fa-angle-left\"></i>");
            sb.append(prefix).append("    </a>");
            if (!isEndFlag) {
                processOtherLevelMenu(tmp.getResourceId(), prefix + INDENT_PLACEHOLDER);
            }
            sb.append(prefix).append("    </li>");
        }
        sb.append(prefix).append("</ul>");
    }

    private void processOtherLevelMenu(String parentResourceId, String prefix) {

        List<ResourceDto> subTree = menuTree.get(parentResourceId);
        if(subTree == null){
            return;
        }
        
        sb.append(prefix).append("<ul class=\"sub-menu\">");
        for (ResourceDto tmp : subTree) {

            String href = "#";
            boolean isEndFlag = false;
            if ("1".equals(tmp.getEndFlag())) {
                href = tmp.getActionUrl();
                isEndFlag = true;
            }

            sb.append(prefix).append("    <li>");
            if (isEndFlag) {
                sb.append(prefix).append("        <a href=\"javascript:multiIframeTabs.open({url:'").append(href).append("',name:'").append(tmp.getResourceName()).append("',tabId:'").append(tmp.getResourceId()).append("',className:'").append(tmp.getResourceIconClass()).append("'})\">");
                sb.append(prefix).append("            <span class=\"text\">").append(tmp.getResourceName()).append("</span>");
                sb.append(prefix).append("        </a>");
            } else {
                sb.append(prefix).append("        <a href=\"").append(href).append("\" class=\"js-sub-menu-toggle\">");
                sb.append(prefix).append("            <span class=\"text\">").append(tmp.getResourceName()).append("</span>");
                sb.append(prefix).append("            <i class=\"toggle-icon fa fa-angle-left\"></i>");
                sb.append(prefix).append("        </a>");
                processOtherLevelMenu(tmp.getResourceId(), prefix + INDENT_PLACEHOLDER);
            }
            sb.append(prefix).append("    </li>");
        }
        sb.append(prefix).append("</ul>");
    }
    
    private ResourceDao getResourceDao() {
        return (ResourceDao) this.getBaseDao();
    }
    
    public List<ResourceDto> getResource(HashMap<String, String> condition){
        return getResourceDao().getResource(condition);
    }
    
    /**
     * save
     */
    public int saveResource(ResourceDto resourceDto){
        return getResourceDao().insertNotNull(resourceDto);
    }
    
    
    /**
     * 查找所有菜单资源
     */
    public List<TreeNode> findAllMenuResource(){
        return getResourceDao().findAllMenuResource();
    }
    
    /**
     * 查找所有服务资源
     */
    public List<TreeNode> findAllServiceResource(){
        return getResourceDao().findAllServiceResource();
    }
    
    /**
     * 查询最大资源ID
     * @return
     */
    public List<ResourceDto> findMaxResourceId(){
    	return getResourceDao().findMaxResourceId();
    }
}
