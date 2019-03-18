<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>密码修改</title>
<!--公共的js与css文件-->
<#include "/common/common.ftl">
</head>
<body>
	<#include "/admin/common/top.ftl">
	<!-- 要于顶部有距离-->
    <div class="projects-header page-header" style="margin: 30px 0 22px;" > </div>
	<div class="container-fluid">
       <div class="row">
	        <#include "/admin/common/leftmenu.ftl">
            <div class="col-sm-9 col-md-10  main">
          <ol class="breadcrumb" style="margin-top: 3px;margin-bottom: 10px">
	      <li class="glyphicon glyphicon-home" aria-hidden="true"></li>
		  <li class="active">手机设备信息维护</li>
	  </ol>
	<div class="row">
	 
  <div class="projects-header page-header" style="margin: 3px 0 6px;"></div>
  
<div class="panel panel-default">
<div class="panel-body">
   <div style="padding: 8px;margin: 8px" class="row">
								 
		<form novalidate="novalidate" method="post"  id="form" role="form" class="order-form">
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 新密码</span></div>
					   <div class="col-md-5">
					     <input type="password" id="newpwd"  name="newpwd" aria-describedby="basic-addon1" placeholder="填写新密码" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div> 
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 旧密码</span></div>
					   <div class="col-md-5">
					     <input type="password"  id="oldpwd" name="oldpwd" aria-describedby="basic-addon1" placeholder="填写旧密码" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			   <div class="col-md-12"  style="height:50px">
			            <div class="col-md-4"></div>
			            <div class="col-md-4" id="tip"></div>
			            <div class="col-md-4"></div>
			   </div>
			     
			   <div style="padding: 8px;padding-bottom: 10px;margin: 8px">
			        <div style="text-align: center;" class="col-md-12">
			           <button   type="button" onclick="submitForm()" class="btn btn-success">提交表单</button>
				    </div>
			   </div>
			   
		</form>
	</div>
     
</div></div>
 
 <script>
 function submitForm(){ 
     if($('#newpwd').val() && $('#oldpwd').val() && $('#newpwd').val() != $('#oldpwd').val()){
		 var  targetUrl = "/admin/pwdedit"; 
		 var data1 = $("#form").serialize(); 
	     $.ajax({
		    type:'post',  
		    cache:false, 
		    url:targetUrl, 
		    data:data1,  
		    success:function(res){  
		       if(res == 'success'){
		           $("#tip").html("<font color='green'>修改成功!</font>");
		           location.href="/exit";
		       }else if(res == 'fail'){
		           $("#tip").html("<font color='red'>旧密码错误!</font>");
		       }else{
		           $("#tip").html("<font color='red'>修改失败!</font>");
		       }
		    },   
	        error : function() {   
	            alert("异常！");   
	        }  
	     })
	 }else{
	    $("#tip").html("<font color='red'>新旧密码不能为空且不能相同!</font>");
	 }
     
 }
 </script>
</body>
</html>