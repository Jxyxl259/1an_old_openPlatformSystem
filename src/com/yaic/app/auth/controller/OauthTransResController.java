/*
 * Created By lujicong (2016-05-03 14:19:38)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.auth.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.auth.dto.domain.OauthTransResDto;
import com.yaic.app.auth.service.OauthTransResService;
import com.yaic.fa.dto.JqGridPageDto;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.dto.PageDto;
import com.yaic.fa.mybatis.mapper.entity.Condition;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/transRes")
public class OauthTransResController {

    @Autowired
    private OauthTransResService oauthTransResService;
    
    /**
     * 跳转到设置主页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manage")
    public String managerPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
        
        return "auth/resource/resourceManage";
    }
    
    /**
     * 加载列表
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/query")
    public  JqGridPageDto<OauthTransResDto> queryResList(@RequestParam Map<String , String> paraMap) throws Exception {
    
        String id_search = paraMap.get("id_search");
        String interfaceName_search = paraMap.get("interfaceName_search");
        String requestUrl_search = paraMap.get("requestUrl_search");
        String transUrl_search = paraMap.get("transUrl_search");
        String remark_search = paraMap.get("remark_search");
        String invalidFlag_search = paraMap.get("invalidFlag_search");
        int page;
        if(StringUtil.isInteger(paraMap.get("page"))){
        	 page = Integer.parseInt(paraMap.get("page"));
        }else{
        	 page = 1;
        }
        int rows;
        if(StringUtil.isInteger(paraMap.get("rows"))){
        	rows = Integer.parseInt(paraMap.get("rows"));
        }else{
        	 rows = 10;
        }
        Condition condition = new Condition(OauthTransResDto.class);
        if (!StringUtil.isEmpty(id_search)) {
            condition.createCriteria().andEqualTo("id", Integer.parseInt(id_search));
        }
        if (!StringUtil.isEmpty(interfaceName_search)) {
            condition.createCriteria().andLike("interfaceName",  "%"+interfaceName_search.trim()+"%");
        }
        if (!StringUtil.isEmpty(requestUrl_search)) {
            condition.createCriteria().andLike("requestUrl", "%"+requestUrl_search.trim()+"%");
        }
        if (!StringUtil.isEmpty(transUrl_search)) {
            condition.createCriteria().andLike("transUrl", "%"+transUrl_search.trim()+"%");
        }
        if (!StringUtil.isEmpty(remark_search)) {
            condition.createCriteria().andLike("remark", "%"+remark_search.trim()+"%");
        }
        if (!StringUtil.isEmpty(invalidFlag_search)) {
            condition.createCriteria().andEqualTo("invalidFlag", Integer.parseInt(invalidFlag_search));
        }
        
        if (!StringUtil.isEmpty(paraMap.get("sidx")) && !StringUtil.isEmpty(paraMap.get("sord"))) {
            if("idStr".equals(paraMap.get("sidx"))) {
                condition.setOrderByClause("id" + " " + paraMap.get("sord"));
            }else {
                condition.setOrderByClause(paraMap.get("sidx") + " " + paraMap.get("sord"));
            }
        }else {
            condition.setOrderByClause("updatedDate desc");
        }

        PageDto<OauthTransResDto> pageDto = new PageDto<OauthTransResDto>();
        pageDto.setPageSize(rows);
        pageDto.setPageNo(page);

        PageDto<OauthTransResDto> dataList = oauthTransResService
                .selectByPage(pageDto, condition);
        JqGridPageDto<OauthTransResDto> pageDataDto = new JqGridPageDto<OauthTransResDto>();

        pageDataDto.setPage(page);
        pageDataDto.setRecords(dataList.getTotalSize());
        pageDataDto.setRows(dataList.getResults());
        pageDataDto.setTotal(dataList.getTotalPage());
        return pageDataDto;
    }
    
    /**
     * 新增\修改
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public Map<String, Object> saveOrUpdate(@RequestBody JsonRequest<OauthTransResDto> jsonRequest ,
            HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY) ;
        Map<String, Object> result = new HashMap<String, Object>();
        
        String operType = jsonRequest.getExtend().get("operType");
        
        OauthTransResDto oauthTransResDto = new OauthTransResDto();
        oauthTransResDto.setInterfaceName(jsonRequest.getExtend().get("interfaceName"));
        oauthTransResDto.setRequestUrl(jsonRequest.getExtend().get("requestUrl"));
        oauthTransResDto.setTransUrl(jsonRequest.getExtend().get("transUrl"));
        oauthTransResDto.setEncodingType(jsonRequest.getExtend().get("encodingType"));
        oauthTransResDto.setAccept(jsonRequest.getExtend().get("accept"));
        oauthTransResDto.setOrgId(jsonRequest.getExtend().get("orgId"));
        oauthTransResDto.setAgrtCode(jsonRequest.getExtend().get("agrtCode"));
        oauthTransResDto.setUserId(jsonRequest.getExtend().get("userId"));
        oauthTransResDto.setFuzzy(Integer.parseInt(jsonRequest.getExtend().get("fuzzy")));
        oauthTransResDto.setIsAuth(Integer.parseInt(jsonRequest.getExtend().get("isAuth")));
        oauthTransResDto.setIsPair(Integer.parseInt(jsonRequest.getExtend().get("isPair")));
        oauthTransResDto.setRemark(jsonRequest.getExtend().get("remark"));
        oauthTransResDto.setAllowIp(jsonRequest.getExtend().get("allowIp"));
        oauthTransResDto.setIsEncrypt(jsonRequest.getExtend().get("isEncrypt"));
        oauthTransResDto.setEncryptType(jsonRequest.getExtend().get("encryptType"));
        oauthTransResDto.setEncryptKey(jsonRequest.getExtend().get("encryptKey"));
        oauthTransResDto.setInvalidFlag(Integer.parseInt(jsonRequest.getExtend().get("invalidFlag")));
        oauthTransResDto.setUpdatedUser(userId);
        oauthTransResDto.setUpdatedDate(new Date());
        
        if("add".equals(operType)){
            OauthTransResDto checkOauthTransRes = new OauthTransResDto();
            checkOauthTransRes.setRequestUrl(oauthTransResDto.getRequestUrl());
            checkOauthTransRes.setInvalidFlag(0);
            if(oauthTransResService.count(checkOauthTransRes) > 0) {
                result.put("flag", false);
                result.put("msg", "已经存在该接口资源！");
                return result;
            }
            
            oauthTransResDto.setCreatedUser(userId);
            oauthTransResDto.setCreatedDate(new Date());
            oauthTransResService.insertNotNull(oauthTransResDto);
            result.put("flag", true);
            result.put("msg", "新增成功！");
        }else if("edit".equals(operType)){    
            
            oauthTransResDto.setId(Integer.parseInt(jsonRequest.getExtend().get("id")));
            
            Condition condition = new Condition(OauthTransResDto.class);
            condition.createCriteria().andEqualTo("requestUrl", oauthTransResDto.getRequestUrl())
                                      .andNotEqualTo("id", oauthTransResDto.getId())
                                      .andEqualTo("invalidFlag", 0);
            if(oauthTransResService.count(condition) > 0) {
                result.put("flag", false);
                result.put("msg", "已经存在该接口资源！");
                return result;
            }
            
            OauthTransResDto transResDtoTmp = new OauthTransResDto();
            transResDtoTmp.setId(oauthTransResDto.getId());
            transResDtoTmp = oauthTransResService.selectByPrimaryKey(transResDtoTmp);
            transResDtoTmp.setInterfaceName(oauthTransResDto.getInterfaceName());
            transResDtoTmp.setRequestUrl(oauthTransResDto.getRequestUrl());
            transResDtoTmp.setTransUrl(oauthTransResDto.getTransUrl());
            transResDtoTmp.setEncodingType(oauthTransResDto.getEncodingType());
            transResDtoTmp.setAccept(oauthTransResDto.getAccept());
            transResDtoTmp.setOrgId(oauthTransResDto.getOrgId());
            transResDtoTmp.setAgrtCode(oauthTransResDto.getAgrtCode());
            transResDtoTmp.setUserId(oauthTransResDto.getUserId());
            transResDtoTmp.setFuzzy(oauthTransResDto.getFuzzy());
            transResDtoTmp.setIsAuth(oauthTransResDto.getIsAuth());
            transResDtoTmp.setIsPair(oauthTransResDto.getIsPair());
            transResDtoTmp.setRemark(oauthTransResDto.getRemark());
            transResDtoTmp.setAllowIp(oauthTransResDto.getAllowIp());
            transResDtoTmp.setIsEncrypt(oauthTransResDto.getIsEncrypt());
            transResDtoTmp.setEncryptType(oauthTransResDto.getEncryptType());
            transResDtoTmp.setEncryptKey(oauthTransResDto.getEncryptKey());
            transResDtoTmp.setInvalidFlag(oauthTransResDto.getInvalidFlag());
            transResDtoTmp.setUpdatedUser(userId);
            transResDtoTmp.setUpdatedDate(new Date());
            
            oauthTransResService.updateByPrimaryKey(transResDtoTmp);
            result.put("flag", true);
            result.put("msg", "修改成功！");
        }
        
        return result;
    }
    
    /**
     * 删除
     * 
     * @param jsonRequest
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/del")
    public Map<String, Object> delParaType(@RequestBody JsonRequest<OauthTransResDto> jsonRequest,
            HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
        List<String> ids = (List<String>) JSON.parse(jsonRequest.getExtend().get("ids"));

        OauthTransResDto oauthTransResDto = null;
        for (String id : ids) {
            oauthTransResDto = new OauthTransResDto();
            oauthTransResDto.setId(Integer.parseInt(id));
            oauthTransResDto.setInvalidFlag(1);
            oauthTransResDto.setUpdatedUser(userId);
            oauthTransResDto.setUpdatedDate(new Date());
            oauthTransResService.updateByPrimaryKeyNotNull(oauthTransResDto);
        }
        result.put("flag", true);
        result.put("msg", "删除成功！");
        return result;
    }
}
