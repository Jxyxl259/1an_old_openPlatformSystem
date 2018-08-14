//用户密码修改页面
function preModifyPassword(){
	var url = contextPath+'/user/preModifyPassword?tabId='+tabId;
	var tabName = '用户密码修改';
	openTab({url: url,name:tabName,tabId:tabId ,className:'fa fa-wrench'});
	
}

$(function(){
	   $.fn.serializeObject = function(){  
           var o = {};  
           var a = this.serializeArray();  
           $.each(a, function(){  
               if (o[this.name]) {  
                   if (!o[this.name].push) {  
                       o[this.name] = [o[this.name]];  
                   }  
                   o[this.name].push(this.value || '');  
               }  
               else {  
                   o[this.name] = this.value || '';  
               }  
           });  
           return o;  
       };
   
       
});

if (top !== self) {
    top.location.href = location.href;
}