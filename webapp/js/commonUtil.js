/**
 * 统计字符串长度
 * @param str
 * @returns {Number}
 */
function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
	     var c = str.charCodeAt(i); 
	     //单字节加1 
	     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
	    	 len++; 
	     } else { 
	    	 len+=2; 
	     } 
    } 
    return len;
}

// eg:options={url:'login',name:'登录',tabId:'0000',className:'fa fa-key fa-fw'}
function openTab(options){
    window.parent.multiIframeTabs.open(options);
}
//关闭tab页
function closeTab(tabId){
    window.parent.multiIframeTabs.close(tabId);
}

/**
 * yyyy-MM-dd 10位日期比较
 * @param DateOne10
 * @param DateTwo10
 * @returns {Boolean}
 */
function compareDate(DateOne10, DateTwo10) {

	var OneYear = DateOne10.substring(0, DateOne10.indexOf("-"));
	var OneMonth = DateOne10.substring(5, DateOne10.lastIndexOf("-"));
	var OneDay = DateOne10.substring(DateOne10.length,
			DateOne10.lastIndexOf("-") + 1);
	
	var TwoYear = DateTwo10.substring(0, DateTwo10.indexOf("-"));
	var TwoMonth = DateTwo10.substring(5, DateTwo10.lastIndexOf("-"));
	var TwoDay = DateTwo10.substring(DateTwo10.length,
			DateTwo10.lastIndexOf("-") + 1);
	//时间格式必须是 MM/dd/yyyy
	if (Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) > Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) {
		return true;
	} else {
		return false;
	}
}

/**
 * yyyyMMdd 8位日期格式比较
 * @param DateOne8
 * @param DateTwo8
 * @returns {Boolean}
 */
function compareDate8(DateOne8, DateTwo8) {

	var OneYear = DateOne8.substring(0, 4);
	var OneMonth = DateOne8.substring(4, 6);
	var OneDay = DateOne8.substring(6,8);
	
	var TwoYear = DateTwo8.substring(0, 4);
	var TwoMonth = DateTwo8.substring(4, 6);
	var TwoDay = DateTwo8.substring(6, 8);
	//时间格式必须是 MM/dd/yyyy
	if (Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) > Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) {
		return true;
	} else {
		return false;
	}
}

//定义format方法
Date.prototype.format = function(format) {
   var o = {
       "M+": this.getMonth() + 1,
       // month
       "d+": this.getDate(),
       // day
       "h+": this.getHours(),
       // hour
       "m+": this.getMinutes(),
       // minute
       "s+": this.getSeconds(),
       // second
       "q+": Math.floor((this.getMonth() + 3) / 3),
       // quarter
       "S": this.getMilliseconds()
       // millisecond
   };
   if (/(y+)/.test(format) || /(Y+)/.test(format)) {
       format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
   }
   for (var k in o) {
       if (new RegExp("(" + k + ")").test(format)) {
           format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
       }
   }
   return format;
};
//返回Date(Mon Dec 14 08:00:00 CST 2015 => 2015-12-14)或TIMESTAMP（1450051200000 => 2015-12-14 ）的时候格式化显示
function getFormatDate(formatDate , format){
	var formatPattern = format;
	if(typeof(formatPattern) == "undefined"){
		formatPattern = "yyyy-MM-dd" ;
	}
	if(!isNaN(formatDate) && formatDate){
		return (new Date(parseFloat(formatDate))).format(formatPattern);
	}else{
		return (new Date(formatDate)).format(formatPattern);
	}
}

//显示附件
function showFilesList(operType , dataList , showDownloadFlag , showDeleteFlag){
	var preList = "<div class='clearfix'>" ; 
    for ( var i = 0; i < dataList.length; i++) {  
       var array_element = dataList[i];  
       var tmp =  "<div class=\"col-lg-3 col-md-3\" id=\"file_" + array_element.attachmentId + "\">" +
       
       	"<div class=\"widget\"><div class=\"widget-head clearfix\"><div class=\"pull-left\"> <span class='file-icon-2x'>" ;
		
       if(array_element.attachmentName.indexOf("txt")>0){// txt类型的展示  
    	   tmp += "<i class='fa fa-file-text-o text-info'></i>" ;
       }else if(array_element.attachmentName.indexOf("docx")>0 || 
    		   array_element.attachmentName.indexOf("doc")>0 ){  
    	   tmp += 	"<i class='fa fa-file-word-o text-info'></i>" ;
       }else if(array_element.attachmentName.indexOf("xlsx")>0 ||
    		   array_element.attachmentName.indexOf("xls")>0 ){  
    	   tmp += 	"<i class='fa fa-file-excel-o text-info'></i>" ;
       }else if(array_element.attachmentName.indexOf("pptx")>0 ||
    		   array_element.attachmentName.indexOf("ppt")>0 ){  
    	   tmp += 	"<i class='fa fa-file-powerpoint-o text-info'></i>" ;
       }else if(array_element.attachmentName.indexOf("pdf")>0 ){  
    	   tmp += 	"<i class='fa fa-file-pdf-o text-info'></i>" ;
       }else{  
    	   tmp += 	"<i class='fa fa-file-photo-o text-info'></i>" ;
       }  
       
       tmp += array_element.attachmentName + "</span></div></div>";
       tmp += "<div class=\"widget-content clearfix\">" ;
       tmp += "<div class=\"form-group\">";
       if(showDownloadFlag){
    	   tmp += "<div class=\"col-lg-4 col-md-4\">";
           tmp += "<a onclick=\"downLoadFile('" + array_element.attachmentId + "');\" class=\"btn btn-primary\"><i class='fa fa-cloud-download'></i>下载</a>";
           tmp += "</div> " ;
       }
       if(showDeleteFlag){
    	   tmp += "<div class=\"col-lg-4 col-md-4\">";
           tmp += "	<a onclick=\"deleteFile('" + array_element.attachmentId + "');\" class=\"btn btn-danger\"><i class='fa fa-trash'></i>删除</a>";
           tmp += "	</div> " ;
       }
       tmp += "</div> </div> </div> </div>";
       preList += tmp;
    }  
    preList += "</div>";
    //查看时隐藏上传附件
    if(operType.toLowerCase() == 'view'){
    	$("#showFilesDiv").prepend(preList );
    }else{
    	$(".file-preview ").prepend(preList );
    }
    
}
//下载附件
function downLoadFile(attachmentId){
	//校验文件是否存在
	RestfulClient.post(contextPath + "/attachment/fileExists", {
		"extend":{
			"attachmentId" : attachmentId
		}
	}, function(data) {
        if(data.successFlag){
        	//下载附件
        	window.location.href= contextPath + "/attachment/download?attachmentId=" + attachmentId ;
        }else{
        	alert(data.msg );
        }
    } );
	
}
//删除附件
function deleteFile(attachmentId){
	RestfulClient.post(contextPath + "/attachment/delete", {
		"extend":{
			"attachmentId" : attachmentId
		}
	}, function(data) {
        if(data.successFlag){
        	//移除页面显示的文件
        	$("div[id='file_" + attachmentId + "']").remove();
        }
    } );
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};
//验证身份证
function checkIdentifyCard(certNo) { 
    var length = certNo.length;
    var reg;
    var checkResult = true;
    if (length != 15 && length != 18) {
        checkResult = false;
    } else {
        if (length == 15) {
            reg = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/gi);
        } else {
            reg = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})(\d|x)$/gi);
        }
        var matchResult = certNo.match(reg);
        if (matchResult == null) {
            checkResult = false;
        } else{
        	checkResult = checkIdcard(certNo);
        }
    }
    return checkResult;
}  
//验证身份证
function checkIdcard(idcard) {
	var area = {
		11 : "11",
		12 : "12",
		13 : "13",
		14 : "14",
		15 : "15",
		21 : "21",
		22 : "22",
		23 : "23",
		31 : "31",
		32 : "32",
		33 : "33",
		34 : "34",
		35 : "35",
		36 : "36",
		37 : "37",
		41 : "41",
		42 : "42",
		43 : "43",
		44 : "44",
		45 : "45",
		46 : "46",
		50 : "50",
		51 : "51",
		52 : "52",
		53 : "53",
		54 : "54",
		61 : "61",
		62 : "62",
		63 : "63",
		64 : "64",
		65 : "65",
		71 : "71",
		81 : "81",
		82 : "82",
		91 : "91"
	};
	var idcard, Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	if (area[parseInt(idcard.substr(0, 2))] == null)
		return false;
	switch (idcard.length) {
	case 15:
		if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
				|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
						.substr(6, 2)) + 1900) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
		} else {
			ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
		}
		if (ereg.test(idcard))
			return true;
		else
			return false;
	case 18:
		if (parseInt(idcard.substr(6, 4)) % 4 == 0
				|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
						.substr(6, 4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
		} else {
			ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
		}
		if (ereg.test(idcard)) {
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
					* 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
					* 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
					* 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
					* 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
					* 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
					* 2 + parseInt(idcard_array[7]) * 1
					+ parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9])
					* 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1);
			if (M == idcard_array[17])
				return true;
			else
				return false;
		} else
			return false;
	default:
		return false;
	}
	return true;
}