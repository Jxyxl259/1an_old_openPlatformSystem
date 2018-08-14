package com.yaic.app.callback.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.yaic.app.callback.util.ClientUtils;
import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.util.ConfigUtil;

/**
 * open对外访问URL接口开发
 * ITPRO-907
 * @author mengxy
 *
 */
@Controller
@RequestMapping("/callthird")
public class CallThirdController {
	
	private static Logger logger = LoggerFactory.getLogger(CallThirdController.class);
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/dealcall")
    private void dealcall(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        long timeBegin = System.currentTimeMillis();
        logger.info("-------------调用第三方接口开始---------------");
        StringBuilder sb = new StringBuilder(1024);
        String content = null;
        String responseMsg = "";
        try {
        	List ioList = IOUtils.readLines(request.getInputStream(), StandardCharset.UTF_8.name());
            for (int i = 0; i < ioList.size(); i++) {
                sb.append(ioList.get(i));
            }
            content = URLDecoder.decode(sb.toString(), StandardCharset.UTF_8.name());
            logger.info("接到原始接口信息：" + content);
            logger.info("接口处理开始");
            String result = "";
            if (content != null) {
        		HttpPostRawWrapper wrapper = JSONObject.parseObject(content, HttpPostRawWrapper.class);
    			HttpResponseWrapper responseWrapper = HttpTransceiver.doPost(wrapper);
    			result = JSONObject.toJSONString(responseWrapper);
			}
            logger.info("返回原始接口信息=" + result);
            logger.info("接口处理结束");
            responseMsg = URLEncoder.encode(result, StandardCharset.UTF_8.name());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error reading:" + e.toString(), e);
        }
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter write = response.getWriter();
            write.write(responseMsg);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error sending JSON: " + e.toString(), e);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("服务总响应时间 ：End - Seconds: " + (timeEnd - timeBegin) / 1000.0D);
        logger.info("-------------调用第三方接口结束---------------");
    }
	
	/**
	 * 邦融汇影像上传
	 * <p>User: lshuang
	 * <p>Date: 2018年4月28日
	 * <p>Version: 1.0
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/dealImage")
    private void dealImage(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		long timeBegin = System.currentTimeMillis();
        logger.info("-------------调用第三方接口开始---------------");
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        String channelCode = multipartHttpServletRequest.getParameter("channelCode");
        String applySerialNo = multipartHttpServletRequest.getParameter("applySerialNo");
        String fileName = multipartHttpServletRequest.getParameter("fileName");
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        byte[] bytes = multipartFile.getBytes();
        Map<String,String> param = new HashMap<String,String>();
		param.put("channelCode", channelCode);
		param.put("applySerialNo", applySerialNo);
		param.put("fileName", fileName);
		try {
			HttpResponseWrapper httpResponseWrapper = ClientUtils.httpUpload(ConfigUtil.getValue("bjzzjr.upload.service"), param, bytes, "application/zip");
            response.setContentType("text/html;charset=UTF-8");
            httpResponseWrapper.getStatus();
            PrintWriter write = response.getWriter();
            write.write((String)httpResponseWrapper.getContent());
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error sending JSON: " + e.toString(), e);
        }
        long timeEnd = System.currentTimeMillis();
        logger.info("服务总响应时间 ：End - Seconds: " + (timeEnd - timeBegin) / 1000.0D);
        logger.info("-------------调用第三方接口结束---------------");
	}
}	
