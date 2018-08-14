Array.prototype.remove=
	function(a){
		if(isNaN(a)||a>this.length){
			return false;
		}
		for(var b=0,c=0;b<this.length;b++){
			if(this[b]!=this[a]){
				this[c++]=this[b];
			}
		}
		this.length-=1;
	};
	
	(function(f){
		if(f.browser){
			return;
		}
		f.browser={};
		f.browser.mozilla=false;
		f.browser.webkit=false;
		f.browser.opera=false;
		f.browser.msie=false;
		var d=navigator.userAgent;
		f.browser.name=navigator.appName;
		f.browser.fullVersion=""+parseFloat(navigator.appVersion);
		f.browser.majorVersion=parseInt(navigator.appVersion,10);
		var a,c,b;
		if((c=d.indexOf("Opera"))!=-1){
			f.browser.opera=true;
			f.browser.name="Opera";
			f.browser.fullVersion=d.substring(c+6);
			if((c=d.indexOf("Version"))!=-1){
				f.browser.fullVersion=d.substring(c+8);
			}
		}else{
			if((c=d.indexOf("MSIE"))!=-1){
				f.browser.msie=true;
				f.browser.name="Microsoft Internet Explorer";
				f.browser.fullVersion=d.substring(c+5);
			}else{
				if((c=d.indexOf("Chrome"))!=-1){
					f.browser.webkit=true;
					f.browser.name="Chrome";
					f.browser.fullVersion=d.substring(c+7);
				}else{
					if((c=d.indexOf("Safari"))!=-1){
					f.browser.webkit=true;
					f.browser.name="Safari";
					f.browser.fullVersion=d.substring(c+7);
					if((c=d.indexOf("Version"))!=-1){
						f.browser.fullVersion=d.substring(c+8);
					}
				}else{
					if((c=d.indexOf("Firefox"))!=-1){
						f.browser.mozilla=true;
						f.browser.name="Firefox";
						f.browser.fullVersion=d.substring(c+8);
					}else{
						if((c=d.indexOf("Mozilla"))!=-1){
							if(d.indexOf("Trident")!=-1){
								f.browser.msie=true;
								f.browser.name="Microsoft Internet Explorer";
								var g=d.indexOf("rv:");
								var e=d.indexOf(")",g);
								f.browser.fullVersion=d.substring(g+3,e);
							}else{
								f.browser.mozilla=true;
								f.browser.name="Mozilla";
								f.browser.fullVersion=d.substring(c+8);
							}
						}else{
							if((a=d.lastIndexOf(" ")+1)<(c=d.lastIndexOf("/"))){
								f.browser.name=d.substring(a,c);
								f.browser.fullVersion=d.substring(c+1);
								if(f.browser.name.toLowerCase()==f.browser.name.toUpperCase()){
									f.browser.name=navigator.appName;
								}
							}
						}
					}
				}
			}
		}
	}
		
	if((b=f.browser.fullVersion.indexOf(";"))!=-1){
		f.browser.fullVersion=f.browser.fullVersion.substring(0,b);
	}
	if((b=f.browser.fullVersion.indexOf(" "))!=-1){
		f.browser.fullVersion=f.browser.fullVersion.substring(0,b);
	}
	f.browser.majorVersion=parseInt(""+f.browser.fullVersion,10);
	if(isNaN(f.browser.majorVersion)){
		f.browser.fullVersion=""+parseFloat(navigator.appVersion);
		f.browser.majorVersion=parseInt(navigator.appVersion,10);
	}
	f.browser.version=f.browser.majorVersion;
})(jQuery);
	
	(function(c,e){
		var b="multi-iframe-tabs-";
		var a=b+"header-";
		var g=b+"content-";
		var f=b+"iframe-";
		var d=function(h){
			this.options=h||{};
			this.activeTabId=null;
			this.tabIds=[];
		};
		d.prototype.getActiveTabId=function(){
			return this.activeTabId;
		};
		d.prototype.getTabIndex=function(h){
			if(typeof h=="number"){
				return this.tabIds[h];
			}else{
				return this.tabIds.indexOf(h);
			}
		};
		d.prototype.activate=function(h){
			if(!h){
				this.activeTabId=null;return;
			}
		e("#multi-iframe-tabs").find("li.active").removeClass("active");
		e("#multi-iframe-tabs").find("div.active").removeClass("active");
		var i=e("#"+a+h);var k=e("#"+g+h);
		i.addClass("active");
		k.addClass("active");
		k.addClass("in");
		this.activeTabId=h;
		this.resetIframeHeight(h);
		if(h!=="0000"){
			var j=f+h;
			var l=e("#"+j)[0].contentWindow;
			if(l&&typeof l.refreshPage==="function"){
				l.refreshPage();
			}
		}
	};
	d.prototype.add=function(k){
		var j=k.tabId;
		var i=k.name;
		var l=k.className||"fa fa-cog";
		if(this.getTabIndex(j)>=0){
			this.activate(j);
			return;
		}
		var m='<li id="'+a+j+'"><a href="#'+g+j+'" role="tab" data-toggle="tab" hidefocus="true"><i class="'+l+'"></i> '+i+'</a><span class="tab-close" tabId = "'+j+'"><i class="fa fa-remove"></i></span></li>';
		var h='<div class="tab-pane fade" min-height="1024px" id="'+g+j+'">'+k.content+"</div>";
		e("#multi-iframe-tabs ul").append(m);
		e("#multi-iframe-tabs .tab-content").append(h);
		this.tabIds.push(j);this.activate(j);
	};
	d.prototype.close=function(i){
		if(!i){
			return;
		}
		var h=this.getTabIndex(i);
		if(h<0){
			h=0;
		}
		var j=e("#"+a+i);
		var k=e("#"+g+i);
		if(this.tabIds.length){
			if(i==this.getActiveTabId()){
				this.activate(this.tabIds[h-1]||this.tabIds[h]);
			}
		}else{
			this.activate("0000");
		}
		this.tabIds.remove(h);
		j.remove();
		k.remove();
	};
	d.prototype.resetIframeHeight=function(j){
		if(!j){
			for(var k=0;k<this.tabIds.length;k++){
				this.resetIframeHeight(this.tabIds[k]);
			}
			return;
		}
		var l=e(window).height()-e(".top-bar").outerHeight()-e(".nav-tabs").outerHeight();
		var h=e("#"+g+j);
		h.height(""+l+"px");
	};
	d.prototype.open=function(j){
		var k={};
		if(j&&typeof j=="string"){
			k.url=j;
		}else{
			k=j||{};
		}
		var i=k.tabId=k.tabId||this.getRandom(1000,9999);
		var l=k.name=k.name||i;
		var h=k.url=k.url||"";
		k.content='<iframe id="multi-iframe-tabs-iframe-'+i+'" name="'+l+'" frameborder="0" scrolling="auto" src="'+h+'" width="100%" height="100%"></iframe>';
		this.add(k);
	};
	d.prototype.getRandom=function(j,h){
		var i=h-j;
		return(j+Math.round(Math.random()*i))+"";
	};
	c.multiIframeTabs=new d();
	e("#multi-iframe-tabs").on("click","li>span.tab-close",function(){
		c.multiIframeTabs.close(e(this).attr("tabId"));
	});
	e("#multi-iframe-tabs").on("click","ul > li",function(){
		var i=e(this).attr("id");
		var l=i.split("-");
		if(!l.length){
			return;
		}
		var j=f+l[l.length-1];
		var h=e("#"+j)[0];
		if(h){
			var k=h.contentWindow;
			if(k&&typeof k.refreshPage==="function"){
				k.refreshPage();
			}
		}
	});
})(this,jQuery);
	
$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
$(".multi-iframe-container>.tab-content>div").css({"overflow-x":"auto","overflow-y":"auto"});
if($.browser.msie){
	$("html").addClass("ie ie"+$.browser.version);
};