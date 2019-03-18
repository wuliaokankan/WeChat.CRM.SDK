$(function () {
    $(function () {
        $.scrollUp({
            animation: 'fade',
            activeOverlay: '#00FFFF',
            scrollImg: {
                active: true,
                type: 'background',
                src: '../img/top.png'
            }
        });
    });
});


function customSearch() {
	// 获取当前搜索框得值
	var q = $("#q").val();
	if(q){
		// 得到navbarSelect
		var navbarSelect = $("#navbarSelect").val();
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		
		var url="";
		//根据navbarSelect跳转到不同的方法进行搜索
		if ('product' == navbarSelect) {
			url = result + "/product-page-info.html?keyword=" + q;
		} else if ('channel' == navbarSelect) {
			url = result + "/channel-page-info.html?keyword=" + q;
		} else if ('llleyway' == navbarSelect) {
			url = result + "/calleyway-page-info.html?keyword=" + q;
		} else if ('help' == navbarSelect) {
			url = result + "/help-page-info.html?keyword=" + q;
		} else {
			url = result + "/product-page-info.html?keyword=" + q;
		}
	    location.href = url;
	}else{
		alert("请输入您要搜索得内容");
		return ;
	}
}

function getImg(){
	// 得到项目根路径
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	
	var imgurl=result + "/public/imgauth.do?id="+Math.random(2000);
	 $('#imgCode').attr('src',imgurl);
}
   function login(){
	   var reqUrl=camp_context.preConst+"login.do";
	   var loginName=$('#modal-login-name').val();
	   var loginPwd=$('#modal-login-pwd').val();
	   var authCode=$('#modal-login-captcha').val();

       if(convert_name() && convert_pass() &&check_code()){
    	   $.jsonAjax({
				url:"login.do",
				type: "post", 
				data:{
					'loginName':loginName,
				    'loginPwd':hex_sha1(loginPwd),
				    'authCode':authCode
					},
				fn:function(result,status){
					if("1"==status){
						$('#modalLogin').modal('hide');
						window.location.reload();
					}else{
						$("#modalLoginErrCont").html(result.resMsg);
					}
				}
		   });
       }
	   
   }
   
    /**
	 * 用户登陆验证
	 */
	function convert_name(){
		var user = $('#modal-login-name').val().replace(/(^\s*)|(\s*$)/g, "");
		var rt = true;
		if(user.length >= 2){

		} else {
			rt = false;
			$("#modalLoginErrCont").html("昵称不能为空且长度不能少于两位!");
		}
		return rt;
	}
	
	function convert_pass(){
		var pass = $('#modal-login-pwd').val();
		var rt = true;
		if(pass.length > 0){
			rt = true;
		}
		else{
			rt = false;
			$("#modalLoginErrCont").html("请输入密码!");
		}
		return rt;
	}
	
	function check_code(){
		var code = $('#modal-login-captcha').val();
		var rt = true;
		if(code.length > 0){
			rt = true;
		}else{
			rt = false;
			$("#modalLoginErrCont").html("请输入验证码!");
		}
		return rt;
	}
	
	
	function getImgReg(){
		
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		
		var imgurl = result+"/public/imgauth.do?id="+Math.random(2000);
		 $('#imgCodeReg').attr('src',imgurl);
	}
	function regUser(){
	   var regName=$('#modal-reg-name').val();
	   var regPwd=$('#modal-reg-pwd').val();
	   var authCode=$('#modal-reg-captcha1').val();
	   if(convert_name_reg() && convert_pass_reg() &&check_code_reg()){
    	   $.jsonAjax({
				url:"regist.do",
				type: "post", 
				data:{
					'regName':regName,
				    'regPwd':hex_sha1(regPwd),
				    'authCode':authCode
					},
				fn:function(result,status){
					if("1"==status){
						$('#modalReg').modal('hide');
						window.location.reload();
					}else{
						$("#modalRegErrCont").html(result.resMsg);
					}
				}
		   });
       }
	}
	
	/**
	 * 用户注册验证
	 */
	function convert_name_reg(){
		var user = $('#modal-reg-name').val().replace(/(^\s*)|(\s*$)/g, "");
		var rt = true;
		if(user.length >= 2){
			if(user.length >7){
				rt = false;
				$("#modalRegErrCont").html("昵称不能为空且长度不能超过7位!");
			}
		
		} else {
			rt = false;
			$("#modalRegErrCont").html("昵称不能为空且长度不能少于两位!");
		}
		return rt;
	}
	
	function convert_pass_reg(){
		var pass = $('#modal-reg-pwd').val();
		var rt = true;
		if(pass.length >= 6){
			rt = true;
		}
		else{
			rt = false;
			$("#modalRegErrCont").html("密码长度不能少于6位!");
		}
		return rt;
	}
	
	function check_code_reg(){
		var code = $('#modal-reg-captcha1').val();
		var rt = true;
		if(code.length > 0){
			rt = true;
		}else{
			rt = false;
			$("#modalRegErrCont").html("请输入验证码!");
		}
		return rt;
	}
	
	/**
	 * 拷贝字符串  
	 * @param str  字符串
	 * @param id   copy-link-wrap 的id
	 */
	 function copyText(str,id){
		// 得到项目根路径
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0, index + 1);
		 $("#copy-link-wrap"+id).zclip({
			    path: result+'/js/jqueryzclip/ZeroClipboard.swf',
			    copy: "http://www.5ityx.com/"+str+"?分享来自'我爱推游戏'-单机手游免费推广平台(http://www.5ityx.com)",
			    afterCopy: function() {
			      alert('复制成功');
			    }
		 });
	 }
	 
	 
	 function checkUserLogin(){
		 $.jsonAjax({
				url:"userCheck.do",
				data:{},
				fn:function(result,status){
					if("200"==status){
						// 得到项目根路径
						var pathName = document.location.pathname;
						var index = pathName.substr(1).indexOf("/");
						var result = pathName.substr(0, index + 1);
						location.href=result+"/usercenter.html";
					}else{
						alert("您还未登陆，请先登陆系统！");
					}
				}
		});
		
	}
