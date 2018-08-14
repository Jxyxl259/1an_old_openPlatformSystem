/*
 * Created By lujicong (2016-08-31 09:36:55)
 * Homepage https://github.com/lujicong
 * Since 2013 - 2016
 */
package com.yaic.app.notice.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yaic.app.callback.controller.CallbackController;
import com.yaic.app.notice.service.SnsNoticeInfoService;
import com.yaic.servicelayer.charset.StandardCharset;

@Controller
@RequestMapping("/notice")
public class NoticeInfoController {

    private static final Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private SnsNoticeInfoService snsNoticeInfoService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/dealBiz")
    protected void dealBiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List ioList = IOUtils.readLines(request.getInputStream(), StandardCharset.UTF_8.name());
        long timeBegin = System.currentTimeMillis();
        logger.info("-------------服务调用开始---------------");
        StringBuilder sb = new StringBuilder(1024);
        try {
            for (int i = 0; i < ioList.size(); i++) {
                sb.append(ioList.get(i));
            }
            logger.info("接到原始接口信息：" + sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error reading:" + e.toString(), e);
        }

        logger.info("接口处理开始");
        String result = snsNoticeInfoService.dealBiz(URLDecoder.decode(sb.toString(), StandardCharset.UTF_8.name()));
        logger.info("接口处理结束");

        logger.info("返回原始接口信息=" + result);

        result = URLEncoder.encode(result, StandardCharset.UTF_8.name());
        
        byte[] byteMessages = result.getBytes();
        try {
            OutputStream outputStream = response.getOutputStream();
            response.setContentType("text/html; charset=UTF-8");
            response.setContentLength(byteMessages.length);
            outputStream.write(byteMessages);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error sending JSON: " + e.toString(), e);
        }

        long timeEnd = System.currentTimeMillis();
        logger.info("服务总响应时间 ：End - Seconds: " + (timeEnd - timeBegin) / 1000.0D);

        logger.info("-------------服务调用结束---------------");
    }
}
