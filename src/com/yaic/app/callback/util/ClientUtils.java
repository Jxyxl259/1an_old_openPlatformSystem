package com.yaic.app.callback.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.HttpMultipartMode;

import com.alibaba.fastjson.JSON;
import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.BinaryBody;
import com.yaic.servicelayer.http.wrapper.HttpPostMultipartWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;

public class ClientUtils {

	/**
	 * 与其他系统交互功能基类
	 * 
	 * @param mapData
	 *            需要传送给互动平台的参数，Map的形式传输
	 * @param serverURL
	 *            服务交互地址
	 * @return 数组第一位为是否正常影响Y为正常N为不正常
	 */
	public HttpResponseWrapper connectServer(final Object inputObject, final String serverURL) {
		final String jsonData = JSON.toJSONString(inputObject);
		return HttpTransceiver.doPostRaw(serverURL, "text/plain", jsonData, true, true);
	}
	
	/**
	 * 电子发票调收付下载接口
     * <p>User: hguoqing
     * <p>Date: 2017-3-1
     * <p>Version: 1.0
	 * @param inputObject
	 * @param serverURL
	 * @return
	 */
	public HttpResponseWrapper connectInvoice(final Object inputObject, final String serverURL) {
		final String jsonData = JSON.toJSONString(inputObject);
		return HttpTransceiver.doPostRaw(serverURL, "text/plain", jsonData, false, false);
	}

	/**
	 * 返回xml格式报文
     * <p>Date: 2016年7月13日
     * <p>Version: 1.0
	 * @param inputObject
	 * @param serverURL
	 * @return
	 */
	public static String connectServerXML(final Object inputObject, final String serverURL) {
		final HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPostRaw(serverURL, "application/xml", inputObject.toString(),
				false, true);
		return httpResponseWrapper.getStatus() ? (String) httpResponseWrapper.getContent() : httpResponseWrapper.getErrorMessage();
	}

	/**
	 * 影像文件上传服务 User: lpengfei Date: 2018-01-15 Version: 1.0
	 */
	public static HttpResponseWrapper httpUpload(String serverURL, Map<String, String> param, byte[] filebyte, String mimeType) {
		final HttpPostMultipartWrapper httpPostWrapper = new HttpPostMultipartWrapper();
		httpPostWrapper.setServerUrl(serverURL);
		
		final String boundary = "----------" + System.currentTimeMillis();
		httpPostWrapper.setBoundary(boundary);
		
		final Map<String, String> headers = new HashMap<>(4);
		headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);
		headers.put("Connection", "keep-alive");
		headers.put("Cache-Control", "no-cache");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) "
						+ "Chrome/58.0.3029.110 Safari/537.36 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
		httpPostWrapper.setHeaders(headers);
		
		httpPostWrapper.setConnectTimeout(120000);
		httpPostWrapper.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		httpPostWrapper.setCharset(StandardCharset.UTF_8.name());
		httpPostWrapper.setTextBody(param);
		
		final List<BinaryBody> binaryBody = new ArrayList<>(1);
		binaryBody.add(new BinaryBody("file", param.get("fileName"), filebyte, mimeType));
		httpPostWrapper.setBinaryBody(binaryBody);
		
		return HttpTransceiver.doPost(httpPostWrapper);
	}

	/**
	 * 影像上传成功通知接口 User: lpengfei Date: 2018-01-15 Version: 1.0
	 */
	public static HttpResponseWrapper imageDoPost(final String reqBody, final String serverURL,boolean urlEncodeEnabled, boolean urlDecodeEnabled) {
		final Map<String, String> headers = new HashMap<>(4);
		final HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
		headers.put("Content-type", "application/json;charset=" + StandardCharset.UTF_8.name());
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "text/json");
		headers.put("Connection", "keep-alive");
		httpPostWrapper.setServerUrl(serverURL);
		httpPostWrapper.setMimeType("text/json");
		httpPostWrapper.setRawBody(reqBody);
		httpPostWrapper.setUrlEncodeEnabled(urlEncodeEnabled);
		httpPostWrapper.setUrlDecodeEnabled(urlDecodeEnabled);
		httpPostWrapper.setHeaders(headers);
		return HttpTransceiver.doPost(httpPostWrapper);
	}
	
	/**
	 * 影像下载
	 * <p>User: lshuang
	 * <p>Date: 2018年5月2日
	 * <p>Version: 1.0
	 * @param reqBody
	 * @param serverURL
	 * @param urlEncodeEnabled
	 * @param urlDecodeEnabled
	 * @param receiveInputStreamEnabled
	 * @return
	 */
	public static HttpResponseWrapper imageDownloadPost(final String reqBody, final String serverURL,boolean urlEncodeEnabled, boolean urlDecodeEnabled, boolean receiveInputStreamEnabled) {
		final Map<String, String> headers = new HashMap<>(4);
		final HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
		headers.put("Content-type", "application/json;charset=" + "UTF-8");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "text/json");
		headers.put("Connection", "keep-alive");
		httpPostWrapper.setServerUrl(serverURL);
		httpPostWrapper.setMimeType("text/json");
		httpPostWrapper.setRawBody(reqBody);
		httpPostWrapper.setUrlEncodeEnabled(urlEncodeEnabled);
		httpPostWrapper.setUrlDecodeEnabled(urlDecodeEnabled);
		httpPostWrapper.setReceiveInputStreamEnabled(receiveInputStreamEnabled);
		httpPostWrapper.setHeaders(headers);
		final HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(httpPostWrapper);
		return httpResponseWrapper;
	}
	
}
