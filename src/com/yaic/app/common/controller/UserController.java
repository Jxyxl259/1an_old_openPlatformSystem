/************************************************************************
 * 描述 ：数据库表CMS_USER对应的Controller，代码生成。
 * 作者 ：HZHIHUI
 * 日期 ：2015-11-02 16:26:50
 *
 ************************************************************************/

package com.yaic.app.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yaic.app.Constants;
import com.yaic.app.Constants.MutualStatus;
import com.yaic.app.common.dto.AutoCompleteDto;
import com.yaic.app.common.dto.ComboDto;
import com.yaic.app.common.dto.domain.CompanyDto;
import com.yaic.app.common.dto.domain.UserDto;
import com.yaic.app.common.service.CompanyService;
import com.yaic.app.common.service.ParameterService;
import com.yaic.app.common.service.UMUserService;
import com.yaic.app.common.util.PasswordHelper;
import com.yaic.fa.dto.JqGridPageDto;
import com.yaic.fa.dto.JsonRequest;
import com.yaic.fa.dto.PageDto;
import com.yaic.fa.log.Log;
import com.yaic.fa.mybatis.mapper.entity.Condition;
import com.yaic.fa.util.UuidUtils;
import com.yaic.servicelayer.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Log("auditLogger")
	private Logger auditLogger;

	@Autowired
	private UMUserService userService;
	
	@Autowired
    private ParameterService parameterService ;
	
	@Autowired
    private CompanyService companyService ;
	
    @Autowired
    private CacheManager cacheManager;

	private static final String PASSWORD_RETRY_CACHE = "passwordRetryCache";

	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/preQuery")
	public ModelAndView userPreQuery(ModelMap modelMap) throws Exception {
		ModelAndView mv = new ModelAndView();
	    mv.setViewName("sys/user/userManage");
	    List<AutoCompleteDto> companyList = companyService.getAcDataList("C");
	    modelMap.put("companyList", JSON.toJSONString(companyList));
	    return mv;
	}


	/**
	 * 用户删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public Map<String, Object> deleteList(@RequestBody JsonRequest<UserDto> jsonRequest , HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String loginUserId = (String) session
		.getAttribute(Constants.LOGIN_USER_ID_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		int deleteCount = 0;
		List<UserDto>  userList = JSON.parseArray((jsonRequest.getExtend().get("userList")), UserDto.class);
		  
		if (userList != null) {
			deleteCount = userService.deleteList(userList);
		} 
		//shiroCacheService.clearAllCache();
		logger.info("用户 " + "[" + loginUserId + "] 删除 用户数：" +  deleteCount );    
		map.put("deleteCount", deleteCount);
		map.put("msg", "删除成功用户数：");
		return map;

	}

	/**
	 * 用户预编辑
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/preEdit")
	public Map<String, Object> preEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String actionType = request.getParameter("actionType");
		String userId = request.getParameter("userId");
		String loginUserId = (String) session
				.getAttribute(Constants.LOGIN_USER_ID_KEY);
	  	Map<String,String> paraMap = new HashMap<String, String>();
        paraMap.put("localLanguage", "C");
        paraMap.put("parameterType", Constants.PARAMETERTYPE_VALID_FLAG);
    	List<ComboDto> validFlagList =  parameterService.getComboList(paraMap);
    
		UserDto userDto = new UserDto();
		if (userId != null && !"undefined".equals(userId)
				&& !"null".equals(userId)) {
			userDto.setUserId(userId);
			userDto = userService.selectByPrimaryKey(userDto);
		} else {
			userDto.setCreatedBy(loginUserId);
			userDto.setCreatedDate(new Date());
			userDto.setPasswordSetDate(new Date());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		userDto.setPassword(null);
		map.put("userDto", userDto);
		map.put("actionType", actionType);
		map.put("validFlagList", validFlagList);
         
		return map;

	}

	/**
	 * 用户更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Map<String, Object> updateUser(@RequestBody JsonRequest<UserDto> jsonRequest ,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String actionType = request.getParameter("actionType");
		HttpSession session = request.getSession();
		String loginUserId = (String) session
				.getAttribute(Constants.LOGIN_USER_ID_KEY);
		
		UserDto   userDto = JSON.parseObject((jsonRequest.getExtend().get("userDto")), UserDto.class);
		
		CompanyDto companyDto = new CompanyDto();
		companyDto.setCompanyId(userDto.getCompanyCode());
		List<CompanyDto> companyList = companyService.select(companyDto);
		
		if(companyList != null  && companyList.size() > 0 ){
		  if ("insert".equals(actionType)) {
	    	UserDto userDtoTmp = new UserDto();
			userDtoTmp.setUserCode(userDto.getUserCode());
			List<UserDto> list = userService.select( userDtoTmp);
			if(list != null && list.size() > 0 ){
				map.put("dealStatus", "-1");
				map.put("msg", "处理失败，该用户已存在");
				return map;
			}
			PasswordHelper passwordHelper = new PasswordHelper();
			passwordHelper.encryptPassword( userDto) ;
			String userId = UuidUtils.getUuid(); 
			userDto.setUserId(userId);
			userDto.setUpdatedBy(loginUserId);
			userDto.setUpdatedDate(new Date());
			userService.insert(userDto);
			logger.info("用户 " + "[" + loginUserId + "] 添加 用户 [" + userId +  "]");    
			map.put("dealStatus", "0");
			map.put("msg", "用户添加成功");
		 } else {
		    if(StringUtil.isNotEmpty(userDto.getPassword())) {
		        PasswordHelper passwordHelper = new PasswordHelper();
	            passwordHelper.encryptPassword( userDto) ;
		    }else {
		        userDto.setPassword(null);
		    }
			userDto.setUpdatedBy(loginUserId);
			userDto.setUpdatedDate(new Date());
			userService.updateByPrimaryKeyNotNull(userDto);
			logger.info("用户 " + "[" + loginUserId + "] 修改 用户 [" + userDto.getUserId() +  "]");    
			map.put("dealStatus", "0");
			map.put("msg", "用户修改成功");
		 }
		}else{
			map.put("dealStatus", "-1");
			map.put("msg", "处理失败，录入的机构不存在");
			return map;
		}  
	    //shiroCacheService.clearAllCache();
		return map;
	}
	

    /**
     * 用户密码修改页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/preModifyPassword")
	public String preModifyPassword(ModelMap modelMap,HttpServletRequest request,
			@RequestParam Map<String, String> reqPara) throws Exception {
		HttpSession session = request.getSession();
		String userId = (String) session
				.getAttribute(Constants.LOGIN_USER_ID_KEY);
		String tabId = reqPara.get("tabId");
		modelMap.put("userCode", userId);
		modelMap.put("tabId", tabId);
	    return "sys/user/preModifyPassword";

	}
	
    /**
     * 用户修改密码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/modifyPassword")
	public Map<String,Object> modifyPassword(@RequestBody JsonRequest<UserDto> jsonRequest,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> result =new HashMap<String, Object>();
		Map<String,String> paraMap =new HashMap<String, String>();
	    HttpSession session = request.getSession();
		String userCode = (String) session.getAttribute(Constants.LOGIN_USER_ID_KEY);
		String passwordOld = jsonRequest.getExtend().get("passwordOld");
		String passwordNew = jsonRequest.getExtend().get("passwordNew");
		String passwordNewSec = jsonRequest.getExtend().get("passwordNewSec");
		String msg = "";
		String flag ="";
		
		UserDto user = new UserDto();
		user.setUserCode(userCode);
		user = userService.selectOne(user);
		
		//输入的原密码
		UserDto userDtoOld = new UserDto();
		userDtoOld.setUserCode(userCode);
		userDtoOld.setPassword(passwordOld);
		PasswordHelper passwordHelper = new PasswordHelper();
		String encryptPasswordOld = passwordHelper.getEncryptPassword(userDtoOld);
		
		//输入的新密码
		UserDto userDtoNew = new UserDto();
		userDtoNew.setUserCode(userCode);
		userDtoNew.setPassword(passwordNew);
		String encryptPasswordNew = passwordHelper.getEncryptPassword(userDtoNew);
		
		if(!user.getPassword().equals(encryptPasswordOld)){
			flag= "errorOld";
			msg = "原密码输入错误！";
	        result.put("flag",flag);
			result.put("msg",msg);
		    return result;
		}
		
		if(passwordOld.equals(passwordNew)||user.getPassword().equals(encryptPasswordNew)){
			flag="errorNew";
			msg = "新密码不能与原密码相同！";
	        result.put("flag",flag);
			result.put("msg",msg);
		    return result;
		}
		
		if(!passwordNew.equals(passwordNewSec)){
			flag="errorNewSec";
			msg = "两次输入的新密码不一致！";
	        result.put("flag",flag);
			result.put("msg",msg);
		    return result;
		}
		
		paraMap.put("userCode", userCode);
		paraMap.put("password",encryptPasswordNew);
		userService.updatePassword(paraMap);
		flag= "success";
		msg = "密码修改成功,请重新登陆！";
		
		//shiroCacheService.clearCache();
		session.invalidate();
		
        auditLogger.info("用户【{}】修改密码",userCode);
        logger.info("用户【{}】修改密码",userCode);
        result.put("flag",flag);
		result.put("msg",msg);
	    return result;
	}
	
    /**
	 * 用户验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	@ResponseBody
	@RequestMapping(value = "/validUser")
	public JsonResponse<UserDto> validUser(
			@RequestBody JsonRequest<UserDto> jsonRequest) throws Exception {
		PageDto pageDto = jsonRequest.getPage();
		PageDto pageResult = userService.selectByPage(pageDto, jsonRequest
				.getForm());
		UserDto userDto = jsonRequest.getForm();
		JsonResponse<UserDto> result = new JsonResponse<UserDto>();
		result.setForm(jsonRequest.getForm());
		result.setPage(pageResult);
		return result;

	}*/
 
	/**
     * 加载用户列表
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/query")
	public  JqGridPageDto<UserDto> queryUserList(@RequestParam Map<String , String> paraMap) throws Exception {
	
		String userCode = paraMap.get("searchUserCode");
		String userTName = paraMap.get("searchUserCName");
		String userCName = paraMap.get("searchUserTName");

		int page = Integer.parseInt(paraMap.get("page"));// 取得当前页数
		int rows = Integer.parseInt(paraMap.get("rows")); // 取得每页显示行数
		
		Condition condition = new Condition(UserDto.class);
		if (!StringUtil.isEmpty(userCode)) {
			condition.createCriteria().andLike("userCode", userCode.trim());
		}
		if (!StringUtil.isEmpty(userTName)) {
			condition.createCriteria().andLike("userTname", userTName.trim());
		}
		if (!StringUtil.isEmpty(userCName)) {
			condition.createCriteria().andLike("userCname", userCName.trim());
		}
		if (!StringUtil.isEmpty(paraMap.get("sidx"))
				&& !StringUtil.isEmpty(paraMap.get("sord"))) {
			condition.setOrderByClause(paraMap.get("sidx") + " "
					+ paraMap.get("sord"));
		}

		PageDto<UserDto> pageDto = new PageDto<UserDto>();
		pageDto.setPageSize(rows);
		pageDto.setPageNo(page);

		PageDto<UserDto> dataList = userService
				.selectByPage(pageDto, condition);
		JqGridPageDto<UserDto> pageDataDto = new JqGridPageDto<UserDto>();

		pageDataDto.setPage(page);
		pageDataDto.setRecords(dataList.getTotalSize());
		pageDataDto.setRows(dataList.getResults());
		pageDataDto.setTotal(dataList.getTotalPage());
		return pageDataDto;
	}

	/**
	 * 用户解锁
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/unlock")
	public Map<String, Object> userUnlock(@RequestBody final JsonRequest<UserDto> jsonRequest, final HttpServletRequest request) {
		final Map<String, Object> response = new HashMap<>();
		
		final Subject subject = SecurityUtils.getSubject();
		if(subject == null) {
			logger.error("系统异常, 无法获取Subject!");
			response.put("status", MutualStatus.ERR);
			response.put("msg", "系统异常");
			return response;
		}
		
		final Session session = subject.getSession();
		if(session == null) {
			logger.error("系统异常, 无法获取Session!");
			response.put("status", MutualStatus.ERR);
			response.put("msg", "系统异常");
			return response;
		}
		
		final Object userObject = session.getAttribute(Constants.CURRENT_USER);
		if(userObject == null) {
			logger.error("系统异常, CurrentUser为空!");
			response.put("status", MutualStatus.ERR);
			response.put("msg", "系统异常");
			return response;
		}
		
		final UserDto user = (UserDto) userObject;
		final Cache<String, AtomicInteger> cache = cacheManager.getCache(PASSWORD_RETRY_CACHE);
		
		int unlockCount = 0;
		String userCode = null;
		AtomicInteger retryCount = null;
		StringBuilder builder = new StringBuilder();
		
		final List<UserDto> userList = JSON.parseArray((jsonRequest.getExtend().get("userList")), UserDto.class);
		for (final UserDto userDto : userList) {
			if(userDto != null) {
				userCode = userDto.getUserCode();
				retryCount = (AtomicInteger) cache.get(userCode);
				if(retryCount != null && retryCount.get() > 5) {
					cache.remove(userCode);
					unlockCount++;
					builder.append(userCode).append(",");
				}
			}
		}
		
		logger.info("用户 [{}] 解锁的用户数：{}", user.getUserCode(), unlockCount);
		if (unlockCount <= 0) {
			response.put("status", MutualStatus.ERR);
			response.put("msg", "所选用户未被锁定");
			return response;
		}
		
		final String unlockUserName = builder.substring(0, builder.length()-1);
		response.put("status", MutualStatus.OK);
		response.put("msg", "解锁成功用户数为" + unlockCount + ", 解锁成功用户为" + unlockUserName);
		return response;
	}
}