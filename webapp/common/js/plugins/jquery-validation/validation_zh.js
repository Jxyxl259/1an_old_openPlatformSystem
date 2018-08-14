$.extend($.validator.messages, {
    required: "该字段必须录入",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的银行卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: $.validator.format("请输入一个最大为 {0} 的值"),
    min: $.validator.format("请输入一个最小为 {0} 的值")
});
//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

//电话号码验证 
jQuery.validator.addMethod("isTel", function(value, element) { 
  var tel = /^\d{3,4}-?\d{7,9}(-?\d{3})?$/; //电话号码格式010-12345678(-098)
  return this.optional(element) || (tel.test(value)); 
}, "请正确填写您的电话号码"); 
//邮政编码验证     
jQuery.validator.addMethod("isPostCode", function(value, element) {     
    var tel = /^[0-9]{6}$/;  
    return this.optional(element) || (tel.test(value));  
}, "请正确填写您的邮政编码");  

//0-400个字符
$.validator.addMethod("byteRangeLength", function(value, element, param) {
	var length = strlen(value);
	return this.optional(element) || ( length >= param[0]&&length <= param[1] );
}, "请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)");

/**
* 判断是否为“YYYYMMDD”式的时期
*
*/
$.validator.addMethod("isDate8", function(value, element, param) {
    if (!/^[0-9]{8}$/.test(value)) {
        return false;
    }
    var year, month, day;
    year = value.substring(0, 4);
    month = value.substring(4, 6);
    day = value.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}, "请输入合法日期格式yyyyMMdd") ;