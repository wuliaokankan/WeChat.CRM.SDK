<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>手机设备维护</title>
<!--公共的js与css文件-->
<#include "/common/common.ftl">
</head>
<body>
	<#include "/user/common/top.ftl">
	<!-- 要于顶部有距离-->
    <div class="projects-header page-header" style="margin: 30px 0 22px;" > </div>
	<div class="container-fluid">
       <div class="row">
	        <#include "/user/common/leftmenu.ftl">
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
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">* 设备ID</span></div>
					   <div class="col-md-5">
					     <input type="text" id="deviceid" value="${entity.deviceid!}" name="deviceid" aria-describedby="basic-addon1" placeholder="填写手机imei" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div> 
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
					   <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">&nbsp;&nbsp;手机别名</span></div>
					   <div class="col-md-5">
					     <input type="text" value="${entity.nickname!}" name="nickname" aria-describedby="basic-addon1" placeholder="填写手机名" class="form-control"> 
					   </div>
					   <div class="col-md-3"></div>
			   </div>
			    <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
                       <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">&nbsp;&nbsp;手机型号</span></div>
                       <div class="col-md-5">
                         <input type="text" value="${entity.module!}" name="module" aria-describedby="basic-addon1" placeholder="填写手机型号" class="form-control"> 
                       </div>
                       <div class="col-md-3"></div>
               </div>
               <div class="col-md-12" style="height:50px">
			           <div class="col-md-2"></div>
                       <div class="col-md-2"><span style="text-align: right;color: #666;  padding: 8px">&nbsp;&nbsp;手机品牌</span></div>
                       <div class="col-md-5">
                         <input type="text" value="${entity.brand!}" name="brand" aria-describedby="basic-addon1" placeholder="填写手机品牌" class="form-control"> 
                       </div>
                       <div class="col-md-3"></div>
               </div>
               <div class="col-md-12">
                        <input type="hidden" id="id" name="id" value="${entity.id!}"/>
                        <div class="col-md-4"></div>
			            <div class="col-md-4" id="flabel"></div>
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
     if($('#deviceid').val()){
		 var targetUrl ="";
		 if($('#id').val()){
	         targetUrl = "/user/phone/edit"; 
	     }else{
	         targetUrl = "/user/phone/add"; 
	     }
		 var data1 = $("#form").serialize(); 
	     $.ajax({
		    type:'post',  
		    cache:false, 
		    url:targetUrl, 
		    data:data1,  
		    success:function(res){  
		       if(res == 'success'){
		           location.href="/user/phone/list";
		       }else if(res == 'over'){
		          $("#flabel").html("<font color='red'>失败，绑定设备超过限制</font>");
		       }else{
		          $("#flabel").html("<font color='red'>失败，请检查imei是否重复</font>");
		       }
		    },   
	        error : function() {   
	            alert("异常！");   
	        }  
	     })
	 }else{
	    $("#flabel").html("<font color='red'>imei不能为空!</font>");
	 }
     
 }
 </script>
</body>
</html>