var reqUrl="";

var camp_context= {
		ctx:'..',
		sfix:'.do',
		preConst: '',
		ver:'20160222'
};
var AJAX_TIMEOUT = 15000;//15秒

 
  
var camp_base = (function($) {


var pluses = /\+/g;
function encode(s) {
	return config.raw ? s : encodeURIComponent(s)
}
function decode(s) {
	return config.raw ? s : decodeURIComponent(s)
}
function stringifyCookieValue(value) {
	return encode(config.json ? JSON.stringify(value) : String(value))
}
function parseCookieValue(s) {
	if (s.indexOf('"') === 0) {
		s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\')
	}
	try {
		s = decodeURIComponent(s.replace(pluses, ' '));
		return config.json ? JSON.parse(s) : s
	} catch (e) {
	}
}
function read(s, converter) {
	var value = config.raw ? s : parseCookieValue(s);
	return $.isFunction(converter) ? converter(value) : value
}
var config = $.cookie = function(key, value, options) {
	if (value !== undefined && !$.isFunction(value)) {
		options = $.extend({}, config.defaults, options);
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setTime(+t + days * 864e+5)
		}
		return (document.cookie = [
		                           encode(key),
		                           '=',
		                           stringifyCookieValue(value),
		                           options.expires ? '; expires=' + options.expires.toUTCString() : '',
		                           options.path ? '; path=' + options.path : '',
		                           options.domain ? '; domain=' + options.domain : '',
		                           options.secure ? '; secure' : '' ].join(''))
	}
	var result = key ? undefined : {};
	var cookies = document.cookie ? document.cookie.split('; ') : [];
	for ( var i = 0, l = cookies.length; i < l; i++) {
		var parts = cookies[i].split('=');
		var name = decode(parts.shift());
		var cookie = parts.join('=');
		if (key && key === name) {
			result = read(cookie, value);
			break;
		}
		if (!key && (cookie = read(cookie)) !== undefined) {
			result[name] = cookie
		}
	}
	return result;
};
config.defaults = {};
$.removeCookie = function(key, options) {
	if ($.cookie(key) === undefined) {
		return false;
	}
	$.cookie(key, '', $.extend({}, options, {
		expires : -1
	}));
	return !$.cookie(key);
};
var useAgent = false;//是否使用代理 true or false
var camp_context = window.camp_context;

var camp_wap = window.camp_wap ? window.camp_wap : {
	baseUrl:camp_context.preConst,
	/**
	 * 获取url参数值
     * @method getUrlParam
     * @param {String} paramName 参数名
     * @return {String} 参数值
	 */
	getUrlParam : function(paramName) {
		var href = PX_CURRENT_URL;
		var url = decodeURI(href);
		var idx = url.indexOf("?");
		var params = url.substring(idx + 1);
		if (params) {
			params = params.split("&");
			for (var i = 0; i < params.length; i+=1) {
				var param = params[i].split("=");
				if (param[0] == paramName) {
					//修改  完善获取url参数的逻辑
					var pArr = [];
					for(var k = 1,len = param.length;k<len;k++){
						pArr.push(param[k]);
					}
					var p = pArr.join('=');
					var idx1 = p.indexOf("#");
					if(idx1 !=-1){
						p = p.substring(0,idx1);
					}
					return p;
				}
			}
		}
	},
	/**
     * 数字千分位格式化
     * @method formatMoney
     * @param {number} mVal 数值
     * @param {Number} iAccuracy 小数位精度(默认为2)
     * @return {String}
     */
	formatMoney : function(mVal, iAccuracy){
        var fTmp = 0.00;//临时变量
        var iFra = 0;//小数部分
        var iInt = 0;//整数部分
        var aBuf = new Array(); //输出缓存
        var bPositive = true; //保存正负值标记(true:正数)
        /**
         * 输出定长字符串，不够补0
         * @param int iVal 值
         * @param int iLen 输出的长度
         */
        function funZero(iVal, iLen){
            var sTmp = iVal.toString();
            var sBuf = new Array();
            for(var i=0,iLoop=iLen-sTmp.length; i<iLoop; i++)
                sBuf.push('0');
            sBuf.push(sTmp);
            return sBuf.join('');
        };

        if (typeof(iAccuracy) === 'undefined')
            iAccuracy = 2;
        bPositive = (mVal >= 0);//取出正负号
        fTmp = (isNaN(fTmp = parseFloat(mVal))) ? 0 : Math.abs(fTmp);//强制转换为绝对值数浮点
        //所有内容用正数规则处理
        iInt = parseInt(fTmp,10); //分离整数部分
        iFra = parseInt((fTmp - iInt) * Math.pow(10,iAccuracy) + 0.5,10); //分离小数部分(四舍五入)

        do{
            aBuf.unshift(funZero(iInt % 1000, 3));
        }while((iInt = parseInt(iInt/1000,10)));
        aBuf[0] = parseInt(aBuf[0],10).toString();//最高段区去掉前导0
        return ((bPositive)?'':'-') + aBuf.join(',') +(iAccuracy === 0?'':'.')+ ((0 === iFra)?(function(){
        	var zeroArr = [];
        	for(var i = 0;i<iAccuracy?iAccuracy:0;i++){
        		zeroArr.push('0');
        	}
        	return zeroArr.join('');
        }()):funZero(iFra, iAccuracy));
    },
	getCode : function(e) {
		if (!e)
			var e = window.event;
		if (e.keyCode)
			return e.keyCode;
		else if (e.which)	//FF
			return e.which
	},
	
	jsonAjax : function(param) {
		
		var url = $.baseUrl+param.url;//$.baseUrl+"?action=" + param.url;// 临时打桩接口
		//url += "?" + getPubUrlParam();
		
		var ajaxType = "POST";
		var dataType = "json";
		var asyncType = (typeof(param.asyncType) != "undefined" && null != param.asyncType) ? param.asyncType : true;
		if (param.dataType)
			dataType = param.dataType;
		var sdata = JSON.stringify(param.data||{});
		//console.log("send:"+JSON.stringify(param.data||{}));
//		var postData = {_post_data_:base64encodefun(JSON.stringify(param.data||{}))};
		var postData =param.data||{};
		if(useAgent){
			postData.url = param.url;
		}
		var ajax = $.ajax({
					url :  useAgent?$.baseUrl:url,//$.baseUrl, 
					data : postData,//{_post_data_:postData,url:param.url},
					async : asyncType,
					cache:false,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",  	
					type : param.type || ajaxType,
					dataType : dataType,
					timeout: AJAX_TIMEOUT,
					success : function(json,status) {
//						var sdata = base64decodefun(json._post_data_);
						//json = eval("("+json+")");
						param.fn(json,json.sysStatus);
					},
					error : function(e) {
						 var msg = "";
						 if(e.status == 401){
							 msg = "您没有权限访问该页面，\n请先登录后在进行访问！";
							 alert(msg);
						 }else{
							 if((e.status+"") == "500" ){
								 msg = "系统繁忙，请稍后再试！";
								 alert(msg);
							 }else{
								 msg = "系统繁忙，请稍后再试！";
								 alert(msg);									 
							 }
						 }
					}
				});
		return ajax;
	}
};

jQuery.extend(camp_wap);

})(jQuery);
