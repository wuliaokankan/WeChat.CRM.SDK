<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台登陆</title>
<#include "/common/common.ftl">
<style type="text/css">
.background_main{
 background-color: rgba(255, 255, 255, 0.5);
 border-radius:15px;
 -moz-border-radius:15px;
}
</style>
</head>
<body style="background-image:url('../img/bg_login.png')" >
<div   style="padding: 80px"></div>
<div class="container" >
				<div class="col-md-12"> 
			        <div class="col-md-4"> </div>
			       
			        <div class="col-md-4"> 
			           <div class="panel panel-default background_main"  >
						  <div class="panel-body background_main"  >
								<form class="form-signin" action="/admin/login"  method="post"  id="form1" name="form1">
									<h2 align="center" style="color:#428bca;">管理后台登陆</h2>
									<label   class="sr-only">账号</label> 
									 <div class="input-group-lg" style="padding-bottom: 8px">
									  <input type="text"  class="form-control" id="pname"  name="pname"   required autofocus> 
									 </div>
									 
									<div class="input-group-lg" style="padding-bottom: 4px">
									  <input type="password"  id="password" name="password" class="form-control"  required>
									 </div>
									<div align="center" style="padding: 6px">
										<label ><font color='red' id="flabel">${error!}</font></label>
									</div>
									<button class="btn btn-lg btn-primary btn-block" onclick="javascript:formsubmit()" type="button">登陆</button>
								</form>
						    </div>
						</div>
					</div>
					 <div class="col-md-4"> </div>
			    </div>
	</div>
 <script type="text/javascript">
  function  formsubmit(){
     if($('#pname').val() && $('#password').val()){
	    $('#form1').submit();
     }else{
         $("#flabel").html("账号或密码不能为空!");
     }
  }
  
  $("body").bind('keyup',function(event) {  
    if(event.keyCode==13){  
        formsubmit();
    }     
  }); 
 </script>
</body>
</html>