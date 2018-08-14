package com.yaic.app;

public class Constants {
    
    public static final String LOGIN_USER_ID_KEY = "LOGIN_USER_ID";
    public static final String CURRENT_USER = "user";
    public static final String PARAMETERTYPE_VALID_FLAG = "VALID_FLAG";
    
    public static final String PARAMETERTYPE_JOB_STATUS = "JOB_STATUS"; //定时任务运行状态
    
    public static final String PUBLIC_ALLOW_IP = "255.255.255.255"; // 代表不限制访问IP
    
    public static final String STATE_SUCCESS = "1";
    public static final String STATE_FAIL = "0";
    
    public static final String CODE_SUCCESS = "0000";
    public static final String MSG_SUCCESS = "success";
    
    public static final String CODE_FAIL = "9999";
    
    public static final String CODE_40001 = "40001";
    public static final String MSG_40001 = "Invalid app_id";
    
    public static final String CODE_40002 = "40002";
    public static final String MSG_40002 = "Invalid app_secret";
    
    public static final String CODE_40004 = "40004";
    public static final String MSG_40004 = "Invalid grant type error";
    
    public static final String CODE_40005 = "40005";
    public static final String MSG_40005 = "Refresh token expired";
    
    public static final String CODE_40006 = "40006";
    public static final String MSG_40006 = "Invalid param";
    
    public static final String CODE_40007 = "40007";
    public static final String MSG_40007 = "Invalid refresh token";
    
    public static final String CODE_40008 = "40008";
    public static final String MSG_40008 = "The signature is not correct";
    public static final String CODE_40009 = "40009";
    public static final String MSG_40009 = "The rsa_algorithm is not correct";
    
    public static final String CODE_40029 = "40029";
    public static final String MSG_40029 = "Access_token expired";
    
    public static final String CODE_40030 = "40030";
    public static final String MSG_40030 = "Invalid access_token";
    
    public static final String CODE_40031 = "40031";
    public static final String MSG_40031 = "Invalid open_id";
    
    public static final String CODE_40032 = "40032";
    public static final String MSG_40032 = "Invalid access_token or Invalid open_id";
    
    public static final String CODE_50001 = "50001";
    public static final String MSG_50001 = "not exist this resource config info";
    
    public static final String CODE_50002 = "50002";
    public static final String MSG_50002 = "no permission access this resource";
    
    public static final String CODE_50003 = "50003";
    public static final String MSG_50003 = "This IP is not allowed to access";
    
    public static final String CODE_50004 = "50004";
    public static final String MSG_50004 = "not exist this app_id";
    
    public static final String CODE_50005 = "50005";
    public static final String MSG_50005 = "This appSecret is not correct";
    
    public static final class MutualStatus {
		/**
		 * 成功
		 */
		public static final int OK = 200;
		/**
		 * 异常错误
		 */
		public static final int ERR = 500;
	}

}
